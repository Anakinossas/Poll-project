package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import it.zerob.poll.mail.MailService;
import it.zerob.poll.model.Requests;
import it.zerob.poll.repository.RequestsRepository;
import it.zerob.poll.repository.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import it.zerob.poll.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    //Method that generate a random password for the users
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
    @Autowired
    private RequestsRepository requestsRepository;

    @GetMapping("sendMail")
    public ResponseEntity getEmailSent() {
        boolean response = false;

        List<Users> usersWithoutPassword = usersRepository.getAllByPasswordIsNull();

        //Sending to every user inserted into the db from the Excel the registration email
        for (Users users : usersWithoutPassword) {
            String associatePassword = generatePassword();
            users.setPassword(passwordEncoder.encode(associatePassword));
            response = mailService.sendMailWithAttachment(users.getUsername(), "Dati di Accesso",
                    "<h1>Dati di Accesso</h1><p>Un <strong>ADMIN</strong> ha inserito la tua email nel servizio di sondaggi <strong>ZeroPoll</strong>.</p>" +
                            "<p>Di conseguenza ti è stata assegnata una password da utilizzare per accedere al portale.</p>" +
                            "<p>La password è la seguente: <strong>" + associatePassword + "</strong></p>" +
                            "<p>Per effettuare il login: " +
                            "<ul>" +
                            "<li>Accedere al link: http://localhost:8080/login</li>" +
                            "<li>Inserire la propria mail e la password che ti abbiamo assegnato</li></ul>");
        }

        usersRepository.saveAll(usersWithoutPassword);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    //Method that sends the confirmation email to the users and the anonymous data to the admin
    @RequestMapping("setDataMail")
    public void setDataMail(@ModelAttribute("pollDTO") PollDTO pollDTO, @ModelAttribute("username") String username,
                            HttpServletResponse response, HttpServletRequest request)
            throws ParseException, IOException, ServletException {

        String confirmEmail;
        String smoker = pollDTO.getSmoker() ? "Si" : "No";

        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String reformattedStr = myFormat.format(fromUser.parse(pollDTO.getBirth()));

        //Email that has to be sent to the user with his data
        confirmEmail = "<h1>Your inserted data: </h1><br>" +
                "<ul>"
                + "<li> <strong>Height</strong>: " + pollDTO.getHeight() + " cm</li><br>"
                + "<li> <strong>Weight</strong>: " + pollDTO.getWeight() + " kg</li><br>"
                + "<li> <strong>BirthDate</strong>: " + reformattedStr + "</li><br>"
                + "<li> <strong>Smoker</strong>: " + smoker + "</li> </ul>";

        //Email that has to be sent to the admin with user data
        String email1 = "<h1>Data of anonymous user: </h1><br>" +
                "<ul>"
                + "<li> <strong>Height</strong>: " + pollDTO.getHeight() + " cm</li><br>"
                + "<li> <strong>Weight</strong>: " + pollDTO.getWeight() + " kg</li><br>"
                + "<li> <strong>BirthDate</strong>: " + reformattedStr + "</li><br>"
                + "<li> <strong>Smoker</strong>: " + smoker + "</li> </ul>";

        mailService.sendMailWithAttachment(username, "Confirm email", confirmEmail);
        mailService.sendMailWithAttachment("francesco.tierno@zerob.it", "Anonymous poll data", email1);

        //Forced logout avoid another submit from the user that already answered to the poll
        request.logout();

        response.sendRedirect("/login?dataSent");
    }

    @GetMapping("/sendNotification")
    public ResponseEntity sendNotification()
    {
        boolean response = false;

        List<Users> usersWithoutRequest = usersRepository.getUsersWithNoSubmit();

        for(int i = 0; i < usersWithoutRequest.size(); i++)
        {
            response = mailService.sendMailWithAttachment(usersWithoutRequest.get(i).getUsername(), "Avviso",
                    "<h1>Incitamento</h1><p>Questa mail ti è stata spedita perché non hai ancora completato il sondaggio <strong>ZeroPoll</strong>.</p>" +
                            "<p>Ti invitiamo a compilarlo al più presto.</p>");
        }

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
