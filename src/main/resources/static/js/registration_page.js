// Show validation messages
document.getElementById("register-form").addEventListener("submit", function (event) {
    if (!validatePasswords()) {
        event.preventDefault();
        displayDifferentPasswordsMessage();
    }
    if (validateUserExists() === 'true') {
        event.preventDefault();
        displayUserExistsMessage();
    }
});

// Are passwords same?
function validatePasswords() {
    let password1 = document.getElementById("password").value;
    let password2 = document.getElementById("password_repeat").value;
    return password1 === password2;
}

// Is Username exists?
function validateUserExists() {
    let xhr = new XMLHttpRequest();
    let username = document.getElementById("username").value;
    let endpoint = '/exist?username=' + username;
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// show Different Passwords alert message
function displayDifferentPasswordsMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Введено некоректні дані`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Введені паролі не співпадають. Введіть однакові паролі!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}

// show User Exists alert message
function displayUserExistsMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Введено некоректні дані`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Введене ім'я користувача вже існує. Введіть інше ім'я користувача!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}