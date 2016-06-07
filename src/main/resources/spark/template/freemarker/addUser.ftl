<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/visit_style.css">
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
</html>