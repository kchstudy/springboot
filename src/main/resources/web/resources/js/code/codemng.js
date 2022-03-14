$(function(){

    cm_pager.init('grp_cd_pager');
    cm_pager.init('cd_pager');

    cm_callsvc.exec(
         '/code/mng/getGrpCdList'
        ,{
             grp_cd      : $('#grp_cd_grp_cd').val()
            ,grp_nm      : $('#grp_cd_grp_nm').val()
            ,prnt_grp_cd : $('#grp_cd_prnt_grp_cd').val()
            ,use_yn      : $('#grp_cd_use_yn').val()
            ,page        : {
                 page_no      : '1'
                ,cnt_per_page : $('#grp_cd_page_cnt').val()
            }
        }
        ,function(result) {
            console.log(result);
        }
        ,cm_util.STEP
    );
});
