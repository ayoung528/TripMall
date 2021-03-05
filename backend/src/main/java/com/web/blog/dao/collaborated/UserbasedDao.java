package com.web.blog.dao.collaborated;

import java.util.List;

import com.web.blog.model.collaborated.Userbased;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UserbasedDao extends JpaRepository<Userbased, Integer> {
    List<Userbased> findAllBySelectedContent(int selected_content);
}