package blog.benggri.springboot.auth.vo;

import blog.benggri.springboot.jpa.entity.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResVo {

    private String id;

    public static LoginResVo of(MemberEntity entityUsr) {
        return new LoginResVo(entityUsr.getId());
    }
}
