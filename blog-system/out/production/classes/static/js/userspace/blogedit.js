"use strict"

$(function () {
    $("#md").markdown({
        language: 'en',
        fullscreen: {
            enable: true
        },
        resize: 'vertical',
        localStorage: 'md',
        imgurl: 'http://localhost:8081',
        base64url: 'http://localhost:8081'
    });

    // Initialize the tags
    $('.form-control-tag').tagsInput({
        'defaultText':'Input the tags'

    });



    $('.form-control-chosen').chosen();

    // upload image
    $('#uploadImage').click(function () {
        $.ajax({
            url: 'http://localhost:8081/upload',
            type: 'POST',
            cache: false,
            data: new FormData($('#uploadformid')[0]),
            processData: false,
            contentType: false,
            success: function (data) {
                var mdcontent = $("#md").val();
                $("#md").val(mdcontent + "\n![](" + data + ") \n");
            }
        }).done(function (res) {
            $('#file').val('');
        }).fail(function (res) {});
    });

    // post blogs
    $("#submitBlog").click(function () {
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        console.log($('#catalogSelect').val());
        $.ajax({
            url: '/u/' + $(this).attr("userName") + '/blogs/edit',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                "id": $("#id").val(),
                "title": $("#title").val(),
                "summary": $("#summary").val(),
                "content": $("#md").val(),
                "catalog":{"id":$('#catalogSelect').val()},
                "tags": $('.form-control-tag').val()
            }),
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // add csrf token
            },
            success: function (data) {
                if (data.success) {
                    window.location = data.body; // redirect the window
                } else {
                    toastr.error("error!" + data.message);
                }
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });
});

function auto_grow(element) {
    if (element.scrollHeight > 80) {
        element.style.height = 5 + "px";
        element.style.height = (element.scrollHeight + 10) + "px";
    }
    // element.style.height = "5px";
    // element.style.height = (element.scrollHeight)+"px";
}