package blog.benggri.springboot.jpa.entity.token;

import blog.benggri.springboot.jpa.entity.comm.DefaultEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "token")
@Entity
public class TokenEntity extends DefaultEntity {

    @Id
    private String k;
    private String v;

    public TokenEntity updateValue(String token) {
        this.v = token;
        return this;
    }

    @Builder
    public TokenEntity(String key, String value) {
        this.k = key;
        this.v = value;
    }

}
