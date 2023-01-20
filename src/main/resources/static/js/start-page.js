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

function printReport(){

    const printContents = document.getElementById("includedContent").innerHTML;
    let myWindow = window.open();

    myWindow.document.write(printContents);
    myWindow.document.write('<scr' + 'ipt type="text/javascript">' + 'window.onload = function() { window.print(); window.close(); };' + '</sc' + 'ript>');

    myWindow.document.close(); // necessary for IE >= 10
    myWindow.focus(); // necessary for IE >= 10

    return true;
}