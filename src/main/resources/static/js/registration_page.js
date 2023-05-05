// Show validation messages
document.getElementById("register-form").addEventListener("submit", function (event) {
    if (!validatePasswords()) {
        event.preventDefault();
        const different_passwords = document.getElementById('different_passwords');
        const different_passwords_message = bootstrap.Toast.getOrCreateInstance(different_passwords);
        different_passwords_message.show();
    }
    if (validateUserExists() === 'true') {
        event.preventDefault();
        const user_exist = document.getElementById('user_exist');
        const user_exist_message = bootstrap.Toast.getOrCreateInstance(user_exist);
        user_exist_message.show();
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