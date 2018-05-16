"use strict"

$(function () {
    var _pageSize;
    // console.log("finish loading u.js");
    function getBlogs(pageIndex, pageSize) {
        $.ajax({
            url: "/u/" + username + "/blogs",
            contentType: "application/json",
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "catalog": catalogId,
                "keyword": $("#keyword").val()
            },
            success: function (data) {
                $("#mainContainer").html(data);

                // if we get blogs with catalog, then we remove the active class
                if (catalogId) {
                    $(".nav-item .nav-link").removeClass("active");
                }
            },
            error: function () {
                toastr.error("error");
            }
        });
    }

    // divide the page
    $.tbpage("#mainContainer", function (pageIndex, pageSize) {
        getBlogs(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // when the search is input, focus the enter to the search button
    $('#keyword').keypress(function (event) {
        if (event.keyCode === 13) {
            console.log("enter is pressed");
            $("#searchBlogs").click();
        }
    });

    // search for the keyword
    $("#searchBlogs").click(function () {
        getBlogs(0, _pageSize);
    });

    // switch between the new/hot event
    $(".nav-item .nav-link").click(function () {
        var url = $(this).attr("url");
        console.log(url);

        // firstly remove other classes, then add class
        $(".nav-item .nav-link").removeClass("active");
        $(this).addClass("active");

        // load other module
        $.ajax({
            url: url + '&async=true',
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });

        // clear the keyword
        $("#keyword").val('');
    });

    // get the catalogs list
    function getCatalogs(username) {
        $.ajax({
            url: '/catalogs',
            type: 'GET',
            data: {"username":username},
            success(data) {
                $("#catalogMain").html(data);
            },
            error() {
                toastr.error("error!");
            }
        });
    }

    // get the page of edit catalog
    $(".blog-content-container").on("click",".blog-add-catalog", function () {
        $.ajax({
            url: '/catalogs/edit',
            type: 'GET',
            success: function(data){
                $("#catalogFormContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    $(".blog-content-container").on("click",".blog-edit-catalog", function () {

        $.ajax({
            url: '/catalogs/edit/'+$(this).attr('catalogId'),
            type: 'GET',
            success: function(data){
                $("#catalogFormContainer").html(data);
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // submit the catalog edit result
    $("#submitEditCatalog").click(function() {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/catalogs',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify({
                "username": username,
                "catalog": {"id": $('#catalogId').val(), "name": $('#catalogName').val()}
            }),
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    // refresh the catalog list
                    getCatalogs(username);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // delete the catalog
    $(".blog-content-container").on('click', '.blog-delete-catalog', function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: `/catalogs/${$(this).attr('catalogId')}?username=${username}`,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(data) {
                if (data.success) {
                    toastr.info(data.message);
                    getCatalogs(username);
                } else {
                    toastr.error(data.message);
                }
            },
            error() {
                toastr.error("error!");
            }
        });
    });

    $(".blog-content-container").on("click",".blog-query-by-catalog", function () {
        catalogId = $(this).attr('catalogId');
        console.log(catalogId);
        getBlogs(0, _pageSize);
    });

    getCatalogs(username);
});