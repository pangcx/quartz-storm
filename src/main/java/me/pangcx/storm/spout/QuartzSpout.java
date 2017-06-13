package me.pangcx.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

/**
 * quartz-projects
 *
 * @author pangchuanxiao
 * @since 16-2-17
 */
public class QuartzSpout extends BaseRichSpout {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzSpout.class);
    private SpoutOutputCollector collector;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("jobName"));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        QuartzJobLoader.load(conf);
    }

    @Override
    public void nextTuple() {
        try {
            String jobName = QuartzJobExecutors.take();
            if (StringUtils.isEmpty(jobName)) {
                LOG.warn("quartz job name is empty, will not emit this job. please check the config.");
                return;
            }
            String messageId = jobName + "_" + DateFormat.getTimeInstance(DateFormat.FULL).format(new Date());
            collector.emit(new Values(jobName), messageId);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage(), e);
        }
    }

    @Override
    public void ack(Object msgId) {
        super.ack(msgId);
        LOG.info("quartz job : {} succeed!", msgId);
    }

    @Override
    public void fail(Object msgId) {
        super.fail(msgId);
        LOG.info("quartz job : {} fail!", msgId);
    }
}
