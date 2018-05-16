/**
 * users main js
 * @since 1.0.0 2018.2.12
 * @author xinyuan gui
 * */

"use strict"

// after the load of DOM
$(function() {
    var _pageSize; // store for search

    // search with username, pageindex, pagesize
    function getUsersByName(pageIndex, pageSize) {
        $.ajax({
            url: "/users",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "username": $("#searchName").val()
            },
            success: function (data) {
                $("#mainContainer").html(data)
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    // divide pages
    //tbpage is in the thymeleaf-bootstrap-paginator.js file
    $.tbpage("#mainContainer", function(pageIndex, pageSize) {
        getUsersByName(pageIndex, pageSize);
        _pageSize = pageSize;
    });

    // search for users
    $("#searchNameBtn").click(function () {
        getUsersByName(0, _pageSize);
    });

    // get the add user page
    $("#addUser").click(function () {
        $.ajax({
            url: "/users/add",
            success: function (data) {
                // console.log(data);
                $("#userFormContainer").html(data);
            },
            error: function (data) {
                toastr.error("error!");
            }
        });
    });
    
    // get the user edit page
    $("#rightContainer").on("click", ".blog-edit-user", function () {
        $.ajax({
            url: "/users/edit/" + $(this).attr("userId"),
            success: function (data) {
                $("#userFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // clear the page after the submit
    $("#submitEdit").click(function () {
        console.log("submitEdit get called", $("#userForm").serialize());
        $.ajax({
            url: "/users",
            type: "POST",
            data: $("#userForm").serialize(),
            success: function (data) {
                // $("#userForm")[0].reset();
                // the code above will cause interruption
                $("#userForm").get(0).reset();
                // this is for the bean constraint
                if (data.success) {
                    // refresh the page
                    getUsersByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error, please check if your username or email exists!");

            }
        });
    });

    // delete user
    $("#rightContainer").on("click", ".blog-delete-user", function () {
        // delete user

        // get CSRF-TOKEN
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "/users/" + $(this).attr("userId"),
            type: "DELETE",
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // add csrf token
            },
            success: function (data) {
                if (data.success) {
                    // refresh the page
                    getUsersByName(0, _pageSize);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });
});