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

function checkConnection() {
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
        })
}

function getTagValue(tagName) {
    if(tagName === "Выберите тег" || tagName === ""){
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
                tagValueField.className = "alert alert-success";
                tagValueField.innerText = data;
            }
        })
}