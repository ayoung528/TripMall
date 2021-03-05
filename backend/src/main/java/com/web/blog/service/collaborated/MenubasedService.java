package com.web.blog.service.collaborated;

import java.util.List;

import com.web.blog.dao.collaborated.MenubasedDao;
import com.web.blog.model.collaborated.Menubased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenubasedService {
	@Autowired
	MenubasedDao menubasedDao;
	
	public List<Menubased> findAllBySelectedStore(int selected_content) {
		List<Menubased> menubased = menubasedDao.findAllBySelectedStore(selected_content);
		return menubased;
	}
}