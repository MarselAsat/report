window.onload = function () {
    $(document).ready(function () {
        new TomSelect("#select", {
            create: true,
            sortField: {
                field: "text",
                direction: "asc"
            },
            maxOptions: null
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
        .then(response => {
            if (response.status === 503) {
                Swal.fire({
                    icon: 'error',
                    text: 'Нет связи с OPC сервисом'
                })
            }
            return response.text()
        })
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
        .then(response => {
            if (response.status === 503) {
                Swal.fire({
                    icon: 'error',
                    text: 'Нет связи с OPC сервисом'
                })
            }
            else if(response.status >= 500){
                Swal.fire({
                    icon: 'error',
                    text: 'Не удалось переподключиться к OPC серверу'
                })
            }
            return response.text()
        })
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
    let tagValueField = document.getElementById("tag-value");
    tagValueField.style.display = 'none';
    $('#loaderTagValue').css("display", "block")

    if (tagName === "") {
        $('#loaderTagValue').css("display", "none")
        return;
    }
    var url = "/opcService/readValue/" + tagName;

    fetch(url, {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(response => {
            if (response.status === 503) {
                tagValueField.style.display = 'block';
                tagValueField.className = "alert alert-danger";
                tagValueField.innerText = "Нет связи с OPC сервисом";
            }
            return response.text()
        })
        .then(data => {
            if (data === "No data") {
                tagValueField.style.display = 'block';
                tagValueField.className = "alert alert-danger";
                tagValueField.innerText = "Тег не найден";
            } else if (data !== "OPC service is unavailable") {
                tagValueField.style.display = 'block';
                tagValueField.className = "alert alert-secondary";
                tagValueField.innerText = data;
            }
            $('#loaderTagValue').css("display", "none")
        })
}