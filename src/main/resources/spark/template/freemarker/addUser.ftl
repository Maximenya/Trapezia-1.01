<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/addUser_style.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css">
    <link rel="shortcut icon" href="../../../public/stylesheets/img/favicon.ico" type="image/x-icon">
    <script src="scripts/codeAddUser.js"></script>
</head>
<body>
<div class="all">
    <div class="caption">
        <a href="/climbingList" id="link"></a>
        <p class="caption_text">Регистрация</p>
    </div>
    <div class="contain">
        <form action="/addUser" method="post">
            <p id="invalid"></p>
            <div class="table">
                <div class="row">
                    <label for="lastName">Фамилия</label>
                    <input type="text" class="info" id="lastName" name="lastName" autofocus>
                </div>
                <div class="row">
                    <label for="firstName">Имя</label>
                    <input type="text" class="info" id="firstName" name="firstName">
                </div>
                <div class="row">
                    <label for="middleName">Отчество</label>
                    <input type="text" class="info" id="middleName" name="middleName">
                </div>
                <div class="row">
                    <label for="phone">Телефон</label>
                    <input type="tel" class="info" id="phone" name="phone" pattern="[+][0-9 ]+" value="+375 ">
                </div>
                <div class="row">
                    <label for="eMail">E-mail</label>
                    <input type="email" class="info" id="eMail" name="eMail">
                </div>
                <div class="row">
                    <label for="document">Документ</label>
                    <input type="text" class="info" id="document" name="document">
                </div>
                <div class="row">
                    <label for="sex">Пол</label>
                    <select class="info" id="sex" name="sex">
                        <option value="м">Мужской</option>
                        <option value="ж">Женский</option>
                    </select>
                </div>
                <div class="row">
                    <label for="birthDate">Дата рождения</label>
                    <input type="date" class="info" id="birthDate" name="birthDate">
                </div>
                <div class="row">
                    <label for="knowFrom">Узнали от</label>
                    <input type="text" class="info" id="knowFrom" name="knowFrom" >
                </div>
            </div>
            <button class="push" id="but" type="submit">Добавить</button>
        </form>
    </div>
</div>
</body>
</html>

<!--<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/addUser_style.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css">
    <link rel="shortcut icon" href="stylesheets/img/favicon.ico" type="image/x-icon">
    <script src="scripts/codeAddUser.js"></script>
</head>
<body>
<div class="all">
    <div class="caption">
        <a href="/climbingList" id="link"></a>
        <p class="caption_text">Регистрация</p>
    </div>
    <div class="contain">
        <form action="/addUser" method="post">
            <p id="invalid"></p>
            <table id="table">
                <tr>
                    <td>
                        <label for="lastName">Фамилия</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="lastName" name="lastName" autofocus />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="firstName">Имя</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="firstName" name="firstName" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="middleName">Отчество</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="middleName" name="middleName" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="phone">Телефон</label>
                    </td>
                    <td>
                        <input type="tel" class="one" id="phone" name="phone" pattern="[+][0-9 ]+" value="+375 " />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="eMail">E-mail</label>
                    </td>
                    <td>
                        <input type="email" class="one" id="eMail" name="eMail" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="document">Документ</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="document" name="document"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="sex">Пол</label>
                    </td>
                    <td>
                        <select class="one" id="sex" name="sex" />
                        <option value="м">Мужской</option>
                        <option value="ж">Женский</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="birthDate">Дата рождения</label>
                </td>
                <td>
                    <input type="date" class="one" id="birthDate" name="birthDate"/>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="knowFrom">Узнали от</label>
                </td>
                <td>
                    <input type="text" class="one" id="knowFrom" name="knowFrom" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button id="but" type="submit">Добавить</button>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>-->