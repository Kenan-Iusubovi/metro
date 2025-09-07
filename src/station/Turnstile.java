package station;

import ticket.Ticket;

public class Turnstile {

    private static long idCounter = 0;
    private Long id;
    private String code;
    private boolean isActive;
    private boolean isClosed;

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
        return isActive;
    }

    public void activate() {
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void open() {
        this.isClosed = false;
        System.out.println("Turnstile with code" + code + " opened.");
    }

    public void close() {
        this.isClosed = true;
    }

    public void pass(Ticket ticket) {
        if (!isActive) {
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
        this.isActive = active;
    }
}
