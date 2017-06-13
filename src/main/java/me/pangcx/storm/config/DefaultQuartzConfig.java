package me.pangcx.storm.config;

import backtype.storm.Config;
import me.pangcx.storm.job.QuartzJob;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * aa.
 *
 * @author pangchuanxiao
 * @since 2016/3/23
 */
public class DefaultQuartzConfig extends Config {
    /**
     * 增加一项定时任务
     * @param jobName
     * @param expression
     */
    public void addJob(String jobName, String expression) {
        put("quartz." + jobName, expression);
    }

    /**
     * 添加单个job
     * @param quartzJob
     */
    public void addJob(QuartzJob quartzJob) {
        put("quartz." + quartzJob.getJobName(), quartzJob.getExpression());
    }

    /**
     * 添加多个job
     * @param jobs
     */
    public void addJobs(List<QuartzJob> jobs) {
        if (CollectionUtils.isEmpty(jobs)) {
            return;
        }
        for (QuartzJob quartzJob : jobs) {
            addJob(quartzJob);
        }
    }
}
