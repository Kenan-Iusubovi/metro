package service;

import people.passenger.PassengerCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FareCalculator {

    private BigDecimal baseCost = new BigDecimal("2.50");
    private short childDiscountPercentage = 100;
    private short studentDiscountPercentage = 50;
    private short seniorDiscountPercentage = 75;
    private short disableDiscountPercentage = 100;

    public BigDecimal calculateTicketPrice(PassengerCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("Passenger category can't be null.");
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
        return baseCost.subtract(discount);
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(BigDecimal baseCost) {
        if (baseCost == null || baseCost.signum() < 0) {
            throw new IllegalArgumentException("Base cost can't be null or negative.");
        }
        this.baseCost = baseCost;
    }

    public short getChildDiscountPercentage() {
        return childDiscountPercentage;
    }

    public void setChildDiscountPercentage(short childDiscountPercentage) {
        checkNewDiscountPercentage(childDiscountPercentage);
        this.childDiscountPercentage = childDiscountPercentage;
    }

    public short getStudentDiscountPercentage() {
        return studentDiscountPercentage;
    }

    public void setStudentDiscountPercentage(short studentDiscountPercentage) {
        checkNewDiscountPercentage(studentDiscountPercentage);
        studentDiscountPercentage = studentDiscountPercentage;
    }

    public short getSeniorDiscountPercentage() {
        return seniorDiscountPercentage;
    }

    public void setSeniorDiscountPercentage(short seniorDiscountPercentage) {
        checkNewDiscountPercentage(seniorDiscountPercentage);
        this.seniorDiscountPercentage = seniorDiscountPercentage;
    }

    public short getDisableDiscountPercentage() {
        return disableDiscountPercentage;
    }

    public void setDisableDiscountPercentage(short disableDiscountPercentage) {
        checkNewDiscountPercentage(disableDiscountPercentage);
        this.disableDiscountPercentage = disableDiscountPercentage;
    }

    private void checkNewDiscountPercentage(short percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Discount should be between 0% and 100%");
        }
    }

    private BigDecimal calculateDiscountAmount(short discountPercentage) {
        return baseCost.multiply(BigDecimal.valueOf(discountPercentage))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
}

