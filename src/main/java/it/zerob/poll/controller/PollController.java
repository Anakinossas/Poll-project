package it.zerob.poll.controller;

import it.zerob.poll.dto.PollDTO;
import it.zerob.poll.model.Polls;
import it.zerob.poll.model.Requests;
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

            //Get the user that is logged
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            //Saving attributes that will be
            //redirect in another functions
            redirectAttributes.addFlashAttribute("pollDTO", pollDTO);
            redirectAttributes.addFlashAttribute("username", loggedUser.getUsername());

            //Setting the user that did the poll
            //and the id of the poll
            requests.setIdUserFk(usersRepository.findByUsername(loggedUser.getUsername()));
            requests.setIdPollFk(pollsRepository.findByIdPoll(1L));

            //Memorizing in the db the request
            //done by user after the survey
            requestsRepository.save(requests);

            Polls poll;

            //If the number of the missing users is equals to 0 that poll can be close
            //And POLLS is_closed field is set to 1
            if(pollsRepository.usersMissingByIdPoll(1L) == 0){
                poll = pollsRepository.findByIdPoll(1L);
                poll.setIs_closed("1");
                pollsRepository.save(poll);
            }

            //Redirect to another function
            return new RedirectView("setDataMail");
        } else{
            //Redirect to another function
            return new RedirectView("poll");
        }
    }
}
