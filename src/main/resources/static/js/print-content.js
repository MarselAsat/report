function printContent(){

    const printContents = document.getElementById("report-content").innerHTML;
    let myWindow = window.open();

    myWindow.document.write(printContents);
    myWindow.document.write('<scr' + 'ipt type="text/javascript">' + 'window.onload = function() { window.print(); window.close(); };' + '</sc' + 'ript>');

    myWindow.document.close(); // necessary for IE >= 10
    myWindow.focus(); // necessary for IE >= 10

    return true;
}