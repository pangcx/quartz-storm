package me.pangcx.storm.job;

/**
 * trunk.
 *
 * @author pangchuanxiao
 * @since 2016/4/20
 */
public interface QuartzJob {

    public String getJobName();

    public String getExpression();
}
