// For hide/show sidebar at the main HTML page
window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});

// display information in log-in block
$(document).ready(function () {
    let navbar_dropdown = document.getElementById("navbar_dropdown");
    let navbar_first = document.getElementById("navbar_first");
    let navbar_second = document.getElementById("navbar_second");
    let username = getUserInfo();
    if (username === '') {
        navbar_dropdown.innerHTML = "Зареєструватися";
        navbar_first.innerHTML = `
                    <a class="dropdown-item" href="/login">Увійти</a>
        `;
        navbar_second.innerHTML = `
                    <a class="dropdown-item" href="/registration">Зареєструватися</a>
        `;
    } else {
        navbar_dropdown.innerHTML = username;
        navbar_first.innerHTML = `
                    Користувач-${username}
        `;
        navbar_second.innerHTML = `
                    <a class="dropdown-item" href="/logout">Вийти</a>
        `;
    }
});

// get current user username
function getUserInfo() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/user-info', false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// display information block
$(function () {
    const $about_program = $('#about_program');
    const $about_author = $('#about_author');
    let information_block = document.getElementById("information_block");

    // $(document).ready(function () {
    //     displayBlockAboutProgram(information_block);
    // })

    $($about_program).on('click', function () {
        displayBlockAboutProgram(information_block);
    })

    $($about_author).on('click', function () {
        displayBlockAboutAuthor(information_block);
    })
})

function displayBlockAboutProgram(information_block) {
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Інформація про деталі роботи програми</h1>
            <div id="main_content">
                <p>
                    * В подальшому буде заповнено інформацією про деталі роботи програми!
                </p>
            </div>
        `;
}

function displayBlockAboutAuthor(information_block) {
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Інформація про автора програми</h1>
            <div id="main_content">
                <p>Ковальчук Володимир</p>
                <p>
                    GeekHub 2023
                </p>
            </div>
        `;
}