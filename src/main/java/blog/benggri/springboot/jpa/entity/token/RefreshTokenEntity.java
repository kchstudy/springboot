package blog.benggri.springboot.jpa.entity.token;

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
public class RefreshTokenEntity {

    @Id
    private String key;
    private String value;

    public RefreshTokenEntity updateValue(String token) {
        this.value = token;
        return this;
    }

    @Builder
    public RefreshTokenEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

}
