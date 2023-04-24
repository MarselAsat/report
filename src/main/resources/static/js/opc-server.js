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