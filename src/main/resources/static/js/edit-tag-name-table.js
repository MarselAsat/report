var changedRows = new Set();
var deletedRows = new Set();
var initialValues = {};
var dataTable;

$(document).ready( function () {
    dataTable = $('#tag-name-table').DataTable(
        {
            paging: false,
            ordering: false,
            info: false,
            language: {
                "url": "//cdn.datatables.net/plug-ins/1.13.1/i18n/ru.json"
            }
        }
    );
} );

window.onload = function () {
    $("textarea").each(function () {
        if(this.scrollHeight===0){
            this.style.height="2em";
        }
        else{
            this.style.height = (this.scrollHeight) + "px";
        }
        this.style.overflowY = "hidden";
    }).on("input", function () {
        this.style.height = 0;
        this.style.height = (this.scrollHeight) + "px";
    });
}

function writeInitialValue(element){
    var tagName = element.parentElement.parentElement;
    var id = tagName.id;
    if(initialValues[id]==null){
            var name = tagName.getElementsByClassName("name")[0].value;
            var description = tagName.getElementsByClassName("description")[0].value;
            var reportType = tagName.getElementsByClassName("report-type")[0].value;
            initialValues[id] = {"name": name, "description": description, "reportTypeId": reportType};
    }
}

function tagNameIsChanged(element){
    var tagNameRow = element.parentElement.parentElement;
    var id = tagNameRow.id;
    changedRows.add(id);

    console.log(changedRows);
}

function saveChangedTagNames(){

    var changedTagNameList = [];
    var responsesTd = document.getElementsByClassName("response");
    for(let r of responsesTd){
        r.innerHTML = "";
    }

    fillChangedTagNameList(changedTagNameList);
    updateTagNamesInDB(changedTagNameList);

    // delete strikeout rows from db
    for(const id of deletedRows){
        var tagNameRow = document.getElementById(id);
        deleteTagNameFromDB(id, tagNameRow);
    }
    saveNewTagNames();
    deletedRows.clear();
    changedRows.clear();
    initialValues = {};
}

function fillChangedTagNameList(changedTagNameList){
    for (const id of changedRows) {
        var tagNameRow = document.getElementById(id);
        if(!tagNameRow.classList.contains("strikeout")){
            var tagName = {};
            var name = tagNameRow.getElementsByClassName("name")[0].value;
            var description = tagNameRow.getElementsByClassName("description")[0].value;
            var reportType = tagNameRow.getElementsByClassName("report-type")[0].value;
            if(name!==initialValues[id].name ||
                description!== initialValues[id].description ||
                reportType!== initialValues[id].reportType){
                tagName.id = id;
                tagName.name = name;
                tagName.description = description;
                tagName.reportTypeId = reportType;
                changedTagNameList.push({...tagName});
            }
        }
    }
}
async function updateTagNamesInDB(changedTagNameList){
    var url = "/admin/tagName";
    let response = await fetch(url, {
                             method: "POST",
                             body: JSON.stringify(changedTagNameList),
                             headers: {'Content-Type': 'application/json'
    }})
    let responseJson = await response.json();
    for (var id in responseJson) {
        var tagNameRow = document.getElementById(id);
        var tagNameRowName = tagNameRow.getElementsByClassName('name')[0];
        var tagNameRowDescription = tagNameRow.getElementsByClassName('description')[0];
        var tagNameRowReportType = tagNameRow.getElementsByClassName('report-type')[0];
        var label = "";
        let responseTd = tagNameRow.getElementsByClassName('response')[0];
        if(responseJson[id]===true){
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

}
function highlightRow(buttonX){
    var tagNameRow = buttonX.parentElement.parentElement;
    if(tagNameRow.classList.contains("highlight-red")){
        tagNameRow.classList.remove("highlight-red");
        deletedRows.delete(tagNameRow.id);
    }
    else{
        tagNameRow.classList.add("highlight-red");
        deletedRows.add(tagNameRow.id);
    }
}

async function deleteTagNameFromDB(id, tagNameRow){
    var url = "/admin/tagName/"+id;
        let response = await fetch(url, {
                             method: "DELETE",
                             headers: {'Content-Type': 'application/json'
        }})
        let responseJson = await response.json();
        if(responseJson[id]===true){
            deleteTagNameRow(tagNameRow);
        }
        else{
            tagNameRow.classList.remove("strikeout");
            let responseTd = tagNameRow.getElementsByClassName('response')[0];
            responseTd.style.color = "red";
            responseTd.innerHTML = "can't be deleted";
        }
}
function deleteTagNameRow(tagNameRow){
    tagNameRow.remove();
    dataTable.row(tagNameRow).remove().draw();
}

function createNewRow(){
    var tagNameTable = document.getElementById('tag-name-tbody');
    var tagNameRow = tagNameTable.insertRow(0);
    tagNameRow.innerHTML = document.getElementById('hidden-tag-name-row').innerHTML;
    tagNameRow.className="new-tag-name";
}

async function saveNewTagNames(){
    var newTagNameRows = document.getElementsByClassName("new-tag-name")

    while(newTagNameRows.length > 0) {
        var newTagNameRow = newTagNameRows[0];
        let name = newTagNameRow.getElementsByClassName('name')[0];
        let description = newTagNameRow.getElementsByClassName('description')[0];
        let reportType = newTagNameRow.getElementsByClassName('report-type')[0];
        let url = "/admin/tagName/new";

        // Converting JSON data to string
        var data = JSON.stringify({ "name": name.value, "description": description.value, "reportTypeId": reportType.value});
        let response = await fetch(url, {
                             method: "POST",
                             body: data,
                             headers: {'Content-Type': 'application/json'
        }})

        let savedTagNameId = await response.text();

        let responseTd = newTagNameRow.getElementsByClassName('response')[0];
        responseTd.style.color = "red";

        if(savedTagNameId===""){
            responseTd.style.color = "red";
            responseTd.innerHTML = "isn't saved";

        }
        else{
            responseTd.style.color = "green";
            responseTd.innerHTML = "is saved";
            newTagNameRow.id = savedTagNameId;
            newTagNameRow.classList.remove("new-tag-name")
            var deleteButton = newTagNameRow.getElementsByClassName("delete-button")[0]
            deleteButton.onclick = function(){
                highlightRow(this);
            };
        }
    }
}