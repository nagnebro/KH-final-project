
//배경사진 선택 자바스크립트
const photoDiv = document.querySelector('.photo');
const fileInput = document.getElementById('fileInput');

photoDiv.addEventListener('click', () => {
    fileInput.click();
});

fileInput.addEventListener('change', (event) => {
    const selectedImage = event.target.files[0];

    if (selectedImage) {
        const imageUrl = URL.createObjectURL(selectedImage);
        photoDiv.style.backgroundImage = `url(${imageUrl})`;
    }
});



//작성완료 버튼 클릭시 accompany_detail(동행게시글 상세보기)으로 이동
const writeButton = document.querySelector('.writebtn');

writeButton.addEventListener('click', () => {
    window.location.href = '/community/accompany/writePro';
});

//취소 버튼 클릭시 accompany(동행 메인)으로 이동
const cancelbtn = document.querySelector('.cancelbtn');

cancelbtn.addEventListener('click', () => {
    window.location.href = '/community/accompany';
});


// // 지역 선택 시 색깔 변함
// const Cnavbtnsp1 = document.querySelectorAll(".C-nav-btn1 > div");
// const Cnavbtnsp2 = document.querySelectorAll(".C-nav-btn2 > div");
//
// function handleClick(e) {
//     const targetElement = e.currentTarget;
//
//     if (targetElement.classList.contains("regionCliked")) {
//         targetElement.classList.remove("regionCliked");
//     } else {
//         for (var i = 0; i < Cnavbtnsp1.length; i++) {
//             Cnavbtnsp1[i].classList.remove("regionCliked");
//         }
//         for (var i = 0; i < Cnavbtnsp2.length; i++) {
//             Cnavbtnsp2[i].classList.remove("regionCliked");
//         }
//         targetElement.classList.add("regionCliked");
//     }
// }
//
// function regionInit() {
//     Cnavbtnsp1.forEach(btn => {
//         btn.addEventListener("click", handleClick);
//     });
//
//     Cnavbtnsp2.forEach(btn => {
//         btn.addEventListener("click", handleClick);
//     });
// }
//
// regionInit();


const Cnavbtnsp1 = document.querySelectorAll(".C-nav-btn1 .C-nav-btn");
const Cnavbtnsp2 = document.querySelectorAll(".C-nav-btn2 .C-nav-btn");
const Cnavbtnsp3 = document.querySelectorAll(".C-activ");
// const regionInput = document.querySelector("#region");

function handleClick(e) {
    const targetElement = e.currentTarget;
    const totalBtn = [...Cnavbtnsp1, ...Cnavbtnsp2]
    console.log(totalBtn.length);

    if (targetElement.classList.contains("regionCliked")) {
        targetElement.classList.remove("regionCliked");
    } else {
        for(let i=0; i< totalBtn.length; i++) {
            totalBtn[i].classList.remove("regionCliked");
        }
        targetElement.classList.add("regionCliked");
        region.value = targetElement?.children[0]?.textContent
    }

}

function regionInit() {
    Cnavbtnsp1.forEach(btn => {
        btn.addEventListener("click", handleClick);
    });

    Cnavbtnsp2.forEach(btn => {
        btn.addEventListener("click", handleClick);
    });
    Cnavbtnsp3.forEach(btn => {
        btn.addEventListener("click", handleClick);
    });
}

regionInit();


var myRange = document.querySelector('#customRange1');
var myValue = document.querySelector('#slider_value_view');
var thumbWidth = 18;

function updateSliderValue() {
    var off = (myRange.clientWidth - thumbWidth) / (parseInt(myRange.max) - parseInt(myRange.min));
    var px = ((myRange.valueAsNumber - parseInt(myRange.min)) * off) - (myValue.clientWidth / 2) + (thumbWidth / 2);

    myValue.style.left = px + 'px';
    myValue.style.top = -myRange.offsetHeight - 5 + 'px';
    myValue.innerHTML = myRange.value;
}

myRange.oninput = function() {
    updateSliderValue();
};

// 페이지 로드 시 슬라이더 값 업데이트
window.onload = function() {
    updateSliderValue();
};




