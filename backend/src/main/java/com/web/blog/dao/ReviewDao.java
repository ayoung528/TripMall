package com.web.blog.dao;

import java.util.List;

import com.web.blog.model.review.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ReviewDao extends JpaRepository<Review, Integer> {
    Page<Review> findAllByUid(int userid, Pageable pageable);
    Page<Review> findAllByContentId(int contentId, Pageable pageable);
    List<Review> findAllByContentId(int contentId);
    List<Review> findAllByUid(int userid);
}