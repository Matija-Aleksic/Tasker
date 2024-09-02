package com.javaprojektni.tasker.model;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Mailer {
    private static Properties svojstva = new Properties();


    public static void sendEmail(String to, String subject, String body) throws IOException {
        String MAIL_FILE = "src/main/java/com/javaprojektni/tasker/Files/mail.properties";
        svojstva.load(new FileReader(MAIL_FILE));
        String APP_PASSWORD = svojstva.getProperty("mail.password");
        String USERNAME = "matijaaleksic22@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email");
        }
    }

//    public static void main(String[] args) throws IOException {
//        // Example usage
//        sendEmail("test-recipient@gmail.com", "Test Subject", "This is a test email.");
//    }
}
