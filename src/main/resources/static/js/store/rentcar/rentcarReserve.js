
$(document).ready(function () {
    mainsearch();



    // 웹페이지 로드 시 초기화
    $("#result_location").hide();
    $("#result_depart_date").hide();
    $("#result_arrive_date").hide();

    $("#input_location").click(function () {
        if ($("#result_depart_date").is(":visible")) {
            $("#result_depart_date").hide();
        }

        if ($("#result_arrive_date").is(":visible")) {
            $("#result_arrive_date").hide();
        }

        $("#result_location").toggle();
    });


    $("#input_location_booking").click(function () {
        if ($("#result_location").is(":visible")) {
            $("#result_location").hide();
        }

        $("#result_depart_date").show();
    });


    $("#depart_date_booking").click(function () {
        if ($("#result_depart_date").is(":visible")) {
            $("#result_depart_date").hide();
        }


        $("#result_arrive_date").show();
    });


    $("#depart_date").click(function () {
        if ($("#result_location").is(":visible")) {
            $("#result_location").hide();
        }

        if ($("#result_arrive_date").is(":visible")) {
            $("#result_arrive_date").hide();
        }

        $("#result_depart_date").toggle();
    });


    $("#arrive_date").click(function () {
        if ($("#result_location").is(":visible")) {
            $("#result_location").hide();
        }

        if ($("#result_depart_date").is(":visible")) {
            $("#result_depart_date").hide();
        }


        $("#result_arrive_date").toggle();


    });

    $("#arrive_date_booking").click(function () {
        if ($("#result_arrive_date").is(":visible")) {
            $("#result_arrive_date").hide();
        }


    });





// 가격 높은 순 정렬을 위한 컨트롤러 연결
    $('#high_price_list_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/highPriceList", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });

    //가격 낮은 순 정렬
    $('#low_price_list_btn').click(function () {


        $.ajax({
            type: "GET",
            url: "/lowPriceList", // 낮은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    //차종순 정렬
    $('#car_type_list_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/carTypeList", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    //국내외 구분 버튼 ajax
    // 구분 전체
    $('#nation_filter_button_all').click(function () {

        $.ajax({
            type: "GET",
            url: "/nationall", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    //국내외 구분 버튼 ajax
    // 구분 국내
    $('#domestic_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/nationDomestic", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    //국내외 구분 버튼 ajax
    // 구분 해외
    $('#oversea_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/nationOversea", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    //차종 구분 버튼 ajax
    // 차종 전체
    $('#type_filter_button_all').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByTypeAll", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 차종 경형
    $('#small_car_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByTypeSmall", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 차종 승용
    $('#midsize_car_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByTypeMid", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 차종 suv
    $('#suv_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByTypeSuv", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 차종 승합
    $('#rv_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByTypeRv", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 유종 전체
    $('#oil_filter_button_all').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByOilAll", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 유종 디젤
    $('#diesel_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByOilDiesel", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 유종 전기
    $('#elec_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByOilElec", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 유종 가솔린
    $('#gasoline_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByOilGasoline", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 유종 lpg
    $('#lpg_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByOilLpg", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 유종 하이브리드
    $('#hybrid_btn').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByOilHybrid", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 나이 21세 이상
    $('#21over').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByAgeDown", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    // 나이 26세 이상
    $('#26over').click(function () {

        $.ajax({
            type: "GET",
            url: "/FindByAgeUp", // 높은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


// 왼쪽 사이드바에서 키워드 검색 이벤트
    $('#reserve_search_btn').click(function () {
        var searchKeyword = $('#searchKeyword').val();

        $.ajax({
            type: "GET",
            url: "/rentcarReserveKeyword", // 컨트롤러 엔드포인트 URL
            dataType: "json", // 반환되는 데이터 형식
            data: {
                searchKeyword: searchKeyword // 검색어를 쿼리 매개변수로 전달
            },
            success: function (data) {
                // 서버에서 반환된 데이터를 사용하여 화면 업데이트
                updateCarList(data);
            },
            error: function () {
                alert("데이터를 불러오는 중에 오류가 발생했습니다.");
            }
        });
    });


    //검색결과 정렬 css 작동 js
    // low_price_list_btn 버튼을 초기 active 상태로 설정
    $('#low_price_list_btn').addClass('active');

    $('.result_category_text').click(function () {
        $('.result_category_text').removeClass('active'); // 모든 버튼에서 active 클래스 제거
        $(this).addClass('active'); // 클릭한 버튼에 active 클래스 추가
    });

    //필터링 버튼 활성화/비활성화 js

    //국내외 구분버튼
    $('#nation_filter_button_all').addClass('active');

    $('.nation_filter_button').click(function () {
        $('.nation_filter_button').removeClass('active'); // 모든 버튼에서 active 클래스 제거
        $(this).addClass('active'); // 클릭한 버튼에 active 클래스 추가
    });

//차종 버튼
    $('#type_filter_button_all').addClass('active');

    $('.type_filter_button').click(function () {
        $('.type_filter_button').removeClass('active'); // 모든 버튼에서 active 클래스 제거
        $(this).addClass('active'); // 클릭한 버튼에 active 클래스 추가
    });


    //유종 버튼
    $('#oil_filter_button_all').addClass('active');

    $('.oil_filter_button').click(function () {
        $('.oil_filter_button').removeClass('active'); // 모든 버튼에서 active 클래스 제거
        $(this).addClass('active'); // 클릭한 버튼에 active 클래스 추가
    });

    //운전자 나이버튼
    $('.age_filter_button').click(function () {
        $('.age_filter_button').removeClass('active'); // 모든 버튼에서 active 클래스 제거
        $(this).addClass('active'); // 클릭한 버튼에 active 클래스 추가
    });


});


//결과 상자의 호버효과


//정렬조건에 따라 결과 상자 보여주기
function updateCarList(data) {
    var carListContainer = $(".reserv_result_gird");
    carListContainer.empty(); // 기존 목록 삭제



    // 반환된 데이터를 반복하여 목록에 추가
    for (var i = 0; i < data.length; i++) {
        var carInfo = data[i];


        var carItemHtml = `
                <!-- 차량 안내 카드1 -->
                <div class="result_item" id="result_item" onclick="location.href='/store/rentcar/rentcarChoice?car_name=${carInfo.car_name}&cartype=${carInfo.car_type}&caryear=${carInfo.car_year}&carprice=${carInfo.car_discount}&carimg=${carInfo.car_img}'">
                    <div><span class="result_item_year">${carInfo.car_year}</span></div>
                    <p class="result_item_name" id="car_name">${carInfo.car_name}</p>
                    <p class="result_item_type">${carInfo.car_type}</p>
                    <img src="/img/store/rentcar/${carInfo.car_img}" style="width: 100%; max-width: 163px;">
                    <div class="price_box">
                      
                        <p class="PriceText-iWKXRR result_item_price">${carInfo.car_discount}</p>
                        <p class="result_item_price">원</p>
                    </div>
                </div>
            `;

        console.log(carInfo.car_img);
        carListContainer.append(carItemHtml);
    }
}



//reserve 페이지로 넘어갔을 때 자동으로 결과 나오는 함수
function mainsearch() {


    $.ajax({
        type: "GET",
        url: "/lowPriceList", // 낮은 가격순으로 정렬하는 컨트롤러 엔드포인트 URL
        dataType: "json", // 반환되는 데이터 형식
        success: function (data) {
            // 서버에서 반환된 데이터를 사용하여 화면 업데이트
            var carListContainer = $(".reserv_result_gird");

            for (var i = 0; i < data.length; i++) {
                var carInfo = data[i];
                var carItemHtml = `
                <!-- 차량 안내 카드1 -->
                <div class="result_item" id="result_item" onclick="location.href='/store/rentcar/rentcarChoice?car_name=${carInfo.car_name}&cartype=${carInfo.car_type}&caryear=${carInfo.car_year}&carprice=${carInfo.car_discount}&carimg=${carInfo.car_img}'">
                    <div><span class="result_item_year">${carInfo.car_year}</span></div>
                    <p class="result_item_name" id="car_name">${carInfo.car_name}</p>
                    <p class="result_item_type">${carInfo.car_type}</p>
                    <img src="/img/store/rentcar/${carInfo.car_img}" style="width: 100%; max-width: 163px;">
                    <div class="price_box">
                      
                        <p class="PriceText-iWKXRR result_item_price">${carInfo.car_discount}</p>
                        <p class="result_item_price">원</p>
                    </div>
                </div>
            `;
                carListContainer.append(carItemHtml);
            }

        },

        error: function () {
            alert("데이터를 불러오는 중에 오류가 발생했습니다.");
        }

    });

}

