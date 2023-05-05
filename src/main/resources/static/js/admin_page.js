// display information block
$(function () {
    let information_block = document.getElementById("information_block");

    $(document).ready(function () {
        displayBlockChangeCitiesInOperation(information_block);
    })

    $('#change_cities_in_operation').on('click', function () {
        displayBlockChangeCitiesInOperation(information_block);
    })

    $('#save_cities_button').on('click', function () {
        console.log(1)
        saveCities();
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

// display Change Cities In Operation parameters
function displayBlockChangeCitiesInOperation(information_block) {
    let cities = getEuropeanCities();
    let citiesObject = JSON.parse(cities);
    let countries = citiesObject.europeCitiesWithCountry;

    let html = '';
    for (const country in countries) {
        const cities = countries[country];
        html += `
                    <h6 class="mt-2">
                         ${country}
                    </h6>
                `;
        for (const city of cities) {
            let checked = city.inOperation ? "checked" : "";

            html += `
                    <div class="row ms-3 col-12">
                        <div class="form-check form-switch">
                            <label class="form-check-label" for="low_price_switcher">${city.cityName}</label>
                            <input class="form-check-input" type="checkbox" role="switch" id="${city.cityId}" ${checked}>
                        </div>
                    </div>
                `;
        }
    }
    html += `
                <button id="save_cities_button" class="btn btn-outline-primary mt-4 mb-5">
                    Зберегти зміни й почати оновлювати рейси
                </button>
                `;

    information_block.innerHTML = html;
    saveCities();
}

// get European Cities List with countries and boolean isInOperation?
function getEuropeanCities() {
    let xhr = new XMLHttpRequest();
    let endpoint = '/admin/cities';
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// save and update cities in operation
function saveCities() {
        $('#save_cities_button').on('click', function () {
            let information_block = document.getElementById("information_block");
            let inputs = information_block.querySelectorAll("input");
            let cities = new Map();
            for (const input of inputs) {
                cities.set(input.id, input.checked);
            }
            $.ajax({
                url: '/admin/update_cities',
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: JSON.stringify(Object.fromEntries(cities)),
                success: function () {
                    displayCitiesValidationMessage();
                }
            });
        })
    }

// show save alert message
function displayCitiesValidationMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Зміна переліку міст в додатку`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Перелік міст оновлено, через деякий час дані, щодо вартості авіаперельтів буде оновлено та збережено в системі!`;

    const save_cities = document.getElementById('save_cities');
    const save_cities_message = bootstrap.Toast.getOrCreateInstance(save_cities);
    save_cities_message.show();
}


function displayBlockChangeCashingMonths(information_block) {
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">В розробці, буде додано пізніше!</h1>
<!--            <div id="main_content">-->
<!--                <p>Ковальчук Володимир</p>-->
<!--                <p>-->
<!--                    GeekHub 2023-->
<!--                </p>-->
<!--            </div>-->
        `;
}

function displayBlockUpdateLocations(information_block) {
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">В розробці, буде додано пізніше!</h1>
<!--            <div id="main_content">-->
<!--                <p>Ковальчук Володимир</p>-->
<!--                <p>-->
<!--                    GeekHub 2023-->
<!--                </p>-->
<!--            </div>-->
        `;
}

function displayBlockUpdateRoutes(information_block) {
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">В розробці, буде додано пізніше!</h1>
<!--            <div id="main_content">-->
<!--                <p>Ковальчук Володимир</p>-->
<!--                <p>-->
<!--                    GeekHub 2023-->
<!--                </p>-->
<!--            </div>-->
        `;
}


// display change Users status parameters
function displayBlockEditUsers(information_block) {
    let users = getUsers();
    let usersObject = JSON.parse(users);

    let html = '';
    html += `
            <table class="table table-sm">
            <thead>
            <tr>
            <th scope="col">Ім'я користувача</th>
            <th scope="col">E-mail</th>
            <th scope="col">Статус адміністратора</th>
            <th scope="col">Заблокувати користувача</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
                    `;
    for (const user of usersObject) {
        let checked_admin = user.admin ? "checked" : "";
        let checked_blocked = user.blocked ? "checked" : "";

        html += `
                <tr>
                <th scope="row">${user.username}</th>
                <td>${user.email}</td>
                <td>
                        <div class="form-check form-switch ms-5">
                            <input class="form-check-input" type="checkbox" role="switch" id="admin_${user.userId}" ${checked_admin}>
                        </div>
                </td>
                <td>
                        <div class="form-check form-switch ms-5">
                            <input class="form-check-input" type="checkbox" role="switch" id="blocked_${user.userId}" ${checked_blocked}>
                        </div>
                </td>
                </tr>
                        `;
    }

    html += `
                </tbody>
                </table>
                
                <button id="save_users_button" class="btn btn-outline-primary mt-4 mb-5">Зберегти зміни й оновити права користувачів</button>
                `;

    information_block.innerHTML = html;
    saveUsers();
}

// get users list
function getUsers() {
    let xhr = new XMLHttpRequest();
    let endpoint = '/admin/users';
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// save and update users
function saveUsers() {
    $('#save_users_button').on('click', function () {
        let information_block = document.getElementById("information_block");
        let inputs = information_block.querySelectorAll("input");
        let users = new Map();
        for (const input of inputs) {
            users.set(input.id, input.checked);
        }
        $.ajax({
            url: '/admin/update_users',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(Object.fromEntries(users)),
            success: function () {
                displayUsersValidationMessage();
            }
        });
    })
}

// show save Users alert message
function displayUsersValidationMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Зміна статусу та прав користувачів додатку`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Статус та права користувачів оновлено та збережено в системі!`;

    const save_cities = document.getElementById('save_cities');
    const save_cities_message = bootstrap.Toast.getOrCreateInstance(save_cities);
    save_cities_message.show();
}