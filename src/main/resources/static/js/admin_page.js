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
    let information_block = document.getElementById("information_block");

    $(document).ready(function () {
        displayBlockChangeCitiesInOperation(information_block);
    })

    $('#change_cities_in_operation').on('click', function () {
        displayBlockChangeCitiesInOperation(information_block);
    })

    $('#change_cashing_months').on('click', function () {
        displayBlockChangeCashingMonths(information_block);
    })

    $('#update_locations').on('click', function () {
        displayBlockUpdateLocations(information_block);
    })

    $('#update_routes').on('click', function () {
        displayBlockUpdateRoutes(information_block);
    })

    $('#edit_users').on('click', function () {
        displayBlockEditUsers(information_block);
    })
})

function displayBlockChangeCitiesInOperation(information_block) {
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Зміна переліку міст, по яким здійснюється пошук</h1>
            <div id="main_content">
                <p>
                    * В подальшому буде заповнено інформацією про деталі роботи програми!
                </p>
            </div>
        `;
}

function displayBlockChangeCashingMonths(information_block) {
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

function displayBlockUpdateLocations(information_block) {
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

function displayBlockUpdateRoutes(information_block) {
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

function displayBlockEditUsers(information_block) {
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