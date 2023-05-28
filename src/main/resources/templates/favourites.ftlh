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
            <a class="list-group-item list-group-item-action list-group-item-light p-3" id="search_parameters">
                Збережені параметри пошуку
            </a>
        </div>
    </div>
    <!-- Page content wrapper-->
    <div id="page-content-wrapper">
        <!-- Top navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
            <div class="container-fluid">
                <button class="btn btn btn-outline-primary" id="sidebarToggle">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         class="bi bi-skip-backward-fill" viewBox="0 0 16 16">
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
            <h1 class="d-flex justify-content-center mt-4">Збережені параметри пошукових запитів</h1>
            <div id="main_content">
                <#if favourites.favouritesMap?size == 0>
                    <h3 style="text-align: center">
                        Немає жодних збережених параметрів пошукових запитів!
                    </h3>`
                <#else>
                    <div class="accordion accordion-flush" id="favourites">
                        <#assign counter = 0>
                        <#list favourites.favouritesMap as key, value>
                            <#assign counter = counter + 1>
                            <#assign flush_collapse = "flush-collapse" + counter>
                            <#assign flush_collapseId = "#" + flush_collapse>
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="${flush_collapseId}" aria-expanded="false"
                                            aria-controls="flush-collapseOne">
                                        <strong>
                                            <#list key as routeCities>
                                                ${routeCities.fromCity}-${routeCities.toCity}
                                                <#if !routeCities?is_last>
                                                    &nbsp;&nbsp;&nbsp;--->&nbsp;&nbsp;&nbsp;
                                                </#if>
                                            </#list>
                                        </strong>
                                    </button>
                                </h2>
                                <div id="${flush_collapse}" class="accordion-collapse collapse"
                                     data-bs-parent="#favourites">
                                    <div class="accordion-body" id="${flush_collapse}">
                                        <#list value as parameters>
                                            <div data-id="${parameters.favouriteId}">
                                                Проміжок дат пошуку перельотів:
                                                ${parameters.startTripDate}
                                                &nbsp;&nbsp;-&nbsp;&nbsp; ${parameters.endTripDate}
                                                <br>
                                                Дні перебування в кожному місці:
                                                ${parameters.daysInPointMin} - ${parameters.daysInPointMax}
                                                <br>
                                                Загальна тривалість подорожі, днів:
                                                ${parameters.totalDaysMin} - ${parameters.totalDaysMax}
                                                <br>
                                                Загальна вартість подорожі, USD
                                                ${parameters.totalCostMin} - ${parameters.totalCostMax}
                                                <br>
                                                <button class="btn btn-success btn-sm searching-redirect-button"
                                                        title="Перейти до пошуку з вказаними пошуковими параметрами">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-forward-fill" viewBox="0 2 16 16">
                                                        <path d="m9.77 12.11 4.012-2.953a.647.647 0 0 0 0-1.114L9.771 5.09a.644.644 0 0 0-.971.557V6.65H2v3.9h6.8v1.003c0 .505.545.808.97.557z"/>
                                                    </svg>
                                                    <a href="/flight?favouriteId=${parameters.favouriteId}"
                                                       style="color: #ffffff; text-decoration: none;">
                                                        Перейти до пошуку
                                                    </a>
                                                </button>
                                                &nbsp;&nbsp;&nbsp;&nbsp;
                                                <button class="btn btn-danger btn-sm searching-delete-button"
                                                        title="Видалити зі збережених пошукових запитів">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3-fill" viewBox="0 2 16 16">
                                                        <path d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5Zm-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5ZM4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06Zm6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528ZM8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5Z"/>
                                                    </svg>
                                                    Видалити
                                                </button>
                                                <#if !parameters?is_last>
                                                    <br>
                                                    <br>
                                                </#if>
                                            </div>
                                        </#list>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</div>
<!-- JS -->
<script src="js/libraries/bootstrap.bundle.min.js"></script>
<script src="js/libraries/jquery-3.6.4.min.js"></script>
<script src="js/favourites_page.js"></script>
<script src="js/hide_show_sidebar.js"></script>
<script src="js/display_login_block.js"></script>
</body>
</html>
