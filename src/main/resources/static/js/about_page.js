// display information block
$(function () {
    const $about_program = $('#about_program');
    const $about_author = $('#about_author');
    const $reports = $('#reports');
    let information_block = document.getElementById("information_block");

    $(document).ready(function () {
        let data_auth = $('#information_block').attr('data-auth');
        if (data_auth === "NotAuthenticated") {
            displayEntranceBlock(information_block);
        } else {
            displayBlockAboutProgram(information_block);
        }
    })

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

// display Entrance Block
function displayEntranceBlock(information_block) {
    $('#reports_block').attr('style', 'display: none;');
    $('#information_block').removeAttr('style');
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Cheap Euro Trip - Web-додаток пошуку дешевих авіаквитків</h1>
                <h3 style="color: red">Для використання додатку потрібно спочатку <a href="/registration">зареєструватися</a>
                        або <a href="/login">увійти</a> на сайт!
                </h3>
        `;
}

// display Block About Program
function displayBlockAboutProgram(information_block) {
    $('#reports_block').attr('style', 'display: none;');
    $('#information_block').removeAttr('style');
    information_block.innerHTML = `
            <h1 class="d-flex justify-content-center mt-4">Cheap Euro Trip - додаток для пошуку дешевих авіаквитків</h1>
            <div id="main_content">
                <p>
    Cheap Euro Trip - Web-додаток для пошуку авіаквитків в основі якого лежить <a
        href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>! Програма здійснює обробку
    та накопичення даних щодо напрямків, дат та цін на авіаперельоти, кешує отримані дані в базі даних і потім з заданою
    частотою оновлює їх, далі за допомогою гнучких фільтрів дає можливість знайти найдешевші варіанти подорожі, також
    користувачі з роллю адміністратора мають можливість додаткових налаштувань! Окрім backend частини проекту написаної
    безпосередньо на Java core, в проекті використано такі технології як Spring Boot, Spring Web, Spring Security,
    Spring Quartz, Spring Data JPA, JDBC, PostgreSQL, Apache POI (експорт звітів в excel), Freemarker Templates, HTML,
    CSS, Bootsrap Templates, JavaScrit, jQuery, AJAX queries, JSON(Jackson).
</p>
<img src="assets/about_program/image001.jpg" alt="DB Schema">
<p>
    Для зберігання інформації, щодо локацій, які доступні в <a href="https://developers.skyscanner.net/docs/intro">SkyScanner
    API</a> (аеропорти/міста/країни), міст, які доступні
    для пошуку, наявних маршрутів, користувачів та їх збережених пошуків, валюти відображення ціни використовується СКБД
    PostgreSQL. Вивід інформації в Web здійснюється за допомогою Freemarker templates.
</p>
<p>
    Основним джерелом вхідної інформації є <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>.
    Основні запити здійснюється java-програмою за допомогою GET
    та POST запитів, відповідно до <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>, отримані
    JSON відповіді за допомогою Jackson десеріалізуються у java-об’єкти. Потім необхідна частина інформації з них
    зберігається до БД. Оскільки <a
        href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a> дає можливість
    здійснення обмеженої кількості запитів, тому кількість міст, між якими буде можливо робити пошук рейсів та кількісті
    місяців на період яких буде здійснюватися можливість пошуку є обмеженою та задається адміністратором в ручному
    режимі. Запити по <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a> здійснюються постійно з
    врахуванням встановлених обмежень (110 запитів в хвилину), та отримана інформація зберігатися в БД та
    використовуватися як snapshot даних для використання в додатку. Процес
    оновлення між встановленими містами здійснюється циклічно та безперервно. Для цього процесу використовується Spring
    Quartz. Саме через встановлені обмеження в кількості запитів, використовується кешування інформації, а не отримання
    даних напряму в online-режимі (оскільки в такому разі при появі навіть незначної кількості запитів від користувачів
    швидко вичерпав би ліміт запитів по <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a> і
    додаток на деякий час перестав би працювати). Схему даних БД, що використовується в додатку наведено нижче.
</p>

<p>
    При первинному заході в додаток користувач потрапляє на сторінку де йому запропоновано зареєструватися або
    авторизуватися в додатку. Також всі відвідувачі web-додатку (навіть неавторизовані), мають можливість:
<ul>
    <li>Переглянути інформацію, щодо особливостей реалізації та функціонування додатку</li>
    <li>Переглянути інформацію, щодо автора додатку</li>
    <li>Завантажити статистичні excel-звіти кількісних та вартісних показників перельотів (зокрема статистику кількості
        та вартості рейсів з/до міст, які доступні в додатку, статистику вартості польотів з обраного міста, топ-100
        маршрутів з найнижчими цінами)
    </li>
</ul>
</p>
<p>
    Зареєстровані користувачі отримують можливість використовувати основний функціонал додатку – пошук авіаквитків
    згідно заданих параметрів. Для його отримання потрібно авторизуватися в додатку. Web-додаток дає можливість
    здійснювати пошук не тільки по містам, але й по країнам, що значно розширює можливості пошуку.
</p>
<p>
    При реєстрації потрібно вказати унікальне ім’я користувача, електронну пошту та двічі ввести пароль та його
    підтвердження.
</p>
<img src="assets/about_program/image002.jpg" alt="Registration page">
<p>
    Усі поля обов’язкові до заповнення та в разі відсутності інформації в якомусь з полів, таке поле буде провалідоване
    та виділене червоним після натискання кнопки реєстрації. У разі введення імені користувача, який вже існує в системі
    чи неідентичних паролів, користувачу буде відображене відповідне спливаюче повідомлення.
</p>
<img src="assets/about_program/image003.png" alt="Popup message">
<p>
    Зареєстровані користувачі мають можливість авторизуватися на сторінці входу за допомогою імені користувача та
    пароля, що були вказані при реєстрації.
</p>
<img src="assets/about_program/image004.jpg" alt="Login page">
<p>
    В разі введення даних, яких немає серед збережених в системі чи введення даних користувача, якого було заблоковано
    адміністратором, користувачу буде відображене відповідне спливаюче повідомлення. Усі паролі користувачів
    зберігаються в базі даних в зашифрованому вигляді з використанням шифрування BCrypt.
</p>

<p>
    Для каркасу основних сторінок додатку використано шаблон <a
        href="https://github.com/StartBootstrap/startbootstrap-simple-sidebar">StartBootstrap Simple Sidebar</a>. В
    лівій частині додатку розташовується бокове меню. На сторінці пошуку авіаквитків бокове меню містить фільтри пошуку
    авіаквитків, що включає в себе такі можливості:
<ul>
    <li>Увімкнути сортування по зростанню ціни</li>
    <li>Вибір локації початку подорожі серед доступних</li>
    <li>Вибір локації кінця подорожі серед доступних</li>
    <li>Вибір кількості проміжних перельотів (від 2 до 4)</li>
    <li>Вибір дати початку й закінчення між якими має відбутися подорож</li>
    <li>Вибір мінімальної та максимальної кількості днів перебування в кожній локації</li>
    <li>Вибір мінімальної та максимальної кількості днів, яка має тривати вся подорож</li>
    <li>Вибір мінімальної та максимальної вартості всієї подорожі</li>
</ul>
</p>
<img src="assets/about_program/image005.jpg" alt="Search panel">
<p>
    Для зручного використання фільтрів були використані такі бібліотеки:
</p>
<p>
    <a href="https://github.com/jarstone/dselect">Dselect</a> (можливість здійснення пошуку по випадаючим спискам)
</p>
<p>
    <a href="https://github.com/dangrossman/daterangepicker">Date Range Picker</a> (зручний вибір проміжку дат та
    можливість використання попередньо налаштованих проміжків дат)
</p>
<p>
    <a href="https://github.com/slawomir-zaziablo/range-slider">Range slider</a> (зручні слайдери числового проміжку)
</p>
<p>
    Перед здійсненням пошуку виконується валідація наявності даних в полях введення точок початку/кінця перельотів. В
    разі коректного заповнення усіх даних, при натисканні кнопки пошуку, справа від блоку фільтрів виводяться результати
    пошуку.
</p>
<p>
    В заголовку результатів пошуку наявна кнопка додавання/видалення пошукового запиту з обраних запитів.
</p>
<img src="assets/about_program/image006.jpg" alt="Not in favourite">
<br>
<img src="assets/about_program/image007.jpg" alt="In favourite">
<p>
    Збережені пошуки можна переглянути в меню користувача, натиснувши на кномку з іменем користувача в правому верхньому
    куті додатку.
</p>
<img src="assets/about_program/image008.png" alt="User drop-down list">
<p>
    Пошукові запити згруповано за точками переміщення, можливість обрання наявних деталей з’являється при натисканні на
    певні групу, далі можливо перейти до відповідного пошуку авіаперельотів або його видалити з обраних.
</p>
<img src="assets/about_program/image009.png" alt="Favourite list">
<p>
    Оскільки результати пошуку сформовані на основі кешованих індикативних цін, що надаються <a
        href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>, актуальні
    на даний момент ціни можна отримати перейшовши за посиланням під кожним варіантом подорожі! Для відображення
    підказок при наведенні курсора на значок SkyScanner використано бібліотеку <a
        href="http://ameyraut.com/display-tooltip-without-javascript-or-jquery-pure-css/">Pure CSS 3 Tooltip without
    Javascript / Jquery</a>.
</p>
<img src="assets/about_program/image010.jpg" alt="SkyScanner link">
<p>
    Також для зручності результати пошуку відображаються по 10 одиниць на 1 сторінці, а внизу результатів пошуку
    присутні кнопки пагінації.
</p>
<img src="assets/about_program/image011.png" alt="Pagination">
<p>
    Для тестування можливостей додатку з роллю адміністратора, потрібно увійти в систему з іменем користувача «admin» та
    паролем «1» чи будь-яким іншим аккаунтом з призначеною роллю адміністратора. В подальшому можливо присвоїти роль
    адміністратора будь-якому іншому обліковому запису. У додаток до вищеописаного функціоналу, користувачі з
    призначеною роллю адміністратора можуть:
<ul>
    <li>Обирати перелік міст, інформація про перельоти з/в які буде кешуватися та використовуватися в подальшому при
        обранні аеропорту чи міста
    </li>
    <li>Змінювати абревіатури міст, що використовуються для пошуку в системі SkyScanner.com.ua (для коректного
        використання посилань на сторінки пошуку перельотів безпосередньо на сайті SkyScanner.com.ua)
    </li>
    <li>Редагувати статус користувачів та їх ролі (надавати зареєстрованим користувачам роль «USER» або «ADMIN», а також
        можливість заблокувати деяким користувачам вхід та використання додатку)
    </li>
    <li>Можливість оновити перелік локацій</li>
    <li>Можливість оновити доступність польотів (відповідно до переліку обраних міст)</li>
    <li>Можливість змінити кількість місяців на період яких буде здійснюватися кешування цін для здійснення пошуку (за
        змовчуванням 6 місяців)
    </li>
</ul>
</p>
<img src="assets/about_program/image012.png" alt="Choose cities in operation">
<br>
<br>
<img src="assets/about_program/image013.png" alt="Set the abbreviations">
<br>
<br>
<img src="assets/about_program/image014.jpg" alt="User options">
<br>
<br>
<img src="assets/about_program/image015.jpg" alt="Other options">
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