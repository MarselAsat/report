function saveInDB(){
    fetch("/calc/MI3622/save", {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(response => {
            if (response.status >= 500) {
                Swal.fire({
                    icon: 'error',
                    text: 'Что-то пошло не так'
                })
            }
            return response.text()
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                text: data
            })
        })
}