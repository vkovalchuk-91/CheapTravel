<html lang="UA">
<head>
    <title>sfb</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/rSlider.min.css" rel="stylesheet">
    <link href="css/select2.min.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<div class="form-floating">
    <select class="form-select select2" id="select-with-search" aria-label="Select with search">
        <option selected disabled hidden value="">Оберіть початкову точку</option>
        <option value="1">Option 1</option>
        <option value="2">Option 2</option>
        <option value="3">Option 3</option>
        <option value="4">Option 4</option>
        <option value="5">Option 5</option>
    </select>
</div>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-3.6.4.min.js"></script>
<!-- Підключення скрипту select2 -->
<script src="js/select2.full.min.js"></script>

<script>
    $(document).ready(function() {
        $('.select2').select2();
    });
</script>
</body>
</html>