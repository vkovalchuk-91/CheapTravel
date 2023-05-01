// Show validation messages
document.getElementById("login-form").addEventListener("submit", function (event) {
    if (validateUserExists() === 'false') {
        event.preventDefault();
        const wrong_login_or_password = document.getElementById('wrong_login_or_password');
        const wrong_login_or_password_message = bootstrap.Toast.getOrCreateInstance(wrong_login_or_password);
        wrong_login_or_password_message.show();
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
        console.log(xhr.responseText)
        return xhr.responseText;
    }
}