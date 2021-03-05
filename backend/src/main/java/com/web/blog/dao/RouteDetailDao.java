
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.route.RouteDetail;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RouteDetailDao extends JpaRepository<RouteDetail, Integer> {
    List<RouteDetail> findAllByRouteid(int routeid);
    void deleteByRdid(int rdid);
    Optional<RouteDetail> findByRdid(int rdid);
}
