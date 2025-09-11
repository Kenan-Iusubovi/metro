package application.port;

import java.util.UUID;

public interface EmailService {

    void send(String to, String subject, String text);
    void sendTicketPurchaseSuccess(String to, String passengerName,
                              UUID ticketId, String price);

    void sendRegistrationSuccess(String to, String passengerName);

}
