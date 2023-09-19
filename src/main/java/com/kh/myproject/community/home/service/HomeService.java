package com.kh.myproject.community.home.service;

import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.home.repository.HomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j // 데이터베이스 로그를 확인
@Service
public class HomeService {

    @Autowired
    private HomeRepository homeRepository;

    public List<Accompany> findTop8byOrderByRegdateAsc(){
        return homeRepository.findTop8byOrderByRegdateAsc();
    }
}
