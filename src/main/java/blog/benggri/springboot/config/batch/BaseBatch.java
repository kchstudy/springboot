package blog.benggri.springboot.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static blog.benggri.springboot.comm.util.StringUtil.STEP;

@Slf4j
@Component
@DisallowConcurrentExecution
public class BaseBatch extends BatchAbstract {

    @Autowired
    private BatchService batchService;

    @Override
    public void executeBatch(JobExecutionContext context) throws Exception {
        STEP(log, "BatBase");
    }
}
