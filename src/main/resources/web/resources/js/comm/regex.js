function cm_regex() {};

/* 이메일 인지 확인 */
cm_regex.prototype.email = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    var regex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
    return regex.test(param);
}
/* 숫자 인지 확인 */
cm_regex.prototype.number = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    if ( isNaN(Number(param)) ) {
        return false;
    }
    var regex = /^[0-9]/g;
    return regex.test(Number(param));
}
/* 사업자번호 인지 확인 */
cm_regex.prototype.bizno = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    if ( param.indexOf('-') > -1 && param.split('-').length != 3 ) {
        return false;
    }
    var str = param.replace(/-/g,'');
    if ( str.length != 10 ) {
        return false;
    }
    return cm_regex.number(str);
}
/* 영어 인지 확인 */
cm_regex.prototype.eng = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    var regex = /^[a-zA-Z]*$/;
    return regex.test(param);
}
/* 한글 인지 확인 */
cm_regex.prototype.kor = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    var regex = /^[ㄱ-ㅎㅏ-ㅣ가-힣]*$/;
    return regex.test(param);
}
/* 특수문자 인지 확인 */
cm_regex.prototype.specialChar = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    var regex = /[\s]|[\n]|[\r]|[ㄱ-ㅎ]|[ㅏ-ㅣ]|[가-힣]|[0-9]|[a-z]|[A-Z]/g;
    var result = param.replace(regex, '');
    if ( result.length != param.length ) {
        return false;
    }
    return (result.length > 0 ? true : false);
}
/* 전화번호 인지 확인 */
cm_regex.prototype.phone = function( param ) {
    if ( isEmptyObj(param) ) {
        return false;
    }
    if ( param.indexOf('-') > -1 && param.split('-').length != 2 && param.split('-').length != 3 ) {
        return false;
    }
    var str = param.replace(/-/g,'');
    if ( str.length != 8 && str.length != 9 && str.length != 10 && str.length != 11 ) {
        return false;
    }
    return cm_regex.number(str);
}

var cm_regex = new cm_regex();