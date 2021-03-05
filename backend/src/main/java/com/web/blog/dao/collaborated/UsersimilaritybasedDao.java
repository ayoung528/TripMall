package com.web.blog.dao.collaborated;

import java.util.List;

import com.web.blog.model.collaborated.Usersimilaritybased;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersimilaritybasedDao extends JpaRepository<Usersimilaritybased, Integer> {
    List<Usersimilaritybased> findAllBySelectedUser(String email);
}