// -----------------------------------------------------------------------------------------------------

// 카카오맵 js

var item = []; //일정 선택을 담을 배열
var type = document.getElementById('type').value;
console.log(type);
// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div.
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex: 1});

// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        Swal.fire('키워드를 입력해주세요.', '', 'warning')
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch(keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        console.log(data);
        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);

        // 페이지 번호를 표출합니다
        displayPagination(pagination);

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

        Swal.fire('검색결과가 존재하지 않습니다.', '', 'warning')
        return;

    } else if (status === kakao.maps.services.Status.ERROR) {

        Swal.fire('검색 결과 중 오류가 발생했습니다.', '', 'warning')
        return;

    }
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for (var i = 0; i < places.length; i++) {

        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function (marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function () {
                displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function () {
                infowindow.close();
            });

            itemEl.onmouseover = function () {
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout = function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환
function getListItem(index, places) {

    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
            '<div class="info">' +
            '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '   <span class="jibun gray">' + places.address_name + '</span>';
    } else {
        itemStr += '    <span>' + places.address_name + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone + '</span>' +
        '</div>';

    itemStr += `<div><button class="select_placebtn">선택</button></div>`;
    el.innerHTML = itemStr;
    el.className = 'item';

    // 장소 누르면 카카오 플레이스 이동
    var infoDiv = el.querySelector('.info');
    infoDiv.addEventListener('click', function () {
        window.open(places.place_url, "_blank", "width=900, height=750");
        //console.log(places.place_name);
    });

    //장소 검색 후 '선택' 클릭 시 temp 객체에 값을 저장 ->
    var placebtn = el.querySelector('.select_placebtn');

    placebtn.addEventListener('click', function () {


        temp = {
            pbdDate: $('#day').val(),
            pbdPlaceName: places.place_name,
            pbdCategoryGroupName: places.category_group_name,
            pbdX: places.x,
            pbdY: places.y
        };

        item.push(temp);
        console.log(item);

        // place_name 값을 alert로 출력
        Swal.fire(`${temp.pbdPlaceName} 선택`, '', 'info')

    });


    return el;

}

// 장소 추가하기 이동 버튼 클릭 시 plan_write(일정 쓰기)으로 이동
$('.add_place_btn').on('click', function () {

    $.ajax({
        url: '/community/plan/move',
        type: 'post',
        data: JSON.stringify(item),
        contentType: 'application/json',
        success: function (data) {
            if (type === 'detail') {
                window.history.go(-1);
            } else if (type === 'write') {
                location.href = "/community/plan/write?option=1";
                /*window.history.go(-1);*/
            }


        }

    });
});

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function (i) {
                return function () {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}


// 추가 기능


//2. 지도에 컨트롤 올리기 추가
// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

//3. Toolbox 사용하기
// 도형 스타일을 변수로 설정합니다
var strokeColor = '#39f',
    fillColor = '#cce6ff',
    fillOpacity = 0.5,
    hintStrokeStyle = 'dash';

var options = { // Drawing Manager를 생성할 때 사용할 옵션입니다
    map: map, // Drawing Manager로 그리기 요소를 그릴 map 객체입니다
    drawingMode: [
        kakao.maps.Drawing.OverlayType.MARKER,
        kakao.maps.Drawing.OverlayType.ARROW,
        kakao.maps.Drawing.OverlayType.POLYLINE,
        kakao.maps.Drawing.OverlayType.RECTANGLE,
        kakao.maps.Drawing.OverlayType.CIRCLE,
        kakao.maps.Drawing.OverlayType.ELLIPSE,
        kakao.maps.Drawing.OverlayType.POLYGON
    ],
    // 사용자에게 제공할 그리기 가이드 툴팁입니다
    // 사용자에게 도형을 그릴때, 드래그할때, 수정할때 가이드 툴팁을 표시하도록 설정합니다
    guideTooltip: ['draw', 'drag', 'edit'],
    markerOptions: {
        draggable: true,
        removable: true
    },
    arrowOptions: {
        draggable: true,
        removable: true,
        strokeColor: strokeColor,
        hintStrokeStyle: hintStrokeStyle
    },
    polylineOptions: {
        draggable: true,
        removable: true,
        strokeColor: strokeColor,
        hintStrokeStyle: hintStrokeStyle
    },
    rectangleOptions: {
        draggable: true,
        removable: true,
        strokeColor: strokeColor,
        fillColor: fillColor,
        fillOpacity: fillOpacity
    },
    circleOptions: {
        draggable: true,
        removable: true,
        strokeColor: strokeColor,
        fillColor: fillColor,
        fillOpacity: fillOpacity
    },
    ellipseOptions: {
        draggable: true,
        removable: true,
        strokeColor: strokeColor,
        fillColor: fillColor,
        fillOpacity: fillOpacity
    },
    polygonOptions: {
        draggable: true,
        removable: true,
        strokeColor: strokeColor,
        fillColor: fillColor,
        fillOpacity: fillOpacity
    }
};

// 위에 작성한 옵션으로 Drawing Manager를 생성합니다
var manager = new kakao.maps.Drawing.DrawingManager(options);

// Toolbox를 생성합니다.
// Toolbox 생성 시 위에서 생성한 DrawingManager 객체를 설정합니다.
// DrawingManager 객체를 꼭 설정해야만 그리기 모드와 매니저의 상태를 툴박스에 설정할 수 있습니다.
var toolbox = new kakao.maps.Drawing.Toolbox({drawingManager: manager});

// 지도 위에 Toolbox를 표시합니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOP은 위 가운데를 의미합니다.
map.addControl(toolbox.getElement(), kakao.maps.ControlPosition.TOP);



