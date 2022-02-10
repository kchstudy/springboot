package blog.benggri.springboot.config.batch;

import lombok.Getter;

@Getter
public enum BatchSttsEnum {
         IDLE("0000", "배치 실행 대기") //대기
        ,START("1000", "배치 실행 요청") //시작
        ,RUN("2000", "배치 실행 중")   //실행
        ,COMPLETE("5000", "배치 정상 종료") //종료(성공)
        ,ERROR("9000", "배치 오류 종료"); //종료(오류)

    private String status; //상태코드
    private String msg; //상태메시지
    
    BatchSttsEnum(String val, String msg) {
        status = val;
        msg = msg;
    }    
}
