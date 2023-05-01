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
        </div>
    </div>
    <!-- Page content wrapper-->
    <div id="page-content-wrapper">
        <!-- Top navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
            <div class="container-fluid">
                <button class="btn btn btn-outline-primary" id="sidebarToggle"><<</button>
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
                    В основі програми лежить <a href="https://developers.skyscanner.net/docs/intro">SkyScanner API</a>!
                    Програма здійснює обробку та накопичення даних щодо напрямків, дат та цін на авіаперельоти та за
                    допомогою гнучких фільтрів дає можливість знайти найдешевші варіанти подорожі, що підходять саме
                    вам!
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
    </div>
</div>
<!-- JS -->
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/jquery-3.6.4.min.js"></script>
<script src="js/about_page.js"></script>

</body>
</html>
