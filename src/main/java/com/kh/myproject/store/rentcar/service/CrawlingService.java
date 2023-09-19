//package com.kh.myproject.store.rentcar.service;
//
//import com.kh.myproject.store.rentcar.model.dto.CrawlingDto;
////import lombok.extern.slf4j.Slf4j;
////import org.jsoup.Jsoup;
////import org.jsoup.nodes.Document;
////import org.jsoup.nodes.Element;
////import org.jsoup.select.Elements;
////import org.springframework.stereotype.Service;
////
////import javax.annotation.PostConstruct;
////import java.io.IOException;
////import java.util.ArrayList;
////import java.util.List;
////
////@Slf4j
////@Service
////public class CrawlingService {
////    private static String URL = "https://whitelabel.imsmobility.co.kr/tripsoda/reservation/request/247?pickupAt=2023-09-06T01%3A00%3A00.000Z&dropoffAt=2023-09-07T01%3A00%3A00.000Z&pickupLat=33.50707895781836&pickupLng=126.492769004244&birth=000101&pickupAddress=%EC%A0%9C%EC%A3%BC%EA%B5%AD%EC%A0%9C%EA%B3%B5%ED%95%AD&pickupFullAddress=%EC%A0%9C%EC%A3%BC%ED%8A%B9%EB%B3%84%EC%9E%90%EC%B9%98%EB%8F%84+%EC%A0%9C%EC%A3%BC%EC%8B%9C+%EA%B3%B5%ED%95%AD%EB%A1%9C+2+%EC%A0%9C%EC%A3%BC%EA%B5%AD%EC%A0%9C%EA%B3%B5%ED%95%AD&insuranceAge=21&isJeju=true&sortOption=low_price&rentType=DAILY&submodelId=2";
////
////    @PostConstruct
////    public List<CrawlingDto> getReviewDatas() throws IOException {
////        List<CrawlingDto> reviewList = new ArrayList<>();
////        Document doc = Jsoup.connect(URL).get();
////        Elements document = doc.select(".review"); // 리뷰박스를 감싸는 클래스
////        Elements contents = document.select(".StyledReviewBox-kQYaKO XkTcB"); // 리뷰 elements
////
////
////        for (Element content : contents) {
////            CrawlingDto Crwl = CrawlingDto.builder()
////                    .img(content.select(".StyledReviewBox-kQYaKO XkTcB > div > div > div > div > img").attr("abs:src"))// 별 이미지
//                    .rating(content.select(".StyledReviewBox-kQYaKO XkTcB > div > div > div:nth child(2)").text()) // 평점
//                    .ratingDetail(content.select(".StyledReviewBox-kQYaKO XkTcB > div > div:nth child(2)").text()) // 세부평점
//                    .date(content.select(".StyledReviewBox-kQYaKO XkTcB > div > div:nth child(3)").text()) // 추가 날짜
//                    .reviewText(content.select(".StyledReviewBox-kQYaKO XkTcB > div:nth child(2)").text()) // 평글
//                    .build();
//            reviewList.add(Crwl);
//        }
//
//        return reviewList;
//    }
//
//}
