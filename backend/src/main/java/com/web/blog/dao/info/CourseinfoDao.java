package com.web.blog.dao.info;

import java.util.List;

import com.web.blog.model.info.Courseinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseinfoDao extends JpaRepository<Courseinfo, Integer>{
    List<Courseinfo> findAll();
    Courseinfo findAllByContentId(int contentId);
}
