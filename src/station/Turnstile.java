package station;

import ticket.Ticket;

public class Turnstile {


    private static long idCounter = 0;

    private long id;

    private String code;

    private boolean active;

    private boolean closed;

    public Turnstile(String code, boolean active, boolean closed) {

        this.id = ++idCounter;
        setCode(code);
        setActive(active);
        setClosed(closed);
    }

    public long getId() {
        return id;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        if (code == null || code.trim().isEmpty()) throw new IllegalArgumentException("code");
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
    }

    public void close() {

        this.closed = true;
    }

    public boolean pass(Ticket ticket) {

        if (!active) {
            System.out.println("Turnstile " + code + " is deactivated.");
            return false;
        }
        if (closed) {
            System.out.println("Turnstile " + code + " is closed.");
            return false;
        }
        if (ticket == null) {
            System.out.println("No ticket presented.");
            return false;
        }
        if (ticket.getStatus() == null) {
            System.out.println("Ticket has no status.");
            return false;
        }

        System.out.println("Ticket " + ticket.getId() + " accepted at " + code);
        return true;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
