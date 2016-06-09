<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/editUser_style.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css" />
    <link rel="shortcut icon" href="../../../public/stylesheets/img/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="all">
    <div class="caption">
        <a href="/climbingList" id="link"></a>
        <p class="caption_text">Информация о клиенте</p>
    </div>
    <div class="contain">
        <form action="/editUser" method="post">
            <div class="table">
                <div class="row">
                    <label for="clientId">ID</label>
                    <input type="text" class="info" id="clientId" name="clientId" readonly  value="${client.clientId?c}"/>
                </div>
                <div class="row">
                    <label for="lastName">Фамилия</label>
                    <input type="text" class="info" id="lastName" name="lastName" autofocus value="${client.lastName}"/>
                </div>
                <div class="row">
                    <label for="firstName">Имя</label>
                    <input type="text" class="info" id="firstName" name="firstName" value="${client.firstName}"/>
                </div>
                <div class="row">
                    <label for="middleName">Отчество</label>
                    <input type="text" class="info" id="middleName" name="middleName" value="${client.middleName}" />
                </div>
                <div class="row">
                    <label for="phone">Телефон</label>
                    <input type="tel" class="info" id="phone" name="phone" pattern="[+][0-9 ]+" value="${client.phone}" />
                </div>
                <div class="row">
                    <label for="eMail">E-mail</label>
                    <input type="email" class="info" id="eMail" name="eMail" value="${client.email}"/>
                </div>
                <div class="row">
                    <label for="document">Документ</label>
                    <input type="text" class="info" id="document" name="document" value="${client.document}"/>
                </div>
                <div class="row">
                    <label for="sex">Пол</label>
                    <input type="text" class="info" id="sex" name="sex" value="${client.sex}"/>
                </div>
                <div class="row">
                    <label for="knowFrom">Узнали от</label>
                    <input type="text" class="info" id="knowFrom" name="knowFrom" value="${client.knowFrom}"/>
                </div>
                <div class="row">
                    <label for="birthDate">Дата рождения</label>
                    <input type="date" class="info" id="birthDate" name="birthDate" value="${client.birthDate}"/>
                </div>
                <div class="row">
                    <label for="registrationDate">Дата регистрации</label>
                    <input type="date" class="info" id="registrationDate" name="registrationDate" value="${client.registrationDate}"/>
                </div>
            </div>
            <button id="but" class="push" type="submit">Применить</button>

            <button id="ton" class="push" type="button">Редактировать</button>

            <form action="/deleteClient?clientId=${client.clientId?c}" method="post">
                <button id="but" class="push" type="submit">Удалить</button>
            </form>
        </form>
        <div class="grayline"></div>
        <form action="/editSubscription" method="post">
            <#list subscriptions as subscription class="central">
            <div class="table">
                <div class="row">
                    <label for="subscriptionId">ID</label>
                    <input type="text" class="info" id="subscriptionId" name="subscriptionId" readonly  value="${subscription.subscriptionId?c}"/>
                </div>
                <div class="row">
                    <label for="clientId">ID</label>
                    <input type="text" class="info" id="clientId" name="clientId" readonly  value="${subscription.clientId?c}"/>
                </div>
                <div class="row">
                    <label for="saleTime">Дата продажи</label>
                    <input type="text" class="info" id="saleTime" name="saleTime" autofocus value="${subscription.saleTime}"/>
                </div>
                <div class="row">
                    <label for="firstName">Дата начала</label>
                    <input type="date" class="info" id="firstDate" name="firstDate" value="${subscription.firstDate}"/>
                </div>
                <div class="row">
                    <label for="middleName">Дата окончания</label>
                    <input type="date" class="info" id="lastDate" name="lastDate" value="${subscription.lastDate}" />
                </div>
                <div class="row">
                    <label for="counter">Осталось посещений</label>
                    <input type="text" class="info" id="counter" name="counter"  value="${subscription.counter}" />
                </div>
                <div class="row">
                    <label for="type">Тип абонемента</label>
                    <select name="type" class="ser info" id="type">
                        <option value="${subscription.type}">${subscription.fancyName}</option>
                        <option value="22">4 посещения в любое время, на месяц</option>
                        <option value="21">Абонемент безлимитный 'дневной'</option>
                        <option value="23">Абонемент безлимитный на месяц</option>
                        <option value="24">Групповые занятия с тренером для взрослых 4 раза в месяц</option>
                        <option value="25">Групповые детские занятия с тренером 1 раз в неделю</option>
                        <option value="26">Групповые детские занятия с тренером 2 раз в неделю</option>
                    </select>
                </div>
                <div class="row">
                    <label for="current">Действителен</label>
                    <select name="current" class="ser info" id="current">
                        <option value="${subscription.current}">Да</option>
                        <option value="0">Нет</option>
                    </select>
                </div>
            </div>
            <button id="but" class="push" type="submit">Применить</button>

            <button id="ton" class="push" type="button">Редактировать</button>
        </#list>
    </form>
</div>
</div>
</body>
        </html><!--<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link type="text/css" rel="stylesheet" href="../../../public/stylesheets/editUser_style.css">
    <link rel="stylesheet" href="../../../public/stylesheets/shape.css" />
    <link rel="shortcut icon" href="stylesheets/img/favicon.ico" type="image/x-icon">
</head>
<body>
<div class="all">
    <div class="caption">
        <a href="/climbingList" id="link"></a>
        <p class="caption_text">Информация о клиенте</p>
    </div>
    <div class="contain">
        <form action="/editUser" method="post">
            <table id="table">
                <tr>
                    <td>
                        <label for="clientId">ID</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="clientId" name="clientId" readonly  value="${client.clientId?c}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="lastName">Фамилия</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="lastName" name="lastName" autofocus value="${client.lastName}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="firstName">Имя</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="firstName" name="firstName" value="${client.firstName}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="middleName">Отчество</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="middleName" name="middleName" value="${client.middleName}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="phone">Телефон</label>
                    </td>
                    <td>
                        <input type="tel" class="one" id="phone" name="phone" pattern="[+][0-9 ]+" value="${client.phone}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="eMail">E-mail</label>
                    </td>
                    <td>
                        <input type="email" class="one" id="eMail" name="eMail" value="${client.email}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="document">Документ</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="document" name="document" value="${client.document}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="sex">Пол</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="sex" name="sex" value="${client.sex}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="knowFrom">Узнали от</label>
                    </td>
                    <td>
                        <input type="text" class="one" id="knowFrom" name="knowFrom" value="${client.knowFrom}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="birthDate">Дата рождения</label>
                    </td>
                    <td>
                        <input type="date" class="one" id="birthDate" name="birthDate" value="${client.birthDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="registrationDate">Дата регистрации</label>
                    </td>
                    <td>
                        <input type="date" class="one" id="registrationDate" name="registrationDate" value="${client.registrationDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <button id="but" type="submit">Применить</button>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <button id="ton" type="button">Редактировать</button>
                    </td>
                </tr>
                <tr>
                    <td>
                    </td>
                    <td>
                        <form action="/deleteClient?clientId=${client.clientId?c}" method="post">
                            <button id="but" type="submit">Удалить</button>
                        </form>
                    </td>
                </tr>
            </table>
        </form>
        <div id="yellowline"></div>
        <div  id="table2">
            <form action="/editSubscription" method="post">
                <#list subscriptions as subscription>
                <table>
                    <tr>
                        <td>
                            <label for="subscriptionId">ID</label>
                        </td>
                        <td>
                            <input type="text" class="one" id="subscriptionId" name="subscriptionId" readonly  value="${subscription.subscriptionId?c}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="clientId">ID</label>
                        </td>
                        <td>
                            <input type="text" class="one" id="clientId" name="clientId" readonly  value="${subscription.clientId?c}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="saleTime">Дата продажи</label>
                        </td>
                        <td>
                            <input type="text" class="one" id="saleTime" name="saleTime" autofocus value="${subscription.saleTime}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="firstName">Дата начала</label>
                        </td>
                        <td>
                            <input type="date" class="one" id="firstDate" name="firstDate" value="${subscription.firstDate}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="middleName">Дата окончания</label>
                        </td>
                        <td>
                            <input type="date" class="one" id="lastDate" name="lastDate" value="${subscription.lastDate}" />
                        </td>
                    </tr>>
                    <tr>
                        <td>
                            <label for="counter">Осталось посещений</label>
                        </td>
                        <td>
                            <input type="text" class="one" id="counter" name="counter"  value="${subscription.counter}" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="type">Тип абонемента</label>
                        </td>
                        <td colspan="5">
                            <select name="type" class="ser one" id="type">
                                <option value="${subscription.type}">${subscription.fancyName}</option>
                                <option value="22">4 посещения в любое время, на месяц</option>
                                <option value="21">Абонемент безлимитный 'дневной'</option>
                                <option value="23">Абонемент безлимитный на месяц</option>
                                <option value="24">Групповые занятия с тренером для взрослых 4 раза в месяц</option>
                                <option value="25">Групповые детские занятия с тренером 1 раз в неделю</option>
                                <option value="26">Групповые детские занятия с тренером 2 раз в неделю</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="current">Действителен</label>
                        </td>
                        <td>
                            <select name="current" class="ser" id="current">
                                <option value="${subscription.current}">Да</option>
                                <option value="0">Нет</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        </td>
                        <td>
                            <button id="but" type="submit">Применить</button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        </td>
                        <td>
                            <button id="ton" type="button">Редактировать</button>
                        </td>
                    </tr>
                </table>
            </#list>
        </form>
    </div>
</div>
</div>
</body>
        </html>-->