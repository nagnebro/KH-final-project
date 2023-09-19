package com.kh.myproject.member.manager.service;


import com.kh.myproject.member.manager.model.entity.Manager;
import com.kh.myproject.member.manager.repository.UserRepositoryM;
import com.kh.myproject.member.user.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j // 데이터베이스 로그를 확인
@Service
public class UserServiceM {


    @Autowired
    private UserRepositoryM userRepository;



    public List<User> findAllUser(){

        List<User> userlist = userRepository.findAll();

        return userlist;
    }


    public List<Integer> getUserJoinCount(){

        List<Integer> countList = new ArrayList<>();

        for(int i = 9 ; i >= 0 ; i--){
            countList.add(userRepository.countByDate(i));
        }


        return countList;
    }

    public List<Object[]> getUserAgeCount(){

        return userRepository.getUserAgeCount();
    }

//    public Map<Integer,Integer> getUserAgeCount(){
//
//        Map<Integer,Integer> ageMap = new HashMap<>();
//        userRepository.selectUserByAge();
//
//        return ;
//    }


    public void deleteUser(Long user_number){

        userRepository.deleteById(user_number);
    }



    // 페이징 처리 관련 메서드  ------

    public int selectUserCount(){

        int count = userRepository.selectUserCount();
        return count;
    }

    public List<User> findUserByPage(int pageNo){

//        userRepository.findUserByPage(startNo,endNo);
        Pageable pageable = PageRequest.of(pageNo-1,10, Sort.by("userNumber").descending());
        Page<User> users = userRepository.findAll(pageable);
        List<User> userList = users.getContent();

        return userList;
    }

    public List<User> selectUserBySearchWord(int pageNo,String search_word,String search_option){

        Page<User> userList = null;
        Pageable pageable = PageRequest.of(pageNo-1,10); // 정렬기능 추가안했음.

        if(search_option.equals("user_id")){

            userList = userRepository.findByUserIdLike(pageable,"%" + search_word +"%");
        }else if(search_option.equals("user_name")){
            userList = userRepository.findByUserNameLike(pageable,"%" + search_word+ "%");

        }

        List<User> resultList = userList.getContent();
        System.out.println(resultList);


        return resultList;
    }

    public int countBysearchWorld(String search_word, String search_option){

        int result = 0;

        if(search_option.equals("user_id")){
            result = userRepository.countByUserIdLike("%" + search_word +"%");
        }else if(search_option.equals("user_name"))
            result = userRepository.countByUserNameLike("%" + search_word +"%");

        return result;
    }
    // 페이징 처리 관련 메서드 끝 -------




    public int countByUserGender(String gender){

        int count = userRepository.countByUserGender(gender);

        return count;
    }

    public User getUserByManager(Manager manager){

        return userRepository.findByUserIdAndUserPassword(manager.getManagerId(),manager.getManagerPassword());

    }


}



