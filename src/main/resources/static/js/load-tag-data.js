function getTagData(id) {
    fetch(contextPath + "api/tagData/"+id, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const tagDataDiv = document.getElementById("tagData");
            tagDataDiv.innerHTML = '';
            var listLength = data.length;
            for (let i = 0; i < listLength; i++) {
                let li = document.createElement('tr');
                console.log(data);
                li.innerHTML = '<th>'+data[i].tagName+'</th><th>'+data[i].data+'</th><th>'+data[i].date+'</th><th>'+data[i].reportType+'</th>';
                tagDataDiv.appendChild(li);
            }
        })
}

function logout(){
    window.location.href = "http://localhost:8080/logout";
}
function toNewTagName(){
    window.location.href = "http://localhost:8080/admin/tagName/new";
}
function toTagNameEditor(){
    window.location.href = "http://localhost:8080/admin/tagName";
}
function updatePassword(){
    window.location.href = "http://localhost:8080/user/updatePassword";
}