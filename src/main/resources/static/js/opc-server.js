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
    let $loaderTestConnection = $('#loaderTestConnection')
    $loaderTestConnection.css("display", "block")
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
            $loaderTestConnection.css("display", "none")
        })
}

function reconnect() {
    let $loaderReconnect = $('#loaderReconnect')
    $loaderReconnect.css("display", "block")
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
            } else if (response.status >= 500) {
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
            $loaderReconnect.css("display", "none")
        })
}

function getTagValue(tag) {
    let tagValueField = document.getElementById("tag-value");
    tagValueField.style.display = 'none';
    let $loaderTagValue = $('#loaderTagValue')
    $loaderTagValue.css("display", "block")

    if (tag === "") {
        $loaderTagValue.css("display", "none")
        return;
    }
    let url = "/opcService/readValue";

    fetch(url, {
            method: 'POST',
            body: tag,
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(async response => {
            if (response.ok) {
                return await response.text()
            } else {
                const error = await response.text();
                throw new Error(error);
            }
        })
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
            $loaderTagValue.css("display", "none")
        })
        .catch((error) => {
            tagValueField.style.display = 'block';
            tagValueField.className = "alert alert-danger";
            tagValueField.innerText = error.message;
            $loaderTagValue.css("display", "none")
        })
}

function generateHourReport() {
    generateReport('#loaderGenHourReport', 'hourReport')
}

function generateReport(loaderReportId, url){
    let $loaderGenRep = $(loaderReportId)
    $loaderGenRep.css("display", "block")
    fetch("/manualGeneration/"+url, {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(response => {
            $loaderGenRep.css("display", "none")
            if (response.status >= 300) {
                return Promise.reject(response);
            }
            else {
                return response.text()
            }
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                text: data
            })
        })
        .catch((response) => {
            if (response.status >= 500) {
                response.text().then((data) => {
                    Swal.fire({
                        icon: 'error',
                        text: data
                    })
                })
            }
            else {
                response.text().then((data) => {
                    Swal.fire({
                        icon: 'info',
                        text: data
                    })
                })
            }
        })
}

function generate2HourReport() {
    generateReport('#loaderGen2HourReport', '2hourReport')
}

function generateDailyReport() {
    generateReport('#loaderGenDailyReport', 'dailyReport')
}

