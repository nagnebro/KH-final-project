$(document).ready(function () {

    var user_name = $('[name=user_name]'); // name input

    var user_update_list = $('.user_info_input'); // info input

    var update_user_nick = document.querySelector(".update_user_nick");// name input div

    let today = new Date();

    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜
    let hours = today.getHours(); // 시
    let minutes = today.getMinutes();  // 분
    let seconds = today.getSeconds();  // 초

    $('#name_update_btn1').click(() => { // 이름 수정 아이콘 클릭시

        // classList속성으로 추가제거해도 됨.
        user_name.prop('disabled', false);
        update_user_nick.removeAttribute("disabled");

    })

    $('#name_update_btn2').click(() => { // 회원 정보 수정 아이콘 클릭시.

        for (var i = 0; i < user_update_list.length; i++) {

            user_update_list[i].removeAttribute("disabled");
            console.log(user_update_list[i])


            // user_update_list[i].prop('disabled',false);
        }

    })


    // 질문 등록
    $('#question_btn_submit').click(() => {
        console.log("submit click");
        if($('[name=question_title]').val() != "" && $('[name=question_content]').val() != "") {
            const line = "<div class=\"mql_list_new\">\n" +
                "                        <div class=\"ml_1\"><button color=\"#F1F9F6\" class=\"qna_status\">답변대기</button></div>\n" +
                "                        <div class=\"ml_2\">"+$('[name=question_title]').val()+"</div>\n" +
                "                        <div class=\"ml_3\">"+$('[name=question_content]').val()+"</div>\n" +
                "                        <div class=\"ml_4\">"+ year + '-' + month + '-' + date +' '+ hours + ':' + minutes + ':' + seconds+"</div>\n" +
                "                    </div>"
            $('.my_question_list').append(line);
            $('.ask_content').removeClass("disappear");
            $('.my_question_list').removeClass("disappear");
            $('.question').addClass("disappear");
            $('.write_btn').removeClass("disappear");

            var query = {
                qna_title: $('[name=question_title]').val(),
                qna_content: $('[name=question_content]').val(),
            }

            var check = false;

            for (var key in query) {
                if (query.hasOwnProperty(key)) {

                    check = true;
                }
            }

            if (check) {
                $.ajax({
                    url: '/member/questionSubmit',
                    method: 'POST',
                    data: query,
                    success: function (data) {
                        console.log("questionSubmit ajax통신 성공")
                    },
                    error: function () {
                        console.log("questionSubmit ajax통신 실패")
                    }
                });
            }
        }else{

            Swal.fire('내용을 입력해주세요.', '', 'warning')
        }
        $('[name=question_title]').val("");
        $('[name=question_content]').val("");
    })


    $('.submit_btn').click(function () { // form 제출 시

        console.log("mypage update submit_btn 클릭")
        update_user_nick.setAttribute("disabled", true);
        user_update_list.prop('disabled', true);
        user_name.prop('disabled', true);

        var query = {

            user_name: $('[name=user_name]').val(),
            user_gender: $('[name=user_gender]').val(),
            user_password: $('[name=user_password]').val(),
            user_year: $('[name=user_year]').val(),
            user_month: $('[name=user_month]').val(),
            user_day: $('[name=user_day]').val(),
        }

        var check = false;

        for (var key in query) {
            if (query.hasOwnProperty(key)) {

                check = true;
            }
        }

        if (check) {
            $.ajax({
                url: '/member/mypageUpdate',
                method: 'POST',
                data: query,
                success: function (data) {

                    console.log("mypage update ajax통신 성공")
                    // window.location.href = "/member/mypage";

                },
                error: function () {
                    console.log("mypage update ajax통신 실패")
                }
            });
        }
    })
})
$(document).on('click', '.mql_list', function(e){
    e.preventDefault();
    // console.log($(this).find('button').css('background-color'));
    var bgc = $(this).find('button').css('background-color');
   if(bgc ==  'rgb(56, 224, 66)') {
       if ($(this).next(".mql_answer").is(':visible')) {
           $(this).next(".mql_answer").slideUp();
       } else {
           $(this).next(".mql_answer").slideDown();
           $('.mql_list').not($(this)).next(".mql_answer").slideUp();
       }
  }
})
