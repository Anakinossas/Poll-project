package it.zerob.poll.service;

import it.zerob.poll.model.Users;
import it.zerob.poll.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("usersDetailsService")
public class UsersDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username);
        //If the user exists it receives the role of ADMIN
        if (user != null) {
            List<GrantedAuthority> ruoliUtente = new ArrayList<>();
            if(user.getRole().equalsIgnoreCase("ADMIN")){
                ruoliUtente.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else if (user.getRole().equalsIgnoreCase("USER")){
                ruoliUtente.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new User(user.getUsername(), user.getPassword(), true, true, true, true, ruoliUtente);

        } else {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }
    }
}
