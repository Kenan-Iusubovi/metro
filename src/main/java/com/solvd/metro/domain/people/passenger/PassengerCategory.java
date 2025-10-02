package com.solvd.metro.domain.people.passenger;

import java.math.BigDecimal;
import java.util.Arrays;

public enum PassengerCategory {
    ADULT(0, "Standard adult fare"),
    CHILD(50, "Children under 12"),
    STUDENT(25, "Students with valid ID"),
    SENIOR(30, "Seniors 65+"),
    DISABLED(40, "Persons with disabilities");

    private final int discountPercentage;
    private final String description;

    static {
        System.out.println("PassengerCategory enum loaded with " + values().length + " categories");
    }

    PassengerCategory(int discountPercentage, String description) {
        this.discountPercentage = discountPercentage;
        this.description = description;
    }

    public BigDecimal applyDiscount(BigDecimal basePrice) {
        switch (this) {
            case CHILD:
                return basePrice.multiply(BigDecimal.valueOf(0.5));
            case STUDENT:
                return basePrice.multiply(BigDecimal.valueOf(0.75));
            case SENIOR:
                return basePrice.multiply(BigDecimal.valueOf(0.7));
            case DISABLED:
                return basePrice.multiply(BigDecimal.valueOf(0.6));
            default:
                return basePrice;
        }
    }

    public String getFullDescription() {
        return name() + ": " + description + " (" + discountPercentage + "% discount)";
    }

    public static PassengerCategory fromDescription(String desc) {
        return Arrays.stream(PassengerCategory.values())
                .filter(passengerCategory -> passengerCategory.description.equals(desc))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No category with description "
                        + desc + " was found"));
    }
}