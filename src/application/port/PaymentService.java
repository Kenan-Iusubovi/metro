package application.port;

import application.exception.PaymentFailedException;
import application.service.payment.PaymentMethod;
import domain.paymentrecord.PaymentReceipt;

import java.math.BigDecimal;

public interface PaymentService {

    PaymentReceipt processPayment(BigDecimal amount,
                                  PaymentMethod method) throws PaymentFailedException;

    PaymentReceipt refund(BigDecimal amount,
                          PaymentMethod method) throws PaymentFailedException;

}
