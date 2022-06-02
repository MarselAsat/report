function createTagNameForm(){
    let tagNameForms = document.getElementById('tag-name-forms');
    let tagNameForm = document.getElementById('tag-name-form');
    let tagNameFormNew = tagNameForm.cloneNode(true);
    tagNameFormNew.getElementsByClassName("name")[0].value = "";
    tagNameFormNew.getElementsByClassName("description")[0].value = "";
    tagNameForms.append(tagNameFormNew);
}
async function sendTagNames(){
     let responseField = document.getElementById('responses');
     let nameInputs = document.getElementsByClassName('name');
     let descriptionInputs = document.getElementsByClassName('description');
     let reportTypeInputs = document.getElementsByClassName('report-type');
     var responsesArr = new Array();
     console.log(nameInputs);
     var numForm = nameInputs.length;

    for (let i = 0; i < numForm; i++) {
        let name = nameInputs[i];

        let description = descriptionInputs[i];

        let reportType = reportTypeInputs[i];

        let url = "/startPage/tagName/new";

        // Converting JSON data to string
        var data = JSON.stringify({ "name": name.value, "description": description.value, "reportType": reportType.value});

        let responseP = document.createElement('p');
        let response = await fetch(url, {
                               method: "POST",
                               body: data,
                               headers: {'Content-Type': 'application/json'
        }})

        let text = await response.text();
        var label = name.value;
        if(text=="true"){
            responseP.style.color = "green";
            label = label+" is saved";
        }
        else{
            responseP.style.color = "red";
            label = label+" isn't saved";
        }
        responseP.innerHTML = label;
        responseField.appendChild(responseP);
    }
}