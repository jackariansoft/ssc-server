function buildDatabaseQueryString() {
    var staticLink = '';
    var site_option = $('#channel').val();
    var entrys = new Array();
    if (site_option === "all") {
        staticLink += 'all';
    } else {
        entrys.push(site_option);
        if (Object.keys(entrys).length > 0) {
            var len = Object.keys(entrys).length;
            var index = 0;
            for (k in entrys) {
                if (index === len - 1) {
                    staticLink += entrys[k];
                } else {
                    staticLink += entrys[k] + ",";
                }
                index++;
            }
        }
    }

    return staticLink;
}
function buildQueryStringChannelExclude() {
    var staticLink = '';
    var site_option = $('#channelExcl').val();
    var entrys = new Array();
    if (site_option === "all") {
        staticLink += 'all';
    } else {
        entrys.push(site_option);
        if (Object.keys(entrys).length > 0) {
            var len = Object.keys(entrys).length;
            var index = 0;
            for (k in entrys) {
                if (index === len - 1) {
                    staticLink += entrys[k];
                } else {
                    staticLink += entrys[k] + ",";
                }
                index++;
            }
        }
    }

    return staticLink;
}
function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

var optionsComboChart = {

    //hAxis: {title: 'Prodotti - Skus'},
    vAxis: {title: 'Quantita\' Prodotti Venduti ', scaleType: 'mirrorLog'},
    width: '100%',
    height: '100%',
    chartArea: {'width': '80%', 'height': '80%'},
//    allowHtml: 'true',
//    vAxis: {minValue: 0},
    legend: {position: 'top', textStyle: {fontSize: 12}, maxLines: 2},
    histogram: {lastBucketPercentile: 1, bucketSize: 10},
    //vAxis: { scaleType: 'mirrorLog' }
    //page: 'enable',
    //pageSize: 250
};
var optionsPriceQtyProduct = {
    title: 'Monthly Sales',
    curveType: 'function',
    legend: {position: 'bottom', textStyle: {color: '#555', fontSize: 14}}  // You can position the legend on 'top' or at the 'bottom'.
};
function Plot() {

    var url = '/webshopping/selling_stats';
    var requestData = {
        start: $('#date-start').val(),
        end: $('#date-end').val(),
        channel: buildDatabaseQueryString(),
        channelExcl: buildQueryStringChannelExclude(),
        orderlist: $('#orderid_list').val(),
        skus: $('#sku').val()
    };
    $.ajax({
        url: url,
        type: 'POST',
        data: requestData,
        beforeSend: function () {
            $.loader({
                className: "blue-with-image",
                content: ''
            });
        },
        success: function (resp) {
            try {

                if (isJson(resp.Response)) {

                    var respObj = JSON.parse(resp.Response);
                    var errorcode = respObj.fault;
                    if (errorcode) {
                        var error = respObj.errorDescription;
                        switch (error) {
                            case 'SESSION_EXPIRED':

                                $.messager.alert('Sistema', 'Sessione terminata.Reinserire le credenziali.', 'error');
                                location.reload();
                                break;
                            case 'BAD_REQUEST':
                                $.messager.alert('Sistema', 'Bad Request:' + respObj.errorMessage, 'error');
                                break;
                            default :
                                $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + error, 'error');
                                console.log(resp);
                                break;
                        }
                    } else {



                        if (respObj.totalResult > 0) {

                            var compositeCols = respObj.compositeCols;
                            var data = new google.visualization.DataTable();
                            $.each(compositeCols, function (compIndex, comCol) {
                                data.addColumn(comCol.type, comCol.name);
                            });
                            $.each(respObj.result, function (keyday, obj) {
                                var riga = [];
                                riga.push(new Date(obj.purchasedate));
                                //var prod = obj.sellersku+'('+obj.descrizione+') '+obj.qtyVendute;

                                //riga.push(new Number(obj.sellersku));
                                //riga.push(obj.sellersku + '(' + obj.descrizione + ')');
                                //riga.push(obj.totaleVendita);
                                riga.push(obj.prezzo);
                                riga.push(obj.qtyVendute);
                                data.addRow(riga);
                            });

                            var wrapper = new google.visualization.ChartWrapper({

                                chartType: 'LineChart',
                                dataTable: data,
                                options: optionsPriceQtyProduct,
                                containerId: "chart_div"
                            });
                            var colsToView = new Array();
                            colsToView.push(0);
                            colsToView.push(1);
                            colsToView.push(2);
//                            colsToView.push(3);
                            //colsToView.push(4);
                            var myView = new google.visualization.DataView(wrapper.getDataTable());
                            myView.setColumns(colsToView);
                            wrapper.setView(myView.toJSON());
                            wrapper.draw();
                        }else{
                            $.messager.alert('Andamento Vendita Prodotto', 'Nessun risultato', 'info');
                        }


                    }
                }
            } catch (error) {
                console.log(error);
                $.loader('close');
            }

        },
        error: function (resp, status, er) {
            $.loader('close');
            alert('Unexpected result from server, sorry! Devs have been informed');
            //SendEmail(String(resp.responseText));
//           
        },
        complete: function (rest, status) {
            $.loader('close');
//            $('html, body').animate({
//                scrollTop: $("#rangedate-table").offset().top - 70
//            }, 2000);
        }
    });

}

$(document).ready(function () {

    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(Plot);
    $('#serach-order').click(function (e) {
        e.preventDefault();
        Plot();
    });
});
