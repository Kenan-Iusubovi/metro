package train;

import utils.ArrayUtils;

public class Train {

    private static long idCounter = 0;

    private long id;

    private String code;

    private int capacity;

    private double maxSpeed;

    private boolean inService;

    private TrainStatus status;

    private Carriage[] carriages;

    private int onboardCount;

    private byte temperature;

    public Train(String code, int capacity,
                 double maxSpeed, boolean inService,
                 TrainStatus status, byte temperature) {
        this.id = ++idCounter;
        setCode(code);
        setCapacity(capacity);
        setMaxSpeed(maxSpeed);
        setInService(inService);
        setStatus(status);
        setTemperature(temperature);
        this.onboardCount = 0;
        this.carriages = new Carriage[0];
    }

    public boolean board(int count) {

        if (count <= 0) return false;
        if (!inService) return false;
        if (onboardCount + count > capacity) return false;
        onboardCount += count;
        return true;
    }

    public boolean alight(int count) {

        if (count <= 0) return false;
        if (count > onboardCount) count = onboardCount;
        onboardCount -= count;
        return true;
    }

    public boolean isFull() {

        return onboardCount >= capacity;
    }

    public long getId() {

        return id;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        if (code == null || code.isBlank())
            throw new IllegalArgumentException("code");

        this.code = code;
    }

    public int getCapacity() {

        return capacity;
    }

    public void setCapacity(int capacity) {

        if (capacity <= 0)
            throw new IllegalArgumentException("capacity");

        this.capacity = capacity;
    }

    public double getMaxSpeed() {

        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {

        if (maxSpeed <= 0)
            throw new IllegalArgumentException("maxSpeed");

        this.maxSpeed = maxSpeed;
    }

    public boolean isInService() {

        return inService;
    }

    public void setInService(boolean inService) {

        this.inService = inService;
    }

    public TrainStatus getStatus() {

        return status;
    }

    public void setStatus(TrainStatus status) {

        if (status == null)
            throw new IllegalArgumentException("status");

        this.status = status;

    }

    public Carriage[] getCarriages() {

        return carriages;
    }

    public void addCarriage(Carriage carriage) {
        this.carriages = (Carriage[]) ArrayUtils.add(this.carriages, carriage);
    }

    public void removeCarriage(Carriage carriage) {
        this.carriages = (Carriage[]) ArrayUtils.delete(this.carriages, carriage);
    }

    public int getOnboardCount() {

        return onboardCount;
    }

    public void setOnboardCount(int onboardCount) {

        if (onboardCount < 0 || onboardCount > capacity)
            throw new IllegalArgumentException("onboardCount");

        this.onboardCount = onboardCount;
    }

    public byte getTemperature() {

        return temperature;
    }

    public void setTemperature(byte temperature) {

        if (temperature < 16 || temperature > 32)
            throw new IllegalArgumentException("temperature");

        this.temperature = temperature;
    }
}
