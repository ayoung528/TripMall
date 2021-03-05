package com.web.blog.service.collaborated;

import java.util.List;

import com.web.blog.dao.collaborated.DistancebasedDao;
import com.web.blog.model.collaborated.Distancebased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistancebasedService {
	@Autowired
	DistancebasedDao distancebasedDao;
	
	public List<Distancebased> findAllBySelectedSpot(int selected_content) {
		List<Distancebased> distancebased = distancebasedDao.findAllBySelectedSpot(selected_content);
		return distancebased;
	}
}