package blog.benggri.springboot.jpa.repository.cd;

import blog.benggri.springboot.jpa.entity.cd.CdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CdRepository extends JpaRepository<CdEntity, String> {

    @Query(value="select decrypt(:col\\:\\:text)", nativeQuery=true)
    String decrypt(String col);

    @Query(value="select encrypt(:col\\:\\:text)", nativeQuery=true)
    String encrypt(String col);

    List<CdEntity> findByGrpCdOrderBySq(String grpCd);
    CdEntity findByGrpCdAndCd(String grpCd, String cd);

}
