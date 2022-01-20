package com.github.behnazsh.service;

import com.github.behnazsh.common.UnauthorizedUserException;
import com.github.behnazsh.dao.entity.User;
import com.github.behnazsh.dao.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Behnaz Sh
 */

@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {
    private static final String USER_UNAUTHORIZED = "Username or password is wrong";

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String name){

        Optional<User> user = userDetailRepository.findByUsername(name);
        user.orElseThrow(() -> new UnauthorizedUserException(USER_UNAUTHORIZED));

        UserDetails userDetails = new AuthUserDetailService(user.get());
        new AccountStatusUserDetailsChecker().check(userDetails);
        return userDetails;
    }

    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails= (UserDetails) auth.getPrincipal();
        Optional<User> user = userDetailRepository.findByUsername(userDetails.getUsername());
        return user.orElseThrow(() -> new UnauthorizedUserException(USER_UNAUTHORIZED));
    }
}
