package application.port;

import domain.train.Train;

@FunctionalInterface
public interface TrainAssignmentStrategy {

    Train find();
}
