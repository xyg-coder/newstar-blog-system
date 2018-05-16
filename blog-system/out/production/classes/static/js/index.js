"use strict"

$(function () {
    var _pageSize;

    function getBlogsByName(pageIndex, pageSize) {
        $.ajax({
            url: "/blogs",
            contentType: "application/json",
            data:{
                "async":true,
                "pageIndex":pageIndex,
                "pageSize":pageSize,
                "keyword":$("#indexkeyword").val()
            },
            success: function(data){
                $("#mainContainer").html(data);

                var keyword = $("#indexkeyword").val();

                if (keyword.length > 0) {
                    $(".nav-item .nav-link").removeClass("active");
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    // divide pages
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getBlogsByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    $("#indexsearch").click(function() {
        getBlogsByName(0, _pageSize);
    });


    $("#indexkeyword").keypress(function (event) {
        if (event.keyCode === 13) {
            console.log("enter is pressed");
            $("#indexsearch").click();
        }
    });

    // click newest/hotest
    $(".nav-item .nav-link").click(function() {

        var url = $(this).attr("url");


        $(".nav-item .nav-link").removeClass("active");
        $(this).addClass("active");

        $.ajax({
            url: url+'&async=true',
            success: function(data){
                $("#mainContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });


        $("#indexkeyword").val('');
    });
});