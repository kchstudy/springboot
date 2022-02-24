package blog.benggri.springboot.jpa.entity.cd;

import blog.benggri.springboot.jpa.entity.comm.DefaultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="cd")
@Entity
@IdClass(CdEntityPK.class)
public class CdEntity extends DefaultEntity {

    private static final long serialVersionUID = 5785036211586123160L;

    @Id
    private String grpCd; /* varchar-그룹_코드 */
    @Id
    private String cd;    /* varchar-코드 */

    private String cdNm;  /* varchar-코드_명 */
    private String cdV;   /* varchar-코드_값 */
    private String note;  /* text-비고 */
    private Long   sq;    /* numeric-순번 */
    private String useYn; /* varchar-사용_여부 */

}
