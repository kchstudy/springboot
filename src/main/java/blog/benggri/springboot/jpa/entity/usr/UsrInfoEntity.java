package blog.benggri.springboot.jpa.entity.usr;

import blog.benggri.springboot.jpa.entity.comm.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usr_info")
public class UsrInfoEntity extends DefaultEntity {

    private static final long serialVersionUID = 7300722121646292899L;
    @Id
    private Long          usrSq      ; /* numeric-사용자_순번 */
    private String        usrNm      ; /* varchar-사용자_이름 */
    private String        encUsrPhnNo; /* varchar-암호화_사용자_전화_번호 */
    private String        encUsrEmail; /* varchar-암호화_사용자_이메일 */

}
