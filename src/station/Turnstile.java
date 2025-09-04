package station;

public class Turnstile {

    private static long idCounter = 0;

    private long id;

    private boolean isActive;

    private boolean isLocked;

    public Turnstile() {

        this.id = ++idCounter;
        this.isActive = true;
        this.isLocked = true;
    }
}
