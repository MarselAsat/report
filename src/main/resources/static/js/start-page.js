var reportTypeToGroup =
    {mi3272: "poverki", mi3622: "poverki", acceptanceAct: "acts",
        kmhViscometer: "kmh",
    kmhMoisturemeter: "kmh",
        kmhMassByMass: "kmh"};

window.onload = function () {
    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    if(urlParams.has('reportTypeId')){
        const reportTypeId = urlParams.get('reportTypeId')

        if(reportTypeToGroup[reportTypeId] === "poverki"){
            $("#collapsePoverki").collapse("show");
        }
        if(reportTypeToGroup[reportTypeId] === "acts"){
            $("#collapseActs").collapse("show");
        }
        if(reportTypeToGroup[reportTypeId] === "kmh"){
            $("#collapseKmh").collapse("show");
        }

        var reportTypeA = document.getElementById("reportType-"+reportTypeId);
        reportTypeA.classList.add("active");
    }
    if(urlParams.has('date')){
        const date = urlParams.get('date')
        var datePicker = document.getElementById('datePicker');
        datePicker.value = date;
    }
}