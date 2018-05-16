/**
 * fix the footer to the bottom of
 * the page if the content height is too little
 * for the login and register page (there is one mysterious 16px...)
 * contact me if you know better solution
 */
"use strict"

$(document).ready(function () {
    autoHeight();
    $(".blog-footer").removeClass("hidden");
});

function autoHeight() {
    $(".blog-content-container").css("min-height", 0);
    $(".blog-content-container").css("min-height", (
        $(window).height()
        - parseInt($(".blog-content-container").css("margin-top"))
        - 16
        - $(".navbar").outerHeight(true)
        - $(".blog-footer").outerHeight(true)
    ));
}

$(window).resize(function () {
    autoHeight();
});