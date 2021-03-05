package com.web.blog.dao.collaborated;

import java.util.List;

import com.web.blog.model.collaborated.Menubased;

import org.springframework.data.jpa.repository.JpaRepository;



public interface MenubasedDao extends JpaRepository<Menubased, Integer> {
    List<Menubased> findAllBySelectedStore(int selected_content);
}