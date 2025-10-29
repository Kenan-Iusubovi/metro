package com.solvd.metro.mockconnectionpool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Connection {

    private static final Logger logger = LogManager.getLogger(Connection.class);

    private static int idCounter = 0;

    private final int id;

    public Connection() {
        this.id = ++idCounter;
    }

    public void connect() {
        System.out.println("Connecting using " + this);
    }

    public void disconnect() {
        System.out.println("Disconnecting " + this);
    }

    public void create() {
        logger.info("Connection {}: create", id);
    }

    public String get(int id) {
        String result = "Data-" + id;
        logger.info("Connection {}: get({}) -> {}", this.id, id, result);
        return result;
    }

    public void update(int id) {
        logger.info("Connection {}: update to ({})", this.id, id);
    }

    public void delete(int id) {
        logger.info("Connection {}: delete({})", this.id, id);
    }

    @Override
    public String toString() {
        return "Connection-" + id;
    }
}
