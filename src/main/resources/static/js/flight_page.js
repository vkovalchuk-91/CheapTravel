// Bootstrap validation function
(() => {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }

            form.classList.add('was-validated')
        }, false)
    })
})()

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

// slider days in each point options
(function () {
    'use strict';
    var init = function () {

        var slider = new rSlider({
            target: '#days_in_point',
            values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
            range: true,
            set: [3, 5],
            tooltip: false,
            onChange: function (vals) {
                console.log(vals);
            }
        });

        var slider2 = new rSlider({
            target: '#total_days',
            values: {min: 1, max: 60},
            step: 1,
            range: true,
            set: [1, 14],
            scale: false,
            labels: false,
            onChange: function (vals) {
                console.log(vals);
            }
        });

        var slider3 = new rSlider({
            target: '#total_cost',
            values: {min: 1, max: 999},
            step: 1,
            range: true,
            set: [1, 150],
            scale: false,
            labels: false,
            onChange: function (vals) {
                console.log(vals);
            }
        });
    };
    window.onload = init;
})();

// searchable select options
for (const el of document.querySelectorAll('.dselect')) {
    dselect(el, {
        search: true
    })
}

// date range picker options
$('#trip_dates').daterangepicker({
    "autoApply": true,
    ranges: {
        'Найближчі пів року': [moment(), moment().add(5, 'month').endOf('month')],
        'Найближчі 3 місяці': [moment(), moment().add(2, 'month').endOf('month')],
        'Наступний місяць': [moment().add(1, 'month').startOf('month'), moment().add(1, 'month').endOf('month')],
        'Цей місяць': [moment(), moment().endOf('month')],
        'Наступні 7 днів': [moment(), moment().add(6, 'days')],
        'Сьогодні': [moment(), moment()]
    },
    "locale": {
        "format": "YYYY-MM-DD",
        "separator": " - ",
        "applyLabel": "Apply",
        "cancelLabel": "Cancel",
        "fromLabel": "From",
        "toLabel": "To",
        "customRangeLabel": "Інші дати",
        "weekLabel": "W",
        "daysOfWeek": [
            "Нд",
            "Пн",
            "Вт",
            "Ср",
            "Чт",
            "Пт",
            "Сб"
        ],
        "monthNames": [
            "Січень",
            "Лютий",
            "Березень",
            "Квітень",
            "Травень",
            "Червень",
            "Липень",
            "Серпень",
            "Вересень",
            "Жовтень",
            "Листопад",
            "Грудень"
        ],
        "firstDay": 1
    },
    "alwaysShowCalendars": true,
    "startDate": moment(),
    "endDate": moment().add(5, 'month').endOf('month'),
    "minDate": moment(),
    "maxDate": moment().add(5, 'month').endOf('month'),
    "opens": "left"
}, function (start, end) {
    console.log(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
});

// remove/add flight points
$(function () {
    const $removeButton = $('#removeFlightButton');
    const $addButton = $('#addFlightButton');
    let flightNumber = $('#searchButton').attr('data-flights');

    $($addButton).on('click', function () {
        flightNumber++;
        const nextFlight = `#flight-` + flightNumber;
        $(nextFlight).removeAttr('style');
        $('#searchButton').attr('data-flights', flightNumber);

        $removeButton.prop('disabled', flightNumber === 2);
        $addButton.prop('disabled', flightNumber === 4);
    });

    $($removeButton).on('click', function () {
        const nextFlight = `#flight-` + (flightNumber);
        $(nextFlight).attr('style', 'display: none;');
        flightNumber--;
        $('#searchButton').attr('data-flights', flightNumber);

        $removeButton.prop('disabled', flightNumber === 2);
        $addButton.prop('disabled', flightNumber === 4);
    });
});

// get search results
$(function () {
    const $searchButton = $('#searchButton');
    const $start_point_1 = $('#start_point_1');
    const $end_point_1 = $('#end_point_1');
    const $start_point_2 = $('#start_point_2');
    const $end_point_2 = $('#end_point_2');
    const $start_point_3 = $('#start_point_3');
    const $end_point_3 = $('#end_point_3');
    const $start_point_4 = $('#start_point_4');
    const $end_point_4 = $('#end_point_4');
    const low_price_switcher = document.getElementById('low_price_switcher');

    $($searchButton).on('click', function () {
        let flight_number = $searchButton.attr('data-flights');

        if ($start_point_1.val() === null || $end_point_1.val() === null ||
            $start_point_2.val() === null || $end_point_2.val() === null) {
            displayValidationMessage();
        } else if (flight_number >= 3 && ($start_point_3.val() === null || $end_point_3.val() === null)) {
            displayValidationMessage();
        } else if (flight_number >= 4 && ($start_point_4.val() === null || $end_point_4.val() === null)) {
            displayValidationMessage();
        } else {
            $.ajax({
                url: '/search',
                method: 'POST',
                data: {
                    flightNumber: flight_number,
                    lowPriceSwitcher: low_price_switcher.checked,
                    startPoint1: $start_point_1.val(),
                    endPoint1: $end_point_1.val(),
                    startPoint2: $start_point_2.val(),
                    endPoint2: $end_point_2.val(),
                    startPoint3: $start_point_3.val(),
                    endPoint3: $end_point_3.val(),
                    startPoint4: $start_point_4.val(),
                    endPoint4: $end_point_4.val(),

                    tripDates: $('#trip_dates').val(),
                    daysInPoint: $('#days_in_point').val(),
                    totalDays: $('#total_days').val(),
                    totalCost: $('#total_cost').val()
                },
                success: function (data) {
                    displayResults(data);
                    setPagination();
                }
            });
        }
    });
});

// display Validation needs message
function displayValidationMessage() {
    let html = `
            <h3>
            Заповніть будь-ласка всі параметри для коректного пошуку!
            </h3>
`
    $('#main_content').html(html);
}

// display search results
function displayResults(data) {
    let html = '';

    if (data.length === 0) {
        html = `
            <h3>
            Немає перельотів, що задовольняють умови вашого пошуку!
            </h3>
                `
        $('#main_content').html(html);
    } else {
        data.forEach(function (trip) {
            let flights = trip.flights;
            let counter = 0;

            html += `
            <div class="mb-5">
            <table class="table table-sm">
            <thead>
            <tr>
            <th scope="col">#</th>
            <th scope="col">Місто відправки</th>
            <th scope="col">Місто прибуття</th>
            <th scope="col">Дата</th>
            </tr>
            </thead>
            <tbody class="table-group-divider">
                    `;

            flights.forEach(function (flight) {
                counter++;

                html += `
                <tr>
                <th scope="row">${counter}</th>
                <td>${flight.route.fromCity.name}</td>
                <td>${flight.route.toCity.name}</td>
                <td>${flight.flightDate}</td>
                </tr>
                        `;
            });

            html += `
                </tbody>
                <tfoot class="table-group-divider">
                <tr>
                <th scope="row"></th>
                <th colspan="3">Загальна вартість поїздки&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                ${trip.totalCost}  $</th>
                </tr>
                </tfoot>
                </table>
                </div>
                    `;
        })
        $('#main_content').html(html);
        addPaginationButtons()
    }
}

// add pagination buttons
function addPaginationButtons() {
    let main_content = document.getElementById("main_content");
    let html = `
            <div class="d-flex justify-content-center mb-4">
            <div class="btn-group">
            <button type="button" class="btn btn-outline-primary" id="prev-page">
            <span>Попередня сторінка</span>
            </button>
            <button type="button" class="btn btn-outline-primary" id="next-page">
            <span>Наступна сторінка</span>
            </button>
            </div>
            </div>
                `;
    main_content.innerHTML += html;
}

// configure pagination for search results
function setPagination() {
    const $prev_page = $('#prev-page');
    const $next_page = $('#next-page');
    var itemsPerPage = 10; // кількість елементів на сторінці
    var currentPage = 1; // поточна сторінка
    var items = $("table"); // отримання всіх елементів списку

    var totalPages = Math.ceil(items.length / itemsPerPage); // обчислення загальної кількості сторінок

    // функція для показу елементів поточної сторінки
    function showItems() {
        var startIndex = (currentPage - 1) * itemsPerPage;
        var endIndex = startIndex + itemsPerPage;

        items.hide().slice(startIndex, endIndex).show();
    }

    // функція для оновлення інтерфейсу
    function updateInterface() {
        // оновлення кнопки "Наступна"
        if (currentPage === totalPages) {
            $next_page.attr("disabled", "disabled");
        } else {
            $next_page.removeAttr("disabled");
        }

        // оновлення кнопки "Попередня"
        if (currentPage === 1) {
            $prev_page.attr("disabled", "disabled");
        } else {
            $prev_page.removeAttr("disabled");
        }

        // оновлення номера сторінки
        $("#current-page").text(currentPage);
    }

    // показуємо першу сторінку
    showItems();
    updateInterface();

    // обробник натискання на кнопку "Наступна"
    $next_page.click(function () {
        currentPage++;
        showItems();
        updateInterface();
    });

    // обробник натискання на кнопку "Попередня"
    $prev_page.click(function () {
        currentPage--;
        showItems();
        updateInterface();
    });
}

// display information in log-in block
$(document).ready(function() {
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