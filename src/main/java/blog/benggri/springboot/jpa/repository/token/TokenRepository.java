package blog.benggri.springboot.jpa.repository.token;

import blog.benggri.springboot.jpa.entity.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByK(String key);
}
