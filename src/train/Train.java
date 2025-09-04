package train;

import people.worker.Worker;
import route.Line;

import java.util.List;

public class Train {

    private static long idCounter = 0;

    private long id;

    private long number;

    private Line line;

    private List<Carriage> carriages;

    private Worker worker;

    private TrainStatusE trainStatus;

    public Train(long number, Line line, List<Carriage> carriages,
                 Worker worker, TrainStatusE trainStatus) {
        this.id = ++idCounter;
        this.number = number;
        this.line = line;
        this.carriages = carriages;
        this.worker = worker;
        this.trainStatus = trainStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public List<Carriage> getCarriages() {
        return carriages;
    }

    public void setCarriages(List<Carriage> carriages) {
        this.carriages = carriages;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public TrainStatusE getTrainStatus() {
        return trainStatus;
    }

    public void setTrainStatus(TrainStatusE trainStatus) {
        this.trainStatus = trainStatus;
    }
}
