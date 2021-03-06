package me.pangcx.storm.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import me.pangcx.storm.bolt.PrintBolt;
import me.pangcx.storm.config.DefaultQuartzConfig;
import me.pangcx.storm.job.DefaultQuartzJob;
import me.pangcx.storm.spout.QuartzSpout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * quartz-projects
 *
 * @author pangchuanxiao
 * @since 16-2-14
 */
public class SimpleQuartzTopology {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleQuartzTopology.class);

    public static void main(String[] args) throws Exception {


        TopologyBuilder builder = new TopologyBuilder();
        //spout的并发度设置为1
        builder.setSpout("spout", new QuartzSpout(), 1);
        //测试bolt，打印定时任务名称
        builder.setBolt("print", new PrintBolt());

        DefaultQuartzConfig conf = new DefaultQuartzConfig();
        conf.setDebug(true);
        //增加定时任务
        conf.addJob(new DefaultQuartzJob("sms-olympics-country", "0/10 * * * * ?"));
        conf.addJob(new DefaultQuartzJob("sms-olympics-city", "1/3 * * * * ?"));

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);

            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }
        else {
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());

        }
    }
}
