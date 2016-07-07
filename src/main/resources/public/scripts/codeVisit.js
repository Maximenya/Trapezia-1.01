window.onload = function () {
    var clientId = document.getElementById("clientId").innerHTML;
    var numOfIn = clientId.indexOf("$");
    clientId = clientId.substring(numOfIn);
    var subscriptionId = document.getElementById("subscriptionId");
    var service = document.getElementById("service");
    var harness = document.getElementById("harness");
    var griGri = document.getElementById("griGri");
    var magnesia = document.getElementById("magnesia");
    var carabine = document.getElementById("carabine");
    var climbingShoes = document.getElementById("climbingShoes");
    var key = document.getElementById("key");
    var button = document.getElementById("ton");
    var abonLabel = document.getElementsByClassName("abonLabel");

    var buyButton = document.getElementById("buyAbon");
    var buyAbonForm = document.getElementById("new_field2");
    var hideForm = document.getElementById("new_field");
    var exitButton = document.getElementById("exit");
    var abonSubcr = document.getElementById("abonSubscrNW");
    var dateFrom = document.getElementById("validity");
    var buyAbon = document.getElementById("purchase");
    var errorBuy = document.getElementById("errorMessage");

    function showBuyForm () {
        var today = new Date();
        var date;
        if (today.getMonth()<9) {
            date = today.getFullYear() + "-0" + (today.getMonth() + 1) + "-" + today.getDate();
        } else {
            date = today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate();
        }
        dateFrom.value = date;
        buyAbonForm.removeAttribute("class");
        hideForm.removeAttribute("class");
    }

    subscriptionId.onclick = function (event) {
        if (event.target.tagName == "INPUT") {
            if (event.target.checked) {
                service.disabled = true;
            }
        }
        if (event.target.tagName != 'LABEL') return;
        for (var i = 0; i < abonLabel.length; i++) {
            abonLabel[i].classList.remove('checkedAbonRadio');
        }
        event.target.classList.add('checkedAbonRadio');
    };

    function hideBuyForm () {
        buyAbonForm.setAttribute("class", "hidden");
        hideForm.setAttribute("class", "hidden");
    }

    buyButton.onclick = showBuyForm;
    exitButton.onclick = hideBuyForm;

    buyAbon.onclick = function () {
        var newAbon = {};
        var newAbonString;
        var request;
        newAbon.clientId = clientId;
        newAbon.subscriptionType = abonSubcr.value;
        newAbon.firstDate = dateFrom.value;
        newAbonString = JSON.stringify(newAbon);
        request = new XMLHttpRequest();
        request.open("POST", "/buySubscription", true);
        request.send(newAbonString);
            hideBuyForm();
        setTimeout(function() {
            console.log("begin");
            location.reload(true);
            console.log("end")
        }, 500);
    };

    button.onclick = function () {
        var visiter = {};
        visiter.clientId = clientId;
        visiter.service = service.value;
        if(subscriptionId.buztype != null) {
            if(subscriptionId.length == 1){
                if (subscriptionId.buztype.checked) {
                    visiter.subscriptionId = subscriptionId.buztype.value;
                }
        }
            for (var i = 0; i < subscriptionId.buztype.length; i++) {
                if (subscriptionId.buztype[i].checked) {
                    visiter.subscriptionId = subscriptionId.buztype[i].value;
                }
            }
        }
        if(visiter.subscriptionId != null){
            visiter.service = 20;
        }
        visiter.harness = harness.value;
        visiter.griGri = griGri.value;
        visiter.magnesia = magnesia.value;
        visiter.carabine = carabine.value;
        visiter.climbingShoes = climbingShoes.value;
        visiter.key = key.value;

        var visiterString = JSON.stringify(visiter);

        var request = new XMLHttpRequest();
        request.open("POST", "/visit", true);
        request.send(visiterString);
        button.innerHTML = "Клиент в зале";
    };//Обработка и отправка запроса о новом клиенте.
};