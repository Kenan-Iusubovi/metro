package service;

public class EmailService {

    public void send(String to, String subject, String text) {

        if (to == null || to.trim().isEmpty()) throw new IllegalArgumentException("to");
        System.out.println("Email to " + to + " " + subject);
        System.out.println(text);
        System.out.println();
    }
}
