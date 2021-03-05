package com.web.blog.dao.info;

import java.util.List;

import com.web.blog.model.info.Tourinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TourinfoDao extends JpaRepository<Tourinfo, Integer>{
    List<Tourinfo> findAll();
    Tourinfo findAllByContentId(int contentId);
}
