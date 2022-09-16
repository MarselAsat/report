function getTagData(reportNameId) {
    fetch(contextPath + "api/tagData/"+reportNameId, {method: 'GET'})
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
    showReportRef.setAttribute("href", "/"+reportTypeId+"Report/"+reportNameId);

}