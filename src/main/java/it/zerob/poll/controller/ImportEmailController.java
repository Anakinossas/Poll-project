package it.zerob.poll.controller;

import it.zerob.poll.model.Users;
import it.zerob.poll.report.ReportEmailExcel;
import it.zerob.poll.repository.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * <strong>Controller</strong> with post mapping method for email import
 */
@RestController
@MultipartConfig()
public class ImportEmailController {
    @Autowired
    private UsersRepository userRepository;

    /**
     * Method that is activated when an ADMIN press the "Load e-mails" button
     * @param request Request object that contains the Excel import file
     * @return OK and status code 200 if the user's insert works, returns null if every username is not available
     * either returns status code 500 if the file is not available
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping("/importEmails")
    public ResponseEntity<?> importEmails(HttpServletRequest request) throws ServletException, IOException {
        Part importEmailPart = request.getPart("IMPORT_EMAIL");
        boolean isAvailable;

        if (importEmailPart != null) {
            try {
                List<Users> emailUsersFromExcel = new ReportEmailExcel().importEmails(importEmailPart.getInputStream());

                for (Users users : emailUsersFromExcel) {
                    isAvailable = userRepository.findByUsername(users.getUsername()) == null;

                    if (isAvailable) {
                        userRepository.save(users);
                        return new ResponseEntity<>("OK", HttpStatus.OK);
                    }
                }

                return null;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return new ResponseEntity<>("Not working", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
