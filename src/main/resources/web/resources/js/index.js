$('#btn_login').on('click', function(e) {
    if ( cm_util.isEmptyObj($('#usr_id').val()) || cm_util.isEmptyObj($('#usr_pwd').val()) ) {
        cm_util.STEP('필수값확인');
        return;
    }
    axios({
         method: 'post'
        ,url   : '/auth/login'
        ,data  : {
             usrId     : $('#usr_id').val()
            ,encUsrPwd : $('#usr_pwd').val()
        }
    })
    .then(function(res) {
        cm_util.STEP(res);
    })
    .catch(function(err) {
        cm_util.STEP(err);
    });
});