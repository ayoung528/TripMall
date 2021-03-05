
package com.web.blog.dao;

import java.util.Optional;

import com.web.blog.model.touranddining.Touranddining;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TouranddiningDao extends JpaRepository<Touranddining, Integer> {
    Page<Touranddining> findAll(Pageable pageable);
    Page<Touranddining> findAllByContentTypeIdNotAndContentTypeIdNot(Pageable pageable, int contenttypeid, int contenttypeid2);
    Page<Touranddining> findAllByContentTypeIdNotAndContentTypeIdNotAndAreacode(Pageable pageable, int contenttypeid, int contenttypeid2, int areacode);
    Page<Touranddining> findAllByContentTypeIdNotAndContentTypeIdNotAndTitleContaining(Pageable pageable, int contenttypeid, int contenttypeid2, String title);
    Page<Touranddining> findAllByContentTypeIdNotAndContentTypeIdNotAndTitleContainingAndAreacode(Pageable pageable, int contenttypeid, int contenttypeid2, String title, int areacode);
    Page<Touranddining> findAllByContentTypeId(Pageable pageable, int contenttypeid);
    Page<Touranddining> findAllByContentTypeIdAndAreacode(Pageable pageable, int contenttypeid, int areacode);
    Page<Touranddining> findAllByContentTypeIdAndTitleContaining(Pageable pageable, int contenttypeid, String name);
    Page<Touranddining> findAllByContentTypeIdAndTitleContainingAndAreacode(Pageable pageable, int contenttypeid, String name, int areacode);
    Touranddining findAllByContentId(int contentid);
    Optional<Touranddining> findByContentId(int contentid);
}
