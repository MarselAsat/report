window.onload = function () {
    const date = document.getElementById('reportNameDate');
    const log = document.getElementById('log');
    date.addEventListener('change', updateValue);

    var pathArray = window.location.pathname.split('/');
    var reportTypeId = pathArray[3];
    var reportTypeA = document.getElementById("reportType"+reportTypeId);
    reportTypeA.classList.add("active");

    function updateValue(e) {
        var dateValue = e.target.value;

        fetch(contextPath + "startPage/reportName/"+dateValue+"/"+reportTypeId, {method: 'GET'})
            .then(response => response.json())
            .then(data => {
                console.log(data);
                const reportNames = document.getElementById("reportNames");
                reportNames.innerHTML = '';
                var listLength = data.length;
                for (let i = 0; i < listLength; i++) {
	                let a = document.createElement('a');
	                a.setAttribute("href", "#");
	                a.setAttribute("class", "list-group-item");
	                a.setAttribute("onclick", "getTagData("+data[i].id+")");
                    a.innerHTML = data[i].name;
	                reportNames.appendChild(a);
                }
            })
    }
}