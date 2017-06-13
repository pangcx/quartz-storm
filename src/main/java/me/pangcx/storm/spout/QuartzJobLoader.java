package me.pangcx.storm.spout;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * quartz-projects
 *
 * @author pangchuanxiao
 * @since 16-3-4
 */
public class QuartzJobLoader {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobLoader.class);

    public static void load(Map conf) {
        Map<String, String> jobInfos = getJobs(conf);
        if (MapUtils.isEmpty(jobInfos)) {
            return;
        }
        for (String jobName : jobInfos.keySet()) {
            Scheduler scheduler = getScheduler(jobName, jobInfos.get(jobName));
            if (null == scheduler) {
                continue;
            }
            try {
                scheduler.start();
            } catch (SchedulerException e) {
                LOG.error("{}", e.getMessage(), e);
            }
        }
    }

    private static Scheduler getScheduler(String jobName, String jobExp) {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setJobClass(QuartzJobExecutors.class);
        jobDetail.setKey(new JobKey(jobName));
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        CronExpression cexp;
        try {
            cexp = new CronExpression(jobExp);
            cronTrigger.setCronExpression(cexp);
            cronTrigger.setName(jobName + "-trigger");
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            return scheduler;
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
        return null;
    }

    private static Map<String, String> getJobs(Map<String, String> conf) {
        Map<String, String> jobMap = Maps.newHashMap();
        for (String line : conf.keySet()) {
            if (!line.startsWith("quartz.")) {
                continue;
            }
            String jobName = line.replace("quartz.", "");
            String jobExp = conf.get(line);
            if (StringUtils.isNotEmpty(jobName) && StringUtils.isNotEmpty(jobExp)) {
                jobMap.put(jobName, jobExp);
            }
        }
        return jobMap;
    }
}
