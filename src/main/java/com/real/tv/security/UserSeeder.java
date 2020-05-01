package com.real.tv.security;

import com.real.tv.dao.UserDao;
import com.real.tv.entity.UserEntity;
import com.real.tv.enums.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Seeds initial Admin user Info
 */
@Service
public class UserSeeder implements CommandLineRunner {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    public UserSeeder(UserDao userDao, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Optional<UserEntity> adminUser = Optional.ofNullable(userDao.findByUsername("admin"));

        if (!adminUser.isPresent()){
            UserEntity admin = new UserEntity();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            admin.setUsername("admin");
            admin.setStatus(Status.ACTIVE);
            userDao.save(admin);
        }
    }
}
