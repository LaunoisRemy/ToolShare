package util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Class to send mail
 */
public class Mail {
    public static final Properties MAIL = PropertiesResources.getMailProperties();
    public static final Session SESSION = creerSession();

    /**
     * Save properties of the session to send mail
     * @return
     */
    private static Session creerSession () {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MAIL.getProperty("ADDRESS"), MAIL.getProperty("PASSWORD"));
                    }
                });
    }

    /**
     * Method to send mail with session
     * @param subject Object of the mail
     * @param text Content of the mail
     * @param mailReceveir Adress mail of the receiver
     */
    public static void sendMail (String subject, String text, String mailReceveir){
        try {
            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(MAIL.getProperty("ADDRESS")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(mailReceveir));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
