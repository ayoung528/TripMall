package com.web.blog.service;

import java.util.List;

import com.web.blog.dao.info.CourseinfoDao;
import com.web.blog.dao.info.CultureinfoDao;
import com.web.blog.dao.info.FestivalinfoDao;
import com.web.blog.dao.info.FoodinfoDao;
import com.web.blog.dao.info.LeisureinfoDao;
import com.web.blog.dao.info.TourinfoDao;
import com.web.blog.model.info.Courseinfo;
import com.web.blog.model.info.Cultureinfo;
import com.web.blog.model.info.Festivalinfo;
import com.web.blog.model.info.Foodinfo;
import com.web.blog.model.info.Leisureinfo;
import com.web.blog.model.info.Tourinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InfoService {
	@Autowired
	CourseinfoDao courseinfoDao;
	@Autowired
	CultureinfoDao cultureinfoDao;
	@Autowired
	FestivalinfoDao festivalinfoDao;
	@Autowired
	FoodinfoDao foodinfoDao;
	@Autowired
	LeisureinfoDao leisureinfoDao;
	@Autowired
	TourinfoDao tourinfoDao;
	
	public Courseinfo findCourseinfo(int contentid) {
		return courseinfoDao.findAllByContentId(contentid);
	}

	public Cultureinfo findCultureinfo(int contentid) {
		return cultureinfoDao.findAllByContentId(contentid);
	}

	public Festivalinfo findFestivalinfo(int contentid) {
		return festivalinfoDao.findAllByContentId(contentid);
	}
	
	public Foodinfo findFoodinfo(int contentid) {
		return foodinfoDao.findAllByContentId(contentid);
	}
	
	public Leisureinfo findLeisureinfo(int contentid) {
		return leisureinfoDao.findAllByContentId(contentid);
	}

	public Tourinfo findTourinfo(int contentid) {
		return tourinfoDao.findAllByContentId(contentid);
	}
}
