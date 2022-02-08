package blog.benggri.springboot.jpa.entity.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="member")
@SequenceGenerator(name="sq_member_seq", sequenceName="sq_member_seq", initialValue=1, allocationSize=1)
public class MemberEntity {

    private static final long serialVersionUID = 7300722121646292899L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_member_seq")
    private Long   seq    ; /* numeric-순번 */
    private String id     ; /* varchar-ID */
    private String pwd    ; /* varchar-비밀번호 */
    private String auth   ; /* varchar-인증 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reg_dt ; /* timestamp-등록_일시 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime chg_dt ; /* timestamp-수정_일시 */

}
