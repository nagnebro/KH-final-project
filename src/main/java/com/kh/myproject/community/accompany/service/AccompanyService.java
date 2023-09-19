package com.kh.myproject.community.accompany.service;


import com.kh.myproject.community.accompany.entity.Accompany;
import com.kh.myproject.community.accompany.repository.AccompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

@Service
public class AccompanyService {


    @Autowired
    AccompanyRepository accompanyRepository;


    public Accompany updateAccompany(Accompany accompany) {

        System.out.print("수정 전에 가져온 accompany 값" + accompany);


        accompanyRepository.updateAccompany(accompany);
        // save는 덮어씌우기 때문에 직접 query문을 실행한다..


        Accompany result = accompanyRepository.findById(accompany.getAc_num()).orElse(null);
        System.out.println("수정 후 가져온 result 값" + result);

        return result;
    }

    public List<Accompany> findAll(){

        return accompanyRepository.findAll();
    }

    public List<Accompany> selectBySearchName(String searchName){


        return accompanyRepository.findByAc_textOrAc_titleOrderBOrderByAc_regdateDesc("%" + searchName + "%");
    }

    public int getMaxAcNum() {

        return accompanyRepository.findTopByAc_num();
    }

//    public List<Accompany> (String in_period){
//        return accompanyRepository.findByAc_startdateIsBetweenAndAc_enddate();
//    }

    public int getMaxAcViewCount() {

        return accompanyRepository.findTopByAc_viewcount();
    }


    public String saveAccompanyImage(String file_name, MultipartFile multipartfile) {


        String fileName = multipartfile.getOriginalFilename(); // 사용자가 올린 파일의 진짜 이름을 가지고 온다.
        String new_fileName = "";

        if (fileName.equals("") || fileName == null) {
            return "accDefault.png";
        }



        System.out.println("넘어온 fiel_name" + file_name);

        String resourcesPath = "";

        try {
            ClassPathResource resource = new ClassPathResource("/static/file/accompany_image"); // 빈 문자열로 생성
            URLDecoder.decode(resource.getPath(),"UTF-8"); // 어차피 경로가 영어기때문에 디코딩 시키지 않아도 됨.

            File file = resource.getFile(); // 위의 resource객체의 경로를 똑같이 가지는 file객체 생성.
            resourcesPath = file.getAbsolutePath(); // 해당 경로 전체를 읽어온다.

            System.out.println("Resources 폴더 경로: " + resourcesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 실제 로직에서는 유저 아이디의 앞부분 (@스플릿)과 확장자명을 더해서 저장한다.


        // request 객체를 이용해서 여기서 파일업로드를 진행한다.

        String extension = fileName.substring(fileName.lastIndexOf(".")); // 가지고 온 파일의 진짜 이름을 .을 포함한 확장자명까지 가지고 온다.
        new_fileName = file_name + extension; // 확장자명은 그대로 가지고 오고 새로운 이미지 (게시글 번호)번호로 서버에 파일을 저장시킨다.

        String filePath = resourcesPath + "/" + new_fileName; // 서버의 절대경로와 파일이름을 합친곳에 file객체를 저장시킨다.

        System.out.println("newfilename" + new_fileName);
        File file = new File(filePath);

        System.out.println(filePath);
        try {
            multipartfile.transferTo(file);

        } catch (IOException e) {
            e.printStackTrace();

        }
        return new_fileName;



    }


    // 조회수 증가 메서드ㅜ
    public void increaseViewCount(Long ac_num){

        accompanyRepository.increaseViewCount(ac_num);
    }

    public Accompany getAccompany(Long ac_num){


        return accompanyRepository.findById(ac_num).orElse(null);
    }

    public Accompany saveAccompany(Accompany accompany){

        return accompanyRepository.save(accompany);
    }




    public int selectAcoompanyCount(){

        int count = accompanyRepository.selectAccompanyCount();
        return count;
    }

    public List<Accompany> findAccompanyByPage(int pageNo){

//        accompanyRepository.findUserByPage(startNo,endNo);
        Pageable pageable = PageRequest.of(pageNo-1,12);
        Page<Accompany> pageList = accompanyRepository.findAll(pageable);
        List<Accompany> accompanyList = pageList.getContent();

        return accompanyList;
    }




}
