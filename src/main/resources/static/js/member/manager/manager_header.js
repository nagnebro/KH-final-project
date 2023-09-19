// 탭 선택에 따른 background color 조정
var url = window.location.pathname;
var tab = url.split('/')[2];
console.log(tab)
if(tab === "home"){
    $('.snc_home').css('background-color','rgb(220, 233, 245)');
    $('.snc_home').css('color','black');
}else if(tab === "user"){
    $('.snc_user').css('background-color','rgb(220, 233, 245)');
    $('.snc_user').css('color','black');
}else if(tab === "accompany"){
    $('.snc_accompany').css('background-color','rgb(220, 233, 245)');
    $('.snc_accompany').css('color','black');
}else if(tab === "plan"){
    $('.snc_plan').css('background-color','rgb(220, 233, 245)');
    $('.snc_plan').css('color','black');
}else if(tab === "qna"){
    $('.snc_qna').css('background-color','rgb(220, 233, 245)');
    $('.snc_qna').css('color','black');
}else if(tab === "flight"){
    $('.snc_flight').css('background-color','rgb(220, 233, 245)');
    $('.snc_flight').css('color','black');
}else if(tab === "rentcar"){
    $('.snc_rentcar').css('background-color','rgb(220, 233, 245)');
    $('.snc_rentcar').css('color','black');
}
console.log(url);
