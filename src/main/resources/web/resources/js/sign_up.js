$('#btn_save').on('click', function(e) {
    if ( !$('#frm_sign_up')[0].checkValidity() ) {
        return false;
    }

    cm_axios.exec(
         '/auth/signup'
        ,{ usrId       : $("#sign_up_usr_id").val()
          ,encUsrPwd   : $("#sign_up_usr_pwd").val()
          ,usrNm       : $("#sign_up_usr_name").val()
          ,encUsrPhnNo : $("#sign_up_usr_phone").val()
          ,encUsrEmail : $("#sign_up_usr_email").val()
        }
        ,cm_util.STEP
        ,cm_util.STEP
    );
});