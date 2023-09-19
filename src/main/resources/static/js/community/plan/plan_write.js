//버튼 관련

//일정 작성 완료 버튼 클릭 시 plan(일정 메인)으로 이동    (원래: 마이페이지-내 일정으로 이동)
const CompleteMySchedule = document.querySelector('.complete_write_btn');

function test(e) {
    if (e.id === "writePlaceAdd") {
        location.href = `/community/plan/add?day=${e.value}&type=write`;
    }
}


var dayValues = document.querySelectorAll('.place_add_btn');
//여행 일정 추가 버튼을 누르면 테이블 추가해주는 함수
var dayValue = dayValues.length + 1; // 테이블 번호 카운터 초기화

// 이벤트 리스너를 추가하여 버튼 클릭 시 테이블을 추가하는 함수를 호출
document.getElementById('schedule_add_btn').addEventListener('click', addTable);


function addTable() {
    console.log('추가 버튼 클릭');
    // 새로운 테이블 요소를 생성
    var newTable = document.createElement('table');
    newTable.classList.add('table'); // 클래스 추가

    // 새로운 테이블의 내용 (thead와 tbody) 생성
    var tableContent = `
                <thead>
                    <tr>
                        <th scope="col">
                            <span class="ctn1_span">
                                <span class="ctn1_span2">
                                    <svg width="15" height="15" viewBox="0 0 24 24" fill="#253333" xmlns="http://www.w3.org/2000/svg" color="#333333" size="15">
                                        <path fill-rule="evenodd" clip-rule="evenodd" d="M21.5143 12.7857C22.1619 12.462 22.1619 11.538 21.5143 11.2143L3.2715 2.09388C2.68733 1.80182 2 2.22657 2 2.87962L2 9.63235C2 10.1346 2.3726 10.559 2.87068 10.624L13.4216 12L2.87068 13.376C2.3726 13.441 2 13.8654 2 14.3676L2 21.1204C2 21.7734 2.68733 22.1982 3.2715 21.9061L21.5143 12.7857Z" fill="#ffffff"></path>
                                    </svg>
                                </span>
                                <span class="day1_span">Day${dayValue}</span>
                            </span>
                        </th>
                        <th scope="col">여행지</th>
                        <th scope="col">장소분류</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row"><div class="ctn1_des_num">${dayValue}</div></th>
                        <td>새로운 여행지</td>
                        <td>새로운 장소분류</td>
                        <td>
                            <div class="sc_des_mark_hujitong">
                                <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512">
                                    <style>svg {fill: #df5368}</style>
                                    <path d="M135.2 17.7C140.6 6.8 151.7 0 163.8 0H284.2c12.1 0 23.2 6.8 28.6 17.7L320 32h96c17.7 0 32 14.3 32 32s-14.3 32-32 32H32C14.3 96 0 81.7 0 64S14.3 32 32 32h96l7.2-14.3zM32 128H416V448c0 35.3-28.7 64-64 64H96c-35.3 0-64-28.7-64-64V128zm96 64c-8.8 0-16 7.2-16 16V432c0 8.8 7.2 16 16 16s16-7.2 16-16V208c0-8.8-7.2-16-16-16zm96 0c-8.8 0-16 7.2-16 16V432c0 8.8 7.2 16 16 16s16-7.2 16-16V208c0-8.8-7.2-16-16-16zm96 0c-8.8 0-16 7.2-16 16V432c0 8.8 7.2 16 16 16s16-7.2 16-16V208c0-8.8-7.2-16-16-16z"/>
                                </svg>
                            </div>
                        </td>
                    </tr>
                </tbody>
            `;

    // 새로운 테이블에 내용을 삽입
    newTable.innerHTML = tableContent;


    // "장소 추가"와 "메모 추가" 버튼을 포함하는 <div> 요소 생성
    var buttonsDiv = document.createElement('div');
    buttonsDiv.style.width = '100%';
    buttonsDiv.style.height = '71px';
    buttonsDiv.style.display = 'flex';
    buttonsDiv.className = 'btm_btn';

    // "장소 추가" 버튼 추가
    var placeButton = document.createElement('button');
    placeButton.className = 'place_add_btn';
    placeButton.textContent = '장소 추가';
    placeButton.id = 'writePlaceAdd';
    placeButton.onclick = function () {
        test(this)
    };
    placeButton.value = dayValue.toString();
    buttonsDiv.appendChild(placeButton);

    // // "메모 추가" 버튼 추가
    // var memoButton = document.createElement('button');
    // memoButton.className = 'memo_add_btn';
    // memoButton.textContent = '메모 추가';
    // buttonsDiv.appendChild(memoButton);
    // // buttonsDiv를 tableContainer에 추가

    // 새로운 테이블을 컨테이너에 추가
    var table_area = document.createElement('div');
    table_area.classList.add("table_area");
    table_area.appendChild(newTable);
    table_area.appendChild(buttonsDiv);


    var article3 = document.querySelector('.article3');
    article3.appendChild(table_area);

    // 테이블 번호 카운터 증가
    dayValue++;

}

// 테이블 행 삭제하는 함수
function deletePlan(day, place_name) {


    console.log(day);
    console.log(place_name);

    $.ajax({
        url: '/community/plan/deletePlan',
        type: 'post',
        data: {
            day: day,
            placeName: place_name
        },
        success: function (data) {
            console.log('성공');
            location.reload();
        },
        error: function (data) {
            console.log('삭제실패');
        }

    });

}


//일정작성완료 버튼 클릭 시
$('.complete_write_btn').on('click', function () {
    // alert("일정이 저장되었습니다.")
    var pbTitle = $('.art1_div_subject').val();
    var pbStartDate = $('#select_start_date').val();
    var pbEndDate = $('#select_end_date').val();
    var pbRegion = $('.form-select_place').val();
    var pbViewCount = 0;

    if (pbStartDate === '') {
        Swal.fire({
            title: '떠나는 날을 선택해 주세요.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        }).then(function () {
            return;
        });
    } else if (pbEndDate === '') {
        Swal.fire({
            title: '돌아오는 날을 선택해 주세요.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        }).then(function () {
            return;
        });
    } else if (pbRegion === '') {
        Swal.fire({
            title: '지역을 선택해 주세요.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        }).then(function () {
            return;
        });
    } else {

        // planBoardDTO 객체 생성 및 값 설정
        var planBoardDTO = {
            // pbNum: pbNum, //게시글번호
            // pbWriteDate: pbWriteDate, //작성일자
            pbTitle: pbTitle, //글제목
            pbStartDate: pbStartDate, //시작날짜
            pbEndDate: pbEndDate, //종료날짜
            pbRegion: pbRegion, //여행지역
            pbViewCount: pbViewCount, //조회수
        };

// AJAX 요청 설정
        $.ajax({
            url: '/community/plan/completePlan', // 실제 서버 엔드포인트 URL
            type: 'post',
            contentType: 'application/json', // 데이터 형식을 JSON으로 설정
            data: JSON.stringify(planBoardDTO), // 직렬화된 JSON 데이터를 요청 데이터로 설정
            success: function (data) {
                console.log('성공');
                Swal.fire({
                    title: '일정이 저장되었습니다.',
                    icon: 'success',
                    confirmButtonColor: '#00b8ff',
                    confirmButtonText: '확인',
                }).then(function () {

                    // 서버 응답에 대한 처리
                    location.href = "/community/plan";
                });
                // 서버 응답에 대한 처리
            },
            error: function (data) {
                console.log('실패');
                // 에러 처리
            }
        });
    }
});


//--------------------------------------------------------------------------------


// 날짜 선택 관련
// 이벤트 리스너를 등록하여 날짜 선택이 변경될 때마다 실행되도록 합니다.
document.getElementById('select_start_date').addEventListener('change', function () {
    // 선택한 날짜 값을 가져옵니다.
    var selectedStartDate = this.value;

    // <div class="sc_date_start"> 요소의 내용을 선택한 날짜로 업데이트합니다.
    document.querySelector('.sc_date_start').textContent = selectedStartDate;
});

// 이벤트 리스너를 등록하여 날짜 선택이 변경될 때마다 실행되도록 합니다.
document.getElementById('select_end_date').addEventListener('change', function () {
    // 선택한 날짜 값을 가져옵니다.
    var selectedEndDate = this.value;

    // <div class="sc_date_start"> 요소의 내용을 선택한 날짜로 업데이트합니다.
    document.querySelector('.sc_date_end').textContent = selectedEndDate;
});

//----------------------------------------------------------------------------------

//카카오맵 관련
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.379777, 126.545873), // 지도의 중심좌표
        level: 10 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 지도에 표시된 마커 객체를 가지고 있을 배열입니다
var markers = [];

// Plan 객체에서 pbdX와 pbdY 값을 추출합니다
var pbdXElements = document.querySelectorAll('.pbdx');


var pbdYElements = document.querySelectorAll('.pbdy');

// 좌표를 담을 배열
var positions = [];

// Plan 객체에서 pbdX와 pbdY 값을 추출하여 좌표 배열에 추가
for (var i = 0; i < pbdXElements.length; i++) {
    var pbdX = parseFloat(pbdXElements[i].value); // 숫자로 변환
    var pbdY = parseFloat(pbdYElements[i].value); // 숫자로 변환

    console.log(pbdX);
    console.log(pbdY);

    if (!isNaN(pbdX) && !isNaN(pbdY)) {
        var position = new kakao.maps.LatLng(pbdY, pbdX);
        positions.push(position);
    }
}

// positions 배열에 있는 좌표로 마커를 추가합니다
for (var i = 0; i < positions.length; i++) {
    addMarker(positions[i]);
}

// 마커를 생성하고 지도위에 표시하는 함수입니다
function addMarker(position) {

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        position: position
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);

    // 생성된 마커를 배열에 추가합니다
    markers.push(marker);
}

// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
function setMarkers(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

//2. 지도에 컨트롤 올리기 추가
// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

