package blog.benggri.springboot.auth.vo;

import blog.benggri.springboot.jpa.entity.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqVo {

    private String id;
    private String pwd;

    public MemberEntity toUsr(PasswordEncoder passwordEncoder) {
        return MemberEntity.builder().id(id).pwd(passwordEncoder.encode(pwd)).auth("ROLE_USER").build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, pwd);
    }
}
