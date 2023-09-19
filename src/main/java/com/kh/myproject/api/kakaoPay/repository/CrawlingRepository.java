package com.kh.myproject.api.kakaoPay.repository;

import com.kh.myproject.api.kakaoPay.model.entity.CrawlingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlingRepository extends JpaRepository<CrawlingEntity, Long> {

    @Query("SELECT c FROM CrawlingEntity c")
    List<CrawlingEntity> findAllCrawlingData();
}
