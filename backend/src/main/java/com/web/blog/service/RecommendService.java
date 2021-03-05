package com.web.blog.service;

import java.util.List;

import com.web.blog.dao.RecommendDao;
import com.web.blog.model.recommend.Recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecommendService {
	@Autowired
	RecommendDao recommendDao;
	
	public List<Recommend> findAllByContentid(int contentid) {
		List<Recommend> recommend = recommendDao.findAllByContentid(contentid);
		return recommend;
	}
	
}
