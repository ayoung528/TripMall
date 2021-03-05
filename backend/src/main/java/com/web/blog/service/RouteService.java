package com.web.blog.service;

import java.util.List;

import javax.transaction.Transactional;

import com.web.blog.dao.RecommendDao;
import com.web.blog.dao.RouteDao;
import com.web.blog.dao.RouteDetailDao;
import com.web.blog.model.recommend.Recommend;
import com.web.blog.model.route.Route;
import com.web.blog.model.route.RouteDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
	@Autowired
    RouteDao routeDao;
    @Autowired
	RouteDetailDao routeDetailDao;
	
	public Page<Route> findAll(Pageable pageable) {
		Page<Route> route = routeDao.findAll(pageable);
		return route;
    }
    public List<Route> findAllByUserid(int userid) {
		List<Route> route = routeDao.findAllByUserid(userid);
		return route;
    }
    public int findLastInsertId(){
        return routeDao.findLastInsertId();
    }
    public Page<Route> findAllByAreacode(Pageable pageable, int areacode) {
		Page<Route> route = routeDao.findAllByAreacode(pageable, areacode);
		return route;
    }
    public void writeRoute(Route route) {
		routeDao.save(route);
    }
    public void writeRouteDetail(RouteDetail rd) {
		routeDetailDao.save(rd);
	}
	@Transactional
	public void deleteRoute(int routeId) {
		routeDao.deleteByRouteid(routeId);
	}
	
}
