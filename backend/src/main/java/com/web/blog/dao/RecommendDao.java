
package com.web.blog.dao;

import java.util.List;

import com.web.blog.model.recommend.Recommend;

import org.springframework.data.jpa.repository.JpaRepository;



public interface RecommendDao extends JpaRepository<Recommend, Integer> {
    List<Recommend> findAllByContentid(int contentid);
}
