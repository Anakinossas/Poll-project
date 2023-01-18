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

/**
 * <strong>Controller</strong> that implements the method to send poll data to the MailController
 */
@RestController
public class PollController {

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PollsRepository pollsRepository;

    /**
     * <strong>POST</strong> Method called by the submit button in the poll form
     *
     * @param pollDTO            Object that contains the data inserted by the user in the poll
     * @param redirectAttributes Object to add attributes when redirect
     * @return new Redirect to the page that sends mail if the poll is not null either redirect to the poll page
     */
    @PostMapping("/dataSurvey")
    public RedirectView getInsertData(@ModelAttribute PollDTO pollDTO, final RedirectAttributes redirectAttributes) {

        if (pollDTO != null) {

            Requests requests = new Requests();
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            redirectAttributes.addFlashAttribute("pollDTO", pollDTO);
            redirectAttributes.addFlashAttribute("username", loggedUser.getUsername());

            requests.setIdUserFk(usersRepository.findByUsername(loggedUser.getUsername()));
            requests.setIdPollFk(pollsRepository.findByIdPoll(1L));

            requestsRepository.save(requests);

            Polls poll;

            //If the number of the missing users is equals to 0 that poll can be close
            //And POLLS is_closed field is set to 1
            if (pollsRepository.usersMissingByIdPoll(1L) == 0) {
                poll = pollsRepository.findByIdPoll(1L);
                poll.setIs_closed("1");
                pollsRepository.save(poll);
            }

            return new RedirectView("setDataMail");
        } else {
            return new RedirectView("poll");
        }
    }

    @GetMapping("/getNumberMissing")
    public Integer getMissing(){
        return pollsRepository.usersMissingByIdPoll(1L);
    }
}
