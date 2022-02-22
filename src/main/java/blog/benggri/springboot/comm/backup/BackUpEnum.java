package blog.benggri.springboot.comm.backup;

import lombok.Getter;

@Getter
public enum BackUpEnum {
     API("API","api_id:API_ID") /* API */
    ,BAT("배치","bat_svr:배치_서버","bat_id:배치_ID") /* 배치 */
    ,USR("사용자","usr_sq:사용자_순번") /* 사용자 */
    ,USR_INFO("사용자_정보","usr_sq:사용자_순번") /* 사용자_정보 */
    ;

    private String tbNm; // 테이블명
    private String[] pks; // 테이블PK목록

    BackUpEnum(String tbNm, String... pks) {
        this.tbNm = tbNm;
        this.pks = pks;
    }
}
