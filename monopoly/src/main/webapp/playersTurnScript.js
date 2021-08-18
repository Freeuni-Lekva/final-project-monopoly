const rules = document.querySelector('.rules');
const dice = document.querySelectorAll('.dice img');
const hiddenFirstDie = document.getElementById('first-die');
const hiddenSecondDie = document.getElementById('second-die');
const buttons = document.querySelectorAll('.buttons button');
const openImage = document.querySelector('.card-display img');
const imageContainer = document.querySelector('.image-container');
const cardImages = document.querySelectorAll('.image-container img');
const dieSides = ['die-one.png', 'die-two.png', 'die-three.png', 'die-four.png', 'die-five.png', 'die-six.png'];
const bankruptcyConfirmation = document.querySelector('.confirm-bankruptcy');
const openImageBox = document.querySelector('.card-display');
const circles = document.querySelectorAll('.circles img');
const cardButtons = document.querySelectorAll('.card-buttons button');
const description = document.getElementById('description');
const hiddenMoney = document.getElementById('money');
const cardDisplayMoney = document.getElementById('card-display-money');
const eventType = document.getElementById('event').getAttribute('value');
const eventCost = parseInt(document.getElementById('event-cost').getAttribute('value'));
const turnsArrested = parseInt(document.getElementById('turns-arrested').getAttribute('value'));
const jailDice = document.querySelectorAll('.arrested img');
let hiddenGetOutOfJailCards;
let getOutOfJailCards;
let jailButtons;
let diceEqual = false;
let money = parseInt(hiddenMoney.getAttribute('value'));
let hiddenLevel;
let level;
let cardType;
let mortgageValue;
let mortgageLiftCost;
let houseCost;
let hotelCost;
let eventWindow = document.querySelector('.event-window');
let buttonsEnabled = [];
let canMove = document.getElementById('can-move').getAttribute('value');
let hiddenUtilityOwnedPayment;
let utilityOwnedPayButton;

if (turnsArrested > 0) {
    jailButtons = document.querySelectorAll('.arrested button');
    hiddenGetOutOfJailCards = document.getElementById('get-out-of-jail-cards');
    getOutOfJailCards = parseInt(hiddenGetOutOfJailCards.getAttribute('value'));
    if (turnsArrested >= 3) jailButtons[1].disabled = true;
    if (getOutOfJailCards < 1) jailButtons[2].disabled = true;
    if (money < 50) jailButtons[3].disabled = true;
    if (turnsArrested == 1) {
        for (let i = 1; i < jailButtons.length - 2; i++) jailButtons[i].disabled = true;
        jailButtons[7].disabled = false;
    }
    const arrested = document.querySelector('.arrested');
    arrested.classList.add('display');
}

if (eventType == 'none') {
    if (canMove == 'false') {
        buttons[1].disabled = true;
        buttons[5].disabled = false;
    }
} else {
    eventWindow.classList.add('display');
    let buttonsList = buttons;
    if (turnsArrested > 0) buttonsList = jailButtons;
    for (let i = 1; i < buttonsList.length; i++) buttonsList[i].disabled = true;
    if (eventType != 'awaiting-response' && eventType != 'trade-decided')
        buttonsList[buttonsList.length -1].disabled = false;
    if (eventType == 'unoccupied-property') {
        if (money < eventCost) document.getElementById('purchase').disabled = true;
    } else if (eventType == 'awaiting-response') {
        setTimeout(function () {
            window.location.href = window.location.href;
        }, 2000);
    } else if (eventType == 'paying-event' || eventType == 'negative-money') {
        if (turnsArrested > 0) {
            buttonsList[5].disabled = false;
            buttonsList[6].disabled = false;
        } else {
            buttonsList[2].disabled = false;
            buttonsList[3].disabled = false;
        }
        if (eventType == 'paying-event' && eventCost > money)
            document.getElementById('pay-button').disabled = true;
    } else if (eventType == 'nearest-utility-owned') {
        buttons[1].disabled = false;
        hiddenUtilityOwnedPayment = document.getElementById('utility-owned-payment');
        utilityOwnedPayButton = document.getElementById('utility-owned-pay-button');
    }

}

for (let i = 0; i < circles.length; i++) {
    let tile = circles[i].getAttribute('tile');
    let hor = 88;
    let ver = 178;
    if (tile < 11) {
        hor -= tile * 7.8;
        ver += i * 1.6;
    } else if (tile < 21) {
        hor = 8 - i * 0.8;
        ver -= (tile - 10) * 15.6 + 1.7;
    } else if (tile < 30) {
        hor = 9.8 + (tile - 20) * 7.8;
        ver = 12 - i * 1.6;
    } else {
        hor += i * 0.8 + 2;
        ver = 5 + (tile - 29) * 15.6;
    }
    circles[i].style.left = hor + '%';
    circles[i].style.top = ver + '%';
}

function rollDice(type) {
    let buttonList;
    let dieList;
    let dieOne;
    let dieTwo;
    if (type == 'regular') {
        buttonList = buttons;
        dieList = dice;
    } else {
        buttonList = jailButtons;
        dieList = jailDice;
    }
    for (let i = 0; i < buttonList.length; i++) {
        buttonsEnabled[i] = buttonList[i].disabled;
        buttonList[i].disabled = true;
    }
    dieList.forEach(die => { die.classList.add('spin'); });
    dieOne = Math.floor(Math.random() * dieSides.length) + 1;
    dieTwo = Math.floor(Math.random() * dieSides.length) + 1;
    if (dieOne == dieTwo) diceEqual = true;
    dieList[0].setAttribute('src', 'dice-images/' + dieSides[dieOne - 1]);
    dieList[1].setAttribute('src', 'dice-images/' + dieSides[dieTwo - 1]);
    if (type == 'regular') {
        hiddenFirstDie.setAttribute('value', dieOne);
        hiddenSecondDie.setAttribute('value', dieTwo);
    }
    setTimeout(function () {
        dieList[0].classList.remove('spin');
        dieList[1].classList.remove('spin');
        for (let i = 0; i < buttonList.length; i++)
            buttonList[i].disabled = buttonsEnabled[i];
        buttonList[1].disabled = true;
        if (eventType == 'nearest-utility-owned') {
            let utilityOwnedPayment = parseInt(hiddenUtilityOwnedPayment.getAttribute('value'));
            utilityOwnedPayment *= dieOne + dieTwo;
            if (money >= utilityOwnedPayment) utilityOwnedPayButton.disabled = false;
            hiddenUtilityOwnedPayment.setAttribute('value', utilityOwnedPayment);
            utilityOwnedPayButton.innerHTML = 'Pay ' + utilityOwnedPayment + '$';
        } else if (type == 'regular') {
            buttonList[2].disabled = true;
            buttonList[3].disabled = true;
            buttonList[4].disabled = false;
        } else if (type == 'jail') {
            buttonList[5].disabled = true;
            buttonList[6].disabled = true;
        }
    }, 310);
}

function updateCardButtonsAndDescription() {
    cardButtons[0].disabled = true;
    cardButtons[1].disabled = true;
    switch (level) {
        case -1:
            cardButtons[0].innerHTML = 'Lift Mortgage';
            cardButtons[1].innerHTML = 'Mortgage';
            if (money >= mortgageLiftCost) cardButtons[0].disabled = false;
            description.innerHTML = 'Mortgaged';
            break;
        case 0:
            cardButtons[1].innerHTML = "Mortgage";
            cardButtons[1].disabled = false;
            if (cardType == 'property') {
                cardButtons[0].innerHTML = 'Place a House';
                if (money >= houseCost) cardButtons[0].disabled = false;
                description.innerHTML = '0 Houses Placed';
            } else {
                cardButtons[0].innerHTML = 'Lift Mortgage';
                description.innerHTML = 'Active';
            }
            break;
        case 1:
        case 2:
        case 3:
            cardButtons[0].innerHTML = 'Place a House';
            if (money >= houseCost) cardButtons[0].disabled = false;
            cardButtons[1].innerHTML = 'Remove a House';
            cardButtons[1].disabled = false;
            description.innerHTML = level + ' Houses Placed';
            break;
        case 4:
            cardButtons[0].innerHTML = 'Place a Hotel';
            if (money >= hotelCost) cardButtons[0].disabled = false;
            cardButtons[1].innerHTML = 'Remove a House';
            cardButtons[1].disabled = false;
            description.innerHTML = '4 Houses Placed';
            break;
        case 5:
            cardButtons[0].innerHTML = 'Place a Hotel';
            cardButtons[1].innerHTML = 'Remove a Hotel';
            cardButtons[1].disabled = false;
            description.innerHTML = 'Hotel Placed';
            break;
    }
}

function show(e) {
    let im = e.target;
    openImage.setAttribute('src', im.getAttribute('src'));
    openImage.setAttribute('alt', im.getAttribute('src'));
    cardType = im.getAttribute('card-type');
    hiddenLevel = document.getElementById(im.getAttribute('id'));
    level = parseInt(hiddenLevel.getAttribute('value'));
    mortgageValue = parseInt(im.getAttribute('mortgage-value'));
    mortgageLiftCost = parseInt(im.getAttribute('mortgage-lift-cost'));
    houseCost = parseInt(im.getAttribute('house-cost'));
    hotelCost = parseInt(im.getAttribute('hotel-cost'));
    updateCardButtonsAndDescription();
    openImageBox.classList.add('display');
}

function increaseLevel() {
    level++;
    switch (level) {
        case 0:
            money -= mortgageLiftCost;
            break;
        case 1:
        case 2:
        case 3:
        case 4:
            money -= houseCost;
            break;
        case 5:
            money -= hotelCost;
            break;
    }
    hiddenLevel.setAttribute('value', level);
    updateCardButtonsAndDescription();
    cardDisplayMoney.innerHTML = money + '$';
    hiddenMoney.setAttribute('value', money);
}

function decreaseLevel() {
    level--;
    switch (level) {
        case -1:
            money += mortgageValue;
            break;
        case 0:
        case 1:
        case 2:
        case 3:
            money += houseCost;
            break;
        case 4:
            money += hotelCost;
            break;
    }
    hiddenLevel.setAttribute('value', level);
    hiddenMoney.setAttribute('value', money);
    updateCardButtonsAndDescription();
    cardDisplayMoney.innerHTML = money + '$';
}

function addCardListeners() {
    cardImages.forEach(im => {
        im.addEventListener('click', show);
    });
    imageContainer.classList.add('display');
}

function displayRules() {
    rules.classList.add('display');
}

function removeRules() {
    rules.classList.remove('display');
}

function ret() {
    openImageBox.classList.remove('display');
}

function doubleCheck() {
    bankruptcyConfirmation.classList.add('display');
}

function declineBankruptcy() {
    bankruptcyConfirmation.classList.remove('display');
}

function getOutByRolling() {
    rollDice('jail');
    jailButtons[4].setAttribute('value', 'pair-rolled');
    setTimeout(function () {
        if (diceEqual) {
            jailButtons[4].disabled = false;
        } else {
            jailButtons[7].disabled = false;
            console.log(jailButtons[7]);
        }
        for (let i = 1; i < 4; i++) jailButtons[i].disabled = true;
        jailButtons[5].disabled = true;
        jailButtons[6].disabled = true;
    }, 310);

}

function getOutByPaying() {
    jailButtons[4].setAttribute('value', 'paid-money');
    for (let i = 1; i < 4; i++) jailButtons[i].disabled = true;
    jailButtons[4].disabled = false;
    jailButtons[5].disabled = true;
    jailButtons[6].disabled = true;
}

function getOutByCard() {
    jailButtons[4].setAttribute('value', 'used-card');
    for (let i = 1; i < 4; i++) jailButtons[i].disabled = true;
    jailButtons[4].disabled = false;
    jailButtons[5].disabled = true;
    jailButtons[6].disabled = true;
}