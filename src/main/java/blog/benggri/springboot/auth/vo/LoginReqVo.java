package blog.benggri.springboot.auth.vo;

import blog.benggri.springboot.biz.jpa.entity.usr.UsrEntity;
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

    private String usrId;
    private String encUsrPwd;

    public UsrEntity toUsr(PasswordEncoder passwordEncoder) {
        return UsrEntity.builder().usrId(usrId).encUsrPwd(passwordEncoder.encode(encUsrPwd)).usrClsCd("ROLE_USER").build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(usrId, encUsrPwd);
    }
}
