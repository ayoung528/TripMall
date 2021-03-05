package com.web.blog.service.collaborated;

import java.util.List;

import com.web.blog.dao.collaborated.UserbasedDao;
import com.web.blog.model.collaborated.Userbased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserbasedService {
	@Autowired
	UserbasedDao userbasedDao;
	
	public List<Userbased> findAllBySelectedContent(int selected_content) {
		List<Userbased> userbased = userbasedDao.findAllBySelectedContent(selected_content);
		return userbased;
	}
}