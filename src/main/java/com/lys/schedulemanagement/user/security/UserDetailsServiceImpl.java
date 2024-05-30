package com.lys.schedulemanagement.user.security;

import com.lys.schedulemanagement.user.model.User;
import com.lys.schedulemanagement.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username: " + username));

        User noopUser = new User(
                user.getId(),
                user.getUsername(),
                "{noop}" + user.getPassword(),
                user.getNickname(),
                user.getRole(),
                user.getCreatedAt()
        );

        return new UserDetailsImpl(noopUser);
    }
}
