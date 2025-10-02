package com.solvd.metro.application.service.payment;

import java.math.BigDecimal;

public enum PaymentMethod {
    CREDIT_CARD("Visa/Mastercard", true, 0.02),
    DEBIT_CARD("Bank debit card", true, 0.01),
    METRO_CARD("Prepaid metro card", false, 0.0),
    MOBILE_WALLET("Apple/Google Pay", true, 0.015),
    CASH("Physical currency", false, 0.0);

    private final String description;
    private final boolean electronic;
    private final double feePercentage;

    PaymentMethod(String description, boolean electronic, double feePercentage) {
        this.description = description;
        this.electronic = electronic;
        this.feePercentage = feePercentage;
    }

    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(feePercentage));
    }

    public boolean requiresProcessing() {
        return electronic;
    }

    public String getPaymentDetails() {
        return name() + " (" + description + ")" +
                (feePercentage > 0 ? " - Fee: " + (feePercentage * 100) + "%" : "");
    }
}