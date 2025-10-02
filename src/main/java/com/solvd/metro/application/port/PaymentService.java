package com.solvd.metro.application.port;

import com.solvd.metro.application.exception.PaymentFailedException;
import com.solvd.metro.application.service.payment.PaymentMethod;
import com.solvd.metro.domain.paymentrecord.PaymentReceipt;


import java.math.BigDecimal;

public interface PaymentService {

    PaymentReceipt processPayment(BigDecimal amount,
                                  PaymentMethod method) throws PaymentFailedException;

    PaymentReceipt refund(BigDecimal amount,
                          PaymentMethod method) throws PaymentFailedException;

}
