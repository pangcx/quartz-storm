package me.pangcx.storm.spout;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 *
 * @author pangchuanxiao
 * @since 16-2-17
 */
public class QuartzJobExecutors implements Job {
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1000);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        queue.offer(jobExecutionContext.getJobDetail().getKey().getName());
    }

    public static String take() throws InterruptedException {
        return queue.take();
    }
}
