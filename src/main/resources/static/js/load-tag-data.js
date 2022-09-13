function getTagData(id) {
    fetch(contextPath + "api/tagData/"+id, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const tagDataDiv = document.getElementById("tagData");
            tagDataDiv.innerHTML = '';
            var listLength = data.length;
            for (let i = 0; i < listLength; i++) {
                let li = document.createElement('tr');
                console.log(data);
                li.innerHTML = '<th>'+data[i].tagName+'</th><th>'+data[i].data+'</th><th>'+data[i].date+'</th><th>'+data[i].reportType+'</th>';
                tagDataDiv.appendChild(li);
            }
        })

    path = window.location.search;
    const urlParams = new URLSearchParams(path);
    let reportTypeId = urlParams.get("reportTypeId");
    const showReportRef = document.getElementById("show-report-ref");

    switch(reportTypeId) {
        case '1':
            showReportRef.setAttribute("href", "/hourReport/"+id);
            break
        case '2':
            showReportRef.setAttribute("href", "/dailyReport/"+id);
            break
        case '3':
            showReportRef.setAttribute("href", "/shiftReport/"+id);
            break
        case '4':
            showReportRef.setAttribute("href", "/monthReport/"+id);
            break
        case '5':
            showReportRef.setAttribute("href", "/yearReport/"+id);
            break
        case '6':
            showReportRef.setAttribute("href", "/manualReport/"+id);
            break
        default:
            break
    }

}