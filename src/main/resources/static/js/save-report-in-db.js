function saveReportInDB(url){
    fetch(url, {
            method: 'GET',
            headers: {
                'Content-type': 'application/json; charset=UTF-8',
            }
        }
    )
        .then(async response => {
            if (response.ok) {
                return await response.text()
            } else {
                throw new Error();
            }
        })
        .then(data => {
            Swal.fire({
                icon: 'success',
                text: data
            })
        })
        .catch((error) => {
            Swal.fire({
                icon: 'error',
                text: 'Что-то пошло не так'
            })
        })
}