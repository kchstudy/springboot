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