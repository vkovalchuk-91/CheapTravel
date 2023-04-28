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

$(function () {
    showCommentsBlock();

    $('#loadMoreButton').on('click', function () {
        showCommentsBlock();
    });

    $('#submitButton').on('click', function () {
        addComment();
    });
});

function showCommentsBlock() {
    let pageNumber = $('#loadMoreButton').data('page');

    $.ajax({
        url: '/comments?page=' + pageNumber,
        success: function (comments) {
            displayComments(comments);
            hideOrGetLoadMoreButtonOnShowCommentsAction(pageNumber);
        }
    })
}

function displayComments(comments) {
    let html = '';

    if (comments.length === 0) {
        html = 'No comments yet!'
    } else {
        comments.forEach(function (comment) {
            html += `
                    <div data-id="${comment.id}"
                    <li class="list-group-item d-flex justify-content-between align-items-start">
                    <div class="ms-2 me-auto">
                        <div class="fw-bold">${comment.name}</div>
                        ${comment.comment}
                    </div>
                    <span class="badge bg-primary rounded-pill">${comment.rating}</span>
                    </li>
                    </div>
                `;
        })
    }

    $('#viewer').html(html);
}

function hideOrGetLoadMoreButtonOnShowCommentsAction(pageNumber) {
    const $button = $('#loadMoreButton');
    $.ajax({
        url: '/pages',
        success: function (numberOfPages) {
            if (pageNumber === numberOfPages) {
                $button.attr('style', 'display: none;');
            } else if (0 === numberOfPages) {
                $button.attr('style', 'display: none;');
            } else {
                $button.removeAttr('style')
                $button.data('page', pageNumber + 1);
            }
        }
    })
}

function addComment() {
    const $commentName = $('#name')
    const $commentRating = $('#rating')
    const $commentComment = $('#comment')

    $.ajax({
        url: '/comments',
        method: 'POST',
        data: {
            name: $commentName.val(),
            rating: $commentRating.val(),
            comment: $commentComment.val()
        },
        success: function (comments) {
            displayComments(comments);
            hideOrGetLoadMoreButtonOnAddAction();
        }
    });
}

function hideOrGetLoadMoreButtonOnAddAction() {
    $.ajax({
        url: '/pages',
        success: function (numberOfPages) {
            if (numberOfPages === 1) {
                $('#loadMoreButton').attr('style', 'display: none;');
            }
        }
    })
}

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
            target: '#slider',
            values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
            range: true,
            set:  [3, 5],
            tooltip: false,
            onChange: function (vals) {
                console.log(vals);
            }
        });

        var slider2 = new rSlider({
            target: '#slider2',
            values: {min: 1, max: 60},
            step: 1,
            range: true,
            set:  [6, 14],
            scale: false,
            labels: false,
            onChange: function (vals) {
                console.log(vals);
            }
        });

        var slider3 = new rSlider({
            target: '#slider3',
            values: {min: 1, max: 500},
            step: 1,
            range: true,
            set:  [1, 100],
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
$('#daterange').daterangepicker({
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
        "format": "DD/MM/YYYY",
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
}, function(start, end) {
    console.log(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
});