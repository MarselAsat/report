window.onload = function () {
    $(document).ready(function() {
        new TomSelect("#select",{
            create: true,
            sortField: {
                field: "text",
                direction: "asc"
            }
        });
    });
}

function testConnection() {
    $('#loaderTestConnection').css("display", "block")
    fetch("/opcService/testOpcServerConnection", {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(response => response.text())
        .then(data => {
            if (data === "false") {
                Swal.fire({
                    icon: 'error',
                    text: 'Нет подкючения к OPC серверу'
                })
            } else if (data === "true") {
                Swal.fire({
                    icon: 'success',
                    text: 'OPC сервер доступен'
                })
            }
            $('#loaderTestConnection').css("display", "none")
        })
}

function reconnect() {
    $('#loaderReconnect').css("display", "block")
    fetch("/opcService/reconnect", {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(response => response.text())
        .then(data => {
            if (data === "false") {
                Swal.fire({
                    icon: 'error',
                    text: 'Не удалось переподключиться к OPC серверу'

                })
            } else if (data === "true") {
                Swal.fire({
                    icon: 'success',
                    text: 'Переподключение к OPC серверу прошло успешно'
                })
            }
            $('#loaderReconnect').css("display", "none")
        })
}

function getTagValue(tagName) {
    if(tagName === ""){
        return;
    }
    var url = "/opcService/readValue/" + tagName;

    let tagValueField = document.getElementById("tag-value");

    fetch(url, {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(response => response.text())
        .then(data => {
            if (data === "No data") {
                tagValueField.style.display = 'block';
                tagValueField.className = "alert alert-danger";
                tagValueField.innerText = "Тег не найден";
            } else {
                tagValueField.style.display = 'block';
                tagValueField.className = "alert alert-secondary";
                tagValueField.innerText = data;
            }
        })
}