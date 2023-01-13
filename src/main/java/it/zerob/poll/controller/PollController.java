package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PollController {

    @PostMapping("/dataSurvey")
    public RedirectView getInsertData(@ModelAttribute PollDTO pollDTO){

        return new RedirectView("sendMail");
    }
}
