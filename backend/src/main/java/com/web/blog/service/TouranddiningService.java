package com.web.blog.service;

import java.util.Optional;

import com.web.blog.dao.TouranddiningDao;
import com.web.blog.model.touranddining.Touranddining;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TouranddiningService {
	@Autowired
	TouranddiningDao touranddiningDao;
	
	public Page<Touranddining> findAll(Pageable pageable) {
		Page<Touranddining> touranddining = touranddiningDao.findAll(pageable);
		return touranddining;
	}
	public Boolean isIn(int contentid) {
		Optional<Touranddining> touranddining = touranddiningDao.findByContentId(contentid);
		if(touranddining.isPresent()){
			return true;
		}
		else{
			return false;
		}
	}
	public Touranddining findAllByContentId(int contentid) {
		Touranddining touranddining = touranddiningDao.findAllByContentId(contentid);
		return touranddining;
	}

	public Page<Touranddining> findAllByContentTypeId(Pageable pageable, int contenttypeid) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeId(pageable, contenttypeid);
		return touranddining;
	}
	
	public Page<Touranddining> findAllTour(Pageable pageable, int contenttypeid, int contenttypeid2) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdNotAndContentTypeIdNot(pageable, contenttypeid, contenttypeid2);
		return touranddining;
	}

	public Page<Touranddining> findAllTourAndName(Pageable pageable, int contenttypeid, int contenttypeid2, String name) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdNotAndContentTypeIdNotAndTitleContaining(pageable, contenttypeid, contenttypeid2, name);
		return touranddining;
	}

	public Page<Touranddining> findAllTourAreacode(Pageable pageable, int contenttypeid, int contenttypeid2, int areacode) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdNotAndContentTypeIdNotAndAreacode(pageable, contenttypeid, contenttypeid2, areacode);
		return touranddining;
	}   

	public Page<Touranddining> findAllTourAndNameAndAreacode(Pageable pageable, int contenttypeid, int contenttypeid2, String name, int areacode) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdNotAndContentTypeIdNotAndTitleContainingAndAreacode(pageable, contenttypeid, contenttypeid2, name, areacode);
		return touranddining;
	}   

	public Page<Touranddining> findAllByContentTypeIdAndAreacode(Pageable pageable, int contenttypeid, int areacode) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdAndAreacode(pageable, contenttypeid, areacode);
		return touranddining;
	}

	public Page<Touranddining> findAllByContentTypeIdAndTitleContaining(Pageable pageable, int contenttypeid, String name) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdAndTitleContaining(pageable, contenttypeid, name);
		return touranddining;
	}

	public Page<Touranddining> findAllByContentTypeIdAndTitleContainingAndAreacode(Pageable pageable, int contenttypeid, String name, int areacode) {
		Page<Touranddining> touranddining = touranddiningDao.findAllByContentTypeIdAndTitleContainingAndAreacode(pageable, contenttypeid, name, areacode);
		return touranddining;
	}

	public void save(Touranddining touranddining) {
		touranddiningDao.save(touranddining);
	}
}
