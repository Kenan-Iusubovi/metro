package com.solvd.metro.mockconnectionpool;

public class Connection {

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

    @Override
    public String toString() {
        return "MockConnection-" + id;
    }
}
