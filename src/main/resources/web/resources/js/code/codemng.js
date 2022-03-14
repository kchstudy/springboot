$(function(){
    cm_pager.init('grp_cd_pager', findGrpCdList);
    cm_pager.init('cd_pager');
    findGrpCdList();
});

$('#btn_find_grp_cd').on('click', function(e) {
    findGrpCdList();
});

function findGrpCdList( page_no ) {
    cm_callsvc.exec(
         '/code/mng/getGrpCdList'
        ,{
             grp_cd      : $('#grp_cd_grp_cd').val()
            ,grp_nm      : $('#grp_cd_grp_nm').val()
            ,prnt_grp_cd : $('#grp_cd_prnt_grp_cd').val()
            ,use_yn      : $('#grp_cd_use_yn').val()
            ,page        : {
                 page_no      : cm_util.nvl(page_no, '1')
                ,cnt_per_page : $('#grp_cd_pager_page_cnt').val()
            }
        }
        ,function(result) {
            var d = result.data;
            cm_pager.paginate(
                 'grp_cd_pager'
                ,findGrpCdList
                ,d.tot_cnt
                ,cm_util.nvl(page_no, '1')
                ,$('#grp_cd_pager_page_cnt').val()
            );
            $('#grp_cd_list').children().remove();
            d.data.forEach(function(ele) {
                $('#grp_cd_list').append(
                     '<tr>'
                    +'    <td>'+cm_util.nvl(ele.grp_cd, '-')+'</td>'
                    +'    <td>'+cm_util.nvl(ele.grp_nm, '-')+'</td>'
                    +'    <td>'+cm_util.nvl(ele.prnt_grp_cd, '-')+'</td>'
                    +'    <td>'+cm_util.nvl(ele.use_yn, '-')+'</td>'
                    +'</tr>'
                );
            });

        }
        ,cm_util.STEP
    );
}
