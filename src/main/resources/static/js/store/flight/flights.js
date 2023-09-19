var pageNo = 1;
var numOfRows = 10;
var thStartAirport = "";
var thEndAirport = "";
var thStartDate = "";

/* 공항 리스트 */
$(function () {

    console.log($('#sessionUser').val());
    if ($('#thStartAirport') !== undefined) {
        thStartAirport = $('#thStartAirport').val();
        thEndAirport = $('#thEndAirport').val();
        thStartDate = $('#thStartDate').val();
    }

    console.log(thStartAirport);
    $.ajax({
        url: '/store/flight/airportList',
        type: 'get',
        dataType: 'json',
        success: function (data) {

            var item = data.response.body.items.item;

            /* 출발 */
            for (var i = 0; i < item.length; i++) {
                var content = `<option value=${item[i].airportId}>
                                  ${item[i].airportNm}
                                  </option>`;

                if (item[i].airportNm === '김포' && thStartAirport === undefined) {
                    content = `<option value=${item[i].airportId} selected>
                                  ${item[i].airportNm}
                                  </option>`;
                    $('#start_airport').append(content);
                    continue;
                } else if (thStartAirport !== undefined && item[i].airportId === thStartAirport) {
                    content = `<option value=${item[i].airportId} selected>
                                  ${item[i].airportNm}
                                  </option>`;
                    $('#start_airport').append(content);
                    continue;
                }
                $('#start_airport').append(content);
            }

            /* 도착 */
            for (var i = 0; i < item.length; i++) {
                var content = `<option value=${item[i].airportId}>
                                  ${item[i].airportNm}
                                  </option>`;

                if (item[i].airportNm === '제주' && thEndAirport === undefined) {
                    content = `<option value=${item[i].airportId} selected>
                                  ${item[i].airportNm}
                                  </option>`;
                    $('#end_airport').append(content);
                    continue;
                } else if (thEndAirport !== undefined && item[i].airportId === thEndAirport) {
                    content = `<option value=${item[i].airportId} selected>
                                  ${item[i].airportNm}
                                  </option>`;
                    $('#end_airport').append(content);
                    continue;
                }
                $('#end_airport').append(content);

            }
            if (thStartDate !== undefined) {
                $('#flight_date').val(thStartDate);

                console.log($('#start_airport').val());
                console.log($('#end_airport').val());
                console.log($('#flight_date').val());
                pageNo = 1;
                $('.loading_wrap').css('display', 'block');
                searchFlight(pageNo, numOfRows);
            }
        }
    });


});


/* 검색 시작 */
$('#flight_search_btn').on('click', function () {
    if ($('#flight_date').val() === '') {
        Swal.fire({
            title: '날짜를 입력하세요.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        });
    } else {
        pageNo = 1;
        $('.loading_wrap').css('display', 'block'); //검색 모달

        $('.result_title').empty();
        $('.result_table_wrap').empty();
        searchFlight(pageNo, numOfRows);
    }

});

/* 더보기 */
$('.hr-sect').on('click', function () {
    pageNo++;
    searchFlight(pageNo, numOfRows);
});

/* 검색 */
function searchFlight(pageNo, numOfRows) {

    var totalCount;
    var data;


    data = {
        startAirport: $('#start_airport').val(),
        endAirport: $('#end_airport').val(),
        startDate: $('#flight_date').val(),
        pageNo: pageNo,
        numOfRows: numOfRows
    };


    $.ajax({
        url: '/store/flight/searchFlight',
        type: 'get',
        data: data,
        dataType: 'json',
        success: function (data) {
            var itemList = data.response.body.items.item;
            totalCount = data.response.body.totalCount;
            var startAirport = $('#start_airport option:checked').text();
            var endAirport = $('#end_airport option:checked').text();
            var startDate = $('#flight_date').val();
            var dayOfWeek = getDayOfWeek(startDate);
            var item;


            if (pageNo === 1) {
                var title = `<span>${startAirport}</span>` +
                    '<i></i>' +
                    `<span>${endAirport}</span>` +
                    `<span id="result_flight_date">${startDate}(${dayOfWeek})</span>` +
                    `<hr/>` +
                    `<h6>검색결과 : <b id="total_count">${totalCount}</b>건</h6>`

                $('.result_title').append(title);
            }

            if (totalCount === 0) {
                $('.result_table_wrap').empty();
                $('.none_result_table').css('display', 'block');

                $('.loading_wrap').css('display', 'none');
                return;
            } else {
                $('.none_result_table').css('display', 'none');
            }

            if (itemList.length === undefined) {
                item = itemList;
                appendFlight(item);
            } else {
                for (var i = 0; i < itemList.length; i++) {
                    item = itemList[i];
                    appendFlight(item);

                }
            }

            if ((pageNo * numOfRows) < totalCount) {
                $('.hr-sect').css('display', 'flex');
            } else {
                $('.hr-sect').css('display', 'none');
            }

            $('.loading_wrap').css('display', 'none'); //모달 종료


        },
        error: function (request, status, error) {

            console.log("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            $('.loading_wrap').css('display', 'none');
        }
    });
    $('.result_table').on('click', function () {
        console.log(1);
    });
}

function getDayOfWeek(date) { //ex) getDayOfWeek('2022-06-13')

    const week = ['일', '월', '화', '수', '목', '금', '토'];

    const dayOfWeek = week[new Date(date).getDay()];

    return dayOfWeek;
}

function getLogo(airlineName) {

    switch (airlineName) {
        case "아시아나항공" :
            return "asiana.jpg";
        case "에어부산" :
            return "airbusan.jpg";
        case "에어서울" :
            return "airseoul.jpg";
        case "이스타항공" :
            return "eastar.jpg";
        case "플라이강원":
            return "flygangwon.jpg";
        case "하이에어":
            return "hiair.png";
        case "제주항공" :
            return "jejuair.jpg";
        case "진에어" :
            return "jinair.jpg";
        case "대한항공" :
            return "korean.jpg";
        case "티웨이항공" :
            return "tway.jpg";
        case "에어로케이" :
            return "aerok.png";
    }
}

function getUrl(airlineName) {
    switch (airlineName) {
        case "아시아나항공" :
            return "https://flyasiana.com/C/KR/KO/index";
        case "에어부산" :
            return "https://www.airbusan.com/content/individual/";
        case "에어서울" :
            return "https://flyairseoul.com/";
        case "이스타항공" :
            return "https://www.eastarjet.com/newstar/PGWHC00001";
        case "플라이강원":
            return "https://flygangwon.com/ko/main/main.do";
        case "하이에어":
            return "https://www.hi-airlines.com/";
        case "제주항공" :
            return "https://www.jejuair.net/ko/main/base/index.do";
        case "진에어" :
            return "https://www.jinair.com/booking/index";
        case "대한항공" :
            return "https://www.koreanair.com/?hl=ko";
        case "티웨이항공" :
            return "https://www.twayair.com/app/main";
        case "에어로케이" :
            return "https://www.aerok.com/kr";
    }
}

/* 검색 결과 생성 */
function appendFlight(item) {

    var logo = getLogo(item.airlineNm);
    item.logo = logo;
    var param = JSON.stringify(item);

    var startMin = item.depPlandTime.toString().slice(-2);
    var startHour = item.depPlandTime.toString().slice(-4, 10);
    var startTime = startHour + ":" + startMin;
    var endMin = item.arrPlandTime.toString().slice(-2);
    var endHour = item.arrPlandTime.toString().slice(-4, 10);
    var endTime = endHour + ":" + endMin;

    var price = item.economyCharge;

    var img = `<div class="airline_box">
                            <div class="airline_item">
                            <div class="airline_logos">
                            <img src="/img/store/flight/${logo}">
                            </div>
                            <b class="airline_name">${item.airlineNm}</b>
                            </div>
                            </div>`;

    var route = `<div class="airline_route">
                            <span class="start_route">
                            <b>${item.depAirportNm}(${startTime})</b>
                            </span>
                            <span class="end_route">
                            <b>${item.arrAirportNm}(${endTime})</b>
                            </span>
                            </div>`;

    if (price === undefined) {
        price = `<div class="airline_price">
                            <div>
                            <b style="color: red">항공사 문의</b>
                            </div>
                            </div>`;
    } else {
        price = `<div class="airline_price">
                            <div>
                            <b>₩${price.toLocaleString("ko-KR")}</b>
                            </div>
                            </div>`;
    }

    var content = `
                            <div class="result_table" onclick='resFlight(${param})'>
                            <div class="result_table_inner">
                            <div class="result_schedule">
                            <div class="schedule_item">
                            ${img}
                            ${route}
                            </div>
                            </div>
                            ${price}
                            </div>
                            </div>`;

    $('.result_table_wrap').append(content);


}

function resFlight(param) {


    if (param.economyCharge === undefined) {
        window.open(getUrl(param.airlineNm), '_blank');
    } else {
        var parameter = {
            ticFlightDepartureDate: param.depPlandTime,
            ticFlightArrivalDate: param.arrPlandTime,
            ticSeatGrade: "이코노미",
            ticAirlineName: param.airlineNm,
            ticAirlineLogo: param.logo,
            ticFee: param.economyCharge,
            ticFromLocation: param.depAirportNm,
            ticToLocation: param.arrAirportNm,
            ticVihicleId: param.vihicleId
        };


        if ($('#sessionUser').val() === '') {
            Swal.fire({
                title: '로그인 후 이용 가능합니다.',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '로그인하러 가기',
                cancelButtonColor: '#d33',
                cancelButtonText: '더 둘러보기'
            }).then(function (result) {
                if(result.isConfirmed){
                    location.href = '/member/login';
                }
            });
        } else {
            $.ajax({
                url: '/store/flight/reservationFlight',
                type: 'post',
                data: JSON.stringify(parameter),
                contentType: 'application/json',
                success: function () {
                    location.href = '/store/flight/pay/flightPayment';
                }
            });
        }

    }
}
