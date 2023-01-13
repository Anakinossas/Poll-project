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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
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
            response = mailService.sendMailWithAttachment(usersWithoutPassword.get(i).getUsername(), "Email prova", associatePassword);
        for (String mail : mails) {
            response = mailService.sendMailWithAttachment(mail, "Email prova", generatePassword());
        }

        usersRepository.saveAll(usersWithoutPassword);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping("setDataMail")
    public void setDataMail(@ModelAttribute("pollDTO") PollDTO pollDTO, @ModelAttribute("username") String username,
                              HttpServletResponse response) throws ParseException, IOException {
        String email;
        String smoker = pollDTO.getSmoker() ? "Si" : "No";

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String reformattedStr = myFormat.format(fromUser.parse(pollDTO.getBirth()));

        email = "<h1>Your inserted data: </h1><br>" +
                "<ul>"
                + "<li> <strong>Height</strong>: " + pollDTO.getHeight() + " cm</li><br>"
                + "<li> <strong>Weight</strong>: " + pollDTO.getWeight() + " kg</li><br>"
                + "<li> <strong>BirthDate</strong>: " + reformattedStr + "</li><br>"
                + "<li> <strong>Smoker</strong>: " + smoker + "</li> </ul>";

        mailService.sendMailWithAttachment(username, "Confirm email", email);
        response.sendRedirect("/login?dataSent");
    }
    @PostMapping("/sendMailWithAnonymousData")
    public ResponseEntity sendAnonymousDataToAdmin(@ModelAttribute("pollDTO") PollDTO pollDTO)
    {
        String dataAnonymousToSend = pollDTO.toString();
        mailService.sendMailWithAttachment("elvislacku37@gmail.com", "Poll data", dataAnonymousToSend);

        return new ResponseEntity<>("Everything is good", HttpStatus.OK);
    }
}
