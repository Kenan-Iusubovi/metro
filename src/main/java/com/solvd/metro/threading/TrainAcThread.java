package com.solvd.metro.threading;

import com.solvd.metro.domain.train.Train;

public class TrainAcThread extends Thread{

    private final Train train;
    private final byte temperature;

    public TrainAcThread(Train train, byte temperature) {
        this.train = train;
        this.temperature = temperature;
    }

    @Override
    public void run() {
        train.setAcTemperature(temperature);
    }
}
