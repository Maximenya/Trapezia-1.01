window.onload = function () {
    var button = document.getElementById('button');
    var input = document.getElementById('search');
    input.onkeyup = function() {
        if (this.value.length > 0) {
            button.disabled = false;
        }
        else {
            button.disabled = true;
        }
    };
    button.onclick = function() {
            var  str = document.getElementById('search').value;
            var clientId = str.replace(/\D/g, '');
            window.location.href='/visit?clientId='+clientId;
    };
};