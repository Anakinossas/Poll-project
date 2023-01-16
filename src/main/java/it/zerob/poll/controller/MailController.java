package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import it.zerob.poll.mail.MailService;
import it.zerob.poll.repository.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import it.zerob.poll.model.Users;
import it.zerob.poll.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class MailController {

    @Autowired
    private UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UsersRepository usersRepository;
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
    public ResponseEntity getEmailSent() {
        //Static
        List<String> mails = new ArrayList<>();
        mails.add("davode.m787@gmail.com");
        boolean response = false;

        List<Users> usersWithoutPassword = usersRepository.getAllByPasswordIsNull();

        for(int i = 0; i < usersWithoutPassword.size(); i++)
        {
            String associatePassword = generatePassword();
            usersWithoutPassword.get(i).setPassword(passwordEncoder.encode(associatePassword));
            response = mailService.sendMailWithAttachment(usersWithoutPassword.get(i).getUsername(), "Dati di Accesso",
                    "<h1>Dati di Accesso</h1><p>Un <strong>ADMIN</strong> ha inserito la tua email nel servizio di sondaggi <strong>ZeroPoll</strong>.</p>" +
                            "<p>Di conseguenza ti è stata assegnata una password da utilizzare per accedere al portale.</p>" +
                            "<p>La password è la seguente: <strong>" + associatePassword + "</strong></p>" +
                            "<p>Per effettuare il login: " +
                            "<ul>" +
                            "<li>Accedere al link: http://localhost:8080/login</li>" +
                            "<li>Inserire la propria mail e la password che ti abbiamo assegnato</li></ul>");
        for (String mail : mails) {
            response = mailService.sendMailWithAttachment(mail, "Email prova", generatePassword());
        }

        return new ResponseEntity(response, HttpStatus.OK);
    }

//    @PostMapping("setDataMail")
//    public ResponseEntity setDataMail()
//    {
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
