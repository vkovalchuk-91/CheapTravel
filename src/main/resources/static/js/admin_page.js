// display information block
$(function () {
    let information_block = document.getElementById("information_block");

    $(document).ready(function () {
        displayBlockChangeCitiesInOperation(information_block);
    })

    $('#change_cities_in_operation').on('click', function () {
        displayBlockChangeCitiesInOperation(information_block);
    })

    $('#change_abbreviations').on('click', function () {
        displayBlockChangeAbbreviations(information_block);
    })

    $('#edit_users').on('click', function () {
        displayBlockEditUsers(information_block);
    })

    $('#other_options').on('click', function () {
        displayBlockOtherOptions(information_block);
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
                            <label class="form-check-label" for="${city.cityId}">${city.cityName}</label>
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
            url: '/admin/cities',
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

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}

// display Change abbreviations for Cities which used in SkyScanner.com.ua search
function displayBlockChangeAbbreviations(information_block) {
    let cities = getCitiesAbbreviation();
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
            html += `
                    <div class="row ms-3 col-12 input-group-sm">
                            <label style="width: 250px" class="col-form-label" for="${city.cityId}">${city.cityName}</label>
                            <input style="width: 60px" class="form-control" required type="text" id="${city.cityId}" value="${city.abbreviation}" aria-describedby="inputGroup-sizing-sm">
                    </div>
                `;
        }
    }
    html += `
                <button id="save_abbreviations_button" class="btn btn-outline-primary mt-4 mb-5">
                    Зберегти зміни абревіатур міст
                </button>
                `;

    information_block.innerHTML = html;
    saveCitiesAbbreviation();
}

// get European Cities List with countries and string Abbreviation?
function getCitiesAbbreviation() {
    let xhr = new XMLHttpRequest();
    let endpoint = '/admin/abbreviations';
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// save and update cities Abbreviation
function saveCitiesAbbreviation() {
    $('#save_abbreviations_button').on('click', function () {
        let information_block = document.getElementById("information_block");
        let inputs = information_block.querySelectorAll("input");
        let cities = new Map();
        for (const input of inputs) {
            cities.set(input.id, input.value);
        }
        $.ajax({
            url: '/admin/abbreviations',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(Object.fromEntries(cities)),
            success: function () {
                displayAbbreviationsValidationMessage();
            }
        });
    })
}

// show save Abbreviations alert message
function displayAbbreviationsValidationMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Зміна абревіатур міст в додатку`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Абревіатури міст оновлено, збережено в системі та готові для використання для пошуку в системі SkyScanner.com.ua!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
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
            url: '/admin/users',
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

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}

function displayBlockOtherOptions(information_block) {
    let months_number = getMonthsNumber();
    let html = '';
    html += `
            <button id="update_locations_button" class="btn btn-outline-primary mt-4 mb-4">
                Оновити перелік локацій SkyScanner API
            </button>
            <hr>
            <button id="update_routes_button" class="btn btn-outline-primary mt-4 mb-4">
                Оновити доступність маршрутів
            </button>
            <hr>
            <div class="row  mt-4 mb-4">
                <div class="col-1">
                    <select class="form-select" required id="updated_months_number" name="to">
        `;

    for (let i = 1; i <= 12; i++) {
        let selected = "";
        if (i == months_number) {
            selected = "selected";
        }
        html += `<option ${selected} value="${i}">${i}</option>`;
    }

    html += `
                    </select>
                    </div>
                    <div class="col-6">
                    <button id="change_months_number_button" class="btn btn-outline-primary">
                        Змінити кількість місяців, на які кешуються ціни
                    </button>
                    </div>
            </div>
        `;
    information_block.innerHTML = html;
    updateLocations();
    updateRoutes();
    changeMonthsNumber();
}

function getMonthsNumber() {
    let xhr = new XMLHttpRequest();
    let endpoint = '/admin/months';
    xhr.open('GET', endpoint, false);
    xhr.send();
    if (xhr.status === 200) {
        return xhr.responseText;
    }
}

// save and update users
function updateLocations() {
    $('#update_locations_button').on('click', function () {
        $.ajax({
            url: '/admin/locations',
            method: 'POST',
            success: function () {
                displayUpdateLocationsMessage();
            }
        });
    })
}

// show Update Locations alert message
function displayUpdateLocationsMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Оновлення переліку локацій`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Запущено процес оновлення переліку локацій SkyScanner, локації оновлено та збережено в системі!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}

// save and update users
function updateRoutes() {
    $('#update_routes_button').on('click', function () {
        $.ajax({
            url: '/admin/routes',
            method: 'POST',
            success: function () {
                displayUpdateRoutesMessage();
            }
        });
    })
}

// show Update Routes alert message
function displayUpdateRoutesMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Оновлення доступності маршрутів`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Запущено процес оновлення доступності маршрутів, через деякий час маршрути буде оновлено та збережено в системі!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}

// save and update users
function changeMonthsNumber() {
    $('#change_months_number_button').on('click', function () {
        let months_number = document.getElementById("updated_months_number").value;
        $.ajax({
            url: '/admin/months',
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: months_number,
            success: function () {
                displayChangeMonthsNumberMessage();
            }
        });
    })
}

// show Update Routes alert message
function displayChangeMonthsNumberMessage() {
    let alert_header = document.getElementById("alert_header");
    alert_header.innerHTML = `&nbsp;&nbsp;&nbsp;Зміна кількості місяців, на які кешуються ціни`;
    let alert_message = document.getElementById("alert_message");
    alert_message.innerHTML = `Кількість місяців, на які кешуються ціни оновлено, через деякий час дані щодо польотів буде оновлено та збережено в системі!`;

    const main_alert = document.getElementById('main_alert');
    const main_alert_message = bootstrap.Toast.getOrCreateInstance(main_alert);
    main_alert_message.show();
}