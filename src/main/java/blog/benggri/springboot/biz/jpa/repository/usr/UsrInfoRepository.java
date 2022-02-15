package blog.benggri.springboot.biz.jpa.repository.usr;

import blog.benggri.springboot.biz.jpa.entity.usr.UsrInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrInfoRepository extends JpaRepository<UsrInfoEntity, Long> {

    UsrInfoEntity findByUsrSq(Long seq);

}
