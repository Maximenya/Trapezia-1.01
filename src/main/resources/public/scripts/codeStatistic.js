window.onload = function () {
    google.charts.load('current', {'packages':['corechart', 'timeline']});
    var buttons  = document.getElementById("buttons");

    var request = new XMLHttpRequest();
     var url = "StatisticController";

     request.onreadystatechange = function() {
         if (request.readyState == 4 && request.status == 200) {
             var statisticArr = JSON.parse(request.responseText);
         }
         
         showRegMonthChart(statisticArr);
         
         buttons.onclick = function (event) {
             var target = event.target;
             if (target.tagName != "A") return;
             hideAndShowCharts(target.getAttribute("id"));
         };
         
         window.onresize = function () {
             var stringId = getIdOfShowedChart();
             var height = getOffsetParameters('offsetHeight');
             var width = getOffsetParameters('offsetWidth');
             if (stringId == "chart_div_reg_month") {
                 showRegMonthChart(statisticArr, height, width);
             } else if (stringId == "chart_div_total_people") {
                 showTotalPeopleChart(statisticArr, height, width);
             } else if (stringId == "chart_div_pop_subscr") {
                 showPopSubscrChart(statisticArr, height, width);
             } else if (stringId == "chart_div_gender_age") {
                 showGenderAgeChart(statisticArr, height, width);
             } else if (stringId == "chart_div_know_from") {
                 showKnowFromChart(statisticArr, width);
             } else if (stringId == "chart_div_attendance") {
                 showAttendanceChart(statisticArr, height, width);
             }
         };

         function hideAndShowCharts(stringId) {
             var height = getOffsetParameters('offsetHeight');
             var width = getOffsetParameters('offsetWidth');
             var activeChartId = "#" + getIdOfShowedChart();
             $(activeChartId).hide(350);
             if (stringId == "regMonth") {
                 showRegMonthChart(statisticArr, height, width);
                 $("#chart_div_reg_month").show(350);
             } else if (stringId == "totalPeople") {
                 showTotalPeopleChart(statisticArr, height, width);
                 $("#chart_div_total_people").show(350);
             } else if (stringId == "popSubscr") {
                 showPopSubscrChart(statisticArr, height, width);
                 $("#chart_div_pop_subscr").show(350);
             } else if (stringId == "genderAge") {
                 showGenderAgeChart(statisticArr, height, width);
                 $("#chart_div_gender_age").show(350);
             } else if (stringId == "knowFrom") {
                 showKnowFromChart(statisticArr, width);
                 $("#chart_div_know_from").show(350);
             } else if (stringId == "attendance") {
                 showAttendanceChart(statisticArr, height, width);
                 $("#chart_div_attendance").show(350);
             }
         }
     };

     request.open("GET", url, true);
     request.send();
};

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

function showRegMonthChart(statisticArr, height, width) {
    var regMonth = makeDataArr(statisticArr.client.regMonth);
    regMonth.sort(compareElementsByDate);
    regMonth.unshift(['Дата', 'Количество зарегистрировавшихся']);

    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable(regMonth);

        var options = {
            backgroundColor: '#f7f7f5',
            width: width,
            height: height,
            title: 'Дата регистрации',
            hAxis: {title: 'Дата',  titleTextStyle: {color: '#333'}},
            vAxis: {minValue: 0},
            legend: {position: 'top'}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div_reg_month'));
        chart.draw(data, options);
    }
}

function showTotalPeopleChart(statisticArr, height, width) {
    var totalPeople = makeDataArr(statisticArr.client.totalPeoples);
    for (var i = 0; i < totalPeople.length; i++) {
        if (totalPeople[i][0] == 'all') {
            totalPeople[i][0] = 'Всего посетителей';
            totalPeople[i].push('#C044F7');
        }
        if (totalPeople[i][0] == 'м') {
            totalPeople[i][0] = 'Мужчины';
            totalPeople[i].push('#2BA2E0');
        }
        if (totalPeople[i][0] == 'ж') {
            totalPeople[i][0] = 'Женщины';
            totalPeople[i].push('#E05EB3');
        }
    }
    totalPeople.sort(compareElementsByName);
    totalPeople.unshift(['', '', {role: 'style'}]);

    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {

        var data = google.visualization.arrayToDataTable(totalPeople);

        var options = {
            backgroundColor: '#f7f7f5',
            title: 'Количество уникальных посетителей',
            chartArea: {width: '50%'},
            hAxis: {
                title: 'Количество',
                minValue: 0
            },
            vAxis: {
                title: ''
            },
            width: width,
            height: height,
            legend: { position: "none" }
        };

        var chart = new google.visualization.BarChart(document.getElementById('chart_div_total_people'));

        chart.draw(data, options);
    }
}

function showPopSubscrChart(statisticArr, height, width) {
    var popSubscr = makeDataArr(statisticArr.subscription.popSubscr);
    popSubscr.sort(compareElementsByValue);
    popSubscr.unshift(['Вид абонемента', 'Количество абонементов']);

    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {

        var data = google.visualization.arrayToDataTable(popSubscr);

        var options = {
            title: 'Популярность абонементов',
            backgroundColor: '#f7f7f5',
            chartArea: {
                width: '50%',
                left: 255
            },
            hAxis: {
                title: 'Количество абонементов',
                minValue: 0
            },
            vAxis: {
                title: ''
            },
            width: width,
            height: height,
            legend: { position: "none" }
        };

        var chart = new google.visualization.BarChart(document.getElementById('chart_div_pop_subscr'));

        chart.draw(data, options);
    }
}

function showGenderAgeChart(statisticArr, height, width) {
    var genderAgeArr = makeDataArrForGenderAge(statisticArr);

    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = google.visualization.arrayToDataTable(genderAgeArr);

        var options = {
            backgroundColor: '#f7f7f5',
            title: 'Пол и возраст клиентов',
            bars: 'vertical',
            vAxis: {format: 'decimal'},
            height: height,
            width: width,
            colors: ['#2BA2E0', '#E05EB3']
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_gender_age'));

        chart.draw(data, options);
    }
}

function showKnowFromChart(statisticArr, width) {
    var knowFrom = makeDataArr(statisticArr.client.knowFrom);
    for (var i = 0; i < knowFrom.length; i++) {
        if (knowFrom[i][0] === "") {
            knowFrom[i][0] = 'не указали';
        }
    }
    knowFrom.sort(compareElementsByValue);

    var height = 50 * knowFrom.length + 100;
    width = width - 20 + '';

    knowFrom.unshift(['Источник информации', 'Количество']);

    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {

        var data = google.visualization.arrayToDataTable(knowFrom);

        var options = {
            backgroundColor: '#f7f7f5',
            title: 'Популярность рекламного источника',
            chartArea: {
                width: '80%',
                top: '70'
            },
            hAxis: {
                title: 'Количество уникальных посетителей',
                minValue: 0
            },
            vAxis: {
                title: ''
            },
            width: width,
            height: height,
            legend: { position: "none" }
        };

        var chart = new google.visualization.BarChart(document.getElementById('chart_div_overflow'));

        chart.draw(data, options);
    }
}

function showAttendanceChart(statisticArr, height, width) {
    var attendance = makeDataArr(statisticArr.service.attendance);
    attendance.sort(compareElementsByDate);
    attendance.unshift(['Дата', 'Количество посетителей']);

    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable(attendance);

        var options = {
            backgroundColor: '#f7f7f5',
            width: width,
            height: height,
            title: "Посещаемость",
            hAxis: {title: 'Дата',  titleTextStyle: {color: '#333'}},
            vAxis: {minValue: 0},
            legend: {position: 'top'}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div_attendance'));
        chart.draw(data, options);
    }
}

function getOffsetParameters (parameter) {
    var chartsDivs = getAllChartsDivs();
    for (var i = 0; i < chartsDivs.length; i++) {
        if (chartsDivs[i][parameter]) {
            return chartsDivs[i][parameter];
        }
    }
}

function getAllChartsDivs () {
    var chartsDivs = [];
    chartsDivs.push(document.getElementById("chart_div_reg_month"));
    chartsDivs.push(document.getElementById("chart_div_total_people"));
    chartsDivs.push(document.getElementById("chart_div_pop_subscr"));
    chartsDivs.push(document.getElementById("chart_div_gender_age"));
    chartsDivs.push(document.getElementById("chart_div_know_from"));
    chartsDivs.push(document.getElementById("chart_div_attendance"));
    return chartsDivs;
    //сделать универсальной
}

function makeDataArr(statisticArr) {
    var dataArr = [];
    for (var key in statisticArr) {
        if (statisticArr.hasOwnProperty(key)) {
            var current = [];
            current.push(key);
            current.push(+statisticArr[key]);
            dataArr.push(current);
        }
    }
    return dataArr;
}

function makeDataArrForGenderAge (statisticArr) {
    var genderAge = statisticArr.client.genderAge,
        menAge = [],
        womenAge = [],
        genderAgeArr = [
            ['Возраст', 'Мужчины', 'Женщины'],
            ['<18'],
            ['18 - 21'],
            ['21-24'],
            ['24-27'],
            ['27-30'],
            ['30-35'],
            ['35-45'],
            ['>45']];

    for (var key in genderAge) {
        if (genderAge.hasOwnProperty(key)) {
            var current = [];
            current.push(key);
            current.push(+genderAge[key]);
            if (key.split('')[0] == "M") {
                menAge.push(current);
            } else {
                womenAge.push(current);
            }
        }
    }

    menAge.sort(compareElementsByName);
    womenAge.sort(compareElementsByName);

    for (var i = 0; i < menAge.length; i++) {
        genderAgeArr[i + 1].push(menAge[i][1]);
        genderAgeArr[i + 1].push(womenAge[i][1]);
    }

    return genderAgeArr;
}

function getIdOfShowedChart () {
    var chartsDivs = getAllChartsDivs();
    for (var i = 0; i < chartsDivs.length; i++) {
        if (chartsDivs[i].offsetHeight) {
            return chartsDivs[i].getAttribute("id");
        }
    }
}

function compareElementsByName (a, b) {
    if (a[0] > b[0]) return 1;
    if (a[0] < b[0]) return -1;
}

function compareElementsByDate (a, b) {
    var arrA = a[0].split('-'),
        arrB = b[0].split('-');
    if (arrA[0] > arrB[0]) {
        return 1;
    } else if (arrB[0] > arrA[0]) {
        return -1;
    } else {
        if (arrA[1] > arrB[1]) {
            return 1;
        } else {
            return -1;
        }
    }
}

function compareElementsByValue (a, b) {
    if (a[1] > b[1]) return -1;
    if (a[1] < b[1]) return 1;
}