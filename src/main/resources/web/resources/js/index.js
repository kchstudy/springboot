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

    cm_callsvc.exec(
         '/auth/login'
        ,{ usrId     : $('#usr_id').val()
          ,encUsrPwd : $('#usr_pwd').val()
        }
        ,function(result) {
            cm_util.setSession('t'     , result.data.access_token );
            cm_util.setSession('ft'    , result.data.refresh_token);
            cm_util.setSession('usr_id', result.data.usr_id       );
        }
        ,cm_util.STEP
    );
});