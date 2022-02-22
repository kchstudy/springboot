package blog.benggri.springboot.business.usr.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="사용자정보수정",description="사용자정보수정 Model")
public class UpdateUsrInfoReqVo {

    @ApiModelProperty(value="사용자_순번", required=true, example="1")
    private Long   usr_sq;
    @ApiModelProperty(value="사용자_이름", required=true, example="사용자_이름")
    private String usr_nm;     /* varchar_사용자_이름 */
    @ApiModelProperty(value="사용자_전화_번호", required=true, example="사용자_전화_번호")
    private String usr_phn_no; /* varchar_사용자_전화_번호 */
    @ApiModelProperty(value="사용자_이메일", required=true, example="사용자_이메일")
    private String usr_email;  /* varchar_사용자_이메일 */

}
