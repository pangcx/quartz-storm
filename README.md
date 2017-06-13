# quartz-storm
a lib for storm used to start quartz job as tuple in spout.

Example:

    TopologyBuilder builder = new TopologyBuilder();
    //spout的并发度设置为1，必须
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