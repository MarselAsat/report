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
function dateIsChanged(dateElement){
    var dateValue = dateElement.value;
    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    if(!urlParams.has('date')){
        urlParams.append('date', dateValue);
        window.location.href = "/startPage/filter?"+urlParams.toString();
    }
    else{
        urlParams.set('date', dateValue);
        window.location.href = "/startPage/filter?"+urlParams.toString();
    }
}

function reportTypeIsChanged(reportTypeElement){
    var reportTypeId = reportTypeElement.id.split('-')[1];
    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    if(reportTypeId === "poverki"){

    }
    else{

    }
    if(!urlParams.has('reportTypeId')){
        urlParams.append('reportTypeId', reportTypeId);
        window.location.href = "/startPage/filter?"+urlParams.toString();
    }
    else{
        urlParams.set('reportTypeId', reportTypeId);
        window.location.href = "/startPage/filter?"+urlParams.toString();
    }
}