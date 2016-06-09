<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <!--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />-->
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/climbingList.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css">
    <link rel="shortcut icon" href="../../../public/stylesheets/img/favicon.ico" type="image/x-icon">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <script src="scripts/autocompleter.js"></script>
    <script src="scripts/codeCL.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
</head>

<body>
<div class="all">
    <div class="caption">
        <p class="caption_text">Главная</p>
    </div>
    <div class="contain">
        <p>Сейчас на скалодроме:</p>
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
        <div  id="sum">Всего ${count}</div>
        <div class="grayline"></div>
        <div class="search-container">
            <div class="ui-widget">
                <input type="text" class="info" id="search" name="search" class="search" placeholder="Введите ФИО"/>
            </div>
        </div>
        <button id="button" class="push" type="button">Оформить</button>
        <div class="grayline"></div>
        <button id="button2" class="push" type="button" onclick="javascript:window.location='/addUser'">Зарегистрировать</button>
    </div>
</div>
</body>
</html>
<!--<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/visit_style.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css">
    <link rel="shortcut icon" href="stylesheets/img/favicon.ico" type="image/x-icon">
    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <script src="scripts/autocompleter.js"></script>
    <script src="scripts/codeCL.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
</head>

<body>
<div class="all">
    <div class="caption">
        <p class="caption_text">Главная</p>
    </div>
    <div class="contain">
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
        <div  id="sum">Всего ${count}</div>
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
</html>-->