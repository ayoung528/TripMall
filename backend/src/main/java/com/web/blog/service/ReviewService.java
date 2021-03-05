package com.web.blog.service;

import java.util.List;

import com.web.blog.dao.ReviewDao;
import com.web.blog.model.review.Review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
	@Autowired
	ReviewDao reviewDao;
	
	public Page<Review> findAllByUserid(int userid, Pageable pageable) {
		Page<Review> review = reviewDao.findAllByUid(userid, pageable);
		return review;
	}

	public Page<Review> findAllByContentid(int contentid, Pageable pageable) {
		Page<Review> review = reviewDao.findAllByContentId(contentid, pageable);
		return review;
	}

	public List<Review> findAllByContentid(int contentid) {
		List<Review> review = reviewDao.findAllByContentId(contentid);
		return review;
	}

	public List<Review> findAllByUserid(int userid) {
		List<Review> review = reviewDao.findAllByUid(userid);
		return review;
	}

	public void writeReview(Review review) {
		reviewDao.save(review);
	}
	
}