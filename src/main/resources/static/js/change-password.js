function savePass(){
    var newPass = document.getElementById("password").value;
    var currPass = document.getElementById("old-password").value;
    var valid = newPass === document.getElementById("confirm-password").value;
    let messageField = document.getElementById("message");
    if(!valid) {
        messageField.style.display = 'block';
        messageField.className = "alert alert-danger";
        messageField.innerText = "Пароли не совпадают"
      return;
    }
    fetch("/user/updatePassword", {
        method: 'PATCH',
        body: JSON.stringify({
            currentPassword: currPass,
            newPassword: newPass
        }),
        headers: {
            'Content-type': 'application/json; charset=UTF-8',
        }}
    )
        .then(response => response.text())
        .then(data => {
            if(data === "The current password is not valid"){
                messageField.style.display = 'block';
                messageField.className = "alert alert-danger";
                messageField.innerText = "Неверный текущий пароль"
            }
            else if(data === "The password was changed successfully"){
                messageField.style.display = 'block';
                messageField.className = "alert alert-success";
                messageField.innerText = "Пароль успешно изменен"
            }
        })
}