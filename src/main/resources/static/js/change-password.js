function savePass(){
    var pass = document.getElementById("pass").value;
    var oldpass = document.getElementById("oldpass").value;
    var valid = pass == document.getElementById("passConfirm").value;
    if(!valid) {
      document.getElementById("error").style.display = 'block';
      return;
    }
    fetch("/user/updatePassword?password="+pass+"&oldpassword="+oldpass, {method: 'POST'})
        .then(response => response.text())
        .then(data => {
            document.getElementById("errormsg").innerHTML = data;
        })
}