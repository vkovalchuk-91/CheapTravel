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
    <link href="css/rSlider.min.css" rel="stylesheet">
    <link href="css/dselect.css" rel="stylesheet">
    <link href="css/daterangepicker.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<div class="d-flex" id="wrapper">
    <!-- Sidebar-->
    <div class="border-end bg-white" id="sidebar-wrapper">
        <div class="sidebar-heading border-bottom bg-light">Cheap Euro Trip</div>
        <div class="list-group list-group-flush">


            <!--Input form-->
            <form class="row g-3 needs-validation mt-1" novalidate>


                <div class="d-flex justify-content-center">
                    <button class="btn btn btn-outline-primary"
                            id="searchButton"
                            data-flights="${parameters.flightNumber}"
                            data-show-results="${parameters.showResults}">
                        Шукати перельоти
                    </button>
                </div>

                <#--                devider-->
                <hr style="width: 98%;">

                <#--                title-->
                <h6 style="color: #0d6efd; text-align: center;">&nbsp;ПАРАМЕТРИ ПОШУКУ ПЕРЕЛЬОТІВ</h6>

                <div class="row ms-3 col-12">
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" role="switch" id="low_price_switcher" checked>
                        <label class="form-check-label" for="low_price_switcher">Спершу найнижчі ціни</label>
                    </div>
                </div>

                <#--                point 1-->
                <div id="flight-1">
                    <#--                start point 1-->
                    <div class="row ms-1 col-11">
                        <label for="start_point_1" class="col-sm-12 col-form-label">Переліт 1</label>
                        <select class="form-select dselect" required id="start_point_1" name="from">
                            <#if parameters.startPointsId1 == 0>
                            <option selected disabled hidden value="">З</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.startPointsId1 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть початкову точку вильоту!
                        </div>
                    </div>

                    <#--                end point 1-->
                    <div class="row ms-1 mb-3 col-11">
                        <label for="end_point_1" class="col-sm-12 col-form-label"></label>
                        <select class="form-select dselect" required id="end_point_1" name="to">
                            <#if parameters.endPointsId1 == 0>
                            <option selected disabled hidden value="">До</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.endPointsId1 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть кінцеву точку польоту!
                        </div>
                    </div>

                    <#--                devider-->
                    <hr>
                </div>

                <#--                point 2-->
                <div id="flight-2">
                    <#--                start point 2-->
                    <div class="row ms-1 col-11">
                        <label for="start_point_2" class="col-sm-12 col-form-label">Переліт 2</label>
                        <select class="form-select dselect" required id="start_point_2" name="from">
                            <#if parameters.startPointsId2 == 0>
                            <option selected disabled hidden value="">З</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.startPointsId2 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть початкову точку вильоту!
                        </div>
                    </div>

                    <#--                end point 2-->
                    <div class="row ms-1 mb-3 col-11">
                        <label for="end_point_2" class="col-sm-12 col-form-label"></label>
                        <select class="form-select dselect" required id="end_point_2" name="to">
                            <#if parameters.endPointsId2 == 0>
                            <option selected disabled hidden value="">До</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.endPointsId2 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть кінцеву точку польоту!
                        </div>
                    </div>

                    <#--                devider-->
                    <hr>
                </div>

                <#--                point 3-->
                <div id="flight-3" <#if parameters.flightNumber < 3>style="display: none"</#if>>
                    <#--                start point 3-->
                    <div class="row ms-1 col-11">
                        <label for="start_point_3" class="col-sm-12 col-form-label">Переліт 3</label>
                        <select class="form-select dselect" required id="start_point_3" name="from">
                            <#if parameters.startPointsId3 == 0>
                            <option selected disabled hidden value="">З</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.startPointsId3 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть початкову точку вильоту!
                        </div>
                    </div>

                    <#--                end point 3-->
                    <div class="row ms-1 mb-3 col-11">
                        <label for="end_point_3" class="col-sm-12 col-form-label"></label>
                        <select class="form-select dselect" required id="end_point_3" name="to">
                            <#if parameters.endPointsId3 == 0>
                            <option selected disabled hidden value="">До</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.endPointsId3 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть кінцеву точку польоту!
                        </div>
                    </div>

                    <#--                devider-->
                    <hr>
                </div>

                <#--                point 4-->
                <div id="flight-4" <#if parameters.flightNumber < 4>style="display: none"</#if>>
                    <#--                start point 4-->
                    <div class="row ms-1 col-11">
                        <label for="start_point_4" class="col-sm-12 col-form-label">Переліт 4</label>
                        <select class="form-select dselect" required id="start_point_4" name="from">
                            <#if parameters.startPointsId4 == 0>
                            <option selected disabled hidden value="">З</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.startPointsId4 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть початкову точку вильоту!
                        </div>
                    </div>

                    <#--                end point 4-->
                    <div class="row ms-1 mb-3 col-11">
                        <label for="end_point_4" class="col-sm-12 col-form-label"></label>
                        <select class="form-select dselect" required id="end_point_4" name="to">
                            <#if parameters.endPointsId4 == 0>
                            <option selected disabled hidden value="">До</option>
                            </#if>
                            <#list cashedLocationsInOperation as location>
                                <option <#if parameters.endPointsId4 == location.getId()>selected </#if>
                                        value="${location.getId()}">
                                    <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                                </option>
                            </#list>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть кінцеву точку польоту!
                        </div>
                    </div>

                    <#--                devider-->
                    <hr>
                </div>

                <#--                stub point (for the post request does not erase the entered data)-->
                <div id="flight-7" style="display: none">
                    <#--                stub point-->
                    <div class="row ms-1 col-11">
                        <label for="start_point_7" class="col-sm-12 col-form-label"></label>
                        <select class="form-select" required id="start_point_7" name="from">
                            <option selected disabled hidden value="">З</option>
                        </select>
                        <div class="invalid-feedback">
                            Оберіть кінцеву точку польоту!
                        </div>
                    </div>
                </div>

                <#--                remove/add flight button-->
                <div class="d-flex justify-content-center">
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline-primary" id="removeFlightButton"
                                <#if parameters.flightNumber == 2>disabled</#if>>
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                                 class="bi bi-airplane-engines" viewBox="0 0 16 16">
                                <path d="M8 0c-.787 0-1.292.592-1.572 1.151A4.347 4.347 0 0 0 6 3v3.691l-2 1V7.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.191l-1.17.585A1.5 1.5 0 0 0 0 10.618V12a.5.5 0 0 0 .582.493l1.631-.272.313.937a.5.5 0 0 0 .948 0l.405-1.214 2.21-.369.375 2.253-1.318 1.318A.5.5 0 0 0 5.5 16h5a.5.5 0 0 0 .354-.854l-1.318-1.318.375-2.253 2.21.369.405 1.214a.5.5 0 0 0 .948 0l.313-.937 1.63.272A.5.5 0 0 0 16 12v-1.382a1.5 1.5 0 0 0-.83-1.342L14 8.691V7.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v.191l-2-1V3c0-.568-.14-1.271-.428-1.849C9.292.591 8.787 0 8 0ZM7 3c0-.432.11-.979.322-1.401C7.542 1.159 7.787 1 8 1c.213 0 .458.158.678.599C8.889 2.02 9 2.569 9 3v4a.5.5 0 0 0 .276.447l5.448 2.724a.5.5 0 0 1 .276.447v.792l-5.418-.903a.5.5 0 0 0-.575.41l-.5 3a.5.5 0 0 0 .14.437l.646.646H6.707l.647-.646a.5.5 0 0 0 .14-.436l-.5-3a.5.5 0 0 0-.576-.411L1 11.41v-.792a.5.5 0 0 1 .276-.447l5.448-2.724A.5.5 0 0 0 7 7V3Z"></path>
                            </svg>
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                                 class="bi bi-patch-minus" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M5.5 8a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1H6a.5.5 0 0 1-.5-.5z"/>
                                <path d="m10.273 2.513-.921-.944.715-.698.622.637.89-.011a2.89 2.89 0 0 1 2.924 2.924l-.01.89.636.622a2.89 2.89 0 0 1 0 4.134l-.637.622.011.89a2.89 2.89 0 0 1-2.924 2.924l-.89-.01-.622.636a2.89 2.89 0 0 1-4.134 0l-.622-.637-.89.011a2.89 2.89 0 0 1-2.924-2.924l.01-.89-.636-.622a2.89 2.89 0 0 1 0-4.134l.637-.622-.011-.89a2.89 2.89 0 0 1 2.924-2.924l.89.01.622-.636a2.89 2.89 0 0 1 4.134 0l-.715.698a1.89 1.89 0 0 0-2.704 0l-.92.944-1.32-.016a1.89 1.89 0 0 0-1.911 1.912l.016 1.318-.944.921a1.89 1.89 0 0 0 0 2.704l.944.92-.016 1.32a1.89 1.89 0 0 0 1.912 1.911l1.318-.016.921.944a1.89 1.89 0 0 0 2.704 0l.92-.944 1.32.016a1.89 1.89 0 0 0 1.911-1.912l-.016-1.318.944-.921a1.89 1.89 0 0 0 0-2.704l-.944-.92.016-1.32a1.89 1.89 0 0 0-1.912-1.911l-1.318.016z"/>
                            </svg>
                            &nbsp;&nbsp;&nbsp;
                            <span class="visually-hidden">Button</span>
                        </button>

                        <button type="button" class="btn btn-outline-primary" id="addFlightButton"
                                <#if parameters.flightNumber == 4>disabled</#if>>
                            &nbsp;&nbsp;&nbsp;
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                                 class="bi bi-patch-plus" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M8 5.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V10a.5.5 0 0 1-1 0V8.5H6a.5.5 0 0 1 0-1h1.5V6a.5.5 0 0 1 .5-.5z"/>
                                <path d="m10.273 2.513-.921-.944.715-.698.622.637.89-.011a2.89 2.89 0 0 1 2.924 2.924l-.01.89.636.622a2.89 2.89 0 0 1 0 4.134l-.637.622.011.89a2.89 2.89 0 0 1-2.924 2.924l-.89-.01-.622.636a2.89 2.89 0 0 1-4.134 0l-.622-.637-.89.011a2.89 2.89 0 0 1-2.924-2.924l.01-.89-.636-.622a2.89 2.89 0 0 1 0-4.134l.637-.622-.011-.89a2.89 2.89 0 0 1 2.924-2.924l.89.01.622-.636a2.89 2.89 0 0 1 4.134 0l-.715.698a1.89 1.89 0 0 0-2.704 0l-.92.944-1.32-.016a1.89 1.89 0 0 0-1.911 1.912l.016 1.318-.944.921a1.89 1.89 0 0 0 0 2.704l.944.92-.016 1.32a1.89 1.89 0 0 0 1.912 1.911l1.318-.016.921.944a1.89 1.89 0 0 0 2.704 0l.92-.944 1.32.016a1.89 1.89 0 0 0 1.911-1.912l-.016-1.318.944-.921a1.89 1.89 0 0 0 0-2.704l-.944-.92.016-1.32a1.89 1.89 0 0 0-1.912-1.911l-1.318.016z"/>
                            </svg>
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                                 class="bi bi-airplane-engines" viewBox="0 0 16 16">
                                <path d="M8 0c-.787 0-1.292.592-1.572 1.151A4.347 4.347 0 0 0 6 3v3.691l-2 1V7.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.191l-1.17.585A1.5 1.5 0 0 0 0 10.618V12a.5.5 0 0 0 .582.493l1.631-.272.313.937a.5.5 0 0 0 .948 0l.405-1.214 2.21-.369.375 2.253-1.318 1.318A.5.5 0 0 0 5.5 16h5a.5.5 0 0 0 .354-.854l-1.318-1.318.375-2.253 2.21.369.405 1.214a.5.5 0 0 0 .948 0l.313-.937 1.63.272A.5.5 0 0 0 16 12v-1.382a1.5 1.5 0 0 0-.83-1.342L14 8.691V7.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v.191l-2-1V3c0-.568-.14-1.271-.428-1.849C9.292.591 8.787 0 8 0ZM7 3c0-.432.11-.979.322-1.401C7.542 1.159 7.787 1 8 1c.213 0 .458.158.678.599C8.889 2.02 9 2.569 9 3v4a.5.5 0 0 0 .276.447l5.448 2.724a.5.5 0 0 1 .276.447v.792l-5.418-.903a.5.5 0 0 0-.575.41l-.5 3a.5.5 0 0 0 .14.437l.646.646H6.707l.647-.646a.5.5 0 0 0 .14-.436l-.5-3a.5.5 0 0 0-.576-.411L1 11.41v-.792a.5.5 0 0 1 .276-.447l5.448-2.724A.5.5 0 0 0 7 7V3Z"></path>
                            </svg>
                            <span class="visually-hidden">Button</span>
                        </button>
                    </div>
                </div>

                <#--                devider-->
                <hr style="width: 98%;">

                <#--                search dates of trip-->
                <div class="row ms-1 mt-2 col-11">
                    <label for="trip_dates">Проміжок дат пошуку перельотів</label>
                    <div class="ms-1 col-12">
                        <input type="text" id="trip_dates" class="form-control"
                               data-start="${parameters.startTripDate}" data-end="${parameters.endTripDate}">
                    </div>
                </div>

                <#--                devider-->
                <hr style="width: 98%;">

                <#--                days in each point-->
                <div class="row mb-1 ms-1 col-11">
                    <label for="days_in_point" class="col-form-label">Дні перебування в кожному місці</label>
                    <div class="ms-2 col-12">
                        <input type="text" id="days_in_point" class="slider"
                               data-min="${parameters.daysInPointMin}" data-max="${parameters.daysInPointMax}"/>
                    </div>
                </div>

                <#--                total days in trip-->
                <div class="row mb-1 ms-1 col-11">
                    <div class="mb-4">
                        <label for="total_days" class="col-form-label">Загальна тривалість подорожі, днів</label>
                    </div>
                    <div class="ms-2 col-12">
                        <input type="text" id="total_days" class="slider"
                               data-min="${parameters.totalDaysMin}" data-max="${parameters.totalDaysMax}"/>
                    </div>
                </div>

                <#--                total cost of the trip-->
                <div class="row ms-1 col-11">
                    <div class="mb-4">
                        <label for="total_cost" class="col-form-label">Загальна вартість подорожі, USD</label>
                    </div>
                    <div class="ms-2 col-12">
                        <input type="text" id="total_cost" class="slider"
                               data-min="${parameters.totalCostMin}" data-max="${parameters.totalCostMax}"/>
                    </div>
                </div>
            </form>
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
        <div class="container-fluid">
            <h1 class="d-flex justify-content-center mt-4">Результати пошуку&nbsp;&nbsp;&nbsp;
                <div id="favourite">
                    <button type="button" class="btn btn-outline-primary" id="not_in_favourite" style="display: none;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                            <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                        </svg>
                    </button>

                    <button type="button" class="btn btn-outline-primary" id="in_favourite" style="display: none;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                        </svg>
                    </button>
                </div>
            </h1>
            <div id="main_content">
            </div>
        </div>
    </div>
</div>
<!-- JS -->
<script src="js/libraries/bootstrap.bundle.min.js"></script>
<script src="js/libraries/jquery-3.6.4.min.js"></script>
<script src="js/libraries/dselect.js"></script>
<script src="js/libraries/rSlider.min.js"></script>
<script src="js/libraries/moment.min.js"></script>
<script src="js/libraries/daterangepicker.min.js"></script>
<script src="js/flight_page.js"></script>
<script src="js/hide_show_sidebar.js"></script>
<script src="js/display_login_block.js"></script>
<script src="js/bootstrap_validation.js"></script>
</body>
</html>
