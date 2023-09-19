const option1 = document.querySelector(".option1");
const option2 = document.querySelector(".option2");
const option3 = document.querySelector(".option3");
const option4 = document.querySelector(".option4");
const option5 = document.querySelector(".option5");

const option1_btn = document.querySelector(".option1_btn");
const option2_btn = document.querySelector(".option2_btn");
const option3_btn = document.querySelector(".option3_btn");
const option4_btn = document.querySelector(".option4_btn");
const option5_btn = document.querySelector(".option5_btn");


//div 일정크기 이상시 content2 (우측 고정 탭바) 크기 증가시킨다.
// var main_container = document.getElementsByClassName('.main_container');
//
// if(main_container.clientHeight < 700){
//     main_container.style.height = '700';
// }else{
//     main_container.style.height = 'auto';
// }
option1_btn.addEventListener("click",(e)=>{
    option1_btn.style.backgroundColor="#F0F9FFFF";
    option2_btn.style.backgroundColor="white";
    option3_btn.style.backgroundColor="white";
    option4_btn.style.backgroundColor="white";
    option5_btn.style.backgroundColor="white";

    option1.classList.remove("disappear");
    option2.classList.add("disappear");
    option3.classList.add("disappear");
    option4.classList.add("disappear");
    option5.classList.add("disappear");

    window.scroll(0,0);
})

option2_btn.addEventListener("click",(e)=>{
    option1_btn.style.backgroundColor="white";
    option2_btn.style.backgroundColor="#F0F9FFFF";
    option3_btn.style.backgroundColor="white";
    option4_btn.style.backgroundColor="white";
    option5_btn.style.backgroundColor="white";

    option1.classList.add("disappear");
    option2.classList.remove("disappear");
    option3.classList.add("disappear");
    option4.classList.add("disappear");
    option5.classList.add("disappear");

    window.scroll(0,0);
})

option3_btn.addEventListener("click",(e)=>{
    option1_btn.style.backgroundColor="white";
    option2_btn.style.backgroundColor="white";
    option3_btn.style.backgroundColor="#F0F9FFFF";
    option4_btn.style.backgroundColor="white";
    option5_btn.style.backgroundColor="white";

    option1.classList.add("disappear");
    option2.classList.add("disappear");
    option3.classList.remove("disappear");
    option4.classList.add("disappear");
    option5.classList.add("disappear");

    window.scroll(0,0);
})

option4_btn.addEventListener("click",(e)=>{
    option1_btn.style.backgroundColor="white";
    option2_btn.style.backgroundColor="white";
    option3_btn.style.backgroundColor="white";
    option4_btn.style.backgroundColor="#F0F9FFFF";
    option5_btn.style.backgroundColor="white";

    option1.classList.add("disappear");
    option2.classList.add("disappear");
    option3.classList.add("disappear");
    option4.classList.remove("disappear");
    option5.classList.add("disappear");

    window.scroll(0,0);
})

option5_btn.addEventListener("click",(e)=>{
    option1_btn.style.backgroundColor="white";
    option2_btn.style.backgroundColor="white";
    option3_btn.style.backgroundColor="white";
    option4_btn.style.backgroundColor="white";
    option5_btn.style.backgroundColor="#F0F9FFFF";

    option1.classList.add("disappear");
    option2.classList.add("disappear");
    option3.classList.add("disappear");
    option4.classList.add("disappear");
    option5.classList.remove("disappear");

    window.scroll(0,0);
})

$('.write_btn').click(()=>{
    $('.ask_content').addClass("disappear");
    $('.my_question_list').addClass("disappear");
    $('.question').removeClass("disappear");
    $('.write_btn').addClass("disappear");
})

$('#question_btn_cancel').click(()=>{
    $('.ask_content').removeClass("disappear");
    $('.my_question_list').removeClass("disappear");
    $('.question').addClass("disappear");
    $('.write_btn').removeClass("disappear");
})