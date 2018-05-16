$(function() {
    $("#register").click(function () {
        console.log("register is clicked", $("#registerForm").serialize());

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: "register",
            type: "POST",
            beforeSend: function (request) {
                request.setRequestHeader(csrfHeader, csrfToken); // add csrf token
            },
            data: $("#registerForm").serialize(),
            success: function (data) {
                // $("#userForm").get(0).reset();
                if (data.success) {
                    window.location.href = "login";
                } else {
                    toastr.error(data.message);
                }
            },
            error: function () {
                toastr.error("error, please check if your username or email exists!");
            }
        });
    });
});