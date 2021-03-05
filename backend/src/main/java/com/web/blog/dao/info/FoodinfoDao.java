package com.web.blog.dao.info;

import java.util.List;

import com.web.blog.model.info.Foodinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodinfoDao extends JpaRepository<Foodinfo, Integer>{
    List<Foodinfo> findAll();
    Foodinfo findAllByContentId(int contentId);
    
}
