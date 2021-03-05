package com.web.blog.dao.info;

import java.util.List;

import com.web.blog.model.info.Cultureinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CultureinfoDao extends JpaRepository<Cultureinfo, Integer>{
    List<Cultureinfo> findAll();
    Cultureinfo findAllByContentId(int contentId);
}
