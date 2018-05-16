/*
* blog admins main js
* created by xinyuan gui
* */
// mainly used to load the list.html into the right container
"use strict"

$(function () {
    $(".blog-menu .list-group-item").click(function () {
        var url = $(this).attr("url");

        // firstly get rid of other styles, then add click style
        $(".blog-menu .list-group-item").removeClass("active");
        $(this).addClass("active");

        // load other styles into the right work space
        $.ajax({
            url: url,
            success: function (data) {
                $("#rightContainer").html(data);
            },
            error: function () {
                alert("error");
            }
        });
    });
});