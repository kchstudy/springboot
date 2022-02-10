package blog.benggri.springboot.usr.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "사용자", description = "사용자")
public class UsrVo {

    @ApiModelProperty(value="순번", required=true, example="MEMBER_SEQ")
    private Long   seq    ; /* numeric-순번 */
    @ApiModelProperty(value="ID", required=true, example="MEMBER_ID")
    private String id     ; /* varchar-ID */
    @ApiModelProperty(value="비밀번호", required=true, example="PASSWORD")
    private String pwd    ; /* varchar-비밀번호 */
    @ApiModelProperty(value="인증", required=true, example="AUTH")
    private String auth   ; /* varchar-인증 */
    @ApiModelProperty(value="등록_일시", required=true, example="YYYYMMDDHH24MISS")
    private String reg_dt ; /* timestamp-등록_일시 */
    @ApiModelProperty(value="수정_일시", required=true, example="YYYYMMDDHH24MISS")
    private String chg_dt ; /* timestamp-수정_일시 */

}
