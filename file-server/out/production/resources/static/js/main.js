$(function () {
    $(".file-row").on('click', '.deleteButton', function () {
        var fileId = $(this).attr('id');
        console.log(fileId);
        $.ajax({
            url:`${fileId}`,
            type: 'DELETE',
            success: function () {
                console.log('delete success');
                window.location.href = "/";
            },
            error: function () {
                console.error("delete fail");
            }
        });
    })
});