package com.kh.myproject.store.rentcar.repository;



import com.kh.myproject.store.rentcar.model.RentcarInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RentcarRepository extends JpaRepository<RentcarInfoEntity, Long> {



    //차 이름으로 결과검색
    @Query(value="SELECT r FROM RentcarInfoEntity r WHERE r.car_name LIKE %:searchKeyword%")
    List<RentcarInfoEntity> FindByCar_name(@Param("searchKeyword") String searchKeyword);

    //렌트카리스트 정렬
    @Query("SELECT r FROM RentcarInfoEntity r")
    List<RentcarInfoEntity> FindAll();

    //높은 가격순 정렬
    @Query("SELECT r FROM RentcarInfoEntity r ORDER BY r.car_discount DESC")
    List<RentcarInfoEntity> FindDiscountDesc();


    //낮은 가격순 정렬
    @Query("SELECT r FROM RentcarInfoEntity r ORDER BY r.car_discount ASC")
    List<RentcarInfoEntity> FindDiscountAsc();

    //차종순 정렬
    @Query("SELECT r FROM RentcarInfoEntity r ORDER BY r.car_type ASC")
    List<RentcarInfoEntity> FindTypeAsc();


    /*
   @Query(value="select * from car_info where car_name = :car_name ORDER BY car_discount ASC limit 1", nativeQuery = true)
    RentcarInfoEntity FindDiscountOne(@Param("car_name") String car_name);
*/

    @Query(value = "SELECT * FROM car_info ci INNER JOIN com_info co ON ci.com_id = co.com_id WHERE ci.car_name =:car_name", nativeQuery = true)
    List<RentcarInfoEntity> FindCombycarname(@Param("car_name") String car_name);


    // 결제페이지에 넘길 정보
    @Query(value = "SELECT * FROM car_info ci INNER JOIN com_info co ON ci.com_id = co.com_id WHERE ci.car_info_id=:Car_info_id", nativeQuery = true)
    RentcarInfoEntity FindAllInfo(@Param("Car_info_id") Long Car_info_id);



    //국내외구분 필터링 쿼리_전체
    @Query(value="SELECT * FROM car_info ci ORDER BY ci.car_nation ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindBynationAll();

    //국내외구분 필터링 쿼리_국내
    @Query(value="SELECT * FROM car_info ci WHERE ci.car_nation ='국내' ORDER BY ci.car_nation ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindBynationDomestic();

    //국내외구분 필터링 쿼리_해외
    @Query(value="SELECT * FROM car_info ci WHERE ci.car_nation ='해외' ORDER BY ci.car_nation ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindBynationOversea();


    //차종필터링 쿼리_전체
    @Query(value="SELECT * FROM car_info ci ORDER BY ci.car_type ASC",nativeQuery = true)
    List<RentcarInfoEntity> FindByTypeAll();

    //차종필터링 쿼리_경형
    @Query(value="SELECT * FROM car_info ci WHERE ci.car_type ='경형' ORDER BY ci.car_type ASC",nativeQuery = true)
    List<RentcarInfoEntity> FindByTypeSmall();

    //차종필터링 쿼리_승용
    @Query(value="SELECT * FROM car_info ci WHERE ci.car_type ='승용' ORDER BY ci.car_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByTypeMid();

    //차종필터링 쿼리_suv
    @Query(value="SELECT * FROM car_info ci WHERE ci.car_type ='SUV' ORDER BY ci.car_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByTypeSuv();

    //차종필터링 쿼리_승합
    @Query(value="SELECT * FROM car_info ci WHERE ci.car_type ='승합' ORDER BY ci.car_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByTypeRv();


    //유종필터링 쿼리_전체
    @Query(value="SELECT * FROM car_info ci ORDER BY ci.oil_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByOilAll();

    //유종필터링 쿼리_디젤
    @Query(value="SELECT * FROM car_info ci WHERE ci.oil_type='디젤' ORDER BY ci.oil_type ASC",nativeQuery = true)
    List<RentcarInfoEntity> FindByOilDiesel();

    //유종필터링 쿼리_전기
    @Query(value="SELECT * FROM car_info ci WHERE ci.oil_type='전기' ORDER BY ci.oil_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByOilElec();

    //유종필터링 쿼리_가솔린
    @Query(value="SELECT * FROM car_info ci WHERE ci.oil_type='가솔린' ORDER BY ci.oil_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByOilGasoline();

    //유종필터링 쿼리_LPG
    @Query(value="SELECT * FROM car_info ci WHERE ci.oil_type='LPG' ORDER BY ci.oil_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByOilLpg();

    //유종필터링 쿼리_하이브리드
    @Query(value="SELECT * FROM car_info ci WHERE ci.oil_type='하이브리드' ORDER BY ci.oil_type ASC",nativeQuery = true)
    List<RentcarInfoEntity>  FindByOilHybrid();


    //나이필터링 쿼리_21세이상
    @Query(value="SELECT * FROM car_info ci WHERE ci.driver_age='21세 이상'",nativeQuery = true)
    List<RentcarInfoEntity>  FindByAgeDown();

    //나이필터링 쿼리_26세이상
    @Query(value="SELECT * FROM car_info ci WHERE ci.driver_age='26세 이상'",nativeQuery = true)
    List<RentcarInfoEntity>  FindByAgeUp();


}