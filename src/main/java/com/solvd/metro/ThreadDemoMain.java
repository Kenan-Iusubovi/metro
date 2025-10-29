package com.solvd.metro;

import com.solvd.metro.mockconnectionpool.Connection;
import com.solvd.metro.mockconnectionpool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThreadDemoMain {

    private static final Logger logger = LogManager.getLogger(ThreadDemoMain.class);

    private static final int POOL_SIZE = 5;
    private static final int THREAD_COUNT = 7;

    public static void main(String[] args) {

        ConnectionPool connectionPool = ConnectionPool.getInstance(POOL_SIZE);

        Thread[] connectionThreadS = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++){
            connectionThreadS[i] = new Thread(() -> {
                Connection connection = null;
                try {
                    connection = connectionPool.getConnection();
                    connection.create();
                    Thread.sleep(3000);
                    connection.connect();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error(e.getMessage());
                }finally {
                    if (connection != null){
                        try {
                            connectionPool.releaseConnection(connection);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            logger.error(e.getMessage());
                        }
                    }
                }
            },"Connection thread number - " + (i + 1));
            connectionThreadS[i].start();
        }


        for (Thread thread : connectionThreadS){
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error(e.getMessage());
            }
        }
    }
}
