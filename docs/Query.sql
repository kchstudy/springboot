/* 공통 코드 조회 쿼리 */
select d.grp_cd, d.grp_nm, d.note, m.cd, m.cd_nm, m.cd_v, m.sq, m.note
  from           cd     m
       left join grp_cd d on m.grp_cd= d.grp_cd
 order by m.grp_cd, m.cd
;

/* postgresql synonym 대체 search_path 수정 */
-- 현재 search_path 확인
show search_path;
-- search_path 수정
set search_path TO [schema_nm], [schema_nm], ...;

/* checkParam 조회 쿼리 */
SELECT 'String prmString[] = {' || array_to_string(array_agg( concat('"',c.column_name, ':', pgd.description ,'"') ),',') || '};'
FROM   pg_catalog.pg_statio_all_tables as st
       inner join pg_catalog.pg_description  pgd on  (pgd.objoid=st.relid)
       inner join information_schema.columns c   on    ( pgd.objsubid=c.ordinal_position
                                                     and c.table_schema=st.schemaname
                                                     and c.table_name=st.relname
                                                       )
where 1=1
and   c.table_name   = 'cd'
;

/* 테이블 컬럼, 코멘트 조회 쿼리 */
with tbl_name as (
  select 'usr' as nm /* 테이블명 입력 */
),
tbl_info as (
  select c.relname as tbl_nm, obj_description(c.oid) as tbl_desc
    from pg_catalog.pg_class c inner join pg_catalog.pg_namespace n on c.relnamespace=n.oid
   where c.relkind = 'r'
     and relname = (select nm from tbl_name)
),
length_info as (
  select max(length(c.column_name)) as clen
    from information_schema.columns c
   where 1=1
     and c.table_name = (select nm from tbl_name)
),
col_info as (
  select '     , ' || c.column_name || repeat(' ', cast(len.clen as int) - length(c.column_name)) || ' as '
         || c.column_name || repeat(' ', cast(len.clen as int) - length(c.column_name)) || ' /* ' || c.udt_name || '-' || pgd.description || ' */' as str
    from pg_catalog.pg_statio_all_tables st
       , pg_catalog.pg_description       pgd
       , information_schema.columns      c
       , length_info                     len
   where 1=1
     and pgd.objoid     = st.relid
     and pgd.objsubid   = c.ordinal_position
     and c.table_schema = st.schemaname
     and c.table_name   = st.relname
     and c.table_name   = (select nm from tbl_name)
)
select concat(tbl_nm, ' /* ', tbl_desc ,' */') as str from tbl_info
union all
select str from col_info
;

/* 테이블 주석 셀렉트 */
select c.relname, obj_description(c.oid)
from pg_catalog.pg_class c inner join pg_catalog.pg_namespace n on c.relnamespace=n.oid
where c.relkind = 'r'
and nspname = 'public'
order by 2
;
and relname = '테이블명'
;

/* 쿼리 생성 */
with tbl_name as (
  select 'usr' as nm /* 테이블명 입력 */
        , '' as type /* 쿼리 타입 */
), /* 조회대상테이블 */
tbl_info as (
  select c.relname as tbl_name, obj_description(c.oid) as tbl_desc
    from pg_catalog.pg_class c left join pg_catalog.pg_namespace n on c.relnamespace=n.oid
   where c.relkind = 'r'
     and c.relname = (select nm from tbl_name)
),
col_info as (
  select c.table_name            as t_nm     /* 테이블명 */
        , c.column_name           as c_org_nm /* 원본 컬럼명 */
        , case when substring(c.column_name, 1, 4) = 'enc_' then concat( 'decrypt(', c.column_name ,')' )
                else c.column_name
                end              as c_s_nm /* 셀렉트 컬럼명 */
        , case when substring(c.column_name, 1, 4) = 'enc_' then concat( 'encrypt(#{', c.column_name ,'})' )
                when c.udt_name = 'numeric' then concat('cast(#{', c.column_name , '} as numeric)')
                else concat('#{', c.column_name, '}')
                end              as c_b_nm /* 바인딩 컬럼명 */
        , c.ordinal_position     as c_ord_sq
        , c.udt_name             as dt_type
        , pgd.description        as c_desc
        , length(c.column_name) as c_o_len  /* 원본 컬럼 길이 */
        , case when substring(c.column_name, 1, 4) = 'enc_' then length(concat( 'decrypt(', c.column_name ,')' ))
                else length(c.column_name)
                end              as c_s_len /* 셀렉트 컬럼 길이 */
        , case when substring(c.column_name, 1, 4) = 'enc_' then length(concat( 'encrypt(#{', c.column_name ,'})' ))
                when c.udt_name = 'numeric' then length(concat('cast(#{', c.column_name , '} as numeric)'))
                else length(concat('#{', c.column_name, '}'))
                end              as c_b_len /* 바인딩 컬럼길이 */
    from pg_catalog.pg_statio_all_tables st
       , pg_catalog.pg_description       pgd
       , information_schema.columns      c
   where 1=1
     and pgd.objoid     = st.relid
     and pgd.objsubid   = c.ordinal_position
     and c.table_schema = st.schemaname
     and c.table_name   = st.relname
     and c.table_name   = (select nm from tbl_name)
), /* 조회대상테이블 컬럼 길이 정보 */
col_max_length_info as (
  select (select nm from tbl_name)        as t_nm
       , max(c_o_len) as max_c_o_len /* 원본 컬럼 최대 길이 */
       , max(c_s_len) as max_c_s_len /* 셀렉트 컬럼 최대 길이 */
       , max(c_b_len) as max_c_b_len /* 바인딩 컬럼 최대 길이 */
    from col_info
), /* 조회대상테이블 컬럼 최대 길이 정보  */
sql_template as (
--  select * from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  select 'insert /*  */ ' as query_str, 1000 as ord_sq, 'insert' as type
  union all
  select concat('  into ', tbl_name, ' /* ', tbl_desc, ' */ (' ) as query_str, 1500 as order_sq, 'insert' as type from tbl_info
  union all
  select concat('     , ', m.c_org_nm, repeat(' ', d.max_c_b_len - m.c_o_len), ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 3000+m.c_ord_sq as ord_sq, 'insert' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  union all
  select ') values ( ' as query_str, 6000 as order_sq, 'insert' as type
  union all
  select concat('     , ', m.c_b_nm, repeat(' ', d.max_c_b_len - m.c_b_len), ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 7000+m.c_ord_sq as ord_sq, 'insert' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  union all
  select ') ' as query_str, 9000 as order_sq, 'insert' as type
  union all
  select 'select /*  */ ' as query_str, 1000 as ord_sq, 'select' as type
  union all
  select concat('     , ', m.c_s_nm, repeat(' ', d.max_c_b_len - m.c_s_len), ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 3000+m.c_ord_sq as ord_sq, 'select' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  union all
  select concat('  from ', tbl_name, ' /* ', tbl_desc, ' */' ) as query_str, 6000 as order_sq, 'select' as type from tbl_info
  union all
  select ' where 1=1 ' as query_str, 7000 as order_sq, 'select' as type from tbl_info
  union all
  select concat('   and ', m.c_org_nm, repeat(' ', d.max_c_o_len - m.c_o_len), ' = ', m.c_b_nm, repeat(' ', d.max_c_b_len - m.c_b_len) , ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 8000+m.c_ord_sq as ord_sq, 'select' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  union all
  select 'update /*  */ ' as query_str, 1000 as order_sq, 'update' as type from tbl_info
  union all
  select concat('       ', tbl_name, ' /* ', tbl_desc, ' */' ) as query_str, 1500 as order_sq, 'update' as type from tbl_info
  union all
  select '   set ' as query_str, 2000 as order_sq, 'update' as type
  union all
  select concat('     , ', m.c_org_nm, repeat(' ', d.max_c_o_len - m.c_o_len), ' = ', m.c_b_nm, repeat(' ', d.max_c_b_len - m.c_b_len), ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 3000+m.c_ord_sq as ord_sq, 'update' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  union all
  select ' where 1=1 ' as query_str, 5000 as order_sq, 'update' as type
  union all
  select concat('   and ', m.c_org_nm, repeat(' ', d.max_c_o_len - m.c_o_len), ' = ', m.c_b_nm, repeat(' ', d.max_c_b_len - m.c_b_len) , ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 8000+m.c_ord_sq as ord_sq, 'update' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
  union all
  select 'delete /* */ ' as query_str, 1000 as order_sq, 'delete' as type
  union all
  select concat('  from ', tbl_name, ' /* ', tbl_desc, ' */' ) as query_str, 1500 as order_sq, 'delete' as type from tbl_info
  union all
  select ' where 1=1 ' as query_str, 3000 as order_sq, 'delete' as type
  union all
  select concat('   and ', m.c_org_nm, repeat(' ', d.max_c_o_len - m.c_o_len), ' = ', m.c_b_nm, repeat(' ', d.max_c_b_len - m.c_b_len) , ' /* ', m.dt_type, '-', m.c_desc,' */') as query_str, 5000+m.c_ord_sq as ord_sq, 'delete' as type from col_info m join col_max_length_info d on m.t_nm = d.t_nm
) /* SQL_생성문 */
select query_str from sql_template
 where 1=1
  and type like '%' || (select type from tbl_name) || '%'
order by type, ord_sq
;
