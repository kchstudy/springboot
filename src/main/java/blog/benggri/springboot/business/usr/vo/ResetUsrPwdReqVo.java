package blog.benggri.springboot.business.usr.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="사용자정보수정",description="사용자정보수정 Model")
public class ResetUsrPwdReqVo {

    @ApiModelProperty(value="사용자_순번", required=true, example="1")
    private Long   usr_sq;
    @ApiModelProperty(value="사용자_비밀번호", required=true, example="사용자_비밀번호")
    private String usr_pwd;     /* varchar_사용자_비밀번호 */
    @ApiModelProperty(value="비밀번호_만료_일자", required=true, example="비밀번호_만료_일자")
    private String pwd_expr_dt; /* varchar_비밀번호_만료_일자 */

}
