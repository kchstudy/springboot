package blog.benggri.springboot.biz.jpa.entity.usr;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsrLoginEntityPK implements Serializable {

    private static final long serialVersionUID = 6648342149382967197L;


    private String loginDt;  /* varchar-로그인_일자 */
    private Long   loginSq;  /* numeric-로그인_순번 */

}
