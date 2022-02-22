function cm_formatter() {};

cm_formatter.prototype.substr = function( str, length ) {
    if ( String(str).length <= length ) {
        return String(str);
    }
    return String(str).substring(0, length) + '...';
}

cm_formatter.prototype.comma = function( str ) {
    if ( !cm_regex.number(str) ) {
        console.error('['+str+']은 숫자 형식이 아닙니다.');
        return 0;
    }
    var regex = /\B(?=(\d{3})+(?!\d))/g;
    return String(str).replace(regex, ',');
}

cm_formatter.prototype.bizno = function( str ) {
    if ( !cm_regex.bizno(str) ) {
        console.error('['+str+']은 사업자번호 형식이 아닙니다.');
        return '-';
    }
    var regex = /(\d{3})(\d{2})(\d{5})/;
    return str.toString().replace(regex, '$1-$2-$3');
}

cm_formatter.prototype.phone = function( str, isEnc ) {
    if ( !cm_regex.phone(str) ) {
        console.error('['+str+']은 휴대폰 형식이 아닙니다.');
        return '-';
    }

    var phone  = '$1-$2';
    var mobile = '$1-$2-$3';
    if ( isEnc ) {
        phone  = '$1-****';
        mobile = '$1-$2-****';
    }
    var temp = str.replace(/-/g,'');
    if ( temp.length == 8 ) {
        var regex = /([0-9]{4})([0-9]{4})/;
        return String(temp).replace(regex, phone);
    }

    var regex = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/;
    return String(temp).replace(regex, mobile);
}

var cm_formatter = new cm_formatter();

/* jq그리드 포맷터 :: START */
function jq_str10(cellvalue, options, rowObject) {
    return cm_formatter.substr(cellvalue, 10);
}
function jq_str15(cellvalue, options, rowObject) {
    return cm_formatter.substr(cellvalue, 15);
}
function jq_str20(cellvalue, options, rowObject) {
    return cm_formatter.substr(cellvalue, 20);
}
function jq_str25(cellvalue, options, rowObject) {
    return cm_formatter.substr(cellvalue, 25);
}
function jq_str30(cellvalue, options, rowObject) {
    return cm_formatter.substr(cellvalue, 30);
}
function jq_str35(cellvalue, options, rowObject) {
    return cm_formatter.substr(cellvalue, 35);
}
function jq_comma(cellvalue, options, rowObject) {
    return cm_formatter.comma(cellvalue);
}
function jq_phone(cellvalue, options, rowObject) {
    return cm_formatter.phone(cellvalue, false);
}
function jq_encphone(cellvalue, options, rowObject) {
    return cm_formatter.phone(cellvalue, true);
}
/* jq그리드 포맷터 :: END */
