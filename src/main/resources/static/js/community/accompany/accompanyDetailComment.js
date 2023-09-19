// 댓글 작성, 수정, 삭제에 관련된 로직 처리를 하는 곳


//url을 통해 얻은 id값을 가지고온다.
const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('ac_num');
console.log(postId)

$('.please_leave_msg_btn').click(function(){

    var commentValue = $('.please_leave_msg').val();


    if( commentValue === ''){ //댓글 내용이 작성되지 않았다면.

        Swal.fire('댓글 내용을 입력해주세요.', '', 'warning')

    }else{

        var query = {
            user_number : user_number,
            commentValue : commentValue

        };

        $.ajax({

            url:'/community/accompany/detailCoWrite',
            method : 'POST',
            data : query,
            success : function(data){

                console.loo(data);
                if(data.length == 0){

                }
                // 반환받은 list<Comment를 html 태그로 뿌려준다 >

            },error : function(){}
        })
    }

})