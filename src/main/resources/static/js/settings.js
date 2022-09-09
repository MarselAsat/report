function save(){
    var reportTypes = ['hour', 'daily', 'shift', 'month', 'year'];
    var settings = new Map();

    for(var reportType of reportTypes){
        let columns = document.getElementById(reportType+"-columns");
        var checkedColumns = columns.querySelectorAll('input[type=checkbox]:checked');
        var columnNames = [];
        for(let checkbox of checkedColumns){
            columnNames.push(checkbox.getAttribute("name"));
        }
        var columnsStr = columnNames.join(",");
        settings.set(reportType+' report columns', columnsStr);
    }
    updateSettingsInDB(settings);

}

async function updateSettingsInDB(settings) {
    var url = "/settings/update";
    let response = await fetch(url, {
        method: "POST",
        body: JSON.stringify(Object.fromEntries(settings)),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    let responseText = await response.text();
    let responseLabel = document.getElementById("response");
    if(responseText === "true"){
        responseLabel.innerText = "Сохранено";
        responseLabel.style.color = "green";
    }
    else{
        responseLabel.innerText = "Что-то пошло не так";
        responseLabel.style.color = "red";
    }
}