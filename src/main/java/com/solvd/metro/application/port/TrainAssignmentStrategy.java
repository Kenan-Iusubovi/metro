package com.solvd.metro.application.port;

import com.solvd.metro.domain.train.Train;

@FunctionalInterface
public interface TrainAssignmentStrategy {

    Train find();
}
