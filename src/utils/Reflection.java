package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflection {

    private static final String PAYMENT_SYSTEM_SERVICE_NAME = "application.service.payment.PaymentServiceImpl";

    private static final Class<?> paymentSystemClass;

    static {
        try {
            paymentSystemClass = Class.forName(PAYMENT_SYSTEM_SERVICE_NAME);
            for (Field declaredField : paymentSystemClass.getDeclaredFields()) {
                System.out.println("Field: " + declaredField.getName());
            }
            for (Method declaredMethod : paymentSystemClass.getDeclaredMethods()) {
                System.out.println("Method: " + declaredMethod.getName());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
