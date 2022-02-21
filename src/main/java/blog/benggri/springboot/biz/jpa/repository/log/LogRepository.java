package blog.benggri.springboot.biz.jpa.repository.log;

import blog.benggri.springboot.biz.jpa.entity.log.LogEntity;
import blog.benggri.springboot.biz.jpa.entity.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
    LogEntity findByLogDt(String logDt);
    LogEntity findByLogSq(Long logSq);
    LogEntity findByLogDtAndLogSq(String logDt, Long logSq);
}
