package com.web.blog.dao.collaborated;

import java.util.List;

import com.web.blog.model.collaborated.Distancebased;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DistancebasedDao extends JpaRepository<Distancebased, Integer> {
    List<Distancebased> findAllBySelectedSpot(int selected_content);
}