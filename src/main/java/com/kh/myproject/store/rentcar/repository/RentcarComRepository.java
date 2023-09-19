package com.kh.myproject.store.rentcar.repository;


import com.kh.myproject.store.rentcar.model.RentcarComEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentcarComRepository extends JpaRepository<RentcarComEntity, Long>{

/*
    @Query(value = "SELECT * FROM com_info co INNER JOIN car_info ci ON ci.com_id = co.com_id WHERE ci.car_name LIKE %:car_name%", nativeQuery = true)
    List<RentcarComEntity> FindByCar_name(@Param("car_name") String car_name);



 */

}
