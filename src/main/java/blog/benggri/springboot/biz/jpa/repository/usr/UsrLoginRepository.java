package blog.benggri.springboot.biz.jpa.repository.usr;

import blog.benggri.springboot.biz.jpa.entity.usr.UsrLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsrLoginRepository extends JpaRepository<UsrLoginEntity, Long> {

    UsrLoginEntity findByUsrSq(Long usrSq);

}
