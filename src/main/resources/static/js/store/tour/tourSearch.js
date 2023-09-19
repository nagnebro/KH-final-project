var areaCode = '';
var areaName = '';
var sigunguCode = '';
var sigunguName = '';
var contentTypeId = '';
var contentTypeName = '';
var cat1 = '';
var cat1Name = '';
var cat2 = '';
var cat2Name = '';
var cat3 = '';
var cat3Name = '';
var keyWord = '';
var totalCount = 0;
var pageNo = 1;
var totalPage = 0;
var startPage = 0;
var endPage = 0;
var barSize = 10;

var searchType = '지역별';


//지역 선택 모달
$('.search_area_btn').on('click', function () {
    $('.area_container').addClass('modal_on');

    $('#area_list1').empty();
    $('#area_list2').empty();
    $('.sigungu_list').empty();

    $.ajax({
        url: '/store/tour/getArea',
        type: 'get',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var item = data.response.body.items.item;

            /* 시/도 리스트 생성 */
            for (var i = 0; i < item.length; i++) {
                var content =
                    `<button class="modal_btn area_btn" value="${item[i].code}" onclick="insertArea(this)">` +
                    item[i].name + '</button>'

                if (i === Math.floor(item.length / 2)) {

                    var line = '<div class="btn_list area_list" id="area_list2"></div>'
                    $('#area_box').append(line);
                }

                if (i <= Math.floor(item.length / 2)) {
                    $('#area_list1').append(content);
                } else {
                    $('#area_list2').append(content);
                }
            }
        }
    });
});


function insertArea(e) {
    areaCode = e.value;
    areaName = e.textContent;

    $('.area_btn').removeClass('sel');
    e.classList.add('sel');

    sigunguCode = '';
    sigunguName = '';


    console.log("insertArea: " + sigunguName);
    $.ajax({
        url: '/store/tour/getSigungu',
        type: 'get',
        data: {
            areaCode: areaCode
        },
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var item = data.response.body.items.item;
            console.log("item = " + item);

            $('.all_sigungu').empty();
            $('.sigungu_list').empty();
            var content = `<button class="modal_btn sigungu_btn sel" value="" onClick="">
                                전체
                                </button>`;

            $('.all_sigungu').append(content);

            for (var i = 0; i < item.length; i++) {
                content = `<button class="modal_btn sigungu_btn" onclick = "insertAreaCode(this)" value="${item[i].code}">` +
                    item[i].name + '</button>';
                $('.sigungu_list').append(content);
            }
        }

    });
}

function insertAreaCode(e) {
    sigunguCode = e.value;
    sigunguName = e.textContent;

    $('.sigungu_btn').removeClass('sel');
    e.classList.add('sel');
}

//지역 확인 버튼 누르면
$('#area_confirm_btn').on('click', function () {
    if (sigunguCode === '') {
        sigunguName = '전체';
    }

    var areaInput = '광역시/도 : ' + areaName + ' , 시/군/구 : ' + sigunguName;

    if (searchType === '지역별') {
        $('#area_area_code').text(areaInput);
        closeModal();
    } else if (searchType === '통합') {
        $('#keyword_area_code').text(areaInput);
        closeModal();
    }

    console.log('areaCode:' + areaCode + 'areaName:' + areaName);
    console.log('sigunguCode:' + sigunguCode + 'sigunguName:' + sigunguName);
});

//관광 타입 모달
$('.search_content_btn').on('click', function () {
    $('.content_type_container').addClass('modal_on');

    cat1 = '';
    cat1Name = '';
    cat2 = '';
    cat2Name = '';
    cat3 = '';
    cat3Name = '';

    $('#area_category_code').text('');

});
$('.modal_btn[name=contentTypeId]').on('click', function () {
    contentTypeId = $(this).val();
    contentTypeName = $(this).text();

    $('.modal_btn[name=contentTypeId]').removeClass('sel');
    $(this).addClass('sel');

    console.log('contentTypeId:' + contentTypeId);
    console.log('contentTypeName:' + contentTypeName);
});
$('#content_type_confirm_btn').on('click', function () {
    insertContentType(contentTypeName);
    closeModal();
});

$('#content_type_cancel_btn').on('click', function () {
    contentTypeId = '';
    contentTypeName = '';
    insertContentType(contentTypeName);
    $('.modal_btn[name=contentTypeId]').removeClass('sel');
});

function insertContentType(contentTypeName) {
    $('#area_content_id').text(contentTypeName);
}

//서비스 분류 모달
$('.search_category_btn').on('click', function () {
    $('.service_container').addClass('modal_on');

    $('.cat_list').empty();
    var data = {
        contentTypeId: contentTypeId,
    }
    //대분류
    $.ajax({
        url: '/store/tour/getCat1',
        type: 'get',
        data: data,
        dataType: 'json',
        success: function (data) {
            var item = data.response.body.items.item;

            for (var i = 0; i < item.length; i++) {
                var content =
                    `<button class="modal_btn" name="cat1" value="${item[i].code}" onclick="insertCat1(this)">
                ${item[i].name}
                </button>`;
                $('.cat1').append(content);
            }
        }
    });
});


function insertCat1(e) {
    cat1 = e.value;
    cat1Name = e.textContent;

    $('.modal_btn[name=cat1]').removeClass('sel');
    e.classList.add('sel');

    var data = {
        contentTypeId: contentTypeId,
        cat1: cat1
    };

    $('.cat2').empty();
    $('.cat3').empty();

    cat2 = '';
    cat2Name = '';
    cat3 = '';
    cat3Name = '';

    $.ajax({
        url: '/store/tour/getCat2',
        type: 'get',
        data: data,
        dataType: 'json',
        success: function (data) {
            var item = data.response.body.items.item;

            for (var i = 0; i < item.length; i++) {
                var content =
                    `<button class="modal_btn" name="cat2" value="${item[i].code}" onclick="insertCat2(this)">
                ${item[i].name}
             </button>`;
                $('.cat2').append(content);
            }
        }
    });
}

function insertCat2(e) {
    cat2 = e.value;
    cat2Name = e.textContent;

    $('.modal_btn[name=cat2]').removeClass('sel');
    e.classList.add('sel');

    var data = {
        contentTypeId: contentTypeId,
        cat1: cat1,
        cat2: cat2
    };

    cat3 = '';
    cat3Name = '';

    $('.cat3').empty();

    $.ajax({
        url: '/store/tour/getCat3',
        type: 'get',
        data: data,
        dataType: 'json',
        success: function (data) {
            var item = data.response.body.items.item;
            console.log(item);

            for (var i = 0; i < item.length; i++) {

                var content =
                    `<button class="modal_btn" name="cat3" value="${item[i].code}" onclick="insertCat3(this)">
                ${item[i].name}
                </button>`;

                $('.cat3').append(content);
            }
        }
    });
}

function insertCat3(e) {
    cat3 = e.value;
    cat3Name = e.textContent;

    $('.modal_btn[name=cat3]').removeClass('sel');
    e.classList.add('sel');

}

$('#service_confirm_btn').on('click', function () {

    var categoryInput = '';

    if (cat1Name !== '') {
        categoryInput += '대분류 : ' + cat1Name;
    }

    if (cat2Name !== '') {
        categoryInput += ', 중분류 : ' + cat2Name;
    }

    if (cat3Name !== '') {
        categoryInput += ', 소분류 : ' + cat3Name;
    }

    if (searchType === '지역별') {
        $('#area_category_code').text(categoryInput);
        closeModal();
    } else if (searchType === '통합') {
        $('#keyword_category_code').text(categoryInput);
        closeModal();
    }


    closeModal();
});
//모달 창 닫기
$('.modal_cancel_btn').on('click', function () {
    console.log($(this).attr('id'));

    /*switch ($(this).attr('id')){
       case 'area_cancel_btn':
          areaCode = '';
          areaName = '';
          sigunguCode = '';
          sigunguName = '';
          break;
       case 'service_cancel_btn':

    }*/
    closeModal();
});
$('.modal_close_btn').on('click', function () {

    closeModal();
});

function closeModal() {
    $('.modal_container').removeClass('modal_on');
}

//검색 타입 선택
$('.type_btn').on('click', function () {
    //text 초기화


    if ($(this).text() === '지역별 검색') {
        $('.type_area').css('display', 'block');
        $('.type_keyword').css('display', 'none');
        $('.type_keyword .search_option_text').text('');
        areaCode = '';
        areaName = '';
        sigunguCode = '';
        sigunguName = '';
        contentTypeId = '';
        contentTypeName = '';
        cat1 = '';
        cat1Name = '';
        cat2 = '';
        cat2Name = '';
        cat3 = '';
        cat3Name = '';
        searchType = '지역별';
    } else {
        $('.type_keyword').css('display', 'block');
        $('.type_area').css('display', 'none');
        $('.type_area .search_option_text').text('');
        areaCode = '';
        areaName = '';
        sigunguCode = '';
        sigunguName = '';
        contentTypeId = '';
        contentTypeName = '';
        cat1 = '';
        cat1Name = '';
        cat2 = '';
        cat2Name = '';
        cat3 = '';
        cat3Name = '';
        searchType = '통합';
    }

});

$('#search_area_btn').on('click', function () {

    console.log('지역기반 조회버튼 클릭');

    getAreaBaseList(pageNo);

});

function getAreaBaseList(pageNum) {

    $('.loading_wrap').css('display', 'block');

    $.ajax({
        url: '/store/tour/getAreaBaseList',
        data: {
            contentTypeId: contentTypeId,
            areaCode: areaCode,
            sigunguCode: sigunguCode,
            cat1: cat1,
            cat2: cat2,
            cat3: cat3,
            pageNo: pageNum
        },
        type: 'get',
        dataType: 'json',
        success: function (data) {

            console.log(data);
            var item = data.response.body.items.item;
            totalCount = parseInt(data.response.body.totalCount);
            var numOfRows = 12;
            totalPage = Math.ceil(totalCount / numOfRows);
            var currentPage = parseInt(data.response.body.pageNo);

            startPage = Math.floor(((currentPage - 1) / barSize)) * barSize + 1;
            endPage = startPage + barSize - 1;
            if (endPage >= totalPage) {
                endPage = totalPage;
            }

            var firstPage = "";
            var previousPage = "";
            var mainPage = "";
            var nextPage = "";
            var lastPage = "";

            console.log("totalPage = " + totalPage);
            console.log("startPage = " + startPage);
            console.log("endPage = " + endPage);

            $('.search_result').empty();
            $('.content_card_box').empty();
            $('.paging_bar').empty();

            var content = `<h6>검색결과 : <b>${totalCount}</b>건</h6>`;
            $('.search_result').prepend(content);


            if (totalCount === 0) {
                $('.loading_wrap').css('display', 'none');
                Swal.fire({
                    title: '검색결과가 존재하지 않습니다.',
                    icon: 'error',
                    confirmButtonColor: '#00b8ff',
                    confirmButtonText: '확인',
                }).then(function () {
                    return;
                });

            }

            for (var i = 0; i < item.length; i++) {
                var image = item[i].firstimage;

                if (item[i].firstimage === '') {
                    image = '/img/store/tour/noimage.png';
                }
                content =
                    `<div class="content_card">
                                    <a href="/store/tour/tourDetail?contentId=${item[i].contentid}">
                                        <span class="thumb_img">
                                            <img src="${image}">
                                        </span>
                                        <strong class="thumb_name">${item[i].title}</strong>
                                    </a>
                                </div>`;

                $('.content_card_box').append(content);
            }

            if (currentPage === 1) {
                firstPage = `<li class="page-item disabled">
                                <a class="page-link" href="javascript:void(0)" onclick="getFirstPageList()">처음</a>
                             </li>`;

                previousPage = `<li class="page-item disabled">
                                    <a class="page-link" href="javascript:void(0)" onclick="getPreviousPageList(endPage)">이전</a>
                                </li>`;
            } else {
                firstPage = `<li class="page-item">
                                <a class="page-link" href="javascript:void(0)" onclick="getFirstPageList()">처음</a>
                             </li>`;

                previousPage = `<li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" onclick="getPreviousPageList(endPage)">이전</a>
                                </li>`;
            }

            for (var i = startPage; i <= endPage; i++) {
                if (i === currentPage) {
                    mainPage += `<li class="page-item active" aria-current="page">
                                    <a class="page-link" href="javascript:void(0)" onclick="getMainPageList(this)">${i}</a>
                                </li>`;
                } else {
                    mainPage += `<li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" onclick="getMainPageList(this)">${i}</a>
                                </li>`;
                }
            }

            if (currentPage >= totalPage) {
                lastPage = `<li class="page-item disabled">
                                <a class="page-link" href="javascript:void(0)" onclick="getLastPageList(totalPage)">마지막</a>
                             </li>`;

                nextPage = `<li class="page-item disabled">
                                    <a class="page-link" href="javascript:void(0)" onclick="getNextPageList(endPage)">다음</a>
                                </li>`;
            } else {
                lastPage = `<li class="page-item">
                                <a class="page-link" href="javascript:void(0)" onclick="getLastPageList(totalPage)">마지막</a>
                             </li>`;

                nextPage = `<li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" onclick="getNextPageList(endPage)">다음</a>
                                </li>`;
            }

            var pageBar =
                `<ul class="pagination">
                    ${firstPage}
                    ${previousPage}
                    ${mainPage}
                    ${nextPage}
                    ${lastPage}
                </ul>`;

            $('.paging_bar').append(pageBar);

            $('.loading_wrap').css('display', 'none');
        },
        error: function () {
            console.log('에러');
        }
    });

}

function getFirstPageList() {

    console.log("첫페이지 이동");

    pageNo = 1;

    if (searchType === '지역별') {

        getAreaBaseList(pageNo);
    } else if (searchType === '통합') {
        getSearchKeyword(pageNo);
    }

}

function getPreviousPageList(pageNum) {
    console.log("이전페이지 이동");

    if (pageNum <= barSize) {
        pageNo = 1;
    } else {
        pageNo = startPage - barSize;
    }

    if (searchType === '지역별') {

        getAreaBaseList(pageNo);
    } else if (searchType === '통합') {
        getSearchKeyword(pageNo);
    }

}

function getMainPageList(e) {
    pageNo = parseInt(e.textContent);

    if (searchType === '지역별') {

        getAreaBaseList(pageNo);
    } else if (searchType === '통합') {
        getSearchKeyword(pageNo);
    }

}

function getNextPageList(pageNum) {
    pageNo = pageNum + 1;

    if (searchType === '지역별') {

        getAreaBaseList(pageNo);
    } else if (searchType === '통합') {
        getSearchKeyword(pageNo);
    }

}

function getLastPageList(totalPage) {

    pageNo = totalPage;

    if (searchType === '지역별') {

        getAreaBaseList(pageNo);
    } else if (searchType === '통합') {
        getSearchKeyword(pageNo);
    }

}

$('#search_keyword_btn').on('click', function () {

    console.log("통합검색 버튼 클릭");
    keyWord = $('input[name=keyword]').val();
    console.log("keyWord = " + keyWord);

    if (keyWord === '') {
        Swal.fire({
            title: '검색어를 입력하세요.',
            icon: 'error',
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '확인'
        });

        return;
    }

    pageNo = 1;
    getSearchKeyword(pageNo);

});

function getSearchKeyword(pageNum) {

    $('.loading_wrap').css('display', 'block');

    $.ajax({
        url: '/store/tour/getSearchKeyword',
        data: {
            areaCode: areaCode,
            sigunguCode: sigunguCode,
            cat1: cat1,
            cat2: cat2,
            cat3: cat3,
            pageNo: pageNum,
            keyWord: keyWord
        },
        type: 'get',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var item = data.response.body.items.item;
            totalCount = parseInt(data.response.body.totalCount);
            var numOfRows = 12;
            totalPage = Math.ceil(totalCount / numOfRows);
            var currentPage = parseInt(data.response.body.pageNo);

            startPage = Math.floor(((currentPage - 1) / barSize)) * barSize + 1;
            endPage = startPage + barSize - 1;
            if (endPage >= totalPage) {
                endPage = totalPage;
            }

            var firstPage = "";
            var previousPage = "";
            var mainPage = "";
            var nextPage = "";
            var lastPage = "";

            console.log("totalPage = " + totalPage);
            console.log("startPage = " + startPage);
            console.log("endPage = " + endPage);

            $('.search_result').empty();
            $('.content_card_box').empty();
            $('.paging_bar').empty();

            var content = `<h6>검색결과 : <b>${totalCount}</b>건</h6>`;
            $('.search_result').prepend(content);

            if (totalCount === 0) {
                $('.loading_wrap').css('display', 'none');
                Swal.fire({
                    title: '검색결과가 존재하지 않습니다.',
                    icon: 'error',
                    confirmButtonColor: '#00b8ff',
                    confirmButtonText: '확인',
                }).then(function () {
                    return;
                });

            }


            for (var i = 0; i < item.length; i++) {
                var image = item[i].firstimage;

                if (item[i].firstimage === '') {
                    image = '/img/store/tour/noimage.png';
                }
                content =
                    `<div class="content_card">
                                    <a href="/store/tour/tourDetail?contentId=${item[i].contentid}">
                                        <span class="thumb_img">
                                            <img src="${image}">
                                        </span>
                                        <strong class="thumb_name">${item[i].title}</strong>
                                    </a>
                                </div>`;

                $('.content_card_box').append(content);
            }

            if (currentPage === 1) {
                firstPage = `<li class="page-item disabled">
                                <a class="page-link" href="javascript:void(0)" onclick="getFirstPageList()">처음</a>
                             </li>`;

                previousPage = `<li class="page-item disabled">
                                    <a class="page-link" href="javascript:void(0)" onclick="getPreviousPageList(endPage)">이전</a>
                                </li>`;
            } else {
                firstPage = `<li class="page-item">
                                <a class="page-link" href="javascript:void(0)" onclick="getFirstPageList()">처음</a>
                             </li>`;

                previousPage = `<li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" onclick="getPreviousPageList(endPage)">이전</a>
                                </li>`;
            }

            for (var i = startPage; i <= endPage; i++) {
                if (i === currentPage) {
                    mainPage += `<li class="page-item active" aria-current="page">
                                    <a class="page-link" href="javascript:void(0)" onclick="getMainPageList(this)">${i}</a>
                                </li>`;
                } else {
                    mainPage += `<li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" onclick="getMainPageList(this)">${i}</a>
                                </li>`;
                }
            }

            if (currentPage >= totalPage) {
                lastPage = `<li class="page-item disabled">
                                <a class="page-link" href="javascript:void(0)" onclick="getLastPageList(totalPage)">마지막</a>
                             </li>`;

                nextPage = `<li class="page-item disabled">
                                    <a class="page-link" href="javascript:void(0)" onclick="getNextPageList(endPage)">다음</a>
                                </li>`;
            } else {
                lastPage = `<li class="page-item">
                                <a class="page-link" href="javascript:void(0)" onclick="getLastPageList(totalPage)">마지막</a>
                             </li>`;

                nextPage = `<li class="page-item">
                                    <a class="page-link" href="javascript:void(0)" onclick="getNextPageList(endPage)">다음</a>
                                </li>`;
            }

            var pageBar =
                `<ul class="pagination">
                    ${firstPage}
                    ${previousPage}
                    ${mainPage}
                    ${nextPage}
                    ${lastPage}
                </ul>`;

            $('.paging_bar').append(pageBar);

            $('.loading_wrap').css('display', 'none');
        },
        error: function () {
            console.log('통합검색 에러 발생');
        }
    });
}

