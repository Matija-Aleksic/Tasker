package com.javaprojektni.tasker.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Mailer {
    private static final Properties svojstva = new Properties();


    public static void sendEmail(String to, String subject, String body, String icsContent) throws IOException {
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

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (icsContent != null && !icsContent.isEmpty()) {
                MimeBodyPart icsPart = new MimeBodyPart();
                icsPart.setContent(icsContent, "text/calendar; charset=utf-8");
                icsPart.setFileName("invite.ics");
                multipart.addBodyPart(icsPart);
            }

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email");
        }
    }

    public static void sendEmailAsync(String to, String subject, String body, String icsContent) throws IOException {
        new Thread(() -> {
            try {
                sendEmail(to, subject, body, icsContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
