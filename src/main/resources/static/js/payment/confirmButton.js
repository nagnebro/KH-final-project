$(function () {

    function formatNumberWithCommas(number) {                   // 총 가격에 콤마넣기

        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

    }

    var infoAmount = document.querySelector("#info-amount-total").textContent;

    const formattedAmount = formatNumberWithCommas(infoAmount);

    document.querySelector("#info-amount-total").textContent = formattedAmount + "원";
    document.querySelector("#info-amount-total2").textContent = formattedAmount + "원";


    $('#confirm').click(function () {                // 결제 완료시 이동할 페이지

        window.opener.location.href = '/member/mypage';
        window.self.close();

    })
})