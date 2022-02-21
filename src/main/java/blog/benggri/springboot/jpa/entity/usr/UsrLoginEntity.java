package blog.benggri.springboot.jpa.entity.usr;

import java.time.LocalDateTime;

import blog.benggri.springboot.jpa.entity.comm.DefaultEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usr_login")
@IdClass(UsrLoginEntityPK.class)
@SequenceGenerator(name="sq_usr_login_login_sq", sequenceName="sq_usr_login_login_sq", initialValue=1, allocationSize=1)
public class UsrLoginEntity extends DefaultEntity {

    private static final long serialVersionUID = 5932403377774157423L;

    @Id
    private String loginDt;  /* varchar-로그인_일자 */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usr_login_login_sq")
    private Long   loginSq;  /* numeric-로그인_순번 */

    private Long   usrSq;    /* numeric-사용자_순번 */
    private String loginIp;  /* varchar-로그인_IP */
    private String logoutDt; /* varchar-로그아웃_일시 */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDt;    /* timestamp-등록_일시 */

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime chgDt;    /* timestamp-수정_일시 */
}
