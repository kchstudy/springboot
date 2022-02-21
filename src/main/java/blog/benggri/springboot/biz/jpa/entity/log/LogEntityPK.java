package blog.benggri.springboot.biz.jpa.entity.log;

import lombok.Data;

import java.io.Serializable;

@Data
public class LogEntityPK implements Serializable {

    private static final long serialVersionUID = -206076836487207024L;


    private String logDt;  /* varchar-로그_일자 */
    private Long   logSq;  /* numeric-로그_순번 */

}
