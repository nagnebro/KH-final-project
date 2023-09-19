const planModal = document.getElementById("myBtn");
const modalContent = document.getElementById("planModal");

function modalOn() {
    planModal.style.display = "flex";
}

function modalOff() {
    planModal.style.display = "none";
}

const planbtnModal = document.getElementById("myBtn");
planbtnModal.addEventListener("click", e => {
    modalOn();
});

const plancloseBtn = document.getElementsByClassName("planCloseArea");
plancloseBtn.addEventListener("click", e => {
    modalOff();
});

window.addEventListener("keyup", e => {
    if (e.key === "Escape") {
        modalOff();
    }
});