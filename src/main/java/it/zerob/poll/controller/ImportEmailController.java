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

@RestController
@MultipartConfig()
public class ImportEmailController
{
    @Autowired
    private UsersRepository userRepository;

    @PostMapping("/importEmails")
    public ResponseEntity<?> importEmails(HttpServletRequest request) throws ServletException, IOException
    {
        Part importEmailPart = request.getPart("IMPORT_EMAIL");
        boolean isAvailable;

        if(importEmailPart != null)
        {
            try
            {
                List<Users> emailUsersFromExcel = new ReportEmailExcel().importEmails(importEmailPart.getInputStream());

                for (Users users : emailUsersFromExcel) {
                    isAvailable = userRepository.findByUsername(users.getUsername()) == null;

                    if(isAvailable)
                    {
                        userRepository.save(users);
                        return new ResponseEntity<>("OK", HttpStatus.OK);
                    }
                }

                return null;

            } catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
           return new ResponseEntity<>("Not working", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
