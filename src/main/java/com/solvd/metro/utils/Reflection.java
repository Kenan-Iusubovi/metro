package com.solvd.metro.utils;


import com.solvd.metro.application.annotation.SetLocation;
import com.solvd.metro.domain.parking.TrainParking;
import com.solvd.metro.domain.train.Train;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {

    private static final String PARKING_TRAIN_PARKING_NAME = "com.solvd.metro.domain.parking.TrainParking";

    public static TrainParking<Train> createTrainParking() {
        try {
            Class<TrainParking> trainParkingClass = (Class<TrainParking>) Class.forName(PARKING_TRAIN_PARKING_NAME);

            Constructor<TrainParking> constructor = trainParkingClass.getConstructor(String.class);
            TrainParking<Train> parking = constructor.newInstance("Tbilisi parking");

            for (Field declaredField : trainParkingClass.getDeclaredFields()){
                if (declaredField.isAnnotationPresent(SetLocation.class)){
                    SetLocation setLocation = declaredField.getAnnotation(SetLocation.class);
                    String value = setLocation.value();

                    declaredField.setAccessible(true);
                    declaredField.set(parking,value);
                    declaredField.setAccessible(false);
                }
            }
            return parking;

        } catch (Exception e) {
            throw new RuntimeException("Failed to create TrainParking instance via reflection", e);
        }
    }

    public static int getParkedTrainCountReflectively() {
        try {
            TrainParking<Train> parking = createTrainParking();
            Method getParkedTrainCount = parking.getClass().getDeclaredMethod("getParkedTrainCount");
            int parkedTrainCount = (int) getParkedTrainCount.invoke(parking);

            System.out.println("parked train count = " + parkedTrainCount);
            return parkedTrainCount;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get parked train count via reflection", e);
        }
    }
}
