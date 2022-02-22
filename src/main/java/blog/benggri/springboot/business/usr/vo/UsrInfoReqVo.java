package blog.benggri.springboot.business.usr.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="사용자정보조회",description="사용자정보조회 Model")
public class UsrInfoReqVo {

    @ApiModelProperty(value="사용자_순번", required=true, example="1")
    private Long   usr_sq;

}
