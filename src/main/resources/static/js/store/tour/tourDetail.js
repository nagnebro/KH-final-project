var startNo = 1;
var title = $('.detail_info_container h4').text();
var mapX = '';
var mapY = '';
var overlay;
$(document).ready(function () {


    console.log("title = " + title);

    blogSearch(title, startNo);

    mapX = $('input[name=map_x]').val();
    mapY = $('input[name=map_y]').val();

    console.log("mapx = " + mapX);
    console.log("mapy = " + mapY);


});

function blogSearch(title, startNo) {
    $.ajax({
        url: '/store/tour/searchBlog',
        type: 'get',
        data: {
            title: title,
            start: startNo
        },
        datatype: 'text',
        success: function (data) {
            var items = JSON.parse(data).items;
            var total = JSON.parse(data).total;
            var start = JSON.parse(data).start;
            console.log(items);

            for (var i = 0; i < items.length; i++) {
                var content =
                    `<div class="blog_list">
                        <p class="blog_title">
                            <a href="${items[i].link}">${items[i].title}</a>
                        </p>
                        <p class="blog_content">
                            ${items[i].description}
                        </p>
                        <p class="blog_info">
                            <a href="${items[i].link}" class="blogger_name">
                                <img src="/img/store/tour/blog.png">
                                ${items[i].bloggername}
                            </a>
                            <span class="post_date">${items[i].postdate}</span>
                        </p>
                    </div> <hr/>`;

                $('#blog_search').append(content);
            }

            if (start >= total) {
                $('.more_search').css('display', 'none');
            }


        },
        error: function () {
            console.log('블로그 오류');
        }
    });
}

$('.more_search').on('click', function () {
    startNo += 5;
    console.log(startNo);

    blogSearch(title, startNo);
});

$('.map_btn').on('click', function () {
    $('.map_container').css('display', 'block');

    var container = document.getElementById('container'), // 지도와 로드뷰를 감싸고 있는 div 입니다
        mapWrapper = document.getElementById('mapWrapper'), // 지도를 감싸고 있는 div 입니다
        btnRoadview = document.getElementById('btnRoadview'), // 지도 위의 로드뷰 버튼, 클릭하면 지도는 감춰지고 로드뷰가 보입니다
        btnMap = document.getElementById('btnMap'), // 로드뷰 위의 지도 버튼, 클릭하면 로드뷰는 감춰지고 지도가 보입니다
        rvContainer = document.getElementById('roadview'), // 로드뷰를 표시할 div 입니다
        mapContainer = document.getElementById('map'); // 지도를 표시할 div 입니다

// 지도와 로드뷰 위에 마커로 표시할 특정 장소의 좌표입니다
    var placePosition = new kakao.maps.LatLng(mapY, mapX);

// 지도 옵션입니다
    var mapOption = {
        center: placePosition, // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와 지도 옵션으로 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);

// 로드뷰 객체를 생성합니다
    var roadview = new kakao.maps.Roadview(rvContainer);

    var roadviewClient = new kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

// 로드뷰의 위치를 특정 장소를 포함하는 파노라마 ID로 설정합니다
// 로드뷰의 파노라마 ID는 Wizard를 사용하면 쉽게 얻을수 있습니다
    roadviewClient.getNearestPanoId(placePosition, 500, function (panoId) {
        roadview.setPanoId(panoId, placePosition); //panoId와 중심좌표를 통해 로드뷰 실행
    });

// 특정 장소가 잘보이도록 로드뷰의 적절한 시점(ViewPoint)을 설정합니다
// Wizard를 사용하면 적절한 로드뷰 시점(ViewPoint)값을 쉽게 확인할 수 있습니다
    roadview.setViewpoint({
        pan: 321,
        tilt: 0,
        zoom: 0
    });

// 지도 중심을 표시할 마커를 생성하고 특정 장소 위에 표시합니다
    var mapMarker = new kakao.maps.Marker({
        position: placePosition,
        map: map
    });



// 로드뷰 초기화가 완료되면
    kakao.maps.event.addListener(roadview, 'init', function () {

        // 로드뷰에 특정 장소를 표시할 마커를 생성하고 로드뷰 위에 표시합니다
        var rvMarker = new kakao.maps.Marker({
            position: placePosition,
            map: roadview
        });


    });

    var content=
                `<button class="find_to" onclick="window.open('about:blank').location.href='https://map.kakao.com/link/to/${title},${mapY},${mapX}'">
                    <img src="/img/store/tour/pngwing.com (1).png">
                    길찾기</button>`;

    $('#container').append(content);




});



// 지도와 로드뷰를 감싸고 있는 div의 class를 변경하여 지도를 숨기거나 보이게 하는 함수입니다
function toggleMap(active) {
    if (active) {

        // 지도가 보이도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        container.className = "view_map"
    } else {

        // 지도가 숨겨지도록 지도와 로드뷰를 감싸고 있는 div의 class를 변경합니다
        container.className = "view_roadview"
    }
}

$('.modal_close_btn').on('click', function () {
    $('.map_container').css('display', 'none');
});