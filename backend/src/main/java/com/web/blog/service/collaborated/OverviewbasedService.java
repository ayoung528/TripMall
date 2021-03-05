package com.web.blog.service.collaborated;

import java.util.List;

import com.web.blog.dao.collaborated.OverviewbasedDao;
import com.web.blog.model.collaborated.Overviewbased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverviewbasedService {
	@Autowired
	OverviewbasedDao overviewbasedDao;
	
	public List<Overviewbased> findAllBySelectedSpot(int selected_content) {
		List<Overviewbased> overviewbased = overviewbasedDao.findAllBySelectedSpot(selected_content);
		return overviewbased;
	}
}