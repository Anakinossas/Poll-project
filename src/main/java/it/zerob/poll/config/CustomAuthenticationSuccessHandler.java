package it.zerob.poll.config;

import it.zerob.poll.model.Polls;
import it.zerob.poll.model.Requests;
import it.zerob.poll.model.RequestsPK;
import it.zerob.poll.model.Users;
import it.zerob.poll.repository.PollsRepository;
import it.zerob.poll.repository.RequestsRepository;
import it.zerob.poll.repository.UsersRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private PollsRepository pollsRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        //Changing redirect page based on role of the user that is logging in
        if(roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/home"); //Redirect to page for uploading the Excel file and send emails to users.
        } else {
            //Get logged user in the session
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users userLoggedIn = usersRepository.findByUsername(loggedUser.getUsername()); //Finds user by giving username
            Polls idPoll = pollsRepository.findByIdPoll(1L); //Finds the poll by giving its id
            //Find the number of user that didn't answer the poll
            Requests requests = requestsRepository.requestsDone(idPoll, userLoggedIn);

            //If request is null and the poll is not closed the user will be redirected to the poll page
            if(requests == null && idPoll.getIs_closed().equalsIgnoreCase("0")){
                response.sendRedirect("/poll");
            } else {
                //If the user has already completed the poll or if it is closed the user will be redirected
                //To the login page with an error
                response.sendRedirect("/login?alreadyDone");
            }
        }
    }
}
