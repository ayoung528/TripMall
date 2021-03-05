
package com.web.blog.dao;

import java.util.List;

import com.web.blog.model.route.Route;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface RouteDao extends JpaRepository<Route, Integer> {
    Page<Route> findAll(Pageable pageable);
    List<Route> findAllByUserid(int userid);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    int findLastInsertId();

    Page<Route> findAllByAreacode(Pageable pageable, int areacode);
    void deleteByRouteid(int routeid);
}
