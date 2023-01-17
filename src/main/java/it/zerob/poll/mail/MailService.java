package it.zerob.poll.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * <strong>Service</strong> that implements method to send the email
 */
@Service
public class MailService {

    private JavaMailSender mailSender;

    @Value("${mailSender.email}")
    private String email;

    /**
     * Method that sends the email with the given parameters using JavaMailSender class
     * @param to Specify the receiver email address
     * @param subject Specify the email subject
     * @param text Contains the email content
     * @return true if the user receives the email, otherwise return false
     */
    public boolean sendMailWithAttachment(String to, String subject, String text) {

        MimeMessage message = mailSender.createMimeMessage();

        //Setting data of the new mail to send
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to); //Receiver
            helper.setFrom(email); //Sender
            helper.setSubject(subject); //Subject
            helper.setText(text, true); //Html content of the email
            mailSender.send(message);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

}