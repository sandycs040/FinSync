package com.example.FinSync.utils;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class LogConnectPoolStatus {

    @Autowired
    HikariDataSource dataSource;

    private static Logger logger= Logger.getLogger(LogConnectPoolStatus.class.getName());

    public  void logConnectionPoolSttaus(){
        HikariPoolMXBean poolMXBean = dataSource.getHikariPoolMXBean();
        if(poolMXBean != null){
            logger.info("HikariCP Pool Status:");
            logger.info("Active Connections: " + poolMXBean.getActiveConnections());
            logger.info("Idle Connections: " + poolMXBean.getIdleConnections());
            logger.info("Total Connections: " + poolMXBean.getTotalConnections());
            logger.info("Threads Awaiting Connection: " + poolMXBean.getThreadsAwaitingConnection());

        }
    }
}
