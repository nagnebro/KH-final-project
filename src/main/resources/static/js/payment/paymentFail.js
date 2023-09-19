$(function () {

    Swal.fire({
        title: '결제실패, 확인 후 다시 시도하세요.',
        icon: 'warning',
        confirmButtonColor: '#00b8ff',
        confirmButtonText: '확인'
    }).then(function () {

        window.self.close()
    });
});