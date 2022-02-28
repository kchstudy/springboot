$('#a_sing_up').on('click', function(e) {
    $('#sign_up_usr_id').val('');
    $('#sign_up_usr_pwd').val('');
    $('#sign_up_usr_name').val('');
    $('#sign_up_usr_phone').val('');
    $('#sign_up_usr_email').val('');
});

$('#btn_login').on('click', function(e) {

    if ( !$('#frm_sign_in')[0].checkValidity() ) {
        return false;
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