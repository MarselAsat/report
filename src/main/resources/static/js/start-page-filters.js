function getReportData(element, reportId) {
    let activeClass = "active";
    if(!element.classList.contains(activeClass)){
        let reportsDiv = document.getElementById("reports");
        let activeReport = reportsDiv.getElementsByClassName(activeClass)[0];
        if(activeReport!=null){
            activeReport.classList.remove(activeClass)
        }
        element.classList.add(activeClass);
    }

    let path = window.location.search;
    const urlParams = new URLSearchParams(path);
    let reportTypeId = urlParams.get("reportTypeId");

    const url = "/"+reportTypeId+"Report/"+reportId;

    fetch(url).then(function (response) {
        // The API call was successful!
        return response.text();
    }).then(function (html) {
        // This is the HTML from our response as a text string
        html = html.replace("id=\"report-content\"", "");
        html = html.replace("class=\"card\"", "");
        document.getElementById("report-content").innerHTML = html;
    }).catch(function (err) {
        // There was an error
        console.warn('Something went wrong.', err);
    });
}

function dateIsChanged(dateElement){
    var dateValue = dateElement.value;
    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    if(!urlParams.has('date')){
        urlParams.append('date', dateValue);
        window.location.href = "/filter?"+urlParams.toString();
    }
    else{
        urlParams.set('date', dateValue);
        window.location.href = "/filter?"+urlParams.toString();
    }
}

function reportTypeIsChanged(reportTypeElement){
    var reportTypeId = reportTypeElement.id.split('-')[1];
    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    if(!urlParams.has('reportTypeId')){
        urlParams.append('reportTypeId', reportTypeId);
        window.location.href = "/filter?"+urlParams.toString();
    }
    else{
        urlParams.set('reportTypeId', reportTypeId);
        window.location.href = "/filter?"+urlParams.toString();
    }
}
