package service;

import people.passenger.PassengerCategory;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class FareCalculator {

    private static BigDecimal baseCost = new BigDecimal("2.50");
    private static int childDiscountPercentage;
    private static int studentDiscountPercentage;
    private static int seniorDiscountPercentage;
    private static int disableDiscountPercentage;

    public static BigDecimal calculateTicketPrice(PassengerCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("Passenger category cannot be null");
        }

        BigDecimal discount;
        switch (category) {
            case CHILD -> discount = calculateDiscountAmount(childDiscountPercentage);
            case STUDENT -> discount = calculateDiscountAmount(studentDiscountPercentage);
            case SENIOR -> discount = calculateDiscountAmount(seniorDiscountPercentage);
            case DISABLED -> discount = calculateDiscountAmount(disableDiscountPercentage);
            default -> {
                return baseCost;
            }
        }
        return baseCost.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getBaseCost() {
        return baseCost;
    }

    public static void setBaseCost(BigDecimal baseCost) {
        if (baseCost == null || baseCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Base cost must be positive");
        }
        FareCalculator.baseCost = baseCost;
    }

    public static void setBaseCost(int baseCost) {
        if (baseCost < 0) {
            throw new IllegalArgumentException("Base cost must be positive");
        }
        FareCalculator.baseCost = BigDecimal.valueOf(baseCost);
    }

    public static void setBaseCost(long baseCost) {
        if (baseCost < 0) {
            throw new IllegalArgumentException("Base cost must be positive");
        }
        FareCalculator.baseCost = BigDecimal.valueOf(baseCost);
    }

    public static void setBaseCost(double baseCost) {
        if (baseCost < 0) {
            throw new IllegalArgumentException("Base cost must be positive");
        }
        FareCalculator.baseCost = BigDecimal.valueOf(baseCost);
    }

    public static int getChildDiscountPercentage() {
        return childDiscountPercentage;
    }

    public static void setChildDiscountPercentage(int childDiscountPercentage) {
        checkDiscountPercentage(childDiscountPercentage);
        FareCalculator.childDiscountPercentage = childDiscountPercentage;
    }

    public static int getStudentDiscountPercentage() {
        return studentDiscountPercentage;
    }

    public static void setStudentDiscountPercentage(int studentDiscountPercentage) {
        checkDiscountPercentage(studentDiscountPercentage);
        FareCalculator.studentDiscountPercentage = studentDiscountPercentage;
    }

    public static int getSeniorDiscountPercentage() {
        return seniorDiscountPercentage;
    }

    public static void setSeniorDiscountPercentage(int seniorDiscountPercentage) {
        checkDiscountPercentage(seniorDiscountPercentage);
        FareCalculator.seniorDiscountPercentage = seniorDiscountPercentage;
    }

    public static int getDisableDiscountPercentage() {
        return disableDiscountPercentage;
    }

    public static void setDisableDiscountPercentage(int disableDiscountPercentage) {
        checkDiscountPercentage(disableDiscountPercentage);
        FareCalculator.disableDiscountPercentage = disableDiscountPercentage;
    }

    private static void checkDiscountPercentage(int percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
    }

    private static BigDecimal calculateDiscountAmount(int discountPercentage) {
        return baseCost.multiply(BigDecimal.valueOf(discountPercentage))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}