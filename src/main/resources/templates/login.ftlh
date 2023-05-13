<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <link rel="stylesheet" href="css/mdb5/core.min.css">
    <title>Вхід зареєстрованим користувачам</title>
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico"/>
</head>
<body>
<section class="vh-100" style="background-color: #eee;">
    <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col-lg-12 col-xl-11">
                <div class="card text-black" style="border-radius: 25px;">
                    <div class="card-body p-md-5">
                        <div class="row justify-content-center">
                            <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
                                <p class="text-center h1 fw-bold mx-1 mx-md-4 mt-4" style="color: #0a4e25">
                                    Cheap Euro Trip
                                </p>
                                <p class="text-center h4 fw-bold mb-5 mx-1 mx-md-4 mt-2">
                                    Вхід
                                </p>

                                <form action="/login" method="post" class="mx-1 mx-md-4" id="login-form">

                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <input type="text" id="username" class="form-control"
                                                   name="username"/>
                                            <label class="form-label" for="username">Ім'я користувача</label>
                                        </div>
                                    </div>

                                    <div class="d-flex flex-row align-items-center mb-4">
                                        <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                        <div class="form-outline flex-fill mb-0">
                                            <input type="password" id="password" class="form-control"
                                                   name="password"/>
                                            <label class="form-label" for="password">Пароль</label>
                                        </div>
                                    </div>

                                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                        <button type="submit" class="btn btn-primary btn-lg">Увійти</button>
                                    </div>

                                </form>

                                <div class="col d-flex justify-content-center">
                                    <a href="/registration"><h6>Реєстрація нового користувача</h6></a>
                                </div>

                                <div class="toast-container position-fixed bottom-0 end-0 p-3">
                                    <div id="main_alert" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
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
                            <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
                                <img src="assets/Chip.png" class="w-100 rounded-t-5 rounded-tr-lg-0 rounded-bl-lg-5"
                                     alt="Chip and Dale in plane">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="js/libraries/bootstrap.bundle.min.js"></script>
<script src="js/libraries/mdb5/core.min.js"></script>
<script src="js/login_page.js"></script>
</body>
</html>