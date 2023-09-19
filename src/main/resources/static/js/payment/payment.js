$(function () {


    /*###################################################################################################################*/

    // 유저 이름, 핸드폰 번호 가져오기
    const defaultUserName = document.getElementById('userName').value;
    const defaultUserPhoneNum = document.getElementById('userPhone').value;

    var defaultUserNameElement = document.getElementById("input_name");
    var defaultUserPhoneNumElement = document.getElementById("input_phone");

    defaultUserNameElement.value = defaultUserName;
    defaultUserPhoneNumElement.value = defaultUserPhoneNum;

    if (!defaultUserName === '' || !defaultUserPhoneNum === '') {

        defaultUserNameElement.disabled = ture;
        defaultUserNameElement.backgroundColor = rgb(246, 246, 246);

    }

    /*###################################################################################################################*/

    // tab 기능
    var $tablink = $('.second-box-tabs div').click(function (e) {

        var idx = $tablink.index(this);

        $('.second-box-tabs div').css('color', 'rgb(170, 170, 170)');
        $('.second-box-tabs div').css('font-weight', '400');
        $(this).css('color', '#00B8FF');
        $(this).css('font-weight', '600');

        var marginLeftValue = idx * 33.3333;

        $('.second-box-tabs-clicked-bar').animate({
            'margin-left': marginLeftValue + '%'
        }, 200)

        $('.sections-con').removeClass('show');
        $('.third-box section:first > div').eq(idx).addClass('show')

        /*###################################################################################################################*/

        // 카카오 맵
        // 카카오맵의 지도가 로드되고 css가 바뀌게 되면 카카오맵이 감지하는 사이즈와 달라짐
        // 그러므로 display block이 되고난 이후 시점에 수동으로 relayout() 해주기
        if (idx === 2) {

            var mapContainer = document.getElementById('kakaoMap'), // 지도를 표시할 div
                mapOption = {
                    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                };

            // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
            var map = new kakao.maps.Map(mapContainer, mapOption);
            // 주소-좌표 변환 객체를 생성합니다
            var geocoder = new kakao.maps.services.Geocoder();

            // 주소로 좌표를 검색합니다
            geocoder.addressSearch($('#companyAddress').text(), function (result, status) {

                // 정상적으로 검색이 완료됐으면
                if (status === kakao.maps.services.Status.OK) {

                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                    // 결과값으로 받은 위치를 마커로 표시합니다
                    var marker = new kakao.maps.Marker({
                        map: map,
                        position: coords
                    });

                    // 마커가 지도 위에 표시되도록 설정합니다
                    marker.setMap(map);

                    // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                    map.setCenter(coords);

                    // div 요소에서 텍스트를 가져옴
                    var textFromDiv = document.getElementById('companyAddress').textContent;

                    // 링크를 생성하고 텍스트 값을 URL에 추가
                    var link = document.querySelector('.kakaoMap-direct');
                    link.href = 'https://map.kakao.com/link/search/' + encodeURIComponent(textFromDiv);
                }
            });
        }
    });

    /*###################################################################################################################*/

    // 모달 열기
    $('.shuttle-button').click(function () {

        $('.modal').css('display', 'block');
        $("body").css('overflow', 'hidden');
        // $("body").css('margin-left', '1px');  // 스크롤로 인한 화면 꿀렁거림 제거
        // 이유 모르겠는데 위 현상 없어짐

    })
    // 모달 닫기
    $('.modal-btn').click(function () {

        $('.modal').css('display', 'none');
        $("body").css('overflow', 'auto');
        // $("body").css('margin-left', '0px');  // 스크롤로 인한 화면 꿀렁거림 제거
        // 이유 모르겠는데 위 현상 없어짐

    })

    /*###################################################################################################################*/

    // 텍스트 숨기기
    $('.arrow').click(function () {

        if ($(this).hasClass('false')) {

            $('.arrow').removeClass('false');
            $('.arrow').addClass('onArrow');
            $('.policy-detail').css('max-height', '989px');
            $('.policy-detail').css('margin-bottom', '20px');

        } else {

            $('.arrow').removeClass('onArrow');
            $('.arrow').addClass('false');
            $('.policy-detail').css('max-height', '0px');
            $('.policy-detail').css('margin-bottom', '0px');

        }
    })

    /*###################################################################################################################*/

    // 내륙, 제주 지역 선택텝
    var $landLocation = $('.insurance-locations div').click(function () {

        var idx = $landLocation.index(this);

        $('.insurance-locations div').removeClass('locationClick');
        $(this).addClass('locationClick');

        $('.insurance-info').removeClass('showTale');
        $('.sections-con.show > section:nth-child(1) > table').eq(idx).addClass('showTale');
    });

    /*###################################################################################################################*/

    // 대여 가능 여부 생년 검사
    $('#input_birth').keyup(() => {

        var input_birth = document.getElementById('input_birth');
        var warningDiv = document.getElementById('warning');
        var inputYear = parseInt(input_birth.value.substring(0, 2));
        var currentYear = new Date().getFullYear() % 100;
        var btnKakaoPay = document.getElementById('btnKakaoPay');

        if (input_birth.value >= 2) {

            var inputValue = parseInt(input_birth.value.substring(0, 2));

        }

        if (input_birth.value.length >= 6) {

            result = currentYear - inputYear;
            absolute = Math.abs(result);
            console.log(absolute);

            if (absolute >= 20) {

                console.log('20이상');
                // 20세 이상
                warningDiv.textContent = "만 20세, 해당 차량 대여 가능합니다.";
                warningDiv.style.color = "#0064de"; // 기본 색상으로 변경
                btnKakaoPay.disabled = false;

            } else if (absolute < 20) {

                console.log('20이하');
                // 20세 미만
                warningDiv.textContent = "20세 미만입니다.";
                warningDiv.style.color = "red"; // 빨간색으로 변경
                btnKakaoPay.disabled = true;

            }

        } else {

            warningDiv.textContent = "'-' 을 제외한 생년월일 6자리를 입력하세요"; // 경고 메시지 초기화
            btnKakaoPay.disabled = true;

        }
    });

    /*###################################################################################################################*/

    // 핸드폰 인증
    var auth_num = '';
    var auth_check = false;
    var id_check = false;

    // 인증번호 요청, 재요청 클릭시
    $('#verifyBtn').click(function () {

        const phoneNumberPattern = /^01([0|1|6|7|8|9]?)([0-9]{3,4})([0-9]{4})$/;
        const user_phone = $("#input_phone").val();

        if (phoneNumberPattern.test(user_phone)) {

            $('#verify').removeClass('auth');
            $('#verifyBtn').text('재요청');

        } else {

            Swal.fire({
                title: '잘못된 번호입니다.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                btnKakaoPay.disabled = true;
                $("#input_phone").focus();
            });

        }

        if (!auth_check) { // 인증완료가 아직 안됐을 경우.

            $.ajax({
                url: '/member/joinAuth',
                method: 'POST',
                data: {user_phone: user_phone},
                success: function (data) {


                    console.log(data); // controller에서 넘긴 data를 받아온다.
                    auth_num = data;

                    // 인증번호 칸 열기

                },
                error: function () {

                }

            });
        } else {

            Swal.fire({
                title: '이미 인증이 완료됐습니다.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            });
        }
    })

    $('#verifyConfirmBtn').click(function () {

        if (auth_num === $('#input_auth').val() || auth_check) {

            Swal.fire({
                title: '인증이 완료됐습니다.',
                icon: 'success',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                auth_check = true;

                $('#verify').addClass('auth');
                $('#verifyBtn').addClass('auth');
                $('#input_phone').val("인증완료");
                $('#input_phone').css('color', '#0064de');
                $('#input_phone').css('border-color', '#0064de');
                $('#input_phone').prop('disabled', true);
                $("#btnKakaoPay").disabled = false;
            });

        } else {

            Swal.fire({
                title: '잘못 입력했습니다. 인증번호를 확인하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $('#input_auth').focus();
                auth_check = false;
                $("#btnKakaoPay").disabled = ture;
            });


        }
    })

    /*###################################################################################################################*/

    // html에 이쁘게 표기하기
    const daysOfWeek = ["일", "월", "화", "수", "목", "금", "토"];

// 시작일과 종료일 문자열
    var startDateStr = document.querySelector("#depart-date").value;
    var endDateStr = document.querySelector("#arrive-date").value;
    console.log(startDateStr);
    console.log(endDateStr);

// 시작일과 종료일을 "월. 일. 시간" 형식에서 날짜와 시간으로 분리
    var startDateParts = startDateStr.split('. ');
    var endDateParts = endDateStr.split('. ');

// "월. 일" 형식의 날짜를 Date 객체로 변환 (현재 년도 기준으로)
    var startDate = new Date(new Date().getFullYear(), parseInt(startDateParts[0]) - 1, parseInt(startDateParts[1]), parseInt(startDateParts[2]));
    var endDate = new Date(new Date().getFullYear(), parseInt(endDateParts[0]) - 1, parseInt(endDateParts[1]), parseInt(endDateParts[2]));

// 두 날짜 사이의 차이를 계산
    var timeDiff = endDate - startDate;

    var startDayOfWeek = daysOfWeek[startDate.getDay()];
    var endDayOfWeek = daysOfWeek[endDate.getDay()];

// 밀리초를 일로 변환 (1일 = 24시간 * 60분 * 60초 * 1000밀리초)
    var daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24));

// 계산된 일수를 card-top-round-day 엘리먼트에 출력
    document.querySelector(".card-top-round-day").textContent = daysDiff + "일";
    document.querySelector("#depart-date-text").textContent = `${startDateParts[0]}.${startDateParts[1]}(${startDayOfWeek})`;
    document.querySelector("#arrive-date-text").textContent = `${endDateParts[0]}.${endDateParts[1]}(${endDayOfWeek})`;
    document.querySelector("#depart-time").textContent = `${startDateParts[2]}`;
    document.querySelector("#arrive-time").textContent = `${endDateParts[2]}`;

    /*###################################################################################################################*/

    // 총 가격 계산하기
    function formatNumberWithCommas(number) {

        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    }

// 문자열 값을 정수로 변환합니다.
    var amountIntType = parseInt(document.querySelector("#car-price").textContent);
    var premiumPriceElement = document.querySelector("#premiumPrice");

// 콤마(,)를 제거하고 숫자로 변환
    var premiumPriceIntType = parseInt(premiumPriceElement.textContent.replace(/,/g, ""), 10);

// 두 정수 값을 더합니다.
    var total = amountIntType + premiumPriceIntType;
    const formattedAmount3 = formatNumberWithCommas(total);

    // 콤마(,)를 추가하고 반환
    document.querySelector("#emphasis-price").textContent = formattedAmount3 + "원";
    document.querySelector("#btnKakaoPay").textContent = formattedAmount3 + "원 바로 결제하기";

    // success페이지 결과에도 콤마 넣기


    var amount = document.querySelector("#car-price").textContent;
    var amount2 = document.querySelector("#car-price2").textContent;

    const formattedAmount = formatNumberWithCommas(amount);
    const formattedAmount2 = formatNumberWithCommas(amount2);

    document.querySelector("#car-price").textContent = formattedAmount;
    document.querySelector("#car-price2").textContent = formattedAmount2;

    /*###################################################################################################################*/

    // 옵션 갯수 계산
    var carOptionString = document.querySelector(".prod-option-info-detail").textContent;
    var carOptionArray = carOptionString.split(',');

    var numberOfOptions = carOptionArray.length;

    if (numberOfOptions === 1 && carOptionArray[0].trim() === "옵션없음") {

        numberOfOptions = 0; // 옵션이 없는 경우 0으로 설정

    }

    document.querySelector("#optionLength").textContent = "총 " + numberOfOptions + "개 옵션";

    /*###################################################################################################################*/

    // 카카오결제
    $("#btnKakaoPay").click(function () {

        var query = {

            input_name: $("#input_name").val(),
            input_phone: $("#input_phone").val(),
            input_birth: $("#input_birth").val(),
            monthDay: $("#input_birth").val().substring(2),
            checkVal: $("input[formcontrolname=gender]:checked"),
            selectBox: $("select[name='selectBox']").val()

        }

        const phoneNumberPattern = /^01[0-9]-\d{3,4}-\d{4}$/; // 휴대폰 형식검사 정규 표현식
        const birthpattern = /^(\d\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/;
        var btnKakaoPay = document.getElementById('btnKakaoPay');
        var warningDiv = document.getElementById('warning');

        if (query.input_name === '') {

            /*######이름 입력 검사######*/
            query.input_name.disabled = false;
            Swal.fire({
                title: '이름을 입력하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $("#input_name").focus();
            });

        } else if (query.input_phone === '') {

            /*######핸드폰 입력 검사######*/
            Swal.fire({
                title: '핸드폰 번호를 입력하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $("#input_phone").focus();
            });

        } else if (!auth_check) {

            /*######핸드폰 인증 검사######*/
            Swal.fire({
                title: '핸드폰 인증을 완료하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $("#input_phone").focus();
            });


        } else if (query.input_birth === '') {

            /*######생년월일 입력 검사######*/
            Swal.fire({
                title: '생년월일을 입력하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $("#input_birth").focus();
            });

        } else if (!birthpattern.test(query.input_birth)) {

            /*######생년월일 유효성 검사######*/
            console.log(birthpattern.test(input_birth.value));
            warningDiv.textContent = "정확한 생년월일을 입력 하세요.";
            warningDiv.style.color = "red";
            btnKakaoPay.disabled = true;
            $("#input_birth").focus();

        } else if (!query.checkVal) {

            /*######성별 선택 검사######*/
            Swal.fire({
                title: '성별을 선택해 주세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                btnKakaoPay.disabled = true;
                $("input[formcontrolname='gender']:checked").focus();
            });

        } else if (query.selectBox === "") {

            /*######국적 선택 검사######*/
            Swal.fire({
                title: '국적을 선택해 주세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $("select[name='selectBox']").focus();
            });

        } else {

            /*########결제 진입########*/

            $("#btnKakaoPay").disabled = false;

            var checkFlag = false;

            var billInfo = {                // 결제 정보를 form에 저장한다.

                rentName: $("#carName").val(),
                rentType: $("#carType").val(),
                rentYear: $("#carYear").val(),
                rentCompany: $("#comName").val(),
                rentOption: $("#carOption").val(),
                rentOil: $("#oilType").val(),
                rentPrice: total,
                rentDepartureDate: $('#departureDate').val(),
                rentArrivalDate: $('#arrivalDate').val(),
                rentImg: $("#carImg").val(),
                rentAccommodate: $("#carPeople").val(),
                checkFlag: checkFlag,

            };


            $.ajax({                        // 카카오페이 결제전송

                type: 'post'
                , url: '/kakaoPay',
                data: JSON.stringify(billInfo), // JSON 데이터 전송
                contentType: 'application/json' // JSON 데이터임을 명시


                , success: function (response) {

                    // 화면 중앙에 위치시키기 위한 x, y 좌표 계산
                    var screenWidth = window.screen.width;
                    var screenHeight = window.screen.height;
                    var popupWidth = 500; // 팝업 창 가로 크기
                    var popupHeight = 600; // 팝업 창 세로 크기
                    var left = (window.screen.width / 2) - (popupWidth / 2);
                    var top = (window.screen.height / 2) - (popupHeight / 2);

                    // 팝업 창 열기
                    var popup = window.open(response.next_redirect_pc_url, "_blank", "width=500,height=600,left=" + left + ",top=" + top);

                }
            })
        }
    })
});


