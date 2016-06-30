window.onload = function () {
    google.charts.load('current', {'packages':['corechart']});
    var buttons  = document.getElementById("buttons");

    /*var request = new XMLHttpRequest();
     var url = "StatisticController";

     request.onreadystatechange = function() {
     if (request.readyState == 4 && request.status == 200) {
     var statisticArr = JSON.parse(request.responseText);
     }

     showRegMonthChart(statisticArr);
     };

     request.open("GET", url, true);
     request.send();*/

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
            showRegMonthChart(client, height, width);
        } else if (stringId == "chart_div_total_people") {
            showTotalPeopleChart(client, height, width);
        } else if (stringId == "chart_div_pop_subscr") {
            showPopSubscrChart(client, height, width);
        } else if (stringId == "chart_div_gender_age") {
            showGenderAgeChart(client, height, width);
        }
    }
};

var client = {
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
};

function showRegMonthChart(statisticArr, height, width) {
    var regMonth = makeDataArr(statisticArr.client.regMonth);
    regMonth.unshift(['Дата', 'Количество зарегистрировавшихся']);

    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
        var data = google.visualization.arrayToDataTable(regMonth);

        var options = {
            width: width,
            height: height,
            title: 'Дата регистрации',
            hAxis: {title: 'Дата',  titleTextStyle: {color: '#333'}},
            vAxis: {minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div_reg_month'));
        chart.draw(data, options);
    }
}

function showTotalPeopleChart(statisticArr, height, width) {
    var totalPeople = makeDataArr(statisticArr.client.totalPeoples);
    totalPeople.unshift(['', '']);

    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {

        var data = google.visualization.arrayToDataTable(
            totalPeople
            /*[
             ['', '', {role: "style"}],
             ['Men', 175, '#7CD9FF'],
             ['Women', 80, '#FF4BED'],
             ['Total', 255, '#ffff00']
             ]*/);

        var options = {
            title: 'Amount of members',
            chartArea: {width: '50%'},
            hAxis: {
                title: 'Number of members',
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
    popSubscr.unshift(['', '']);

    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {

        var data = google.visualization.arrayToDataTable(
            popSubscr
            /*[
             ['', '', {role: "style"}],
             ['Men', 175, '#7CD9FF'],
             ['Women', 80, '#FF4BED'],
             ['Total', 255, '#ffff00']
             ]*/);

        var options = {
            title: 'Популярность абонементов',
            chartArea: {width: '50%'},
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

function showGenderAgeChart (statisticArr, height, width) {
    var genderAgeArr = makeDataArrForGenderAge(statisticArr);

    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        var data = google.visualization.arrayToDataTable(genderAgeArr);

        var options = {
            chart: {
                title: 'Пол и возраст клиентов'
            },
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

function hideAndShowCharts(stringId) {
    var height = getOffsetParameters('offsetHeight');
    var width = getOffsetParameters('offsetWidth');
    if (stringId == "regMonth") {
        showRegMonthChart(client, height, width);
        $("#chart_div_total_people").hide(350);
        $("#chart_div_pop_subscr").hide(350);
        $("#chart_div_gender_age").hide(350);
        $("#chart_div_reg_month").show(350);
    } else if (stringId == "totalPeople") {
        showTotalPeopleChart(client, height, width);
        $("#chart_div_reg_month").hide(350);
        $("#chart_div_pop_subscr").hide(350);
        $("#chart_div_gender_age").hide(350);
        $("#chart_div_total_people").show(350);
    } else if (stringId == "popSubscr") {
        showPopSubscrChart(client, height, width);
        $("#chart_div_reg_month").hide(350);
        $("#chart_div_total_people").hide(350);
        $("#chart_div_gender_age").hide(350);
        $("#chart_div_pop_subscr").show(350);
    } else if (stringId == "genderAge") {
        showGenderAgeChart(client, height, width);
        $("#chart_div_reg_month").hide(350);
        $("#chart_div_total_people").hide(350);
        $("#chart_div_pop_subscr").hide(350);
        $("#chart_div_gender_age").show(350);
    }
    //Необходимо доработать, когда будут все графики
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
    //график строится некорректно - необходима сортировка данных.
    var genderAge = statisticArr.client.genderAge,
        manAge = {},
        womenAge = {},
        i = 1,
        j = 1,
        genderAgeArr = [
            ['Age', 'Men', 'Women'],
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
            if (key.split('')[0] == "M") {
                manAge[key] = genderAge[key];
            } else {
                womenAge[key] = genderAge[key];
            }
        }
    }

    for (key in manAge) {
        if (manAge.hasOwnProperty(key)) {
            genderAgeArr[i].push(+manAge[key]);
            i++;
        }
    }

    for (key in womenAge) {
        if (womenAge.hasOwnProperty(key)) {
            genderAgeArr[j].push(+womenAge[key]);
            j++;
        }
    }

    return genderAgeArr;
}

function getIdOfShowedChart () {
    var chartsDivs = getAllChartsDivs();
    for (var i = 0; i < chartsDivs.length; i++) {
        if (chartsDivs[i].offsetHeight) {
            console.log(chartsDivs[i].getAttribute("id"));
            return chartsDivs[i].getAttribute("id");
        }
    }
}

showRegMonthChart(client);