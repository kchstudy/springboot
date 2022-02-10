package blog.benggri.springboot.config.batch;

import org.quartz.JobExecutionContext;

public interface BatchInterface {
    void executeBatch(JobExecutionContext context) throws Exception;
}
