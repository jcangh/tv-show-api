package com.real.tv.dao;

import com.real.tv.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository
 */
@Repository
public interface UserDao extends CrudRepository<UserEntity,Integer> {

    UserEntity findByUsername(String username);
}
