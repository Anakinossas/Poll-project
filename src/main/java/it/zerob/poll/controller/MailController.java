package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import it.zerob.poll.mail.MailService;
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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class MailController {

    @Autowired
    private UsersRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
        boolean response = false;

        List<Users> usersWithoutPassword = usersRepository.getAllByPasswordIsNull();

        for(int i = 0; i < usersWithoutPassword.size(); i++)
        {
            String associatePassword = generatePassword();
            usersWithoutPassword.get(i).setPassword(passwordEncoder.encode(associatePassword));
            response = mailService.sendMailWithAttachment(usersWithoutPassword.get(i).getUsername(), "Email prova", associatePassword);
        }

        usersRepository.saveAll(usersWithoutPassword);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/sendMailWithAnonymousData")
    public ResponseEntity sendAnonymousDataToAdmin(@ModelAttribute("pollDTO") PollDTO pollDTO)
    {
        String dataAnonymousToSend = pollDTO.toString();
        mailService.sendMailWithAttachment("elvislacku37@gmail.com", "Poll data", dataAnonymousToSend);

        return new ResponseEntity<>("Everything is good", HttpStatus.OK);
    }
}
