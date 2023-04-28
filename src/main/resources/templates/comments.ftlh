<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Comment service</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <h1 style="color: #0d6efd">Please leave your comment!</h1>

    <!--Input form-->
    <form class="row g-3 needs-validation mt-5 mb-5" novalidate>
        <div class="row mb-3">
            <label for="name" class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="name" placeholder="Your name" required name="name">
            </div>
            <div class="invalid-feedback">
                Please enter your name!
            </div>

            <label for="rating" class="col-sm-1 col-form-label">Rating</label>
            <div class="col-sm-4">
                <select class="form-select" id="rating" required name="rating">
                    <option selected disabled hidden value="">Choose rating</option>
                    <#list ratingNames as id, rating_name>
                        <option value="${id}">${rating_name}</option>
                    </#list>
                </select>
            </div>
            <div class="invalid-feedback">
                Please select a rating!
            </div>
        </div>

        <div class="row mb-3">
            <label for="comment" class="col-sm-2 col-form-label">Comment</label>
            <div class="col-sm-10">
                <textarea class="form-control" id="comment" placeholder="Your comment" rows="5" required
                          name="comment"></textarea>
            </div>
            <div class="invalid-feedback">
                Please enter a comment!
            </div>
        </div>

        <div class="col-12">
            <button class="btn btn-primary" type="submit" id="submitButton">Send comment</button>
        </div>
    </form>

    <!--Display form-->
    <ol class="list-group list-group-numbered mb-5" id="viewer"></ol>
    <div class="d-grid col-2 mx-auto  mb-5">
        <button type="button" class="btn btn-light btn-sm" id="loadMoreButton" data-page="1" style="display: none;">
            Load more...
        </button>
    </div>
</div>

<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-3.6.4.min.js"></script>
<script src="js/javaScript.js"></script>
</body>
</html>