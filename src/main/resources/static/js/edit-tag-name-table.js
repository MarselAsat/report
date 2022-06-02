var changedRows = new Set();
var initialValues = {};

function writeInitialValue(element){
    var tagName = element.parentElement.parentElement;
    var id = tagName.id;
    if(initialValues[id]==null){
            var name = tagName.getElementsByClassName("name")[0].value;
            var description = tagName.getElementsByClassName("description")[0].value;
            var reportType = tagName.getElementsByClassName("report-type")[0].value;
            var initialValue = { "name": name, "description":description, "reportType": reportType};
            initialValues[id] = initialValue;
    }
}

function tagNameIsChanged(element){
    var tagName = element.parentElement.parentElement;
    var id = tagName.id;
    changedRows.add(id);

    console.log(changedRows);
}

async function saveChangedTagName(){

    var tagNameList = new Array();
    let responseField = document.getElementById('responses');
    for (const id of changedRows) {
        var tagNameRow = document.getElementById(id);
        var tagName = {};
        tagName.id = id;
        tagName.name = tagNameRow.getElementsByClassName("name")[0].value;;
        tagName.description = tagNameRow.getElementsByClassName("description")[0].value;
        tagName.reportType = tagNameRow.getElementsByClassName("report-type")[0].value;
        tagNameList.push({...tagName});
    }
    var url = "/startPage/tagName";
    let response = await fetch(url, {
                         method: "POST",
                         body: JSON.stringify(tagNameList),
                         headers: {'Content-Type': 'application/json'
    }})
    let responseJson = await response.json();
    var responsesTd = document.getElementsByClassName("response");
    for(let r of responsesTd){
        r.innerHTML = "";
    }
    for (var id in responseJson) {
        var tagNameRow = document.getElementById(id);
        var tagNameRowName = tagNameRow.getElementsByClassName('name')[0];
        var tagNameRowDescription = tagNameRow.getElementsByClassName('description')[0];
        var tagNameRowReportType = tagNameRow.getElementsByClassName('report-type')[0];
        var label = "";
        let responseTd = tagNameRow.getElementsByClassName('response')[0];
        if(responseJson[id]==true){
            responseTd.style.color = "green";
            label = " is updated";
        }
        else{
            responseTd.style.color = "red";
            label = " isn't updated";

            tagNameRowName.value = initialValues[id].name;
            tagNameRowDescription.value = initialValues[id].description;
            tagNameRowReportType.value = initialValues[id].reportType;
        }
        responseTd.innerHTML = label;
    }
    changedRows.clear();
    initialValues = {};

}