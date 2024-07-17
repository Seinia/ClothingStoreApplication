package com.fix.OnlineClothingStore.Service;


import com.fix.OnlineClothingStore.Model.MyUserDetails;
import com.fix.OnlineClothingStore.Model.User;
import com.fix.OnlineClothingStore.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = repo.findByUsername(username);

        return user.map(MyUserDetails::new).orElseThrow((() -> new UsernameNotFoundException(username + " not found")));
    }
}
