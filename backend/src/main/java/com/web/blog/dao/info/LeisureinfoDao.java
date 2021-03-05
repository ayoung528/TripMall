package com.web.blog.dao.info;

import java.util.List;

import com.web.blog.model.info.Leisureinfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeisureinfoDao extends JpaRepository<Leisureinfo, Integer>{
    List<Leisureinfo> findAll();
    Leisureinfo findAllByContentId(int contentId);
}
