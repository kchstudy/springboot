package blog.benggri.springboot.comm.jpa.repository.usr;

import blog.benggri.springboot.comm.jpa.entity.usr.UsrInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrInfoRepository extends JpaRepository<UsrInfoEntity, Long> {

    UsrInfoEntity findByUsrSq(Long seq);

}
