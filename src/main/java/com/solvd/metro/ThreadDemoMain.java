package com.solvd.metro;

import com.solvd.metro.mockconnectionpool.Connection;
import com.solvd.metro.mockconnectionpool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadDemoMain {

    private static final Logger logger = LogManager.getLogger(ThreadDemoMain.class);

    private static final int POOL_SIZE = 5;
    private static final int THREAD_COUNT = 7;
    private static final int ALL_EXECUTOR_THREADS_TIMEOUT_IN_SECONDS = 12;

    public static void main(String[] args) {

        ConnectionPool connectionPool = ConnectionPool.getInstance(POOL_SIZE);

        Thread[] connectionThreadS = new Thread[THREAD_COUNT];

        logger.error("NO EXECUTOR SERVICE ");

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

        logger.error("WITH EXECUTOR SERVICE ");

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++){
            int threadNumber = i + 1;
            executorService.submit(() -> {
                Connection connection = null;
                try {
                    connection = connectionPool.getConnection();
                    connection.create();
                    logger.info("Thread number - {} created", threadNumber);
                    Thread.sleep(3000);
                    connection.connect();
                    logger.info("Thread number - {} connected", threadNumber);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error(e.getMessage());
                }finally {
                    if (connection != null){
                        try {
                            connectionPool.releaseConnection(connection);
                            logger.warn("Thread number - {} disconnected", threadNumber);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            logger.error(e.getMessage());
                        }
                    }
                }
            });
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(ALL_EXECUTOR_THREADS_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
            logger.error(e.getMessage());
        }
    }

}
