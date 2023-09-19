package com.kh.myproject.store.tour.repository;

import com.kh.myproject.store.tour.model.entity.Tourism;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourismRepository extends JpaRepository<Tourism, Long> {
    public List<Tourism> findAllByAreaName(String areaName);
}
