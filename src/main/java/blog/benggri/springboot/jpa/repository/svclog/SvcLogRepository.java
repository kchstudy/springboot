package blog.benggri.springboot.jpa.repository.svclog;

import blog.benggri.springboot.jpa.entity.svclog.SvcLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SvcLogRepository extends JpaRepository<SvcLogEntity, Long> {
    SvcLogEntity findByLogDt(String logDt);
    SvcLogEntity findByLogSq(Long logSq);
    SvcLogEntity findByLogDtAndLogSq(String logDt, Long logSq);
}
