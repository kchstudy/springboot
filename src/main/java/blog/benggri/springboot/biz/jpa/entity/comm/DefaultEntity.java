package blog.benggri.springboot.biz.jpa.entity.comm;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class DefaultEntity implements Serializable {
    private static final long serialVersionUID = 2878032079837903166L;
}
