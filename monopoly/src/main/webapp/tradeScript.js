const cards = document.querySelectorAll('.curr-player-cards img');

function selector(e) {
    let card = e.target;
    let hiddenSelection = document.getElementById(card.getAttribute('card-name'));
    if (hiddenSelection.getAttribute('value') == 'not-selected') {
        hiddenSelection.setAttribute('value', 'selected');
        card.classList.add('select');
    } else {
        hiddenSelection.setAttribute('value', 'not-selected');
        card.classList.remove('select');
    }
}

cards.forEach(card => {
    card.addEventListener('click', selector);
})