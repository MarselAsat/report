var prevShiftCount;

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
    settings.set("metering station name", document.getElementById("metering-station-name").value)
    settings.set("daily report start time", document.getElementById("start-daily-report").value)
    settings.set("month report start time", document.getElementById("start-month-report").value)
    settings.set("year report start time", document.getElementById("start-year-report").value)

    let shiftSettings = [];
    let shifts = document.getElementsByClassName("shift-start-time");
    for(var shift of shifts){
        let shiftName = shift.getElementsByClassName("shift-name")[0].innerText;
        let wordsInShiftName = shiftName.split(" ");
        const shiftNum = wordsInShiftName[wordsInShiftName.length-1];
        var shiftTime = shift.getElementsByClassName("shift-time")[0].value;
        shiftSettings.push(shiftNum+"-"+shiftTime);
    }
    settings.set('shift report start time', shiftSettings.join(","));

    let checkedRange = document.querySelector('input[name="range"]:checked').id;
    if(checkedRange === "operating-range"){
        settings.set("MI3622 6 or 7 table", "7")
    }
    else if(checkedRange === "subrange"){
        settings.set("MI3622 6 or 7 table", "6")
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

function shiftNumsHasChanged(element){
    var shiftCount = element.value;
    if(shiftCount<=0){
        return;
    }
    if(shiftCount > prevShiftCount){
        for(var i = +prevShiftCount+1; i<= shiftCount; i++){
            let shift = document.getElementById("shift-start-time-1");
            var newShift = shift.cloneNode(true);
            newShift.getElementsByClassName("shift-name")[0].innerText = "Начало смены "+i;
            newShift.id = "shift-start-time-"+i;
            newShift.getElementsByClassName("shift-time")[0].value = "00:00";
            shift.parentElement.appendChild(newShift);
        }
    }
    else{
        for(var k = prevShiftCount; k > shiftCount; k--){
            document.getElementById("shift-start-time-"+k).remove();
        }
    }
    prevShiftCount = shiftCount;
}

function savePrevValue(element){
    if(element.value>0){
        prevShiftCount = element.value;
    }
}