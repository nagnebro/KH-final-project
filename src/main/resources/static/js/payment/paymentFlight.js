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

    // 유저 성별 정보 가져오기
    const userGender = document.getElementById("userGender").value;

    var radioMale = document.getElementById("rad-gender-M-ADT-0");
    var radioFemale = document.getElementById("rad-gender-F-ADT-0");

    if (userGender === "M") {

        radioMale.checked = true;

    } else if (userGender === "F") {

        radioFemale.checked = true;
    }

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
        $('.insurance-info').eq(idx).addClass('showTale'); // sections-con.show > section:nth-child(3) > table
    });

    /*###################################################################################################################*/

    // 출발, 도착시간 날짜 슬라이싱
    const departureInputValue = document.getElementById('departureDate').value;
    const departureYear = departureInputValue.slice(0, 4);
    const departureMonth = departureInputValue.slice(4, 6);
    const departureDay = departureInputValue.slice(6, 8);
    const departureHour = departureInputValue.slice(8, 10);
    const departureMinute = departureInputValue.slice(10);

    const arrivalInputValue = document.getElementById('arrivalDate').value;
    const arrivalYear = arrivalInputValue.slice(0, 4);
    const arrivalMonth = arrivalInputValue.slice(4, 6);
    const arrivalDay = arrivalInputValue.slice(6, 8);
    const arrivalHour = arrivalInputValue.slice(8, 10);
    const arrivalMinute = arrivalInputValue.slice(10);

    const daysOfWeek = ["일", "월", "화", "수", "목", "금", "토"];

    // 해당 날짜의 요일 구하기
    const departureDate = new Date(departureYear, departureMonth - 1, departureDay);
    const departureDayIndex = departureDate.getDay();
    const departureDayOfWeekString = daysOfWeek[departureDayIndex];

    const arrivalDate = new Date(arrivalYear, arrivalMonth - 1, arrivalDay);
    const arrivalDayIndex = arrivalDate.getDay();
    const arrivalDayOfWeekString = daysOfWeek[arrivalDayIndex];

    // 구한 날짜와 시간 표현
    const formattedDepartureDate = `${departureYear}년 ${departureMonth}월 ${departureDay}일 (${departureDayOfWeekString}) ${departureHour}:${departureMinute}`;
    const formattedArrivalDate = `${arrivalHour}:${arrivalMinute}`;

    // 출발 날짜와 도착 날짜가 1일 이상일시 표시하기
    const oneDayMilliseconds = 24 * 60 * 60 * 1000;
    const daysDifference = Math.floor((arrivalDate - departureDate) / oneDayMilliseconds);
    const daysDifferenceString = daysDifference > 0 ? `+(${daysDifference}일)` : '';

    // 최종 표시할 형식
    const flightDateInfo = `${formattedDepartureDate} - ${formattedArrivalDate} ${daysDifferenceString}`;

    // HTML 요소에 표시
    var FlightDateInfoElement = document.getElementById("flightDateInfo");
    FlightDateInfoElement.innerText = flightDateInfo;

    /*###################################################################################################################*/

    // tab 기능
    var $tablink = $('.second-box-tabs div').click(function (e) {

        var idx = $tablink.index(this);

        $('.second-box-tabs div').css('color', 'rgb(170, 170, 170)');
        $('.second-box-tabs div').css('font-weight', '400');
        $(this).css('color', '#00B8FF');
        $(this).css('font-weight', '600');

        var marginLeftValue = idx * 50;
        $('.second-box-tabs-clicked-bar').animate({

            'margin-left': marginLeftValue + '%'

        }, 200)

        $('.sections-con').removeClass('show');
        $('.third-box section:first > div').eq(idx).addClass('show')

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
                icon: 'warning',
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
                $('#btnKakaoPay').prop('disabled', false);
            });


        } else {

            Swal.fire({
                title: '잘못 입력했습니다. 인증번호를 확인하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {
                $('#input_auth').focus();
                $('#btnKakaoPay').disabled = ture;

            });

        }
    })

    /*###################################################################################################################*/

// 카카오결제
    $("#btnKakaoPay").click(function () {

        var query = {

            input_name: $("#input_name").val(),
            input_phone: $("#input_phone").val(),
            // input_birth: $("#input_birth").val(),
            // monthDay: $("#input_birth").val().substring(2),
            checkVal: $("input[formcontrolname=gender]:checked"),
            selectBox: $("select[name='selectBox']").val()

        }

        const phoneNumberPattern = /^01[0-9]-\d{3,4}-\d{4}$/; // 휴대폰 형식검사 정규 표현식
        const birthpattern = /^(\d\d)(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$/;
        var btnKakaoPay = document.getElementById('btnKakaoPay');
        var warningDiv = document.getElementById('warning');

        if (query.input_name === '') {

            /*######이름 입력 검사######*/
            Swal.fire({
                title: '이름을 입력하세요.',
                icon: 'error',
                confirmButtonColor: '#00b8ff',
                confirmButtonText: '확인'
            }).then(function () {

                $("#input_name").focus();
                btnKakaoPay.disabled = true;
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
                btnKakaoPay.disabled = true;

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

            var checkFlag = true;

            var billInfo = {                          // 결제 정보를 form에 저장한다.
                ticTicketId: $("#ticketId").val(),
                ticFlightDepartureDate: $("#departureDate").val(),
                ticFlightArrivalDate: $("#arrivalDate").val(),
                ticSeatGrade: $("#seatGrade").val(),
                ticAirlineName: $("#airlineName").val(),
                ticFee: $("#totalPrice").val(),
                ticFromLocation: $("#fromLocation").val(),
                ticToLocation: $("#toLocation").val(),
                ticVihicleId: $("#vehicleId").val(),
                ticAirlineLogo: $("#ticketLogo").val(),
                checkFlag: checkFlag,
            };


            $.ajax({                                  // 카카오페이 결제전송
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