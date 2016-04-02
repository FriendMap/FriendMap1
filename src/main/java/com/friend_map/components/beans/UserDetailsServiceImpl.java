package com.friend_map.components.beans;


import com.friend_map.business_layer.auth.AuthUserDetails;
import com.friend_map.persistence_layer.entities.user.User;
import com.friend_map.persistence_layer.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    AuthUserDetails currentUserDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                roles);
    }

    public User getCurrentUser() throws UsernameNotFoundException {
        return userService.findByUsername(currentUserDetails.getAuthUserName());
    }

    public User getCurrentUserMin() throws UsernameNotFoundException {
        return userService.findByUsernameMin(currentUserDetails.getAuthUserName());
    }
}
