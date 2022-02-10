package blog.benggri.springboot.config.batch;

import blog.benggri.springboot.comm.exception.CustomException;
import blog.benggri.springboot.comm.util.ExceptionUtil;
import blog.benggri.springboot.comm.util.MapBuilder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import static blog.benggri.springboot.comm.util.StringUtil.nvl;

@Slf4j
public abstract class BatchAbstract implements Job, BatchInterface {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        beforeBatch( context ); // 전처리
        ApplicationContext ctx = (ApplicationContext)context.getJobDetail().getJobDataMap().get("applicationContext");
        try {
            BatchInterface batIf = (BatchInterface) ctx.getBean( nvl(context.getJobDetail().getJobDataMap().get("bat_agent")) );
            batIf.executeBatch( context ); // 배치 실행
            afterBatch( context ); // 후처리
        } catch (Exception e) {
            e.printStackTrace();
            errorBatch( context, e );
            throw new CustomException(e);
        }
    }

    /**
     * Batch 전처리
     * @param context
     * @throws JobExecutionException
     */
    private void beforeBatch(JobExecutionContext context) throws JobExecutionException {
        writeBatchStatus(context, BatchSttsEnum.START.getStatus(), BatchSttsEnum.START.getMsg());
    }

    /**
     * Batch 후처리
     * @param context
     * @throws JobExecutionException
     */
    private void afterBatch(JobExecutionContext context) throws JobExecutionException {
        writeBatchStatus(context, BatchSttsEnum.COMPLETE.getStatus(), BatchSttsEnum.COMPLETE.getMsg());
    }

    /**
     * Batch Error 처리
     * @param context
     * @param e
     */
    private void errorBatch(JobExecutionContext context, Exception e) {
        writeBatchStatus(context, BatchSttsEnum.ERROR.getStatus(), String.format("배치 실행 실패[%s][%s]", e.getMessage(), ExceptionUtil.getPrintStackTrace(e)));
    }

    /**
     * 배치 실행 이력 등록
     * @param context
     * @param exe_stp
     * @param rslt_msg
     */
    protected void writeBatchStatus(JobExecutionContext context, String exe_stp, String rslt_msg) {
        String batSvr = nvl(context.getJobDetail().getJobDataMap().get("bat_svr"));
        String batId  = nvl(context.getJobDetail().getJobDataMap().get("bat_id"));

        ApplicationContext ctx = (ApplicationContext)context.getJobDetail().getJobDataMap().get("applicationContext");
        BatchService batchService = (BatchService)ctx.getBean("batchService");
        batchService.writeBatchStatus(MapBuilder.<String, Object>createInstance()
                                                .add("bat_svr"  , batSvr   )
                                                .add("bat_id"   , batId    )
                                                .add("exe_stp"  , exe_stp  )
                                                .add("rslt_msg" , rslt_msg )
                                                .toMap());
    }

}
