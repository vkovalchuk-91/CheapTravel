// display information block
$(function () {
    const $about_program = $('#about_program');
    const $about_author = $('#about_author');
    const $reports = $('#reports');
    let information_block = document.getElementById("information_block");

    $($about_program).on('click', function () {
        displayBlockAboutProgram(information_block);
    })

    $($about_author).on('click', function () {
        displayBlockAboutAuthor(information_block);
    })

    $($reports).on('click', function () {
        displayBlockReports(information_block);
    })
})

// display Block About Program
function displayBlockAboutProgram(information_block) {
    $('#reports_block').attr('style', 'display: none;');
    $('#information_block').removeAttr('style');
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Інформація про деталі роботи програми</h1>
            <div id="main_content">
                <p>
                    * В подальшому буде заповнено інформацією про деталі роботи програми!
                </p>
            </div>
        `;
}

// display Block About Author
function displayBlockAboutAuthor(information_block) {
    $('#reports_block').attr('style', 'display: none;');
    $('#information_block').removeAttr('style');
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

// display Reports Block
function displayBlockReports() {
    $('#information_block').attr('style', 'display: none;');
    $('#reports_block').removeAttr('style');
}

// searchable select options
for (const el of document.querySelectorAll('.dselect')) {
    dselect(el, {
        search: true
    })
}

// report 2 download button
$(function () {
    $('#report_2_download_button').on('click', function () {
        let cityId = $('#report_city').val().replace(/ /g, "");
        window.location.href='/report2?cityId=' + cityId;
    })
})