package it.zerob.poll.controller;

import it.zerob.poll.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/bo")
public class MailController {

    public static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_+";

    public String generatePassword() {
        int length = 10;
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return password.toString();
    }

    @Autowired
    private MailService mailService;

    @GetMapping("sendMail")
    public ResponseEntity getEmailSent() throws Exception
    {
        //Static
        List<String> mails = new ArrayList<>();
        mails.add("davode.m787@gmail.com");
        boolean response = false;

        for(int i = 0; i < mails.size(); i++)
        {
            response = mailService.sendMailWithAttachment(mails.get(i), "Email prova", generatePassword());
        }

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
