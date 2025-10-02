package application.service.payment;

import java.util.Arrays;

public enum TransactionType {
    PAYMENT("Funds deduction", true, "DR"),
    REFUND("Funds return", false, "CR"),
    AUTHORIZATION("Hold funds", true, "HOLD"),
    CANCELLATION("Cancel transaction", false, "VOID"),
    SETTLEMENT("Finalize transaction", true, "SETTLE");

    private final String description;
    private final boolean affectsBalance;
    private final String code;

    TransactionType(String description, boolean affectsBalance, String code) {
        this.description = description;
        this.affectsBalance = affectsBalance;
        this.code = code;
    }

    public boolean isDebit() {
        return affectsBalance && this != REFUND;
    }

    public String getFullCode() {
        return "TX_" + code + "_" + name();
    }

    public static TransactionType fromCode(String code) {

        return Arrays.stream(TransactionType.values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No transaction type was" +
                        " found by code " + code));
    }
}