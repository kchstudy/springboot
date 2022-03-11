$(function(){
     cm_callsvc.exec(
          '/code/mng/getGrpCdList'
         ,{
              grp_cd      : $('#grp_cd_grp_cd').val()
             ,grp_nm      : $('#grp_cd_grp_nm').val()
             ,prnt_grp_cd : $('#grp_cd_prnt_grp_cd').val()
             ,use_yn      : $('#grp_cd_use_yn').val()
         }
         ,cm_util.STEP
         ,cm_util.STEP
     );
});
