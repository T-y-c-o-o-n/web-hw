window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

function ajax(data, success) {
    $.ajax({
        type: "POST",
        url: "",
        dataType: "json",
        data: data,
        success: function (response) {
            if (success) {
                success(response);
            }
            if (response["error"]) {
                $(".error").text(response["error"]);
            }
            if (response["redirect"]) {
                location.href = response["redirect"];
            }
        }
    });
}
