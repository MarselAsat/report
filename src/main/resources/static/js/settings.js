var prevShiftCount;
var minutesInput;
document.getElementById('saveButton').addEventListener('click', function(event) {
    event.preventDefault(); // Предотвращаем стандартное действие отправки формы

    var minutes = document.getElementById('start-minute-report').value;

    // Отправляем AJAX-запрос на сервер
    fetch('/api/settings/minute', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ minutes: minutes })
    })
        .then(response => response.json())
        .then(data => {
            // Обработка ответа от сервера
            console.log(data);
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
});
// Определение функции resetMinuteReport в глобальной области видимости
function resetMinuteReport() {
    // Получение элемента responseLabel перед использованием
    var responseLabel = document.getElementById("response");

    // Отправка запроса на сервер для сброса минутного отчета
    fetch("/api/settings/minute", {
        method: "POST"
    })
        .then(response => {
            if (response.ok) {
                responseLabel.innerText = "Сохранено";
                responseLabel.style.color = "green";
            } else {
                responseLabel.innerText = "Что-то пошло не так";
                responseLabel.style.color = "red";
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });

    // Очистка поля ввода start-minute-report и сохранение значения в localStorage
    document.getElementById("start-minute-report").value = "";
    localStorage.setItem("start-minute-report", "");
}

document.addEventListener('DOMContentLoaded', function() {
    // Получение элемента minutesInput после полной загрузки DOM
    minutesInput = document.getElementById("start-minute-report");
    var minutes = localStorage.getItem("start-minute-report");
    if (minutes !== null) {
        minutesInput.value = minutes;
    }

    // Привязка функции к обработчику события onclick кнопки
    document.getElementById("resetMinuteReportButton").addEventListener("click", resetMinuteReport);
});

function save(){
    var reportTypes = ['hour', 'twohour', 'daily', 'shift', 'month', 'year' , 'minute'];
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
    settings.set("daily report columns", document.getElementById("start-daily-report").value)
    settings.set("month report columns", document.getElementById("start-month-report").value)
    settings.set("year report columns", document.getElementById("start-year-report").value)
    settings.set("minute report columns", document.getElementById("start-minute-report").value)

    var minutes = document.getElementById('start-minute-report').value;
    fetch('/api/settings/minute', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ minutes: minutes })
    })
        .then(response => response.json())
        .then(data => {
            // Обработка ответа от сервера
            console.log(data);
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });

    let shiftSettings = [];
    let shifts = document.getElementsByClassName("shift-start-time");
    for(var shift of shifts){
        let shiftName = shift.getElementsByClassName("shift-name")[0].innerText;
        let wordsInShiftName = shiftName.split(" ");
        const shiftNum = wordsInShiftName[wordsInShiftName.length-1];
        var shiftTime = shift.getElementsByClassName("shift-time")[0].value;
        shiftSettings.push(shiftNum+"-"+shiftTime);
    }
    settings.set('shift report columns', shiftSettings.join(","));
    var minuteReportValue = document.getElementById("start-minute-report").value;
    localStorage.setItem("start-minute-report", minuteReportValue);
    updateSettingsInDB(settings);

}

async function updateSettingsInDB(settings) {
    var url = "/api/settings";
    let response = await fetch(url, {
        method: "PATCH",
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


// Привязка функции к обработчику события onclick кнопки
    //let resetMinuteReport = document.getElementById("resetMinuteReportButton").addEventListener("click", resetMinuteReport);
    // resetMinuteReport.addEventListener("click", async function () {
    //     var url = "/api/settings/minute";
    //     let response = await fetch(url, {
    //         method: "POST",
    //         body: JSON.stringify(),
    //         headers: {
    //             'Content-Type': 'application/json'
    //         }
    //     })
    //     let responseText = await response.text();
    //     let responseLabel = document.getElementById("response");
    //     if (responseText === "true") {
    //         responseLabel.innerText = "Сохранено";
    //         responseLabel.style.color = "green";
    //     } else {
    //         responseLabel.innerText = "Что-то пошло не так";
    //         responseLabel.style.color = "red";
    //     }
    // })
}