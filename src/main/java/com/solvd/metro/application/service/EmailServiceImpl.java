package com.solvd.metro.application.service;


import com.solvd.metro.application.port.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class EmailServiceImpl implements EmailService {

    private static final String senderName = "noreply@metro.com";
    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

    @Override
    public void send(String to, String subject, String text) {
        if (to == null || to.isBlank()) throw new IllegalArgumentException("to");

        logger.info("====================================");
        logger.info(" 📧 EMAIL SENT ");
        logger.info("------------------------------------");
        logger.info("To      : {}", to);
        logger.info("Subject : {}", subject);
        logger.info("------------------------------------");
        logger.info(text);
        logger.info(senderName);
        logger.info("====================================");
        logger.info("");
    }

    @Override
    public void sendTicketPurchaseSuccess(String to, String passengerName,
                                          UUID ticketId, String price) {
        String subject = "🎟 Ticket Purchase Success";
        String body =
                "Hello " + passengerName + ",\n\n" +
                        "Your ticket has been successfully purchased.\n" +
                        "Ticket ID : " + ticketId + "\n" +
                        "Price     : " + price + "\n\n" +
                        "Thank you for choosing our metro system!";
        send(to, subject, body);
    }

    @Override
    public void sendRegistrationSuccess(String to, String passengerName) {
        String subject = "✅ Registration Completed";
        String body =
                "Welcome " + passengerName + "!\n\n" +
                        "Your registration was successful.\n" +
                        "You can now buy tickets, use turnstiles, and enjoy your metro rides.\n\n" +
                        "We are glad to have you onboard!";
        send(to, subject, body);
    }
}
