package blog.benggri.springboot.auth.vo;

import blog.benggri.springboot.comm.jpa.entity.usr.UsrEntity;
import blog.benggri.springboot.comm.jpa.entity.usr.UsrInfoEntity;
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
public class SignupReqVo {

    private String        usrId    ; /* varchar-사용자_ID */
    private String        encUsrPwd; /* varchar-암호화_사용자_비밀번호 */

    private String        usrNm      ; /* varchar-사용자_이름 */
    private String        encUsrPhnNo; /* varchar-암호화_사용자_전화_번호 */
    private String        encUsrEmail; /* varchar-암호화_사용자_이메일 */

    public UsrEntity toUsr(PasswordEncoder passwordEncoder) {
        return UsrEntity.builder()
                        .usrId(usrId)
                        .encUsrPwd(passwordEncoder.encode(encUsrPwd))
                        .usrClsCd("ROLE_USER").build();
    }

    public UsrInfoEntity toUsrInfo() {
        return UsrInfoEntity.builder()
                            .usrNm(usrNm)
                            .encUsrPhnNo(encUsrPhnNo)
                            .encUsrEmail(encUsrEmail).build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(usrId, encUsrPwd);
    }

}
