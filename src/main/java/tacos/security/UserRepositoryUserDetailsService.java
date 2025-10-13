package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.data.jpa.UserRepositoryJPA;
import tacos.model.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    
    private UserRepositoryJPA userRepo;

    @Autowired
    public UserRepositoryUserDetailsService(UserRepositoryJPA userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
