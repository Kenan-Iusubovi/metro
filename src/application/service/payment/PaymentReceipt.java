package application.service.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentReceipt(

        UUID id,
        TransactionType type,
        BigDecimal amount,
        PaymentMethod method,
        LocalDateTime timestamp
){}
