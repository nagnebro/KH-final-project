package com.kh.myproject.store.tour.contoller;

import com.kh.myproject.api.naverBlog.BlogSearch;
import com.kh.myproject.store.tour.model.dto.TourismDto;
import com.kh.myproject.store.tour.model.vo.*;
import com.kh.myproject.store.tour.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@Slf4j
@SessionAttributes("user")
public class TourController {


    @Autowired
    private TourService tourService;
    private final String TOURAPI_URL = "https://apis.data.go.kr/B551011/KorService1/";
    private final String SERVICE_KEY = "serviceKey=ZgRTKBFIJGjeIJ14VHOZrP9UMtis8xSBTJvnPqQIigzUQ4aIL8V03y5XCVZ5B8GAKHaJX%2FOz2UpnX%2FvgKqv38w%3D%3D";

    private final String LAST_URL = "&MobileOS=ETC&MobileApp=AppTest&_type=json";
    private final String DETAILCOMMON_LAST_URL = "&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&numOfRows=10&pageNo=1";


    @GetMapping("store/home")
    public ModelAndView storeHome(ModelAndView mav) {
        mav.setViewName("store/tour/tourMain");
        return mav;
    }

    @GetMapping("/store/tour/tourSearch")
    public ModelAndView tourSearch(ModelAndView mav) {
        mav.setViewName("store/tour/tourSearch");

        return mav;
    }

    @GetMapping("/store/tour/cities")
    public ModelAndView cities(@RequestParam("cityName") String cityName,
                               ModelAndView mav) {
        log.info("cityName={}", cityName);
        mav.addObject("cityName", cityName);
        List<TourismDto> list = tourService.getTourism(cityName);

        log.info("tourismList={}", list);
        mav.addObject("tourismList", list);
        mav.setViewName("store/tour/cities");

        return mav;
    }


    @GetMapping("/store/tour/tourDetail")
    public ModelAndView tourDetail(@RequestParam("contentId") String contentId,
                                   ModelAndView mav) {

        //기본정보
        String url = TOURAPI_URL + "detailCommon1?" + SERVICE_KEY;
        url += "&contentId=" + contentId;
        url += DETAILCOMMON_LAST_URL + LAST_URL;

        detailCommon data = tourService.getDetailCommon(url);
        log.info("detailCommon={}", data);

        mav.addObject("detailCommon", data);
        mav.addObject("contentTypeId", data.getContentTypeId());

        //상세정보
        url = TOURAPI_URL + "detailIntro1?" + SERVICE_KEY;
        url += "&contentId=" + contentId;
        url += "&contentTypeId=" + data.getContentTypeId() + LAST_URL;

        switch (data.getContentTypeId()) {
            case "12":
                detailTourSpot detailTourSpot = tourService.getDetailTourSpot(url);
                mav.addObject("detailIntro", detailTourSpot);
                break;
            case "14":
                detailCulture detailCulture = tourService.getDetailCulture(url);
                mav.addObject("detailIntro", detailCulture);
                break;
            case "15":
                detailEvent detailEvent = tourService.getDetailEvent(url);
                mav.addObject("detailIntro", detailEvent);
                break;
            case "25":
                detailTourCourse detailTourCourse = tourService.getDetailTourCourse(url);
                mav.addObject("detailIntro", detailTourCourse);
                break;
            case "28":
                detailLeports detailLeports = tourService.getDetailLeports(url);
                mav.addObject("detailIntro", detailLeports);
                break;
            case "32":
                detailAccommodation detailAccommodation = tourService.getDetailAccommodation(url);
                mav.addObject("detailIntro", detailAccommodation);
                break;
            case "38":
                detailShopping detailShopping = tourService.getDetailShopping(url);
                mav.addObject("detailIntro", detailShopping);
                break;
            case "39":
                detailRestaurant detailRestaurant = tourService.getDetailRestaurant(url);
                mav.addObject("detailIntro", detailRestaurant);
                break;
        }

        mav.setViewName("store/tour/tourDetail");

        return mav;
    }

    @GetMapping("/store/tour/getArea")
    public ResponseEntity<?> getArea() {
        StringBuilder result = new StringBuilder();
        String url = TOURAPI_URL + "areaCode1?" + SERVICE_KEY;
        url += "&numOfRows=17" + LAST_URL;

        return ResponseEntity.ok(tourService.getApiData(url));
    }

    @GetMapping("/store/tour/getSigungu")
    public ResponseEntity<?> getSigungu(@RequestParam("areaCode") String areaCode) {
        String url = TOURAPI_URL + "areaCode1?" + SERVICE_KEY;
        url += "&areaCode=" + areaCode;
        url += "&numOfRows=40" + LAST_URL;
        return ResponseEntity.ok(tourService.getApiData(url));
    }

    @GetMapping("/store/tour/getCat1")
    public ResponseEntity<?> getCat1(@RequestParam("contentTypeId") String contentTypeId) {
        String url = TOURAPI_URL + "categoryCode1?" + SERVICE_KEY;
        url += "&contentTypeId=" + contentTypeId + LAST_URL;
        return ResponseEntity.ok(tourService.getApiData(url));
    }

    @GetMapping("/store/tour/getCat2")
    public ResponseEntity<?> getCat2(@RequestParam("contentTypeId") String contentTypeId,
                                     @RequestParam("cat1") String cat1) {
        String url = TOURAPI_URL + "categoryCode1?" + SERVICE_KEY;
        url += "&contentTypeId=" + contentTypeId;
        url += "&cat1=" + cat1 + LAST_URL;

        return ResponseEntity.ok(tourService.getApiData(url));
    }

    @GetMapping("/store/tour/getCat3")
    public ResponseEntity<?> getCat3(@RequestParam("contentTypeId") String contentTypeId,
                                     @RequestParam("cat1") String cat1,
                                     @RequestParam("cat2") String cat2) {
        String url = TOURAPI_URL + "categoryCode1?" + SERVICE_KEY;
        url += "&contentTypeId=" + contentTypeId;
        url += "&cat1=" + cat1;
        url += "&cat2=" + cat2 + LAST_URL;

        return ResponseEntity.ok(tourService.getApiData(url));
    }

    @GetMapping("/store/tour/getAreaBaseList")
    public ResponseEntity<?> getAreaBaseList(@RequestParam("contentTypeId") String contentTypeId,
                                             @RequestParam("areaCode") String areaCode,
                                             @RequestParam("sigunguCode") String sigunguCode,
                                             @RequestParam("cat1") String cat1,
                                             @RequestParam("cat2") String cat2,
                                             @RequestParam("cat3") String cat3,
                                             @RequestParam("pageNo") int pageNo) {

        log.info("contentTypeId={}", contentTypeId);
        log.info("areaCode={}", areaCode);
        log.info("sigunguCode={}", sigunguCode);
        log.info("cat1={}", cat1);
        log.info("cat2={}", cat2);
        log.info("cat3={}", cat3);

        String url = TOURAPI_URL + "areaBasedList1?" + SERVICE_KEY;
        url += "&numOfRows=12" + "&pageNo=" + pageNo;
        url += "&listYN=Y&arrange=A";
        url += "&contentTypeId=" + contentTypeId;
        url += "&areaCode=" + areaCode;
        url += "&sigunguCode=" + sigunguCode;
        url += "&cat1=" + cat1;
        url += "&cat2=" + cat2;
        url += "&cat3=" + cat3 + LAST_URL;

        return ResponseEntity.ok(tourService.getApiData(url));
    }

    @GetMapping("/store/tour/getSearchKeyword")
    public ResponseEntity<?> getSearchKeyword(@RequestParam("areaCode") String areaCode,
                                              @RequestParam("sigunguCode") String sigunguCode,
                                              @RequestParam("cat1") String cat1,
                                              @RequestParam("cat2") String cat2,
                                              @RequestParam("cat3") String cat3,
                                              @RequestParam("pageNo") int pageNo,
                                              @RequestParam("keyWord") String keyWord) throws UnsupportedEncodingException {

        log.info("areaCode={}", areaCode);
        log.info("sigunguCode={}", sigunguCode);
        log.info("cat1={}", cat1);
        log.info("cat2={}", cat2);
        log.info("cat3={}", cat3);
        log.info("keyWord={}", keyWord);

        String url = TOURAPI_URL + "searchKeyword1?" + SERVICE_KEY;
        url += "&numOfRows=12" + "&pageNo=" + pageNo;
        url += "&listYN=Y&arrange=A";
        url += "&areaCode=" + areaCode;
        url += "&sigunguCode=" + sigunguCode;
        url += "&cat1=" + cat1;
        url += "&cat2=" + cat2;
        url += "&cat3=" + cat3;
        url += "&keyword=" + URLEncoder.encode(keyWord, "UTF-8") + LAST_URL;

        return ResponseEntity.ok(tourService.getApiData(url));
    }


    @GetMapping("/store/tour/searchBlog")
    public String searchBlog(@RequestParam("title") String title,
                             @RequestParam("start") int start) {
        log.info("title={}", title);
        return BlogSearch.getSearch(title, start);
    }
}
