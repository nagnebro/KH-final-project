package com.kh.myproject.member.manager.service;


import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.member.manager.repository.AccompanyRepositoryM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccompanyServiceM {


    @Autowired
    AccompanyRepositoryM accompanyRepository;

//    public void deleteAccompanyByUserUserNumber(Long userNumber){
//
//        accompanyRepository.deleteAccompanyByUserUserNumber(userNumber);
//    }

    public List<Accompany> findAll(){

        return accompanyRepository.findAll();
    }

    public void deleteById(Long acNum){

        accompanyRepository.deleteById(acNum);
    }

    // 페이징처리 관련 메서드

    public int selectAcoompanyCount(){

        int count = accompanyRepository.selectAccompanyCount();
        return count;
    }

    public List<Accompany> findAccompanyByPage(int pageNo){

//        accompanyRepository.findUserByPage(startNo,endNo);
        Pageable pageable = PageRequest.of(pageNo-1,10);
        Page<Accompany> pageList = accompanyRepository.findAll(pageable);
        List<Accompany> accompanyList = pageList.getContent();

        return accompanyList;
    }

//    public List<User> selectAcoompanyBySearchWord(int pageNo,String search_word,String search_option){
//
//        Page<Accompany> accompanyList = null;
//        Pageable pageable = PageRequest.of(pageNo-1,10); // 정렬기능 추가안했음.
//
//        if(search_option.equals("user_id")){
//
//            accompanyList = accompanyRepository.findByUserIdLike(pageable,"%" + search_word +"%");
//        }else if(search_option.equals("user_name")){
//            accompanyList = accompanyRepository.findByUserNameLike(pageable,"%" + search_word+ "%");
//
//        }
//
//        List<User> resultList = accompanyList.getContent();
//        System.out.println(resultList);
//
//
//        return resultList;
//    }

    public int countBysearchWorld(String search_word, String search_option) {

        int result = 0;

        if (search_option.equals("user_id")) {
//            result = accompanyRepository.countByUserIdLike("%" + search_word +"%");
        } else if (search_option.equals("user_name")) {
//            result = accompanyRepository.countByUserNameLike("%" + search_word +"%");
        }
        return result;
    }

}
