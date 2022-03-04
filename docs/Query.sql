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
length_info as (
  select max(length(c.column_name)) as clen
    from information_schema.columns c
   where 1=1
     and c.table_name = (select nm from tbl_name)
),
col_info as (
  select '     , ' || c.column_name || repeat(' ', cast(len.clen as int) - length(c.column_name)) || ' as '
         || c.column_name || repeat(' ', cast(len.clen as int) - length(c.column_name)) || ' /* ' || c.udt_name || '-' || pgd.description || ' */'
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
select * from col_info
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
),
tbl_info as (
  select c.relname as tbl_name, obj_description(c.oid) as tbl_desc
    from pg_catalog.pg_class c left join pg_catalog.pg_namespace n on c.relnamespace=n.oid
   where c.relkind = 'r'
     and c.relname = (select nm from tbl_name)
),
length_info as (
  select max(length(c.column_name)) as clen
    from information_schema.columns c
   where 1=1
     and c.table_name = (select nm from tbl_name)
),
col_info as (
  select c.column_name           as c_nm
        , repeat(' ', cast(len.clen as int) - length(c.column_name)) as c_nm_term
        , c.ordinal_position      as c_ord_sq
        , c.udt_name              as dt_type
        , case when c.udt_name = 'numeric' then concat('cast( #{', c.column_name, '} as ', c.udt_name ,') ')
                when c.udt_name = 'timestamp' then 'now()'
                else concat('#{', c.column_name, '}')
                end as binding_str
        , pgd.description         as c_desc
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
),
sql_template as (
  select 'select /*  */ ' as query_str, 1000 as order_sq, 'select' as type
  union all
  select concat('     , ', c_nm, c_nm_term, ' as ', c_nm, c_nm_term, ' /* ', dt_type, '-', c_desc, ' */' ) as query_str, (3000 + c_ord_sq) as order_sq, 'select' as type from col_info
  union all
  select concat('  from ', tbl_name, ' /* ', tbl_desc, ' */' ) as query_str, 6000 as order_sq, 'select' as type from tbl_info
  union all
  select ' where 1=1 ' as query_str, 7000 as order_sq, 'select' as type from tbl_info
  union all
  select concat('   and ', c_nm, c_nm_term, ' = ', binding_str , c_nm_term, ' /* ', dt_type, '-', c_desc, ' */' ) as query_str, (8000 + c_ord_sq) as order_sq, 'select' as type from col_info
  union all
  select 'update /*  */ ' as query_str, 1000 as order_sq, 'update' as type
  union all
  select concat('       ', tbl_name, ' /* ', tbl_desc, ' */' ) as query_str, 1500 as order_sq, 'update' as type from tbl_info
  union all
  select '   set ' as query_str, 2000 as order_sq, 'update' as type
  union all
  select concat('     , ', c_nm, c_nm_term, ' = ', binding_str, c_nm_term, ' /* ', dt_type, '-', c_desc, ' */' ) as query_str, (3000 + c_ord_sq) as order_sq, 'update' as type from col_info
  union all
  select ' where 1=1 ' as query_str, 6000 as order_sq, 'update' as type from tbl_info
  union all
  select concat('   and ', c_nm, c_nm_term, ' = ', binding_str, c_nm_term, ' /* ', dt_type, '-', c_desc, ' */' ) as query_str, (8000 + c_ord_sq) as order_sq, 'update' as type from col_info
  union all
  select 'insert /*  */ ' as query_str, 1000 as order_sq, 'insert' as type
  union all
  select concat('  into ', tbl_name, ' /* ', tbl_desc, ' */ (' ) as query_str, 1500 as order_sq, 'insert' as type from tbl_info
  union all
  select concat('     , ', c_nm, c_nm_term, '    /* ', dt_type, '-', c_desc, ' */' ) as query_str, (3000 + c_ord_sq) as order_sq, 'insert' as type from col_info
  union all
  select ') values ( ' as query_str, 6000 as order_sq, 'insert' as type
  union all
  select concat('     , ', binding_str, c_nm_term, ' /* ', dt_type, '-', c_desc, ' */' ) as query_str, (7000 + c_ord_sq) as order_sq, 'insert' as type from col_info
  union all
  select ') ' as query_str, 9000 as order_sq, 'insert' as type
  union all
  select 'delete /*  */ ' as query_str, 1000 as order_sq, 'delete' as type
  union all
  select concat('  from ', tbl_name, ' /* ', tbl_desc, ' */' ) as query_str, 2000 as order_sq, 'delete' as type from tbl_info
  union all
  select ' where 1=1 ' as query_str, 3000 as order_sq, 'delete' as type
  union all
  select concat('   and ', c_nm, c_nm_term, ' = ', binding_str, c_nm_term, ' /* ', dt_type, '-', c_desc, ' */' ) as query_str, (8000 + c_ord_sq) as order_sq, 'delete' as type from col_info
)
--select * from sql_template
select query_str from sql_template
where 1=1
  and type like '%' || (select type from tbl_name) || '%'
order by type, order_sq
;