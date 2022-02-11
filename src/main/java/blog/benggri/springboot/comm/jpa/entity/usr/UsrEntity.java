package blog.benggri.springboot.comm.jpa.entity.usr;

import blog.benggri.springboot.comm.jpa.entity.comm.DefaultEntity;
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
@Table(name="usr")
@SequenceGenerator(name="sq_usr_usr_sq", sequenceName="sq_usr_usr_sq", initialValue=1, allocationSize=1)
public class UsrEntity extends DefaultEntity {

    private static final long serialVersionUID = 7300722121646292899L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usr_usr_sq")
    private Long          usrSq    ; /* numeric-사용자_순번 */
    private String        usrId    ; /* varchar-사용자_ID */
    private String        encUsrPwd; /* varchar-암호화_사용자_비밀번호 */
    private String        usrClsCd ; /* varchar-사용자_구분_코드 */

}
