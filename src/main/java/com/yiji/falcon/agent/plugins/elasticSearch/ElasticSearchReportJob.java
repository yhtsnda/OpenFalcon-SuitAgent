/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */
package com.yiji.falcon.agent.plugins.elasticSearch;

import com.yiji.falcon.agent.falcon.ReportMetrics;
import com.yiji.falcon.agent.plugins.JMXMetricsValue;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 修订记录:
 * guqiu@yiji.com 2016-06-22 17:48 创建
 */

/**
 * @author guqiu@yiji.com
 */
public class ElasticSearchReportJob implements Job {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JMXMetricsValue metricValue = new ElasticSearchMetricsValue();
        try {
            ReportMetrics.push(metricValue.getReportObjects());
        } catch (Exception e) {
            log.error("agent运行异常",e);
        }
    }
}
