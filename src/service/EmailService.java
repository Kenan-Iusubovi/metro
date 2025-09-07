package service;

import java.util.UUID;

public class EmailService {

    private String senderName = "noreply@metro.com";

    public void send(String to, String subject, String text) {
        if (to == null || to.isBlank()) throw new IllegalArgumentException("to");

        System.out.println("====================================");
        System.out.println(" 📧 EMAIL SENT ");
        System.out.println("------------------------------------");
        System.out.println("To      : " + to);
        System.out.println("Subject : " + subject);
        System.out.println("------------------------------------");
        System.out.println(text);
        System.out.println(senderName);
        System.out.println("====================================");
        System.out.println();
    }

    public void sendTicketPurchaseSuccess(String to, String passengerName, UUID ticketId, String price) {
        String subject = "🎟 Ticket Purchase Success";
        String body =
                "Hello " + passengerName + ",\n\n" +
                        "Your ticket has been successfully purchased.\n" +
                        "Ticket ID : " + ticketId + "\n" +
                        "Price     : " + price + "\n\n" +
                        "Thank you for choosing our metro system!";
        send(to, subject, body);
    }

    public void sendRegistrationSuccess(String to, String passengerName) {
        String subject = "✅ Registration Completed";
        String body =
                "Welcome " + passengerName + "!\n\n" +
                        "Your registration was successful.\n" +
                        "You can now buy tickets, use turnstiles, and enjoy your metro rides.\n\n" +
                        "We are glad to have you onboard!";
        send(to, subject, body);
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        if (senderName == null || senderName.isBlank()) {
            throw new IllegalArgumentException("Sender name can't be null or empty.");
        }
        this.senderName = senderName;
    }
}
