window.onload = function () {
    getTagNameTablePage()
}

function getTagNameTablePage(){
    getTablePage("tag-names")
}

function getReportRowTablePage(){
    getTablePage("report-rows")
}
function getReportTypeTablePage(){
    getTablePage("report-types")
}

function getMeteringNodeTablePage(){
    getTablePage("metering-nodes")
}

function getTablePage(tableName){
    const url = "/admin/recurring-tables-editor/"+tableName;

    fetch(url).then(function (response) {
        // The API call was successful!
        return response.text();
    }).then(function (html) {
        // This is the HTML from our response as a text string
        document.getElementById(tableName).innerHTML = html
    }).catch(function (err) {
        // There was an error
        console.warn('Something went wrong.', err);
    });
}


