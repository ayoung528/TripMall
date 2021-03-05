package com.web.blog.dao.collaborated;

import java.util.List;

import com.web.blog.model.collaborated.Overviewbased;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OverviewbasedDao extends JpaRepository<Overviewbased, Integer> {
    List<Overviewbased> findAllBySelectedSpot(int selected_content);
}