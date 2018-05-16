/*!
 * blog.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=blog.js

// DOM 加载完再执行
$(function() {
	$.catalog("#catalog", ".post-content");
    var blogId = $(".blog-content-container").attr("blogId");
    var blogUrl = $(".blog-delete-blog").attr("blogUrl");
    console.log(blogUrl);

    $(".blog-content-container").on("click",".blog-delete-blog", function () {
        // get CSRF Token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");


        $.ajax({
            url: blogUrl ,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // add  CSRF Token
            },
            success: function(data){
                if (data.success) {
                    // redirect to blogs page
                    window.location = data.body;
                } else {
                    toastr.error(data.message);
                }
            },
            error : function() {
                toastr.error("error!");
            }
        });
    });

    // get the comments list
    function getComment(blogId) {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        console.log(blogId);
        $.ajax({
            url: '/comments',
            type: 'GET',
            data: {"blogId": blogId},
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                console.log('get comment error');
                toastr.error("error!");
            }
        });
    }

    $(".blog-content-container").on("click", "#submitComment", function () {
        // get CSRF token
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            url: '/comments',
            type: 'POST',
            data: {"blogId": blogId, "commentContent": $("#commentContent").val()},
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    // clear the comments
                    $('#commentContent').val('');
                    // add comment
                    getComment(blogId);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error! Please check if you login");
            }
        });
    });

    $(".blog-content-container").on("click", ".blog-delete-comment", function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/comments/' + $(this).attr('commentId') + '?blogId=' + blogId,
            type: 'DELETE',
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    getComment(blogId);
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error! Please check if you login");
            }
        });
    });

    // add vote
    $(".blog-content-container").on('click', '#submitVote', function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: '/votes',
            type: 'POST',
            data: {"blogId": blogId},
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    window.location = blogUrl;
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('unknown errors');
            }
        });
    });

    $(".blog-content-container").on('click', '#cancelVote', function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var voteId = $(this).attr('voteId');

        $.ajax({
            url: `/votes/${voteId}?blogId=${blogId}`,
            type: 'DELETE',
            beforeSend: function(request) {
                request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            },
            success: function (data) {
                if (data.success) {
                    toastr.info(data.message);
                    window.location = blogUrl;
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error('unknown errors');
            }
        });
    });

    getComment(blogId);
});