package com.real.tv.service;

import com.real.tv.dao.UserDao;
import com.real.tv.dto.User;
import com.real.tv.entity.UserEntity;
import com.real.tv.enums.Status;
import com.real.tv.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Spring user service
 */
@Service
public class UserService {

    private static final String USER_NOT_FOUND = "msg.user-not-found";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Integer create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);

        UserEntity entity = mapper.toUserEntity(user);
        userDao.save(entity);

        return entity.getId();
    }

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return mapper.toUsers(userDao.findAll());
    }

    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        UserEntity user = this.getUserEntityById(id);
        return mapper.toUserDto(user);
    }

    @Transactional(readOnly = true)
    private UserEntity getUserEntityById(Integer id){
        return userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Transactional
    public void delete(Integer userId){
        UserEntity userEntity = this.getUserEntityById(userId);
        userDao.delete(userEntity);
    }

    @Transactional
    public void update(Integer id, User newUser){
        UserEntity existingEntity = this.getUserEntityById(id);
        UserEntity newEntity = mapper.toUserEntity(newUser);

        mapper.updateExistingUserEntity(newEntity,existingEntity);
        userDao.save(existingEntity);

    }
}
