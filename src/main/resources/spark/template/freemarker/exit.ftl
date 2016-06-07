<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/visit_style.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css">
    <link rel="shortcut icon" href="stylesheets/img/favicon.ico" type="image/x-icon">
    <script src="scripts/codeExit.js"></script>
</head>
<body>
<div class="all">
    <div class="caption">
        <a href="/climbingList" id="link"></a>
        <p class="caption_text">Конец посещения</p>
    </div> 												<!--Добавила <div id="caption"> и внесла в него ссылку и тег <p> -->
    <div class="contain">
        <div class="reg_form">
            <table id="table">
                <tr>
                    <td>${service.fancyName}</td></tr>
                <td>${client.firstName}</td>
                <td>${client.lastName}</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button id="pro" type="button"  onclick="window.location='/editUser?clientId=${client.clientId?c}'" >Профиль</button>
                </td>
            </tr>
        </table>
        <div class="yellowline"></div>
        <table id="table2">
            <tr>
                <#if rent.harness != 0><td>
                <label for="harness">Обвязка ${rent.harness}шт.</label>
            </td></#if>
        </tr>
        <tr>
            <#if rent.griGri != 0><td>
            <label for="griGri">gri-gri ${rent.griGri}шт.</label>
        </td></#if>
    </tr>
    <tr>
        <#if rent.magnesia != 0><td>
        <label for="magnesia">Магнезия ${rent.magnesia}шт.</label>
    </td></#if>
</tr>
<tr>
    <#if rent.carabine != 0><td>
    <label for="carabine">Карабин ${rent.carabine}шт.</label>
</td></#if>
</tr>
<tr>
    <#if rent.climbingShoes != 0><td>
    <label for="climbingShoes">Скальные туфли ${rent.climbingShoes}шт.</label>
</td></#if>
</tr>
<tr>
<#if service.keyNumber != 0><td>
    <label for="key">Ключ ${service.keyNumber}</label>
</td>
</#if>
        </tr>
        </table>
        </div>
<div class="button2">
<form action="/exit?clientId=${client.clientId?c}" method="post">
    <button id="ton" type="submit" class="buttonSubmit">Ушел</button>
</form>
</div>
        </div>
        </body>
        </html>