package utils;

import domain.parking.TrainParking;
import domain.train.Train;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Reflection {

    private static final String PARKING_TRAIN_PARKING_NAME = "domain.parking.TrainParking";

    public static TrainParking<Train> createTrainParking() {
        try {
            Class<TrainParking> trainParkingClass = (Class<TrainParking>) Class.forName(PARKING_TRAIN_PARKING_NAME);
            Constructor<TrainParking> constructor = trainParkingClass.getConstructor(String.class);
            TrainParking<Train> parking = constructor.newInstance("Tbilisi parking");

            return parking;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create TrainParking instance via reflection", e);
        }
    }

    public static int getParkedTrainCountReflectively() {
        try {
            TrainParking<Train> parking = createTrainParking();
            Method getParkedTrainCount = parking.getClass().getDeclaredMethod("getParkedTrainCount");
            return (int) getParkedTrainCount.invoke(parking);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get parked train count via reflection", e);
        }
    }
}
