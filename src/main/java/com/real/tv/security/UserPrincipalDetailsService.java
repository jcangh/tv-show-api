package com.real.tv.security;

import com.real.tv.dao.UserDao;
import com.real.tv.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserDetailService custom implementation
 */
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<UserEntity> user = Optional.ofNullable(userDao.findByUsername(s));

        if (user.isPresent()) {
            UserPrincipal userPrincipal = new UserPrincipal(user.get());
            return userPrincipal;
        }
        throw new UsernameNotFoundException("msg.user-not-found");
    }
}
