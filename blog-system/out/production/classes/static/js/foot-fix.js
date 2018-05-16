/**
 * fix the footer to the bottom of
 * the page if the content height is too little
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
        - $(".navbar").outerHeight(true)
        - $(".blog-footer").outerHeight(true)
    ));
}

$(window).resize(function () {
    autoHeight();
});