package com.solvd.metro.domain.paymentrecord;

import com.solvd.metro.application.service.payment.PaymentMethod;
import com.solvd.metro.application.service.payment.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentReceipt(

        UUID id,
        TransactionType type,
        BigDecimal amount,
        PaymentMethod method,
        LocalDateTime timestamp
) {
}
