package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PollController {

    @PostMapping("/dataSurvey")
    public RedirectView getInsertData(@ModelAttribute PollDTO pollDTO, final RedirectAttributes redirectAttributes){

        if(pollDTO != null){
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            redirectAttributes.addFlashAttribute("pollDTO", pollDTO);
            redirectAttributes.addFlashAttribute("username", loggedUser.getUsername());
            return new RedirectView("setDataMail");
        } else{
            return new RedirectView("poll");
        }
    }
}
