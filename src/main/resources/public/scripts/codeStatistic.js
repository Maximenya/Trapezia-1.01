var buttons  = document.getElementById("buttons");
var regMonthChart = document.getElementById("chart_div");
var statisticArr;

/*var client = {
 "client":
 {"regMonth":
 {"2015-11":"2","2015-10":"1","2016-3":"5","2016-2":"4","2016-5":"3","2016-4":"1","2015-12":"1"},

 "knowFrom":
 {"":"11","redica":"1","друг":"2","интернет":"4","jill":"1"},

 "totalPeoples":
 {"all":19,"ж":4,"м":15},

 "genderAge":
 {"W18_21":0,"W24_27":0,"W21_24":2,"M21_24":2,"M27_30":3,"M24_27":3,"M45":0,"W27_30":0,"M18":2,"W30_35":1,"M18_21":0,"W45":1,"W35_45":0,"M30_35":5,"M35_45":0,"W18":0}},

 "subscription":
 {"popSubscr":
 {"Абонемент безлимитный":"2","Групповые занятия 8 раза в месяц":"1","4 посещения в любое время":"5","Групповые занятия 4 раза в месяц":"2","Абонемент 'Хан-Тенгри'":"1"}
 }
 };*/

var request = new XMLHttpRequest();
var url = "StatisticController";

request.onreadystatechange = function() {
    if (request.readyState == 4 && request.status == 200) {
        statisticArr = JSON.parse(request.responseText);
    }

    showRegMonthChart(statisticArr);
};

request.open("GET", url, true);
request.send();

buttons.onclick = function (event) {
    var target = event.target;
    if (target.tagName != "A") return;
    hideAndShowCharts(target.getAttribute("id"));
};


function showRegMonthChart(statisticArr) {
    var regMonth = [['Дата', 'Количество зарегистрировавшихся']];
    for (key in statisticArr.client.regMonth) {
        var curent = [];
        curent.push(key);
        curent.push(+statisticArr.client.regMonth[key]);
        regMonth.push(curent);
    }

    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable(regMonth);

        var options = {
            title: 'Дата регистрации',
            hAxis: {title: 'Дата',  titleTextStyle: {color: '#333'}},
            vAxis: {minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
}

function hideAndShowCharts(stringId) {
    if (stringId != "regMonth") {
        $(regMonthChart).slideUp(500);
    } else {
        $(regMonthChart).slideDown(500);
    }
    //Необходимо доработать, когда будут все графики
}


