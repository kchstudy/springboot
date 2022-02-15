package blog.benggri.springboot.auth.vo;

import blog.benggri.springboot.biz.jpa.entity.usr.UsrEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResVo {

    private String usrId;

    public static LoginResVo of(UsrEntity entityUsr) {
        return new LoginResVo(entityUsr.getUsrId());
    }
}
