package com.kh.myproject.api.kakaoPay.service;


import com.kh.myproject.api.kakaoPay.model.dto.*;
import com.kh.myproject.api.kakaoPay.model.entity.CrawlingEntity;
import com.kh.myproject.api.kakaoPay.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayService {

    @Autowired
    private CrawlingRepository crawlingRepository;
    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    // header() 세팅
    private HttpHeaders getHeaders() {

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "KakaoAK " + "31a5df416cc5ad95dd5dee5fdba74286");
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }


    // 카카오페이 레디
    public KakaoPayReadyVO kakaoPayReady(PaybillDto billInfo) {

        log.info("############################## KakaoPayService => kakaoPayReady start");

        // 동적 URL
        String approvalUrl = "http://localhost:8080/pay/success";
        String cancelUrl = "http://localhost:8080/pay/cancel";
        String failUrl = "http://localhost:8080/pay/fail";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "여행시그널");                            // 가맹점 이름
        params.add("tax_free_amount", "0");                                   // 필수지만 필요 없는 항목
        params.add("partner_user_id", billInfo.getUser().getUserId());       // 구매자 유저 id

        // Quantity 및 item_name 설정
        if (billInfo.getCheckFlag()) {
            params.add("quantity", "0");                                   // 분기로 사용
            params.add("item_name", billInfo.getTicFromLocation() + "-" + billInfo.getTicToLocation() + "행 항공권");  // 상품명
            params.add("total_amount", billInfo.getTicFee());             //상품 총액
        } else {
            params.add("quantity", "1");
            params.add("item_name", billInfo.getRentName());
            params.add("total_amount", billInfo.getRentPrice());
        }

        params.add("approval_url", approvalUrl);
        params.add("cancel_url", cancelUrl);
        params.add("fail_url", failUrl);

        KakaoPayApprovalVO dataSet = new KakaoPayApprovalVO();
        dataSet.setPartner_user_id(billInfo.getUser().getUserId());

        if (dataSet.getAmount() == null) {

            dataSet.setAmount(new AmountVO());

        }

        if (billInfo.getCheckFlag()) {

            dataSet.getAmount().setTotal(Integer.valueOf(billInfo.getTicFee()));

        } else {

            dataSet.getAmount().setTotal(Integer.valueOf(billInfo.getRentPrice()));

        }

        kakaoPayApprovalVO = dataSet;

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders()); //(parameters, this.getHeaders()

        // 외부url요청 통로 열기.
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/ready";

        // restTemplate으로 값을 보내고 받아온 KakaoPayReadyVO값 kakaoPayReadyVO 저장.
        kakaoPayReadyVO = restTemplate.postForObject(url, body, KakaoPayReadyVO.class);

        // 받아온 값 return
        return kakaoPayReadyVO;
    }


    // 결제 승인
    public KakaoPayApprovalVO payApprove(String pg_token) {

        log.info("######################## KakaoPayInfoVO start");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "여행시그널");
        params.add("partner_user_id", kakaoPayApprovalVO.getPartner_user_id());
        params.add("pg_token", pg_token);
        params.add("total_amount", String.valueOf(kakaoPayApprovalVO.getAmount().getTotal()));

        log.info(params.toString());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());


        // 외부url 통신
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/approve";

        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스.
        kakaoPayApprovalVO = restTemplate.postForObject(url, body, KakaoPayApprovalVO.class);
        log.info("결제승인 응답객체: " + kakaoPayApprovalVO);

        return kakaoPayApprovalVO;

    }

    public List<CrawlingDto> getAllCrawlingData() {

        List<CrawlingEntity> entityList = crawlingRepository.findAllCrawlingData();
        List<CrawlingDto> dtoList = new ArrayList<>();

        for (CrawlingEntity entity : entityList) {
            CrawlingDto dto = entity.toDto(); // 엔티티를 DTO로 변환
            dtoList.add(dto);
        }

        return dtoList;
    }
}
