var changedRows = new Set();
var deletedRows = new Set();
var initialValues = {};

window.onload = function () {
    $("textarea").each(function () {
        this.style.height = (this.scrollHeight) + "px";
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
            var initialValue = { "name": name, "description":description};
            initialValues[id] = initialValue;
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
}

function fillChangedTagNameList(changedTagNameList){
    for (const id of changedRows) {
        var tagNameRow = document.getElementById(id);
        if(!tagNameRow.classList.contains("strikeout")){
            var tagName = {};
            var permanentName = tagNameRow.getElementsByClassName("permanent-name")[0].textContent;
            var name = tagNameRow.getElementsByClassName("name")[0].value;
            var description = tagNameRow.getElementsByClassName("description")[0].value;
            if(name!==initialValues[id].name ||
                description!== initialValues[id].description){
                tagName.id = id;
                tagName.permanentName = permanentName;
                tagName.name = name;
                tagName.description = description;
                changedTagNameList.push({...tagName});
            }
        }
    }
}
async function updateTagNamesInDB(changedTagNameList){
    var url = "/admin/manualTagName";
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
        }
        responseTd.innerHTML = label;
    }

    deletedRows.clear();
    changedRows.clear();
    initialValues = {};

}
function strikeoutRow(buttonX){
    var tagNameRow = buttonX.parentElement.parentElement;
    if(tagNameRow.classList.contains("strikeout")){
        tagNameRow.classList.remove("strikeout");
        deletedRows.delete(tagNameRow.id);
    }
    else{
        tagNameRow.classList.add("strikeout");
        deletedRows.add(tagNameRow.id);
    }
}

async function deleteTagNameFromDB(id, tagNameRow){
    var url = "/admin/manualTagName/"+id;
        let response = await fetch(url, {
                             method: "DELETE",
                             body: JSON.stringify(tagNameRow),
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
}

