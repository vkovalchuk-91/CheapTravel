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
            <h1 class="d-flex justify-content-center mt-4">Cheap Euro Trip - програма пошуку дешевих авіаквитків</h1>
            <div id="main_content">
                <p>
                    Cheap Euro Trip - Web-додаток для пошуку авіаквитків в основі якого лежить
                    <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>!
                    Програма здійснює обробку та накопичення даних щодо напрямків, дат та цін на авіаперельоти, кешує
                    отримані дані в базі даних і потім з заданою частотою оновлює їх, далі за допомогою гнучких
                    фільтрів дає можливість знайти найдешевші варіанти подорожі, також користувачі з роллю
                    адмыністратора мають можливість гнучких налаштувань! Окрім backend частини проекту написаної
                    безпосередньо на Java, в проекті використано такі технології як Spring Boot, Spring Web,
                    Spring Security, Spring Quartz, Spring Data JPA, JDBC, PostgreSQL, Apache POI (експорт звітів в
                    excel), Freemarker Templates, HTML, CSS, Bootstrap Templates, JavaScript, jQuery, AJAX queries,
                    JSON(Jackson).
                </p>
        `;
}

// display Block About Author
function displayBlockAboutAuthor(information_block) {
    $('#reports_block').attr('style', 'display: none;');
    $('#information_block').removeAttr('style');
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Інформація про автора програми</h1>
            <div id="main_content">
                <br>
                <br>
                <h3 class="mb-5">Ковальчук Володимир</h3>
                <p>
                <img src="assets/logo/linkedin.png" width="30" height="30" alt="LinkedIn logo">
                <a href="https://www.linkedin.com/in/volodymyr-kovalchuk-5b211010a/" target="_blank">LinkedIn</a>                     
                </p>
                <p>
                <img src="assets/logo/telegram.png" width="30" height="30" alt="Telegram logo">
                <a href="https://t.me/slengpack" target="_blank">Telegram</a>                     
                </p>
                <p>
                <img src="assets/logo/email.png" width="30" height="30" alt="E-mail logo">
                <a href="mailto:slengpack@gmail.com" target="_blank">E-mail</a>                     
                </p>
                <p>
                <img src="assets/logo/github.png" width="30" height="30" alt="GitHub logo">
                <a href="https://github.com/vkovalchuk-91" target="_blank">GitHub</a>                     
                </p>
                <p>
                <img src="assets/logo/facebook.png" width="30" height="30" alt="Facebook logo">
                <a href="https://www.facebook.com/slengpack" target="_blank">Facebook</a>                     
                </p>
                <p>
                <img src="assets/logo/instagram.png" width="30" height="30" alt="Instagram logo">
                <a href="https://www.instagram.com/slengpack91/" target="_blank">Instagram</a>                     
                </p>
                <p>
                <img src="assets/logo/telephone.png" width="30" height="30" alt="Telephone logo">
                +380 (96) 96 262 51                   
                </p>
                <p>
                <img src="assets/logo/location.png" width="30" height="30" alt="Location logo">
                Київ, Україна                 
                </p>
                <p class="mt-5"><h6>GeekHub 2023</h6></p>
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