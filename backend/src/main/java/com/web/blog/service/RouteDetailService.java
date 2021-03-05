package com.web.blog.service;

import java.util.List;
import java.util.Optional;

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
public class RouteDetailService {
	@Autowired
	RouteDetailDao routeDetailDao;
	
	public List<RouteDetail> findAllByRouteid(int routeid) {
		List<RouteDetail> rd = routeDetailDao.findAllByRouteid(routeid);
		return rd;
	}
	@Transactional
	public void deleteRouteDetail(int rdid) {
		routeDetailDao.deleteByRdid(rdid);
	}
    
	public Optional<RouteDetail> findByRdid(int rdid){
		return routeDetailDao.findByRdid(rdid);
	}

	public void save(RouteDetail routeDetail) {
		routeDetailDao.save(routeDetail);
	}
}
