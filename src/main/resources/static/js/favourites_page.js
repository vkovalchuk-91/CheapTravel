// actions with favourites
$(function () {
    let $favourites = $('#favourites');
    $favourites.on('click', '.searching-delete-button', function () {
        let currentParameter = $(this).parent();
        let favouriteId = currentParameter.attr('data-id');
        deleteFromFavourite(favouriteId);

        let accordion_bodyId = currentParameter.parent().attr('id');
        let selector = ".accordion-body#" + accordion_bodyId;
        let childCount = $(selector).children().length;
        if (childCount === 1) {
            currentParameter.parent().parent().parent().remove();
        } else {
            currentParameter.remove();
        }
    });

    $favourites.on('click', '.searching-redirect-button', function () {
        let favouriteId = $(this).parent().attr('data-id');
        openFavourites(favouriteId);
    });
})

// delete searching query from DB
function deleteFromFavourite(favouriteId) {
    $.ajax({
        url: '/favourites',
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        data: favouriteId,
        success: function (data) {
        }
    });
}

function openFavourites(favouriteId) {
    window.location.href = "/flight";
    // $.ajax({
    //     url: '/favourites',
    //     method: 'DELETE',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     data: favouriteId,
    //     success: function (data) {
    //     }
    // });
}