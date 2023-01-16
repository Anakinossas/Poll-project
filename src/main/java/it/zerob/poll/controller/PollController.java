package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import it.zerob.poll.model.Polls;
import it.zerob.poll.model.Requests;
import it.zerob.poll.model.Users;
import it.zerob.poll.repository.PollsRepository;
import it.zerob.poll.repository.RequestsRepository;
import it.zerob.poll.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class PollController {

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PollsRepository pollsRepository;

    @PostMapping("/dataSurvey")
    public RedirectView getInsertData(@ModelAttribute PollDTO pollDTO, final RedirectAttributes redirectAttributes){

        if(pollDTO != null){

            Requests requests = new Requests();
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            redirectAttributes.addFlashAttribute("pollDTO", pollDTO);
            redirectAttributes.addFlashAttribute("username", loggedUser.getUsername());

            requests.setIdUserFk(usersRepository.findByUsername(loggedUser.getUsername()));
            requests.setIdPollFk(pollsRepository.findByIdPoll(1L));

            requestsRepository.save(requests);

            return new RedirectView("setDataMail");
        } else{
            return new RedirectView("poll");
        }
    }
}
