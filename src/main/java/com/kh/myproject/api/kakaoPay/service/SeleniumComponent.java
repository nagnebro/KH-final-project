//package com.kh.myproject.api.kakaoPay.service;
//
//import com.kh.myproject.api.kakaoPay.model.dto.CrawlingDto;
//import com.kh.myproject.api.kakaoPay.model.entity.CrawlingEntity;
//import com.kh.myproject.api.kakaoPay.repository.CrawlingRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.beans.factory.BeanCreationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//@Slf4j
//@Service
//public class SeleniumComponent {
//
//    @Autowired
//    private CrawlingRepository crawlingRepository;
//
////    private CrawlingEntity crawlingEntity;
//
//    public SeleniumComponent() {
//        this.chrome();
//    }
//
////    private CrawlingDto crawlingDto;
//// /Users/kim/Downloads/chromedriver-mac-x64
//    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
//    public static String WEB_DRIVER_PATH = "chromedriver"; // WebDriver 경로
//
//    private WebDriver driver;
////    private final List<CrawlingDto> reviewList = new ArrayList<>();
//
////    private CrawlingDto crawlingDto;
//
//    private final String url = "https://tripsoda.com/";
//
//    private void chrome() {
//        log.info("################################driver options");
//        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//
//        // webDriver 옵션 설정.
//        ChromeOptions options = new ChromeOptions();
////        options.setHeadless(true);
//        options.addArguments("--lang=ko");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--remote-allow-origins=*");
//        options.setCapability("ignoreProtectedModeSettings", true);
//
//        // weDriver 생성.
//        driver = new ChromeDriver(options);
//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//        log.info("################################driver options done");
//    }
//
//
//    @PostConstruct
//    public List<CrawlingDto> getReviews() throws InterruptedException {
//        log.info("################################crawling start");
//
//        driver.get(url);
//        Thread.sleep(500);
//
//
//        WebElement closePopup = driver.findElement(By.xpath("//*[@id=\"__next\"]/aside/div/div/div[2]/button[2]"));
//        closePopup.click();
////        Thread.sleep(500);
//        log.info("closePopup 클릭");
//
//        WebElement openMenu = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/header/div/div/div[2]"));
//        openMenu.click();
//        Thread.sleep(200);
//        log.info("openMenu 클릭");
//
//
//        WebElement loginBtn = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/header/div/div/div[3]/div[2]/div[1]/div[2]/div/div/p"));
//        loginBtn.click();
//        Thread.sleep(200);
//        log.info("loginBtn 클릭");
//
//
//        WebElement selectEmailLogin = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div/div/div/button[2]"));
//        selectEmailLogin.click();
//        Thread.sleep(500);
//        log.info("selectEmailLogin 클릭");
//
//        WebElement inputIdFocuse = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div/main/div/fieldset/div[1]/input"));
//        inputIdFocuse.click();
//        inputIdFocuse.sendKeys("kwonch3370@naver.com");
//        log.info("inputIdFocuse 클릭, 입력");
//
//
//        WebElement inputPwFocuse = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div/main/div/fieldset/div[2]/input"));
//        inputPwFocuse.click();
//        inputPwFocuse.sendKeys("listentome1#");
//        log.info("inputIdFocuse 클릭, 입력");
//
//
//        WebElement loginBtnConfirm = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div/main/div/fieldset/button"));
//        loginBtnConfirm.click();
//        Thread.sleep(500);
//        log.info("loginBtnConfirm 클릭");
//        //    kwonch3370@naver.com//*[@id="__next"]/div[1]/div/main/div/fieldset/button
//        //    listentome1#
//
//
//        WebElement selectStore = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[4]"));
//        selectStore.click();
//        Thread.sleep(500);
//        log.info("selectStore 클릭");
//
//        WebElement selectRentcar;
//        try{
//            selectRentcar = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/div/div/div[2]/div[2]/div[2]/div[2]/div/div[3]"));
//        }catch (BeanCreationException e){
//            log.info("에러떴다.");
//            selectRentcar = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div/div[2]/div[2]/div/div[1]/div[2]"));
//
//
//        }
//        selectRentcar.click();
//        Thread.sleep(500);
//        log.info("selectRentcar 클릭");
//
//
//        WebElement inputBirth = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div/div[3]/div/div/div/div[5]/div[2]/input"));
//        inputBirth.click();
//        inputBirth.sendKeys("000101");
////        Thread.sleep(500);
//        log.info("inputBirth 클릭, 입력");
//
//
//        WebElement searchCarBtn = driver.findElement(By.xpath("//*[@id=\"/web/차량검색-검색버튼\"]"));
//        searchCarBtn.click();
//        Thread.sleep(1000);
//        log.info("searchCarBtn 클릭");
//
//        WebElement selectCar = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div/div[2]/div[2]/div/div[1]/div[1]"));
//        selectCar.click();
//        Thread.sleep(1000);
//        log.info("selectCar 클릭");
//
//        WebElement selectCompany = driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/div[2]/div/div[2]/div[2]/div/div/div[1]"));
//        selectCompany.click();
//        Thread.sleep(1500);
//        log.info("selectCompany 클릭");
//
//        WebElement selectTab = driver.findElement(By.xpath("//*[@id=\"/tripsoda/reservation/request/7529/탭-업체/리뷰\"]"));
//        selectTab.click();
//        Thread.sleep(500);
//        log.info("selectTab 클릭");
//
//
//        log.info("######################리뷰 도착######################");
//
//        List<WebElement> reviewElements = driver.findElements(By.className("XkTcB")); // 리뷰박스를 감싸는 클래스
//        List<CrawlingDto> reviewList = new ArrayList<>();
//        log.info("starImages{}", reviewElements);
//        Thread.sleep(200);
//
//
//        log.info("#########################reviewElements{}", reviewElements);
//        for (WebElement reviewElement : reviewElements) {
//            log.info("#########################들어왔니??");
//
////            List<WebElement> starImages = reviewElement.findElements(By.tagName("img"));
////            List<String> imgSrcList = new ArrayList<>();
//
////            for (WebElement imgElement : starImages) {
////                String imgsrc = imgElement.getAttribute("src");
////                imgSrcList.add(imgsrc);
////            }
//
//            WebElement ratingElement = reviewElement.findElement(By.cssSelector(".StyledReviewBox-kQYaKO.XkTcB div[style*='font-size: 14px; font-weight: 500;']"));
//            log.info("ratingElement{}", ratingElement);
//
//            WebElement ratingDetailElement = reviewElement.findElement(By.cssSelector(".StyledReviewBox-kQYaKO.XkTcB div[style*='font-size: 13px; color: rgb(170, 170, 170); flex: 1 1 0%; text-align: end;']")); // 세부평점
//            log.info("ratingDetailElement{}", ratingDetailElement);
//
//            WebElement dateElement = reviewElement.findElement(By.cssSelector(".StyledReviewBox-kQYaKO.XkTcB div[style*='color: rgb(170, 170, 170); font-size: 13px;']")); // 추가 날짜
//            log.info("dateElement{}", dateElement);
//
//            WebElement reviewTextElement = reviewElement.findElement(By.cssSelector(".StyledReviewBox-kQYaKO.XkTcB div[style*='word-break: break-word; margin: 15px 0px;']")); // 평글
//            log.info("reviewTextElement{}", reviewTextElement);
//
//
//            String rating = ratingElement.getText();
//            String ratingDetail = ratingDetailElement.getText();
//            String date = dateElement.getText();
//            String reviewText = reviewTextElement.getText();
//
////            CrawlingDto crwl = new CrawlingDto(imgSrcList, rating, ratingDetail, date, reviewText);
//            CrawlingDto crwl = new CrawlingDto(rating, ratingDetail, date, reviewText);
//            CrawlingEntity crawlEntity = crwl.toEntity();
//            crawlingRepository.save(crawlEntity);
//
//            reviewList.add(crwl);
//            log.info("################################reviewList{}", reviewList);
//
//        }
//        log.info("################################crawling done");
//        driver.close();
//        driver.quit();
//        return reviewList;
//
//    }
//
//    public void quitDriver() {
//        driver.quit(); // webDriver 종료
//    }
//}
//
