<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Cheap Euro trip</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
    <!-- CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/dselect.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<div class="d-flex" id="wrapper">
    <!-- Sidebar-->
    <div class="border-end bg-white" id="sidebar-wrapper">
        <div class="sidebar-heading border-bottom bg-light">Cheap Euro Trip</div>
        <div class="list-group list-group-flush">
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="about_program">
                Інформація про програму
            </a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="about_author">
                Інформація про автора
            </a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="reports">
                Статистичні звіти
            </a>
        </div>
    </div>
    <!-- Page content wrapper-->
    <div id="page-content-wrapper">
        <!-- Top navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
            <div class="container-fluid">
                <button class="btn btn btn-outline-primary" id="sidebarToggle">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-skip-backward-fill" viewBox="0 0 16 16">
                        <path d="M.5 3.5A.5.5 0 0 0 0 4v8a.5.5 0 0 0 1 0V8.753l6.267 3.636c.54.313 1.233-.066 1.233-.697v-2.94l6.267 3.636c.54.314 1.233-.065 1.233-.696V4.308c0-.63-.693-1.01-1.233-.696L8.5 7.248v-2.94c0-.63-.692-1.01-1.233-.696L1 7.248V4a.5.5 0 0 0-.5-.5z"></path>
                    </svg>
                </button>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mt-2 mt-lg-0">
                        <#if role=="ADMIN">
                            <li class="nav-item active"><a class="nav-link" href="/admin">Панель адміністратора</a></li>
                        </#if>
                        <#if role!="NotAuthenticated">
                            <li class="nav-item active"><a class="nav-link" href="/flight">Пошук авіаквитків</a></li>
                        </#if>
                        <li class="nav-item"><a class="nav-link" href="/">Про проект</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbar_dropdown" href="#" role="button"
                               data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
                            <div class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <div id="navbar_first" style="text-align: center;"></div>
                                <div class="dropdown-divider"></div>
                                <div id="navbar_second" style="text-align: center;"></div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Page content-->
        <div class="container-fluid" id="information_block">
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

                <#if role=="NotAuthenticated">
                    <p style="color: red">Для використання програми потрібно спочатку <a href="/registration">зареєструватися</a>
                        або <a href="/login">увійти</a> на сайт!
                    </p>
                <#else>
                    <p><a href="/flight">Натисніть</a> щоб розпочати використання програми!
                    </p>
                </#if>
            </div>
        </div>

        <div class="container-fluid" id="reports_block" style="display: none">
            <h1 class="mt-4">
                Статистика кількості та вартості рейсів з/до міст, які доступні в програмі
            </h1>
            <div id="main_content" class="mb-4">
                <p>
                    Інформація, щодо кількості міст з якими існує авіасполучення з кожного міста, що доступне для пошуку в додатку та середньою вартістю квитків з/до цього міста.
                </p>
                <button class="btn btn btn-outline-primary" onclick="window.location.href='/report1'">
                    Завантажити звіт
                </button>
            </div>

            <hr>

            <h1 class="mt-4">
                Статистика вартості польотів з обраного міста
            </h1>
            <div id="main_content" class="mb-4">
                <p>
                    Мінімальна, середня та найбільша вартість авіарейсів з обраного міста. Перед завантаженням потрібно обрати місто, по якому ви хочете отримати звіт.
                </p>

                <div class="row ms-1 col-2 mb-4">
                    <select class="form-select dselect" required id="report_city" name="from">
                        <#list cashedCitiesInOperation as city>
                            <option value="${city.getId()}">
                                ${city.getName()}
                            </option>
                        </#list>
                    </select>
                </div>

                <button id="report_2_download_button" class="btn btn btn-outline-primary">
                    Завантажити звіт
                </button>
            </div>

            <hr>

            <h1 class="mt-4">
                Топ-10 маршрутів з найнижчими цінами
            </h1>
            <div id="main_content">
                <p>
                    Топ-10 маршрутів за якими ціни на авіаперельоти є найнижчими.
                </p>
                <button class="btn btn btn-outline-primary" onclick="window.location.href='/report3'">
                    Завантажити звіт
                </button>
            </div>

        </div>

    </div>
</div>
<!-- JS -->
<script src="js/libraries/bootstrap.bundle.min.js"></script>
<script src="js/libraries/jquery-3.6.4.min.js"></script>
<script src="js/libraries/dselect.js"></script>
<script src="js/about_page.js"></script>
<script src="js/hide_show_sidebar.js"></script>
<script src="js/display_login_block.js"></script>
</body>
</html>
