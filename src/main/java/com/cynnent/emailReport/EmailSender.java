package com.cynnent.emailReport;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.cynnent.utils.ConfigReader;
import com.cynnent.utils.Constants;

public class EmailSender {
    private static final String EMAIL_PROPERTIES_FILE = "email.properties";

    public static void sendEmailReport(String reportFilePath) {
        // Load email properties from file
        ConfigReader configReader = new ConfigReader(Constants.EMAIL_PROPERTIES_FILE);

        // Retrieve the necessary email configuration properties
        String senderEmail = configReader.getProperty("sender.email");
        String senderPassword = configReader.getProperty("sender.password");
        String recipientEmail = configReader.getProperty("recipient.email");
        String smtpHost = configReader.getProperty("smtp.host");
        String smtpPort = configReader.getProperty("smtp.port");

        // Set properties for the email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);

        // Create a Session instance with the email properties and authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new MimeMessage instance
            MimeMessage message = new MimeMessage(session);

            // Set the email properties
            message.setFrom(new InternetAddress(senderEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Test Report");

            // Create the multipart message
            MimeMultipart multipart = new MimeMultipart();

            // Create the text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Please find attached the test report.");
            multipart.addBodyPart(textPart);

            // Create the attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(reportFilePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName("ExtentReport.html");
            multipart.addBodyPart(attachmentPart);

            // Set the content of the message to the multipart message
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException ex) {
            throw new RuntimeException("Failed to send email.", ex);
        }
    }
}
