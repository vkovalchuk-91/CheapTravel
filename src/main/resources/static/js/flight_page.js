// slider days in each point options
(function () {
    'use strict';
    var init = function () {

        let $days_in_point = $('#days_in_point');
        let days_in_point_min = Number($days_in_point.attr('data-min'));
        let days_in_point_max = Number($days_in_point.attr('data-max'));

        let $total_days = $('#total_days');
        let total_days_min = Number($total_days.attr('data-min'));
        let total_days_max = Number($total_days.attr('data-max'));

        let $total_cost = $('#total_cost');
        let total_cost_min = Number($total_cost.attr('data-min'));
        let total_cost_max = Number($total_cost.attr('data-max'));

        var slider = new rSlider({
            target: '#days_in_point',
            values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
            range: true,
            set: [days_in_point_min, days_in_point_max],
            tooltip: false,
            onChange: function (vals) {
            }
        });

        var slider2 = new rSlider({
            target: '#total_days',
            values: {min: 1, max: 60},
            step: 1,
            range: true,
            set: [total_days_min, total_days_max],
            scale: false,
            labels: false,
            onChange: function (vals) {
            }
        });

        var slider3 = new rSlider({
            target: '#total_cost',
            values: {min: 1, max: 999},
            step: 1,
            range: true,
            set: [total_cost_min, total_cost_max],
            scale: false,
            labels: false,
            onChange: function (vals) {
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
        'Найближчі 2 місяці': [moment(), moment().add(1, 'month').endOf('month')],
        'Наступний місяць': [moment().add(1, 'month').startOf('month'), moment().add(1, 'month').endOf('month')],
        'Цей місяць': [moment(), moment().endOf('month')],
        'Наступні 7 днів': [moment(), moment().add(6, 'days')]
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
    // default values
    "startDate": moment(),
    "endDate": moment().add(1, 'month').endOf('month'),
    "minDate": moment(),
    "maxDate": moment().add(5, 'month').endOf('month'),
    "opens": "left"
}, function (start, end) {
    console.log(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
});

// change the selected date range of that picker
$(function () {
    let $trip_dates = $('#trip_dates');
    let trip_date_start = $trip_dates.attr('data-start');
    let trip_date_end = $trip_dates.attr('data-end');

    $trip_dates.data('daterangepicker').setStartDate(trip_date_start);
    $trip_dates.data('daterangepicker').setEndDate(trip_date_end);
})

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

    let show_results = $searchButton.attr('data-show-results');
    if (show_results === "1") {
        showResultsFromFavourites();
    }

    $($searchButton).on('click', function () {
        showResultsFromSearchButton();
    });
});

// show searching results after request from favourites
function showResultsFromFavourites() {
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

    let flight_number = $searchButton.attr('data-flights');

    let $trip_dates = $('#trip_dates');
    let trip_date_start = $trip_dates.attr('data-start');
    let trip_date_end = $trip_dates.attr('data-end');

    let $days_in_point = $('#days_in_point');
    let days_in_point_min = $days_in_point.attr('data-min');
    let days_in_point_max = $days_in_point.attr('data-max');

    let $total_days = $('#total_days');
    let total_days_min = $total_days.attr('data-min');
    let total_days_max = $total_days.attr('data-max');

    let $total_cost = $('#total_cost');
    let total_cost_min = $total_cost.attr('data-min');
    let total_cost_max = $total_cost.attr('data-max');

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

            tripDates: trip_date_start + " - " + trip_date_end,
            daysInPoint: days_in_point_min + "," + days_in_point_max,
            totalDays: total_days_min + "," + total_days_max,
            totalCost: total_cost_min + "," + total_cost_max
        },
        success: function (data) {
            setSearchingInFavouritesParams(
                flight_number,
                low_price_switcher.checked,
                $start_point_1.val(),
                $end_point_1.val(),
                $start_point_2.val(),
                $end_point_2.val(),
                $start_point_3.val(),
                $end_point_3.val(),
                $start_point_4.val(),
                $end_point_4.val(),
                trip_date_start + " - " + trip_date_end,
                days_in_point_min + "," + days_in_point_max,
                total_days_min + "," + total_days_max,
                total_cost_min + "," + total_cost_max);
            displayFavourites(data);
            displayResults(data);
            setPagination();
        }
    });
}

// show searching results after Search Button press
function showResultsFromSearchButton() {
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

    let flight_number = $searchButton.attr('data-flights');

    if ($start_point_1.val() === null || $end_point_1.val() === null ||
        $start_point_2.val() === null || $end_point_2.val() === null) {
        displayValidationMessage();
    } else if (flight_number >= 3 && ($start_point_3.val() === null || $end_point_3.val() === null)) {
        displayValidationMessage();
    } else if (flight_number >= 4 && ($start_point_4.val() === null || $end_point_4.val() === null)) {
        displayValidationMessage();
    } else {
        displaySearchingProgress();

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
                setSearchingInFavouritesParams(
                    flight_number,
                    low_price_switcher.checked,
                    $start_point_1.val(),
                    $end_point_1.val(),
                    $start_point_2.val(),
                    $end_point_2.val(),
                    $start_point_3.val(),
                    $end_point_3.val(),
                    $start_point_4.val(),
                    $end_point_4.val(),
                    $('#trip_dates').val(),
                    $('#days_in_point').val(),
                    $('#total_days').val(),
                    $('#total_cost').val());
                displayFavourites(data);
                displayResults(data);
                setPagination();
            }
        });
    }
}

// display Searching Progress Bar
function displaySearchingProgress() {
    let html = `
            <div class="progress" role="progressbar" aria-label="Animated striped example" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
              <div class="progress-bar progress-bar-striped progress-bar-animated" style="width: 100%"></div>
            </div>`
    $('#main_content').html(html);
}

// display Validation needs message
function displayValidationMessage() {
    let html = `
            <h3>
            Заповніть будь-ласка всі параметри для коректного пошуку!
            </h3>`
    $('#main_content').html(html);
}

// set searching parameters for adding to favourites
function setSearchingInFavouritesParams(flight_number,
                                        low_price_switcher,
                                        start_point_1,
                                        end_point_1,
                                        start_point_2,
                                        end_point_2,
                                        start_point_3,
                                        end_point_3,
                                        start_point_4,
                                        end_point_4,
                                        trip_dates,
                                        days_in_point,
                                        total_days,
                                        total_cost) {
    const $favourite = $('#favourite');

    $favourite.attr('data-flight_number', flight_number);
    $favourite.attr('data-low_price_switcher', low_price_switcher);
    $favourite.attr('data-start_point_1', start_point_1);
    $favourite.attr('data-end_point_1', end_point_1);
    $favourite.attr('data-start_point_2', start_point_2);
    $favourite.attr('data-end_point_2', end_point_2);
    $favourite.attr('data-start_point_3', start_point_3);
    $favourite.attr('data-end_point_3', end_point_3);
    $favourite.attr('data-start_point_4', start_point_4);
    $favourite.attr('data-end_point_4', end_point_4);
    $favourite.attr('data-trip_dates', trip_dates);
    $favourite.attr('data-days_in_point', days_in_point);
    $favourite.attr('data-total_days', total_days);
    $favourite.attr('data-total_cost', total_cost);
}

// display favourites sign
function displayFavourites(data) {
    let id = data.favouriteId;
    if (id === 0) {
        showNotInFavouritesSign();
    } else {
        showInFavouritesSign(id);
    }
}

// show Not In Favourites Sign
function showNotInFavouritesSign() {
    $('#in_favourite').attr('style', 'display: none;');
    $('#not_in_favourite').removeAttr('style');
}

// show In Favourites Sign
function showInFavouritesSign(favouriteId) {
    const $in_favourite = $('#in_favourite');
    $in_favourite.removeAttr('style');
    $in_favourite.attr('data-favourite-id', favouriteId);
    $('#not_in_favourite').attr('style', 'display: none;');
}

// add/delete favourite
$(function () {
    $('#not_in_favourite').on('click', function () {
        addToFavourite();
    })

    $('#in_favourite').on('click', function () {
        deleteFromFavourite();
    })
})

// add searching query from DB
function addToFavourite() {
    const $favourite = $('#favourite');
    $.ajax({
        url: '/favourites',
        method: 'POST',
        data: {
            flightNumber: $favourite.attr('data-flight_number'),
            lowPriceSwitcher: $favourite.attr('data-low_price_switcher'),
            startPoint1: $favourite.attr('data-start_point_1'),
            endPoint1: $favourite.attr('data-end_point_1'),
            startPoint2: $favourite.attr('data-start_point_2'),
            endPoint2: $favourite.attr('data-end_point_2'),
            startPoint3: $favourite.attr('data-start_point_3'),
            endPoint3: $favourite.attr('data-end_point_3'),
            startPoint4: $favourite.attr('data-start_point_4'),
            endPoint4: $favourite.attr('data-end_point_4'),

            tripDates: $favourite.attr('data-trip_dates'),
            daysInPoint: $favourite.attr('data-days_in_point'),
            totalDays: $favourite.attr('data-total_days'),
            totalCost: $favourite.attr('data-total_cost')
        },
        success: function (data) {
            showInFavouritesSign(data);
        }
    });
}

// delete searching query from DB
function deleteFromFavourite() {
    $.ajax({
        url: '/favourites',
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        data: $('#in_favourite').attr('data-favourite-id'),
        success: function (data) {
            showNotInFavouritesSign();
        }
    });
}

// display search results
function displayResults(data) {
    let html = '';

    if (data.trips.length === 0) {
        html = `
            <h3>
            Немає перельотів, що задовольняють умови вашого пошуку!
            </h3>
                `
        $('#main_content').html(html);
    } else {
        html += `
            <p style="color: red">* Результати пошуку сформовані на основі кешованих індикативних цін, що надаються <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>, актуальні на даний момент ціни можна отримати перейшовши за посиланням під кожним варіантом подорожі!
            </p>`;
        data.trips.forEach(function (trip) {
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
                <td>${flight.fromCity}</td>
                <td>${flight.toCity}</td>
                <td>${flight.date}</td>
                </tr>
                        `;
            });

            html += `
                </tbody>
                <tfoot class="table-group-divider">
                <tr>
                <th scope="row"></th>
                <th colspan="3">Загальна вартість поїздки&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                ${trip.totalCost}  $&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="${trip.link}" target="_blank" class="css-tooltip" data-tooltip="Відкрити актуальні дані щодо вартості подорожі на SkyScanner.com.ua">
                <div class="arrow">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
                <svg style="fill: #0a53be" xmlns="http://www.w3.org/2000/svg" height="20" viewBox="0 0 910 149">
                <path d="M100.1 127c2.1 0 4.1-.5 6-1.6l21.9-12.6c4.4-2.5 9.5-3.6 14.6-3 26.6 3.1 45.2 8.1 50.7 9.7 1.1.3 2.4-.1 3.1-1 .9-1.1 2-2.9 2.9-5.5.8-2.5.9-4.6.8-6.1-.1-1.2-.9-2.3-2.1-2.6-8.6-2.5-46.7-12.8-97.9-12.8s-89.3 10.3-97.9 12.8c-1.2.3-2 1.4-2.1 2.6-.1 1.4 0 3.5.8 6.1.8 2.6 2 4.4 2.9 5.5.7.9 2 1.3 3.1 1 5.5-1.6 24.2-6.6 50.7-9.7 5.1-.6 10.2.5 14.6 3l21.9 12.6c1.9 1.1 4 1.6 6 1.6zM63.6 56.1c1.2 2.1 3.1 3.5 5.3 4.1 2.2.6 4.5.3 6.6-.9 2.1-1.2 3.5-3.1 4.1-5.3.6-2.2.3-4.5-.9-6.6L59 13.3c-.6-1.1-1.9-1.5-3.1-1.4-1.6.1-3.8.9-6.4 2.4-2.6 1.5-4.4 3-5.3 4.3-.7 1-.9 2.3-.3 3.4l19.7 34.1zm-18 24.4c2.1 1.2 4.5 1.4 6.6.9 2.2-.6 4.1-2 5.3-4.1 1.2-2.1 1.4-4.5.9-6.6-.5-2.1-2-4.1-4.1-5.3L20.2 45.7c-1.1-.6-2.4-.4-3.4.3-1.3.9-2.8 2.7-4.3 5.3-1.5 2.6-2.3 4.8-2.4 6.4-.1 1.3.4 2.5 1.4 3.1l34.1 19.7zM108.8 44c0 2.4-1 4.6-2.5 6.2-1.6 1.6-3.7 2.5-6.2 2.5-2.4 0-4.6-1-6.2-2.5-1.6-1.6-2.5-3.7-2.5-6.2V4.7c0-1.3.8-2.3 2-2.8 1.4-.7 3.7-1.1 6.7-1.1s5.3.4 6.7 1.1c1.1.6 2 1.5 2 2.8V44zm27.8 12.1c-1.2 2.1-3.1 3.5-5.3 4.1-2.2.6-4.5.3-6.6-.9-2.1-1.2-3.5-3.1-4.1-5.3-.6-2.2-.3-4.5.9-6.6l19.7-34.1c.6-1.1 1.9-1.5 3.1-1.4 1.6.1 3.8.9 6.4 2.4 2.6 1.5 4.4 3 5.3 4.3.7 1 .9 2.3.3 3.4l-19.7 34.1zm18 24.4c-2.1 1.2-4.5 1.4-6.6.9-2.2-.6-4.1-2-5.3-4.1-1.2-2.1-1.4-4.5-.9-6.6.6-2.2 2-4.1 4.1-5.3L180 45.7c1.1-.6 2.4-.4 3.4.3 1.3.9 2.8 2.7 4.3 5.3 1.5 2.6 2.3 4.8 2.4 6.4.1 1.3-.4 2.5-1.4 3.1l-34.1 19.7zm217.5-27.1h16.4c.6 0 1.1.4 1.4.9l16.3 41.8L422 54.3c.2-.6.8-.9 1.4-.9h16.1c1.1 0 1.8 1.1 1.3 2l-40 91.8c-.2.5-.8.9-1.3.9h-14.2c-1 0-1.8-1.1-1.3-2l13.7-32.1-26.8-58.6c-.6-.9.1-2 1.2-2zm242.1 9.3v-7.8c0-.8.7-1.5 1.5-1.5h14.5c.8 0 1.5.7 1.5 1.5v62.3c0 .8-.7 1.5-1.5 1.5h-14.5c-.8 0-1.5-.7-1.5-1.5V109c-3.1 4.8-9.9 11.1-21 11.1-21.2 0-32.2-16.2-32.2-34.3 0-23.6 16.4-33.7 31.7-33.7 10.1-.1 17.1 4.7 21.5 10.6zm-35.7 23.2c0 11.2 6.9 19.8 18 19.8s18.2-7.5 18.2-19-6.9-20.3-18.7-20.3c-11.1 0-17.5 8.6-17.5 19.5zm66.3 31.3V54.9c0-.8.7-1.5 1.5-1.5h14.5c.8 0 1.5.7 1.5 1.5v8.5c3.4-5.9 9.8-11.3 20.7-11.3 11.2 0 23.7 5.5 23.7 30.8v34.3c0 .8-.7 1.5-1.5 1.5h-14.5c-.8 0-1.5-.7-1.5-1.5v-34c0-6.1-1.5-16.6-12.3-16.6s-14.6 9.5-14.6 18.3v32.3c0 .8-.7 1.5-1.5 1.5h-14.5c-.9-.1-1.5-.7-1.5-1.5zm74.3 0V54.9c0-.8.7-1.5 1.5-1.5h14.5c.8 0 1.5.7 1.5 1.5v8.5c3.4-5.9 9.8-11.3 20.7-11.3 11.2 0 23.7 5.5 23.7 30.8v34.3c0 .8-.7 1.5-1.5 1.5H765c-.8 0-1.5-.7-1.5-1.5v-34c0-6.1-1.5-16.6-12.3-16.6s-14.6 9.5-14.6 18.3v32.3c0 .8-.7 1.5-1.5 1.5h-14.5c-.8-.1-1.5-.7-1.5-1.5zm152.3-63.8h14.1c.8 0 1.5.7 1.5 1.5v12.9c2.8-12.8 13.8-17.2 21.8-15.3.7.1 1.1.7 1.1 1.4v14.2c0 .9-.8 1.6-1.8 1.4-14.8-2.9-20.8 5.1-20.8 16.3v31.4c0 .8-.7 1.5-1.5 1.5h-14.5c-.8 0-1.5-.7-1.5-1.5V54.9c.1-.8.8-1.5 1.6-1.5zM276 67.6c-4.5-1.5-6.9-2.4-10.5-3.6-3.7-1.3-10-4.9-10-10.7 0-5.8 4-9.5 12.1-9.5 7.4 0 11.5 3.6 14.2 9.4.4.8 1.3 1.1 2.1.7l11.9-6.9c.6-.4.9-1.2.6-1.9-3.6-7.9-12-17.4-28.6-17.4-18.5 0-30.1 11-30.1 25.5 0 14.4 9.5 21.9 22.4 26.3 4.7 1.6 6.6 2.3 10.6 3.6 7.6 2.6 11.1 6 11.1 10.7 0 4.7-3 10.3-14.8 10.3-10.8 0-14.6-5.7-16.8-11.1-.3-.8-1.3-1.2-2.1-.7l-12.5 7.2c-.6.4-.9 1.1-.6 1.8 4.8 11.8 17.9 18.8 32.6 18.8 17.3 0 31.9-8.8 31.9-26.6s-19-24.4-23.5-25.9zm95.5 34c-.4-.7-1.4-1-2.1-.5-5.6 4.1-12.2 4.2-17.2-3.5-4.2-6.5-9.3-14.5-9.3-14.5l22-27.3c.8-1 .1-2.4-1.1-2.4h-17.2c-.5 0-.9.2-1.2.6l-19 26.1V27.6c0-.8-.7-1.5-1.5-1.5h-14.5c-.8 0-1.5.7-1.5 1.5v89.6c0 .8.7 1.5 1.5 1.5H325c.8 0 1.5-.7 1.5-1.5V88.1s9.7 15.3 13.4 21c5 7.8 12 11 19.6 11 7.2 0 11.8-1.9 17.3-7.3.5-.5.6-1.2.3-1.8l-5.6-9.4zM475.6 81c-4.6-1.7-6.2-2.3-9.7-3.6-3.5-1.3-6.6-3.2-6.6-6s2.6-5.9 7.9-5.9c4.6 0 7.4 1.8 9 5 .4.8 1.3 1 2 .6l10.2-5.9c.7-.4.9-1.3.5-2-2.8-4.9-8.3-11.1-21.6-11.1-16.3 0-24.7 9.4-24.7 19.8s8.5 15.3 16.3 18.4c8.8 3.6 9.4 3.8 10.5 4.2 2.7 1.1 6 2.8 6 5.9 0 3.1-3.6 5.9-9.1 5.9-5.1 0-10.9-2.1-13.3-8-.3-.8-1.3-1.2-2.1-.7l-10.3 5.9c-.6.4-.9 1.1-.6 1.8 3 7.8 11.9 14.7 26.3 14.7 15.6 0 26-8.3 26-20s-8-15.8-16.7-19zm58.6-13.4c6.5 0 11.3 2 15.8 5.2.7.5 1.7.3 2.1-.4l6-10.4c.4-.7.2-1.5-.5-1.9-6.2-4.2-13.9-8.1-24.3-8.1-10.3 0-19.8 2.8-26.6 9.7-6.8 6.8-9.9 14.9-9.9 24.5 0 11.6 4.8 19 10 24.2 5.2 5.2 14.6 9.6 26.6 9.6 10.8 0 18.8-4.5 24.3-8.1.6-.4.8-1.3.4-1.9l-5.9-10.3c-.4-.7-1.4-1-2.1-.5-3.9 2.8-9.5 5.2-15.8 5.2-7.1 0-20-4-20-18.4 0-14.4 12.8-18.4 19.9-18.4zm294.2 38c-5.8 0-11.3-1.4-15.3-4.6-4-3.2-5.9-6.5-5.9-10.4H859c.8 0 1.5-.7 1.5-1.5-.3-15.4-5.3-22.6-10.1-27.5-5-5-13.5-9.6-24.7-9.6s-19.8 4.1-25.7 9.9c-5.8 5.8-10.2 13.2-10.2 24.4s4.5 18.6 10 24.1 14.5 9.6 27.4 9.6c12.2 0 22.5-4.3 29.9-12.8.6-.7.4-1.8-.4-2.2l-10.3-6c-.5-.3-1.2-.3-1.7.2-5.1 4.8-11.6 6.4-16.3 6.4zM825.6 66c8.3 0 15.9 5 17.1 14h-34.9c2.3-9.7 9.5-14 17.8-14z">
                </path>
                </svg>
                </a>
                </th>
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