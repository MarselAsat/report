window.onload = function () {
    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    if(urlParams.has('reportTypeId')){
        const reportTypeId = urlParams.get('reportTypeId')
        var reportTypeA = document.getElementById("reportType-"+reportTypeId);
        reportTypeA.classList.add("active");
    }
    if(urlParams.has('date')){
        const date = urlParams.get('date')
        var datePicker = document.getElementById('datePicker');
        datePicker.value = date;
    }
}