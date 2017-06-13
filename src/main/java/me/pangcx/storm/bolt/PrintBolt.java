package me.pangcx.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * quartz-projects
 *
 * @author pangchuanxiao
 * @since 16-2-11
 */
public class PrintBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(PrintBolt.class);

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        LOG.info("tuple : {}", input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
