package com.kh.myproject.store.rentcar.service;


import com.kh.myproject.store.rentcar.model.RentcarComDTO;
import com.kh.myproject.store.rentcar.model.RentcarComEntity;
import com.kh.myproject.store.rentcar.model.RentcarInfoDTO;
import com.kh.myproject.store.rentcar.model.RentcarInfoEntity;
import com.kh.myproject.store.rentcar.repository.RentcarComRepository;
import com.kh.myproject.store.rentcar.repository.RentcarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentcarService {

    @Autowired
    private RentcarRepository rentcarRepository;

    @Autowired
    private RentcarComRepository rentcarComRepository;

    public List<RentcarInfoDTO> searchKeyword(String searchKeyword){

        System.out.println("searchKeyword 서비스 메서드 실행");
        System.out.println(searchKeyword);

        List<RentcarInfoEntity> entities = rentcarRepository.FindByCar_name(searchKeyword);


        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }




//차량 전체 리스트 정렬
    public List<RentcarInfoDTO> findAll() {

        List<RentcarInfoEntity> entities = rentcarRepository.FindAll();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());


    }

    //높은 가격순 정렬
    @Transactional
    public List<RentcarInfoDTO> FindDiscountDesc(List<RentcarInfoDTO> rentcarlist) {

        List<RentcarInfoEntity> entities = rentcarRepository.FindDiscountDesc();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());


    }



    //낮은가격순 정렬
    @Transactional
    public List<RentcarInfoDTO> FindDiscountAsc(List<RentcarInfoDTO> rentcarlist) {

        List<RentcarInfoEntity> entities = rentcarRepository.FindDiscountAsc();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());


    }

    //차종순 정렬
    @Transactional
    public List<RentcarInfoDTO> FindTypeAsc(List<RentcarInfoDTO> rentcarlist) {

        List<RentcarInfoEntity> entities = rentcarRepository.FindTypeAsc();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());


    }



    public List<RentcarInfoEntity> FindCombycarname(String car_name){

        List<RentcarInfoEntity> result = rentcarRepository.FindCombycarname(car_name);


        return result;

    }


    //렌트카 결제 페이지 전달
    public RentcarInfoDTO rentcarPay(Long Car_info_id){

        RentcarInfoDTO dto = RentcarInfoDTO.fromEntity(rentcarRepository.FindAllInfo(Car_info_id));


        return dto;
    }


// 구분 필터링_전체
    public List<RentcarInfoDTO> FindBynationAll(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindBynationAll();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 구분 필터링_국내
    public List<RentcarInfoDTO> FindBynationDomestic(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindBynationDomestic();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 구분 필터링_국외
    public List<RentcarInfoDTO> FindBynationOversea(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindBynationOversea();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //차종 필터링_전체
    public List<RentcarInfoDTO> FindByTypeAll(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByTypeAll();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //차종 필터링_경형
    public List<RentcarInfoDTO> FindByTypeSmall(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByTypeSmall();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //차종 필터링_승용
    public List<RentcarInfoDTO> FindByTypeMid(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByTypeMid();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //차종 필터링_suv
    public List<RentcarInfoDTO> FindByTypeSuv(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByTypeSuv();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //차종 필터링_승합
    public List<RentcarInfoDTO> FindByTypeRv(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByTypeRv();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //유종 필터링_전체
    public List<RentcarInfoDTO> FindByOilAll(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByOilAll();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    //유종 필터링_디젤
    public List<RentcarInfoDTO> FindByOilDiesel(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByOilDiesel();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //유종 필터링_전기
    public List<RentcarInfoDTO> FindByOilElec(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByOilElec();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //유종 필터링_가솔린
    public List<RentcarInfoDTO> FindByOilGasoline(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByOilGasoline();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //유종 필터링_lpg
    public List<RentcarInfoDTO> FindByOilLpg(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByOilLpg();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    //유종 필터링_하이브리드
    public List<RentcarInfoDTO> FindByOilHybrid(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByOilHybrid();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }


    // 나이 필터링_21세 이상
    public List<RentcarInfoDTO> FindByAgeDown(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByAgeDown();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 나이 필터링_21세 이상
    public List<RentcarInfoDTO> FindByAgeUp(){

        List<RentcarInfoEntity> entities = rentcarRepository.FindByAgeUp();

        return entities.stream()
                .map(RentcarInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }





/*
    //같은 이름 차 중 가장 저렴한 것 고르기
    public List<RentcarInfoDTO> FindrentcarCom(String car_name){
        List<RentcarInfoDTO> dtolist = RentcarInfoDTO.fromEntity(rentcarRepository.FindDiscountOne(car_name));

        return dto;



    }


    // 렌터카 업체 선택
    public List<RentcarComDTO> getComSelect(String car_name){

        List<RentcarComEntity> entities = rentcarComRepository.FindByCar_name(car_name);

        return entities.stream()
                .map(RentcarComDTO::fromEntity)
                .collect(Collectors.toList());


    }
*/

}
