package com.ecommerce.Security.services;

import com.ecommerce.Model.User;
import com.ecommerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    ///@transactional annotation tells that either the eniter operation will pass or it will rollback if it fails somewhere in between
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username: " +username));

        return UserDetailsImpl.build(user);
    }
}
