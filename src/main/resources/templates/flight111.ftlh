<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flight service</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/rSlider.min.css" rel="stylesheet">
    <link href="css/dselect.css" rel="stylesheet">
    <link href="css/daterangepicker.css" rel="stylesheet" >
    <link href="css/style.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <h1 style="color: #0d6efd">Оберіть параметри пошуку польотів!</h1>

    <!--Input form-->


    <form class="row g-3 needs-validation mt-5 mb-5" novalidate>

        <div class="row mb-3 col-sm-6">

            <label for="slider2" class="col-form-label">Кількість днів перебування в кожному місці</label>
            <input type="text" id="slider2" class="slider"/>
        </div>

        <div class="row mb-3">
            <div class="col-sm-4">
                <label for="searchable-select" class="col-sm-12 col-form-label">З</label>
                <select class="form-select" required id="searchable-select" name="from">
                    <option selected disabled hidden value="">Оберіть початкову точку</option>
                    <#list cashedLocationsInOperation as location>
                        <option value="${location.getId()}">
                            <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                        </option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Будь-ласка оберіть початкову точку вильоту!
                </div>
            </div>

            <div class="col-sm-1"></div>

            <div class="col-sm-4">
                <label for="searchable-select2" class="col-sm-12 col-form-label">До</label>
                <select class="form-select" required id="searchable-select2" name="to">
                    <option selected disabled hidden value="">Оберіть кінцеву точку</option>
                    <#list cashedLocationsInOperation as location>
                        <option value="${location.getId()}">
                            <#if location.isCity()>&nbsp;&nbsp;&nbsp;&nbsp;</#if> ${location.getName()}
                        </option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Будь-ласка оберіть кінцеву точку вильоту!
                </div>
            </div>

            <div class="col-sm-4">
                <label for="daterange">Оберіть дати в які плануєте подорож</label>
                <input type="text" id="daterange" class="form-control" name="daterange">
            </div>
        </div>




        <#--        <div class="row mb-3">-->
        <#--            <label for="comment" class="col-sm-2 col-form-label">Comment</label>-->
        <#--            <div class="col-sm-10">-->
        <#--                <textarea class="form-control" id="comment" placeholder="Your comment" rows="5" required-->
        <#--                          name="comment"></textarea>-->
        <#--            </div>-->
        <#--            <div class="invalid-feedback">-->
        <#--                Please enter a comment!-->
        <#--            </div>-->
        <#--        </div>-->

        <div class="col-12">
            <button class="btn btn-primary" type="submit" id="submitButton">Send comment</button>
        </div>
    </form>

</div>

<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/jquery-3.6.4.min.js"></script>
<script src="js/dselect.js"></script>
<script src="js/rSlider.min.js"></script>
<script src="js/moment.min.js"></script>
<script src="js/daterangepicker.min.js"></script>
<script src="js/javaScript.js"></script>
</body>
</html>