package com.web.blog.service.collaborated;

import java.util.List;

import com.web.blog.dao.collaborated.SurveyresultDao;
import com.web.blog.model.collaborated.Surveyresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyresultService {
	@Autowired
	SurveyresultDao surveyresultDao;
	
	public void save(Surveyresult sr) {
		surveyresultDao.save(sr);
	}

	public List<Surveyresult> findAllByUid(int uid){
		return surveyresultDao.findAllByUid(uid);
	}
}