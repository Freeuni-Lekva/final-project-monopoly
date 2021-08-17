const eventType = document.getElementById('event').getAttribute('value');

if (eventType == 'your-turn') {
    const nextTurnDisplay = document.querySelector('.next-turn');
    nextTurnDisplay.classList.add('display');
} else if (eventType == 'trade') {
    const tradeDisplay = document.querySelector('.confirm-trade');
    tradeDisplay.classList.add('display');
} else if (eventType == 'victory') {
    document.querySelector('.victory-screen').classList.add('display');
} else {
    setTimeout(function () {window.location.href = window.location.href}, 2000);
}

let currPlayer = 0;
let tile;
let circles = document.querySelectorAll('.circles img');

function setCircles() {
    let hor = 88;
    let ver = 89;
    if (tile < 11) {
        hor -= tile * 7.8;
        ver += currPlayer * 0.8;
    } else if (tile < 21) {
        hor = 8 - currPlayer * 0.8;
        ver -= (tile - 10) * 7.8 + 0.85;
    } else if (tile < 30) {
        hor = 9.8 + (tile - 20) * 7.8;
        ver = 7 - currPlayer * 0.8;
    } else {
        hor += currPlayer * 0.8 + 2;
        ver = 2.5 + (tile - 29) * 7.8;
    }
    circles[currPlayer].style.left = hor + '%';
    circles[currPlayer].style.top = ver + '%';
}

for (let i = 0; i < circles.length; i++) {
    tile = parseInt(circles[i].getAttribute('tile'));
    setCircles();
    currPlayer++;
}