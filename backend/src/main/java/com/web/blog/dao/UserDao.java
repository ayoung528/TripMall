
package com.web.blog.dao;

import java.util.Optional;

import com.web.blog.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserDao extends JpaRepository<User, String> {
    User getUserById(int userid);
    User getUserByEmail(String email);
    User getUserByNickname(String nickname);

    Optional<User> findUserByNickname(String nickname);
    Optional<User> findUserById(int userid);
    Optional<User> findUserByEmail(String email);
}
