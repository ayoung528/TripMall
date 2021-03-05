package com.web.blog.service.collaborated;

import java.util.List;

import com.web.blog.dao.collaborated.UsersimilaritybasedDao;
import com.web.blog.model.collaborated.Usersimilaritybased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersimilaritybasedService {
	@Autowired
	UsersimilaritybasedDao usersimilaritybasedDao;
	
	public List<Usersimilaritybased> findAllBySelectedUser(String email) {
		List<Usersimilaritybased> usersimilaritybased = usersimilaritybasedDao.findAllBySelectedUser(email);
		return usersimilaritybased;
	}
}