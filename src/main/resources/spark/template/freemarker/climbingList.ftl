<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<#--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />-->
    <link type="text/css" rel="stylesheet" href="stylesheets/climbingList.css">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <script src="scripts/autocompleter.js"></script>
    <script src="scripts/codeCL.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
</head>

<body>
<div id="all">
    <div id="caption">
        <p id="caption_text">Главная</p>
    </div>
    <div id="contain">
        <p id="main">Сейчас на скалодроме:</p>
        <div id="list">
            <table>
                <tr>
                    <td>
					<#list clients as client>
                        <a href="/exit?clientId=${client.clientId?c}">${client.firstName} ${client.lastName}</a><br>
					</#list>
                    </td>
                </tr>
            </table>
        </div>
<div>Всего ${count}</div>
        <div class="search-container">
            <div class="ui-widget">
                <input type="text" id="search" name="search" class="search" placeholder="Введите ФИО"/>
            </div>
        </div>
        <button id="button" type="button">Оформить</button>
        <div id="yellowline"></div>
        <button id="button2" type="button" onclick="javascript:window.location='/addUser'">Зарегистрировать</button>
    </div>
</div>
</body>
</html>