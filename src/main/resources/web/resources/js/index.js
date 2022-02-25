$('#btn_login').on('click', function(e) {
    if ( cm_util.isEmptyObj($('#usr_id').val()) || cm_util.isEmptyObj($('#usr_pwd').val()) ) {
        cm_util.STEP('필수값확인');
        return;
    }
    cm_axios.exec(
         '/auth/login'
        ,{ usrId     : $('#usr_id').val()
          ,encUsrPwd : $('#usr_pwd').val()
        }
        ,cm_util.STEP
        ,cm_util.STEP
    );
});