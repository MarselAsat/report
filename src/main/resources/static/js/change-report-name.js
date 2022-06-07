window.onload = function () {
    const date = document.getElementById('reportNameDate');
    const log = document.getElementById('log');
    date.addEventListener('change', updateValue);

    function updateValue(e) {
        var dateValue = e.target.value;
        var pathArray = window.location.pathname.split('/');
        var reportTYpeId = pathArray[3];
        fetch(contextPath + "startPage/reportName/"+dateValue+"/"+reportTYpeId, {method: 'GET'})
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const reportNames = document.getElementById("reportNames");
                reportNames.innerHTML = '';
                var listLength = data.length;
                for (let i = 0; i < listLength; i++) {
	                let a = document.createElement('a');
	                a.setAttribute("href", "#");
	                a.setAttribute("onclick", "getTagData("+data[i].id+")");
                    a.innerHTML = data[i].name;
	                reportNames.appendChild(a);
                }
            })
    }
}

function selectReportType(element){
    element.classList.add('active');
}