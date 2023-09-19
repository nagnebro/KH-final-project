$(function () {
    Swal.fire({
        title: '결제가 취소되었습니다.',
        icon: 'info',
        confirmButtonColor: '#00b8ff',
        confirmButtonText: '확인'
    }).then(function () {

    window.self.close()
    });
});