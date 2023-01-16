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

        if(roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/home");
        } else {
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Users userLoggedIn = usersRepository.findByUsername(loggedUser.getUsername());
            Polls idPoll = pollsRepository.findByIdPoll(1L);
            Requests requests = requestsRepository.requestsDone(idPoll, userLoggedIn);

            if(requests == null){
                response.sendRedirect("/poll");
            } else {
                response.sendRedirect("/login?alreadyDone");
            }
        }
    }
}
