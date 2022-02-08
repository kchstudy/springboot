package blog.benggri.springboot.auth.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenReqVo {

    private String accessToken;
    private String refreshToken;

}
