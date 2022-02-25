function cm_axios() {};

/**
 * @description 공통 axios 파라미터 유효성검사
 * @param prm : 파라미터 {
 *     method : 'string'
 *     url    : 'string'
 *     data   : {object} (optional)
 *     succ   : function() {success function} (optional)
 *     err    : function() {error function} (optional)
 * }
 */
cm_axios.prototype.validatePrm = function( prm ) {
    if ( cm_util.isEmptyObj(prm) ) {
        throw 'prm is null!';
    }
    if ( cm_util.isEmptyObj(prm.method) ) {
        throw 'method is null!';
    }
    if ( cm_util.isEmptyObj(prm.url) ) {
        throw 'url is null!';
    }
};

/**
 * @description 공통 axios 헤더 생성
 * @return {
 *
 * }
 */
cm_axios.prototype.createHeaders = function() {
};

/**
 * @description 공통 axios 로딩 생성
 */
cm_axios.prototype.loadingShow = function() {
    cm_util.STEP('loadingShow');
};

/**
 * @description 공통 axios 로딩 제거
 */
cm_axios.prototype.loadingHide = function() {
    cm_util.STEP('loadingHide');
};

/**
 * @description 공통 axios 파라미터 생성
 * @param url    : url
 * @param data   : data
 * @param succ   : success function
 * @param err    : error function
 * @return {
 *     method
 *     url
 *     data
 *     succ
 *     err
 * }
 */
cm_axios.prototype.createPrm = function( method, url, data, succ, err ) {
    if ( cm_util.isEmptyObj(succ) ) {
        succ = function(res) { cm_util.STEP(res) };
    }
    if ( cm_util.isEmptyObj(err) ) {
        err = function(err) { cm_util.STEP(err) };
    }
    return {
         method : method
        ,url    : url
        ,data   : data
        ,succ   : succ
        ,err    : err
    }
};

/**
 * @description 공통 axios
 * @param prm : 파라미터 {
 *     method : 'string'
 *     url    : 'string'
 *     data   : {object} (optional)
 *     succ   : function() {success function} (optional)
 *     err    : function() {error function} (optional)
 * }
 */
cm_axios.prototype.exec = function( url, data, succ, err ) {
    var prm = cm_axios.createPrm( 'post', url, data, succ, err );
    cm_axios.validatePrm(prm);
    axios.post(prm.url, prm.data)
    .then(function(res) {
        prm.succ(res);
    })
    .catch(function(err) {
        prm.err(err);
    });
};

var cm_axios = new cm_axios();

// 요청 인터셉터 추가
axios.interceptors.request.use(
    function(config) {
        cm_axios.loadingShow();
        return config;
    }, function(error) {
        return Promise.reject(error);
    }
);

// 응답 인터셉터 추가
axios.interceptors.response.use(
    function(response) {
        cm_axios.loadingHide();
        return response;
    }, function(error) {
        cm_axios.loadingHide();
        return Promise.reject(error);
    }
);