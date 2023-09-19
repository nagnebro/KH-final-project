$('#deleteBtn').click(function () {
    console.log('delAccompany ajax 메서드 실행');

    var ac_num = $('#inputAcNum').val(); // input 요소의 값을 가져옵니다.

    var query = {
        ac_num: ac_num
    };
    console.log('ac_num실행 중');
    console.log('delAccompany ajax실행 중');

    $.ajax({
        url: '/community/accompany/delete',
        method: 'POST',
        data: query,
        success: function (data) {
            console.log("result : " + data);

            Swal.fire({
                title: '게시물을 삭제하시겠습니까?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: '삭제',
                cancelButtonText: '아니오'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire('삭제',
                        '삭제되었습니다.',
                        'success').then(function () {

                        window.location.href = '/community/accompany/'
                    });
                }
            });
        },
        error: function () {
            Swal.fire('삭제 실패', '작업 수행에 실패하였습니다.', 'error');
        }
    })


});


const coWriteBtn = document.querySelector('.bt_com_div_btn');

coWriteBtn.addEventListener('click', () => {
    window.location.href = '/community/accompany/reWritePro';
})

$('.add_reply_btnEdit').click(function (event) {

    console.log('댓글 수정 버튼 AJAX 실행')

    $('.add_reply_btnEdit').css('display', 'none')
    $('.commentDelBtn').css('display', 'block')
    $('.gotMsg').css('display', 'none')
    $('.add_reply_btnUpdate').css('display', 'inline-block')
});

$('.add_reply_btnUpdate').click(function (event) {

    console.log('댓글 수정 버튼 AJAX 실행')
    console.log(event)
    const id = event.target.id

    var query = JSON.stringify({
        co_number: id,
        co_content: $('#textarea' + id).val()
    });
    $.ajax({
        url: '/community/accompany/commentEdit',
        type: 'POST',
        data: query,
        contentType: 'application/json',
        success: function (res) {
            console.log("res :: ", res)

            $('.commentDelBtn').css('display', 'none')
            $('.gUqDoq').css('display', 'block')
            $('.add_reply_btnUpdate').css('display', 'none')
            window.location.reload();
        }
    })
});

$('.add_reply_btnDelete').click(function (event) {

    console.log('댓글 삭제 버튼 ajax 실행');
    const co_number = event.target.id
    console.log(co_number)

    var query = JSON.stringify({
        co_number: co_number
    });

    $.ajax({
        url: '/community/accompany/commentDelete',
        type: 'POST',
        data: query,
        contentType: 'application/json',
        success: function (res) {
            console.log("res ::", res)

            window.location.reload();
        }
    })
});
