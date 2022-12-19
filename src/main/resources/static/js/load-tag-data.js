function getTagData(element, reportNameId) {
    let activeClass = "active";
    if(!element.classList.contains(activeClass)){
        let reportNamesDiv = document.getElementById("reportNames");
        let activeReportName = reportNamesDiv.getElementsByClassName(activeClass)[0];
        if(activeReportName!=null){
            activeReportName.classList.remove(activeClass)
        }
        element.classList.add(activeClass);
    }

    let path = window.location.search;
    const urlParams = new URLSearchParams(path);
    let reportTypeId = urlParams.get("reportTypeId");
    const url = "/"+reportTypeId+"Report/"+reportNameId;

    fetch(url).then(function (response) {
        // The API call was successful!
        return response.text();
    }).then(function (html) {
        // This is the HTML from our response as a text string
        document.getElementById("includedContent").innerHTML = html
    }).catch(function (err) {
        // There was an error
        console.warn('Something went wrong.', err);
    });
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