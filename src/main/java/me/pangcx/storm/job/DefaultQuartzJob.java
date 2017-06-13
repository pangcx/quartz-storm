package me.pangcx.storm.job;

/**
 * Created by pangchuanxiao on 2017-6-13.
 */
public class DefaultQuartzJob implements QuartzJob {
    private String jobName;
    private String expression;

    public DefaultQuartzJob(String jobName, String expression) {
        this.jobName = jobName;
        this.expression = expression;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getJobName() {
        return jobName;
    }

    @Override
    public String getExpression() {
        return expression;
    }
}
