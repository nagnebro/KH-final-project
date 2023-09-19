function delAccompany(element) {

    var acNum = element.id;

    console.log("삭제버튼 클릭");
    // Swal.fire('비밀번호가 변경됐습니다', '', 'success').then((result) => {
    //
    //     window.location.href = '/';
    //
    // })

    Swal.fire({
        title: '<strong>HTML <u>example</u></strong>',
        icon: 'warning',
        title: '정말 동행 글을 삭제하시겠습니까?',
        showCloseButton: false,
        showCancelButton: true,
        focusConfirm: false,
        confirmButtonText:
            '확인',
        confirmButtonAriaLabel: 'great!',
        cancelButtonText:
            '취소',
        cancelButtonAriaLabel: 'Thumbs down'
    }).then((result) => {

        if (result.isConfirmed) {

            var query = {
                acNum:acNum
            }

            $.ajax({

                url: '/manager/deleteAccompany',
                method: 'POST',
                data: query,
                success: function (data) {

                    Swal.fire('삭제가 완료됐습니다.', '', 'success').then((result) => {

                        location.reload();

                    })
                }, error: function () {

                }
            });

        }

    })
}




var pageSelectBtn = document.querySelectorAll('.page-select-btn');

// #selected-btn은 페이지 렌더링시 히든값으로 가지고 있는 pageNo의 value이다.

for (var i = 0; i < pageSelectBtn.length; i++) {

    if (pageSelectBtn[i].id == $('#selected-btn').val()) {

        console.log(pageSelectBtn[i])
        console.log('h')
        pageSelectBtn[i].style = "background-color:#00b8ff; color:white";

    }
}




// 유저 아이디, 혹은 유저 이름으로 유저 찾기 버튼을 클릭, 혹은 enter를 눌렸을 때


$('#search-input').on('keyup', function (e) {

    if (e.key === "Enter") {

        var search_word = $('#search-input').val();
        var search_option = $('#user-search-option').val();
        console.log(search_word);
        if(search_option === "all" ){
            location.href = `/manager/accompany`;

        }
        else if (search_word === "") {
            Swal.fire('검색어를 입력해주세요.', '', 'warning')
        } else {
            location.href = `/manager/accompany?pageNo=1&search_word=${search_word}&search_option=${search_option}`;
        }
    }
})


var search_option = $('#search_option').val(); // hidden값
console.log(search_option);


// hidden값으로 이전에 지정했던 설정을 저장하고 있는다.
var selected = document.querySelector(`select [value=${search_option}]`);
selected.selected = true;


// hiiden값으로 아까 검색했던 검색어를 검색창에 집어넣ㄴ는다.
$('#search-input').val($('#search_word').val());
var search_word = $('#search-input').val();


function pageChange(element){ // 페이지 버튼 클릭할시

    var pageNo = element.id;

    if(search_word === "") { // 검색어 없을 경우 그냥 페이지만 변경한다.
        location.href = `/manager/accompany?pageNo=${pageNo}`;
    }else if(search_word !== ""){ // 검색어가 있을 경우 페이지NO과 함꼐 검색어와 option도 계속 전달한다.
        location.href = `/manager/accompany?pageNo=${pageNo}&search_word=${search_word}&search_option=${search_option}`;

    }

}

