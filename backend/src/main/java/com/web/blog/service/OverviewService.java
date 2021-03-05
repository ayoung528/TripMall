package com.web.blog.service;

import java.util.List;

import com.web.blog.dao.OverviewDao;
import com.web.blog.model.overview.Overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverviewService {
	@Autowired
	OverviewDao overviewDao;
	
	public Overview findAllByContentId(int contentId) {
		Overview overview = overviewDao.findAllByContentId(contentId);
		return overview;
	}
	
}