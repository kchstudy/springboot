package blog.benggri.springboot.biz.jpa.entity.log;

import blog.benggri.springboot.biz.jpa.entity.comm.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "log")
@Entity
@IdClass(LogEntityPK.class)
@SequenceGenerator(name="sq_log_log_sq", sequenceName="sq_log_log_sq", initialValue=1, allocationSize=1)
public class LogEntity extends DefaultEntity {

    private static final long serialVersionUID = 7141787209004350332L;

    @Id
    private String logDt;       /* varchar-로그_일자 */

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_log_log_sq")
    private Long   logSq;       /* numeric-로그_순번 */

    private String cmpnnt;    /* varchar-컴포넌트 */
    private String method;    /* varchar-메소드 */
    private String reqDt;    /* varchar-요청_일시 */
    private String reqCntnt; /* text-요청_내용 */
    private String resDt;    /* varchar-응답_일시 */
    private String resCntnt; /* text-응답_내용 */
    private String sttsCd;   /* varchar-상태_코드 */

}
