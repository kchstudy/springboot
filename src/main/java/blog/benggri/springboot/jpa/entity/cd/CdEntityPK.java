package blog.benggri.springboot.jpa.entity.cd;

import lombok.Data;

import java.io.Serializable;

@Data
public class CdEntityPK implements Serializable {

    private static final long serialVersionUID = -8319908862606455375L;

    private String grpCd; /* varchar-그룹_코드 */
    private String cd;    /* varchar-코드 */

}
