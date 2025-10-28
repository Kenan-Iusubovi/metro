package com.solvd.metro.mockconnectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private final static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private final BlockingQueue<Connection> pool;

    private static volatile ConnectionPool instance;


    private ConnectionPool(int poolSize) {
        pool = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++){
            pool.add(new Connection());
        }
    }

    public static ConnectionPool getInstance(int poolSize){
        if (instance == null){
            synchronized (Connection.class){
                if (instance == null){
                    instance = new ConnectionPool(poolSize);
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        Connection connection = pool.take();
        logger.info("{} acquired {}", Thread.currentThread().getName(), connection);
        return connection;
    }

    public void releaseConnection(Connection connection){
        if (connection != null){
            pool.offer(connection);
            logger.info("{} released {}", Thread.currentThread().getName(), connection);
        }
    }
}
