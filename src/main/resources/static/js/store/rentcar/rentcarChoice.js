
function moveToPay(e) {

    if ($('#sessionUser').val() === '') {
        Swal.fire({
            title: '로그인 후 이용 가능합니다.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#00b8ff',
            confirmButtonText: '로그인하러 가기',
            cancelButtonColor: '#d33',
            cancelButtonText: '더 둘러보기'
        }).then(function (result) {
            if(result.isConfirmed){
                location.href = '/member/login';
            }
        });
    } else {


        var carInfoId = e.dataset.carInfoId; // 이 부분을 추가하여 car_info_id 값을 가져오기

        console.log(carInfoId);


        location.href = '/store/rentcar/pay/rentcarPaymentPage?Car_info_id=' + carInfoId; // car_info_id를 사용하여 URL을 생성하고 이동


    }

}

