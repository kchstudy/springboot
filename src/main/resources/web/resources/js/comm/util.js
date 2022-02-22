/**
 * @description 공통 로그 함수
 * @param str : 콘솔에 표시할 문자
 */ 
function STEP( str ) {
    var obj = { log : str };
    console.log( "[STEP]["+JSON.stringify(obj)+"]" );
};

/**
 * @description 빈값체크
 * @param obj
 */
function isEmptyObj( obj ) {
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

/* sessionStorage */
/**
 * @description sessionStorage 초기화
 */
function clearSession() {
    sessionStorage.clear();
}

/**
 * @description sessionStorage 정보 조회
 */
function getSession( key ) {
    return sessionStorage.getItem(key);
}

/**
 * @description sessionStorage 정보 조회
 */
function setSession( key, value ) {
    sessionStorage.setItem(key, value);
}

/**  
 * @description 빈값 변환
 * @param val        : 입력값
 * @param defaultVal : 변환할 값(optional)
 */
function nvl(val, defaultVal) {
    if ( isEmpty(defaultVal) ) {
        defaultVal = '';
    }

    if ( isEmpty(val) ) {
        return defaultVal;
    }

    return val;
};

/* String */
/**
 * @description 왼쪽 문자 패딩
 * @param str  : 입력값
 * @param pStr : 반복문자열
 * @param len  : 전체길이
 */
function lpad(str, pStr, len) {
    str += '';
    if ( isEmpty(pStr) ) {
        pStr = ' ';
    }
    var cnt = len - str.length;
    var temp = '';
    for (var i = 0 ; i < cnt ; i++) {
        temp += pStr+'';
    }
    return temp+str;
}

/**
 * @description 오른쪽 문자 패딩
 * @param str  : 입력값
 * @param pStr : 반복문자열
 * @param len  : 전체길이
 */
function rpad(str, pStr, len) {
    str += '';
    if ( isEmpty(pStr) ) {
        pStr = ' ';
    }
    var cnt = len - str.length;
    var temp = '';
    for (var i = 0 ; i < cnt ; i++) {
        temp += pStr+'';
    }
    return str+temp;
}
