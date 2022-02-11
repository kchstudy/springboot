package blog.benggri.springboot.comm.jpa.entity.token;

import blog.benggri.springboot.comm.jpa.entity.comm.DefaultEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class TokenEntity extends DefaultEntity {

    @Id
    private String key;
    private String value;

    public TokenEntity updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public TokenEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
