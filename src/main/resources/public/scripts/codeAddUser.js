window.onload = function () {
    var lastName = document.getElementById("lastName");
    var firstName = document.getElementById("firstName");
    var middleName = document.getElementById("middleName");
    var phone = document.getElementById("phone");
    var eMail = document.getElementById("eMail");
    var userDocument = document.getElementById("document");
    var birthDate = document.getElementById("birthDate");
    var sex = document.getElementById("sex");
    var knowFrom = document.getElementById("knowFrom");
    var button = document.getElementById("but");
    var invalid = document.getElementById("invalid");

    birthDate = new Date('0001-01-01');

    function invalidMessage (msg, element) {
        invalid.innerHTML = msg;
        element.setAttribute("class", "no_valid info");
        return false;
    }

    function formValidator (obj) {
        if (obj.id === lastName.id || obj.id === firstName.id || obj.id === middleName.id) {
            if (!obj.value) {
                obj.setAttribute("class", "info");
                if (obj.id === middleName.id) {
                    middleName.value = "-";
                    return true;
                }
                return false;
            } else if (obj.value.match(/[^a-zа-яё-]/ig)) {
                obj.setAttribute("class", "no_valid info");
                return false;
            } else {
                obj.setAttribute("class", "valid info");
                return true;
            }
        } else if (obj.id === phone.id) {
            if (obj.value.match(/[^\d+ ]/)) {
                return false;
            }
        }
        return true;

    }

    function validationAndVisualization () {
        if (!this.value) {
            this.setAttribute("class", "info");
            return false;
        } else if (this.value.match(/[^a-zа-яё-]/ig)) {
            this.setAttribute("class", "no_valid info");
            return false;
        } else {
            this.setAttribute("class", "valid info");
            return true;
        }
    }

    lastName.oninput = validationAndVisualization;
    firstName.oninput = validationAndVisualization;
    middleName.oninput = validationAndVisualization;
    birthDate.onchange = function () {
        if (/\d\d\d\d-\d\d-\d\d/.test(this.value)) {
            this.setAttribute("class", "valid info");
        } else {
            this.setAttribute("class", "no_valid info");
        }
    };
    phone.oninput = function () {
        if (!this.value.match(/[^\d+ ]/)) {
            this.setAttribute("class", "valid info");
        } else {
            this.setAttribute("class", "no_valid info");
        }
    };//Данный обработчик можно доработать чтобы он "помогал" при вводе и более четко контролировал процесс ввода
    button.onclick = function () {
        if (formValidator(lastName)) {
            if (formValidator(firstName)) {
                if (!formValidator(middleName)) {
                    invalidMessage("Поле ввода отчества заполнено некорректно", middleName);
                    return false;
                } else if (!formValidator(phone)) {
                    invalidMessage("Номер телефона введен некорректно", phone);
                    return false;
                } else {
                    document.getElementById("regForm").submit();
                    return true;
                }
            }   else {
                invalidMessage("Поле ввода имени не заполнено", firstName);
                return false;
            }
        } else {
            if (formValidator(firstName)) {
                invalidMessage("Поле ввода фамилии не заполнено", lastName);
                return false;
            }
            invalidMessage("Поля ввода имени и фамилии не заполнены", lastName);
            firstName.setAttribute("class", "no_valid info");
            return false;
        }
    };
};