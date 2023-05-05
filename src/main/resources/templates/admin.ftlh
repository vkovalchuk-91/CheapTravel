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
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="change_cities_in_operation">
                Змінити перелік міст, по яким здійснюється пошук
            </a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="change_cashing_months">
                Змінити кількість місяців, на які кешуються ціни
            </a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="update_locations">
                Оновити перелік локацій SkyScanner API
            </a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="update_routes">
                Оновити доступність маршрутів
            </a>
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="edit_users">
                Редагувати статус користувачів та їх ролі
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

        </div>

        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="save_cities" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-exclamation-triangle-fill" viewBox="0 0 16 16">
                        <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                    </svg>
                    <strong class="me-auto" id="alert_header">

                    </strong>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body" id="alert_message">

                </div>
            </div>
        </div>

    </div>
</div>
<!-- JS -->
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/jquery-3.6.4.min.js"></script>
<script src="js/admin_page.js"></script>
<script src="js/hide_show_sidebar.js"></script>
<script src="js/display_login_block.js"></script>
</body>
</html>
