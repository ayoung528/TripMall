package com.web.blog.dao.info;

import java.util.List;

import com.web.blog.model.info.Festivalinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalinfoDao extends JpaRepository<Festivalinfo, Integer>{
    List<Festivalinfo> findAll();
    Festivalinfo findAllByContentId(int contentId);
}
