function cm_util() {};

/**
 * @description 공통 로그 함수
 * @param str : 콘솔에 표시할 문자
 */
cm_util.prototype.STEP = function( str ) {
    var obj = { log : str };
//    console.log('arguments.callee.caller.toString()::'+arguments.callee.caller.toString());
    console.log( JSON.stringify(obj) );
};

/**
 * @description 빈값체크
 * @param obj
 */
cm_util.prototype.isEmptyObj = function( obj ) {
    if ( typeof obj === 'undefined' ) {
        return true;
    }
    if ( typeof obj === 'string' && obj == '' ) {
        return true;
    }
    if ( typeof obj === 'function' ) {
        return false;
    }
    if ( typeof obj === 'boolean' ) {
        return false;
    }
    if ( typeof obj === 'number' ) {
        return false;
    }
    if ( typeof obj === 'bigint' ) {
        return false;
    }
    if ( obj instanceof FormData ) {
        return false;
    }
    if ( obj instanceof Promise ) {
        return false;
    }
    if ( typeof obj === 'object' ) {
        if ( obj === null ) {
            return true;
        }
        if ( Array.isArray(obj) && obj.length < 1 ) {
            return true;
        }
        if ( Object.keys(obj).length > 0 ) {
            return false;
        }
        var objStr = JSON.stringify(obj);
        if ( objStr === '{}' || objStr === '[]' ) {
            return true;
        }
    }
    return false;
};

/**
 * @description sessionStorage 초기화
 */
cm_util.prototype.clearSession = function() {
    sessionStorage.clear();
};

/**
 * @description sessionStorage 정보 조회
 */
cm_util.prototype.getSession = function( key ) {
    return sessionStorage.getItem(key);
};

/**
 * @description sessionStorage 정보 조회
 */
cm_util.prototype.setSession = function( key, value ) {
    sessionStorage.setItem(key, value);
};

/**
 * @description 빈값 변환
 * @param val        : 입력값
 * @param defaultVal : 변환할 값(optional)
 */
cm_util.prototype.nvl = function( val, defaultVal ) {
    if ( cm_util.isEmptyObj(defaultVal) ) {
        defaultVal = '';
    }

    if ( cm_util.isEmptyObj(val) ) {
        return defaultVal;
    }

    return val;
};

/**
 * @description 왼쪽 문자 패딩
 * @param str  : 입력값
 * @param pStr : 반복문자
 * @param len  : 전체길이
 */
cm_util.prototype.lpad = function( str, pStr, len ) {
    if ( cm_util.isEmptyObj(str) ) {
        str = '';
    }
    str += '';
    if ( cm_util.isEmptyObj(pStr) ) {
        pStr = ' ';
    }
    var cnt = len - str.length;
    var temp = '';
    for (var i = 0 ; i < cnt ; i++) {
        temp += pStr+'';
    }
    return temp+str;
};

/**
 * @description 오른쪽 문자 패딩
 * @param str  : 입력값
 * @param pStr : 반복문자
 * @param len  : 전체길이
 */
cm_util.prototype.rpad = function( str, pStr, len ) {
    if ( cm_util.isEmptyObj(str) ) {
        str = '';
    }
    str += '';
    if ( cm_util.isEmptyObj(pStr) ) {
        pStr = ' ';
    }
    var cnt = len - str.length;
    var temp = '';
    for (var i = 0 ; i < cnt ; i++) {
        temp += pStr+'';
    }
    return str+temp;
};

cm_util.prototype.getCookie = function(name) {
  let matches = document.cookie.match(new RegExp("(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"));
  return matches ? decodeURIComponent(matches[1]) : undefined;
}

cm_util.prototype.setCookie = function(name, value, options) {
    // {secure: true, 'max-age': 3600}
    if (cm_util.isEmptyObj(options)) {
        options = {};
    }

    if (options.expires instanceof Date) {
      options.expires = options.expires.toUTCString();
    }

    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    for (let optionKey in options) {
      updatedCookie += "; " + optionKey;
      let optionValue = options[optionKey];
      if (optionValue !== true) {
        updatedCookie += "=" + optionValue;
      }
    }

    document.cookie = updatedCookie;
}

var cm_util = new cm_util();