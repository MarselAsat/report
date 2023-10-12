function saveReportInDB(saveUrl){
    let shiftsStartDtElements = document.getElementsByClassName("shifts-start-dt");
    let shiftsEndDtElements = document.getElementsByClassName("shifts-end-dt");
    var shiftStartDtArray = [];
    var shiftEndDtArray = [];

    for (const element of shiftsStartDtElements) {
        shiftStartDtArray.push(element.textContent)
    }
    for (const element of shiftsEndDtElements) {
        shiftEndDtArray.push(element.textContent)
    }
    var urlBase = window.location.origin;
    var url = new URL(saveUrl, urlBase);
    var params = [['dtStartShift', shiftStartDtArray.toString()], ['dtEndShift', shiftEndDtArray.toString()]]

    url.search = new URLSearchParams(params).toString();

    fetch(url, {
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