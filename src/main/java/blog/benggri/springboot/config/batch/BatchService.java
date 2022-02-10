package blog.benggri.springboot.config.batch;

import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static blog.benggri.springboot.comm.util.StringUtil.*;

@Slf4j
@Lazy
@Component
public class BatchService {

    public Scheduler scheduler;
    private SchedulerFactory schedulerFactory;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private BatchDao batchDao;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @PostConstruct
    public void init() throws SchedulerException {
        STEP(log, "batch 기동");
        if ("TEST".equals(activeProfile)) { //TEST일경우 배치기동중단
            return;
        }

        schedulerFactory = new StdSchedulerFactory();
        scheduler = schedulerFactory.getScheduler();

        STEP(log, "서버별 배치 정보를 조회한다");
        List<Map<String, Object>> batList = getBatchList();

        STEP(log, "배치 정보를 스케쥴러에 등록 후 실행한다");
        for ( Map<String, Object> bat : batList ) {
            boolean addJobResult = addJob(bat);
            bat.put("exe_stp", BatchSttsEnum.IDLE.getStatus());
            bat.put("rslt_msg", BatchSttsEnum.IDLE.getMsg());
            writeBatchStatus(bat);
            STEP(log, "배치 등록 결과["+addJobResult+"]배치ID["+nvl(bat.get("bat_id"))+"]" );
        }
        scheduler.start();
    }

    /**
     * 배치 정보 조회
     * @return
     */
    private List<Map<String, Object>> getBatchList() {
        STEP(log, "BATCH LIST");
        return batchDao.getBatchList(MapBuilder.<String,Object>createInstance()
                                               .add("bat_svr", getSvrIp())
                                               .toMap());
    }

    /**
     * 신규 배치 등록
     * @param prmMap
     * @return
     */
    public boolean addJob( Map<String, Object> prmMap ) {
        JobDetail jobDetail = JobBuilder.newJob( (Class<? extends Job>) context.getBean(nvl(prmMap.get("bat_agent"))).getClass() )
                                        .withIdentity( nvl(prmMap.get("bat_id")), "mkyu" )
                                        .build();

        jobDetail.getJobDataMap().put("applicationContext", context); //injection된 Bean을 꺼내기 위해 필수
        jobDetail.getJobDataMap().put("bat_svr"           , nvl(prmMap.get("bat_svr"))  ); //배치기본정보 등록(배치 서버)
        jobDetail.getJobDataMap().put("bat_id"            , nvl(prmMap.get("bat_id"))   ); //배치기본정보 등록(배치 ID)
        jobDetail.getJobDataMap().put("bat_nm"            , nvl(prmMap.get("bat_nm"))   ); //배치기본정보 등록(배치_명)
        jobDetail.getJobDataMap().put("bat_desc"          , nvl(prmMap.get("bat_desc")) ); //배치기본정보 등록(배치_설명)
        jobDetail.getJobDataMap().put("bat_agent"         , nvl(prmMap.get("bat_agent"))); //배치기본정보 등록(배치 에이전트)
        jobDetail.getJobDataMap().put("bat_sch"           , nvl(prmMap.get("bat_sch"))  ); //배치기본정보 등록(배치_스케쥴)
        jobDetail.getJobDataMap().put("basc_prm"          , nvl(prmMap.get("basc_prm")) ); //배치기본정보 등록(기본_파라미터)

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity( nvl(prmMap.get("bat_id")), "mkyu" )
                                        .withSchedule(CronScheduleBuilder.cronSchedule( nvl(prmMap.get("bat_sch"))))
                                        .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 배치 상태 기록
     * @param batMap
     */
    @Transactional(value="transactionManager", propagation = Propagation.REQUIRES_NEW)
    public void writeBatchStatus(Map<String, Object> batMap) {
        batchDao.updateBatchStepInfo(batMap);
        batchDao.createBatchExeHis(batMap);
    }
}
