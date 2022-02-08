package blog.benggri.springboot.jpa.repository.member;

import blog.benggri.springboot.jpa.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findBySeq(Long seq);

    Optional<MemberEntity> findById(String id);
    boolean existsById(String id);

}
