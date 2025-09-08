package station;

import ticket.Ticket;

public class Turnstile {

    private static long idCounter = 0;

    private Long id;
    private String code;
    private boolean active;
    private boolean closed;

    public Turnstile(String code, boolean active) {
        this.id = ++idCounter;
        setCode(code);
        setActive(active);
        close();
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()){
            throw new IllegalArgumentException("Code can't be null or negative.");
        }
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isClosed() {
        return closed;
    }

    public void open() {
        this.closed = false;
        System.out.println("Turnstile with code" + code + " opened.");
    }

    public void close() {
        this.closed = true;
    }

    public void pass(Ticket ticket) {
        if (!active) {
            throw new RuntimeException("Turnstile " + code + " is deactivated.");
        }
        if (ticket == null) {
            throw new RuntimeException("No ticket presented.");
        }
        if (ticket.useForEntry()){
            System.out.println("Ticket " + ticket.getCode() + " accepted at turnstile " + code);
            open();
            System.out.println("Passenger goes throw.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Passenger goes inside the station.");
            close();
            System.out.println("Turnstile " + code + " is closed!");
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
