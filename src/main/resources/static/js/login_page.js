// Show validation messages
document.getElementById("login-form").addEventListener("submit", function (event) {
    if (validateUserExists() === 'false') {
        event.preventDefault();
        displayWrongUsernameOrPasswordMessage();
    } else if (validateUserBlocking() === 'true') {
        event.preventDefault();
        displayBlockedUserMessage();
    }
});

// Is Username exists?
function validateUserExists() {
    let xhr = new XMLHttpRequest();
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let endpoint = '/exist?username=' + username + '&password=' + password;
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// Is User blocked?
function validateUserBlocking() {
    let xhr = new XMLHttpRequest();
    let username = document.getElementById("username").value;
    let endpoint = '/blocked?username=' + username;
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// show Wrong Username Or Password alert message
function displayWrongUsernameOrPasswordMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Введено некоректні дані`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Введене невірне ім'я користувача чи пароль. Повторіть спробу!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}

// show Blocked User alert message
function displayBlockedUserMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Помилка доступу`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Користувача заблоковано, для уточнення деталей зверніться до адміністраторів!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}