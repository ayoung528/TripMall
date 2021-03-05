package com.web.blog.dao;

import com.web.blog.model.overview.Overview;

import org.springframework.data.jpa.repository.JpaRepository;



public interface OverviewDao extends JpaRepository<Overview, Integer> {
    Overview findAllByContentId(int contentId);
}