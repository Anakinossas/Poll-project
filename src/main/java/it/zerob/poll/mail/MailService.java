package it.zerob.poll.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MailService {



    private JavaMailSender mailSender;

    @Value("${mailSender.email}")
    private String email;

    public boolean sendMailWithAttachment(String to, String subject, String text){

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

}