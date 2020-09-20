var currentDate = new Date();
var today = new Date();
var thirtyDaysAgo = new Date();
thirtyDaysAgo.setDate(today.getDate() - 30);
var sixtyDaysAgo = new Date();
sixtyDaysAgo.setDate(today.getDate() - 60);
var ninetyDaysAgo = new Date();
ninetyDaysAgo.setDate(today.getDate() - 90);
var centottantaDaysAgo = new Date();
centottantaDaysAgo.setDate(today.getDate() - 180);
var treseicinqueDaysAgo = new Date();
treseicinqueDaysAgo.setDate(today.getDate() - 365);
var fifteenDaysAgo = new Date();
fifteenDaysAgo.setDate(today.getDate() - 15);
var sevenDaysAgo = new Date();
sevenDaysAgo.setDate(today.getDate() - 7);
var yesterday = new Date();
yesterday.setDate(today.getDate() - 1);
var startMonth = new Date(today.getFullYear(), today.getMonth(), 1);
var endMonth = new Date(today.getFullYear(), today.getMonth() + 1, 0);
var startLastMonth = new Date(today.getFullYear(), today.getMonth() - 1, 1);
var endLastMonth = new Date(today.getFullYear(), today.getMonth(), 0);
var startThisYear = new Date(today.getFullYear(), 0);
var endThisYear = new Date(today.getFullYear() + 1, 0);
var startLastsYear = new Date(today.getFullYear() - 1, 0);
var endLastYear = new Date(today.getFullYear() - 1, 11, 31);

$("#end").datepicker({
    beforeShow: function (dateText, inst) {
        $("#daterange").val('custom');
        $("#search-panel").show("slow");
        $("#btn-search-panel").val("Hide Search Panel");
    },
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    maxDate: 0,
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#start").datepicker("option", "maxDate", selectedDate);
    }
}).datepicker("setDate", today);
//            currentDate.setDate(currentDate.getDate() - 30);
$("#start").datepicker({
    beforeShow: function (dateText, inst) {
        $("#daterange").val('custom');
        $("#search-panel").show("slow");
        $("#btn-search-panel").val("Hide Search Panel");
    },
    defaultDate: "-1d",
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    minDate: new Date(2015, 1 - 1, 1),
    maxDate: -1,
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#end").datepicker("option", "minDate", selectedDate);
    }
}).datepicker("setDate", thirtyDaysAgo);

$("#end_fatture").datepicker({
    beforeShow: function (dateText, inst) {
        $("#daterange-fatture").val('custom');
       
    },
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    maxDate: 0,
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#start_fatture").datepicker("option", "maxDate", selectedDate);
    }
}).datepicker("setDate", today);
//            currentDate.setDate(currentDate.getDate() - 30);
$("#start_fatture").datepicker({
    beforeShow: function (dateText, inst) {
        $("#daterange-fatture").val('custom');
      
    },
    defaultDate: "-1d",
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    minDate: new Date(2015, 1 - 1, 1),
    maxDate: -1,
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#end_fatture").datepicker("option", "minDate", selectedDate);
    }
}).datepicker("setDate", thirtyDaysAgo);

$("#date-end-file").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
//                onClose: function (selectedDate) {
//                    $("#date-start-file").datepicker("option", "maxDate", selectedDate);
//                }
});
$("#livedate_date_start").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    minDate: '03-12-2015',
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#livedate_date_end").datepicker("option", "minDate", selectedDate);
    }
});
$("#livedate_date_end").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    minDate: '03-12-2015',
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#livedate_date_start").datepicker("option", "maxDate", selectedDate);
    }
});
//last_date_sale_start
$("#last_date_sale_start").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    minDate: '04-05-2017',
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#last_date_sale_end").datepicker("option", "minDate", selectedDate);
    }
});
$("#last_date_sale_end").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    minDate: '04-05-2017',
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#last_date_sale_start").datepicker("option", "maxDate", selectedDate);
    }
});
$("#last_sale_start").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#last_sale_end").datepicker("option", "minDate", selectedDate);
    }
});
$("#last_sale_end").datepicker({
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
    onClose: function (selectedDate) {
        $("#last_sale_start").datepicker("option", "maxDate", selectedDate);
    }
});

$("#daterange").change(function () {
    $("#search-panel").show("slow");
    $("#btn-search-panel").val("Hide Search Panel");
    switch ($(this).val()) {
        case 'last-365':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", treseicinqueDaysAgo);
            break;
        case 'last-180':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", centottantaDaysAgo);
            break;
        case 'last-90':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", ninetyDaysAgo);
            break;
        case 'last-60':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", sixtyDaysAgo);
            break;
        case 'last-30':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", thirtyDaysAgo);
            break;
        case 'last-15':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", fifteenDaysAgo);
            break;
        case 'last-7':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", sevenDaysAgo);
            break;
        case 'today':
            $("#end").datepicker("setDate", today);
            $("#start").datepicker("setDate", currentDate);
            break;
        case 'yesterday':
            $("#end").datepicker("setDate", yesterday);
            $("#start").datepicker("setDate", yesterday);
            break;
        case 'this-month':
            $("#end").datepicker("setDate", endMonth);
            $("#start").datepicker("setDate", startMonth);
            break;
        case 'last-month':
            $("#end").datepicker("setDate", endLastMonth);
            $("#start").datepicker("setDate", startLastMonth);
            break;
        case 'this-year':
            $("#end").datepicker("setDate", endThisYear);
            $("#start").datepicker("setDate", startThisYear);
            break;
        case 'last-year':
            $("#end").datepicker("setDate", endLastYear);
            $("#start").datepicker("setDate", startLastsYear);
            break;
    }
});

$("#daterange-fatture").change(function () {
    $("#search-panel").show("slow");
    $("#btn-search-panel").val("Hide Search Panel");
    switch ($(this).val()) {
        case 'last-365':
            $("#end_fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", treseicinqueDaysAgo);
            break;
        case 'last-180':
            $("#end-fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", centottantaDaysAgo);
            break;
        case 'last-90':
            $("#end-fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", ninetyDaysAgo);
            break;
        case 'last-60':
            $("#end-fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", sixtyDaysAgo);
            break;
        case 'last-30':
            $("#end_fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", thirtyDaysAgo);
            break;
        case 'last-15':
            $("#end_fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", fifteenDaysAgo);
            break;
        case 'last-7':
            $("#end_fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", sevenDaysAgo);
            break;
        case 'today':
            $("#end_fatture").datepicker("setDate", today);
            $("#start_fatture").datepicker("setDate", currentDate);
            break;
        case 'yesterday':
            $("#end_fatture").datepicker("setDate", yesterday);
            $("#start_fatture").datepicker("setDate", yesterday);
            break;
        case 'this-month':
            $("#end_fatture").datepicker("setDate", endMonth);
            $("#start_fatture").datepicker("setDate", startMonth);
            break;
        case 'last-month':
            $("#end_fatture").datepicker("setDate", endLastMonth);
            $("#start_fatture").datepicker("setDate", startLastMonth);
            break;
        case 'this-year':
            $("#end_fatture").datepicker("setDate", endThisYear);
            $("#start_fatture").datepicker("setDate", startThisYear);
            break;
        case 'last-year':
            $("#end_fatture").datepicker("setDate", endLastYear);
            $("#start_fatture").datepicker("setDate", startLastsYear);
            break;
    }
});

$("#start_fatture-file").datepicker({
    defaultDate: "-1d",
    changeMonth: true,
    changeYear: true,
    showButtonPanel: true,
    numberOfMonths: 3,
    maxDate: 'today',
    dateFormat: "dd-mm-yy",
//                onClose: function (selectedDate) {
//                    $("#date-end-file").datepicker("option", "minDate", selectedDate);
//                }
});

$("#last_date_sale_start").blur(function () {
    if (!$(this).val() !== null && $(this).val().length > 0) {
        if ($.datepicker.parseDate('dd-mm-yy', $(this).val()) < $.datepicker.parseDate('dd-mm-yy', '04-05-2017')) {
            alert('Invalid date for last date on sale. Minimum date is 04-05-2017');
            $(this).val('04-05-2017');
        }
    }

});
$("#livedate_date_start").blur(function () {
    if (!$(this).val() !== null && $(this).val().length > 0) {
        if ($.datepicker.parseDate('dd-mm-yy', $(this).val()) < $.datepicker.parseDate('dd-mm-yy', '03-12-2015')) {
            alert('Invalid date for live date. Minimum date is 03-12-2015');
            $(this).val('03-12-2015');
        }
    }

});


function checkDateRange(s, e, title) {
    var start = s.datepicker("getDate");
    var end   = e.datepicker("getDate");
    if (start !== null && end !== null) {
        
        var difference = (end-start) / (86400000 * 7);
        if (difference < 0) {
            alert(title + ': ' + 'The From date must come before the To date.');
            return false;
        } else {
            return true;
        }
    } else {
        return true;
    }
}

