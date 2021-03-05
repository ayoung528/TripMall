package com.web.blog.dao.collaborated;

import java.util.List;

import com.web.blog.model.collaborated.Surveyresult;

import org.springframework.data.jpa.repository.JpaRepository;



public interface SurveyresultDao extends JpaRepository<Surveyresult, Integer> {
    List<Surveyresult> findAllByUid(int uid);
}