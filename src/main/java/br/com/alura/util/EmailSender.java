package br.com.alura.util;

public class EmailSender {
    public static void send(String recipientEmail, String subject, String body) {
        System.out.printf("Simulating sending email to [%s]:%n", recipientEmail);
        System.out.printf("Subject: %s%nBody: %s%n", subject, body);
    }
}
