/**
 * Created by Denis on 12.01.2016.
 */

window.onload = function () {
    var button = document.getElementById('button');
    button.onclick = function() {
            var  str = document.getElementById('search').value;
            var clientId = str.replace(/\D/g, '');
            window.location.href='/visit?clientId='+clientId;
        };};