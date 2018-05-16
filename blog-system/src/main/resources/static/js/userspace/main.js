"use strict"

// turn the dataUrl to blob
function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
}

function loadCrop() {
    var avatarApi = "/u/"+$(".blog-edit-avatar").attr("userName")+"/avatar";
    $.ajax({
        url: avatarApi,
        success: function(data){
            function each(arr, callback) {
                var length = arr.length;
                var i;

                for (i = 0; i < length; i++) {
                    callback.call(arr, arr[i], i, arr);
                }

                return arr;
            }

            $("#avatarFormContainer").html(data);
            var URL = window.URL || window.webkitURL;
            var image = document.getElementById('image');
            var previews = document.querySelectorAll('.preview');
            var cropBoxData;
            var canvasData;
            var cropper;
            var options = {
                viewMode: 1,
                dragMode: 'move',
                aspectRatio: 1,
                preview: '.preview',
                autoCropArea: 0.9,
                restore: false,
                guides: false,
                center: false,
                highlight: false,
                cropBoxMovable: false,
                cropBoxResizable: false,
                toggleDragModeOnDblclick: false,
            };
            cropper = new Cropper(image, options);

            var originalImageURL = image.src;
            var uploadImageURL;
            var inputImage = document.getElementById('inputImage');
            var image = document.getElementById("image");

            // // if destroy, there will cause some bug
            // $('#flipFlop').on('hidden.bs.modal', function () {
            //     cropBoxData = cropper.getCropBoxData();
            //     canvasData = cropper.getCanvasData();
            //     cropper.destroy();
            //     cropper = null;
            // });

            inputImage.onchange = function () {
                var files = this.files;
                var file;

                if (cropper && files && files.length) {
                    file = files[0];

                    var uploadImageType = file.type;
                    if (uploadImageURL) {
                        URL.revokeObjectURL(uploadImageURL);
                    }

                    image.src = uploadImageURL = URL.createObjectURL(file);
                    cropper.destroy();
                    cropper = new Cropper(image, options);
                    inputImage.value = null;
                }
            };

            $("#flipFlop").on('click', '#previewButton', function () {
                if (cropper) {
                    var canvasData = cropper.getCroppedCanvas();
                    var dataurl = canvasData.toDataURL();
                    $('#resultImg').attr('src', dataurl);
                }
            });

            $("#flipFlop").on('click', '#sumbitEditAvatar', function () {
                if (cropper) {
                    var canvasData = cropper.getCroppedCanvas();
                    var dataurl = canvasData.toDataURL();
                    var blob = dataURLtoBlob(dataurl);
                    // use ajax to send the data to the file server of mongodb
                    var formData = new FormData();
                    formData.append("file", blob);

                    $.ajax({
                        url: 'http://localhost:8081/upload',
                        type: 'POST',
                        cache: false,
                        data: formData,
                        processData: false,
                        contentType: false,
                        success: function (data) {
                            var avatarUrl = data;

                            // get the CSRF token
                            var csrfToken = $("meta[name='_csrf']").attr("content");
                            var csrfHeader = $("meta[name='_csrf_header']").attr("content");

                            $.ajax({
                                url: avatarApi,
                                type: 'POST',
                                contentType: "application/json; charset=utf-8",
                                data: JSON.stringify({"id" :Number($("#userId").val()), "avatar":avatarUrl}),
                                beforeSend: function(request) {
                                    request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
                                },
                                success: function (data) {
                                    if (data.success) {
                                        // $(".blog-avatar").attr("src", data.avatar);
                                        window.location.href = `/u/${$(".blog-edit-avatar").attr("userName")}/profile`;
                                    } else {
                                        toastr.error("error!"+data.message);
                                    }
                                },
                                error: function() {
                                    toastr.error("error!");
                                }
                            });
                        },
                        error: function () {
                            toastr.error("error!");
                        }
                    });
                }
            });
        },
        error : function() {
            toastr.error("error!");
        }
    });
}

$(function () {
    $(".blog-content-container").on("click",".blog-edit-avatar", loadCrop);
});