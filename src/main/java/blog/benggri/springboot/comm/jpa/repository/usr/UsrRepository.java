package blog.benggri.springboot.comm.jpa.repository.usr;

import blog.benggri.springboot.comm.jpa.entity.usr.UsrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsrRepository extends JpaRepository<UsrEntity, Long> {

    UsrEntity findByUsrSq(Long seq);

    Optional<UsrEntity> findByUsrId(String id);
    boolean existsByUsrId(String id);

}
