/* global easyloader, detailview, parseInt */
function getmodificaIndirizzo(index) {
    $('#dg').datagrid('selectRow', index);
    var row = $('#dg').datagrid('getSelected');
    modificaIndirizzo(row.address);
}

function exportToExcel() {
    $('#dg').datagrid('toExcel', 'prenotazioni.xls');
}

function setHeight() {
    //    var footer = $("#link-nav");
    //    var order_filter = $("#order-filter");
    //    var date_filter = $("#date-filter");
    //    var c = $('#cc');
    //
    ////    var bottom  = $(window).height() - c.height();
    ////    alert(bottom);
    //    var p = c.layout('panel', 'center'); // get the center panel                                                                      
    //    c.layout('resize', {
    //        height: $(window).height() - (order_filter.height() + date_filter.height() + footer.height() + 30)
    //    });
}

function buildTable(page, pageSize) {

    var url = "/ssc/api/resource/reservation";

    var requestData = {
        start: getParameterByName('start'),
        end: getParameterByName('end'),
        channel: buildDatabaseQueryString('channel'),
        status: getParameterByName('logistic-status'),
        page: getParameterByName('page'),
        pageSize: getParameterByName('pageSize')
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
        success: function (respObj) {
            try {
                // erroMap.clear();
                setFieldFormOrdini(populateUrlparams());
                //if (isJson(resp)) {

                //var respObj = JSON.parse(resp);

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
                        default:
                            $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + error, 'error');
                            console.log(resp);
                            break;
                    }
                } else {
                    var pager = $('#dg').datagrid('getPager');


                    if (respObj.totalResult > 0) {


                        $('#amount_label').html('&nbsp;' + formatMoney(respObj.totalAmount) + '&euro;');
                        $('#dg').datagrid('loadData', respObj.result);
                        pager.pagination('refresh', {
                            total: respObj.totalResult,
                            pageNumber: getParameterByName('page')
                        });

                    } else {

                        $('#dg').datagrid('loadData', []);

                        pager.pagination('refresh', {
                            total: 0,
                            pageNumber: 1
                        });
                        alert('No Result');

                    }
                }

            } catch (error) {
                alert("Errore inaspettato: " + error);
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


var editIndex = undefined;
var erroMap = new MyMap();

function endEditing() {
    if (editIndex === undefined) {
        return true;
    }
    if ($('#dg').datagrid('validateRow', editIndex)) {
        $('#dg').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

function showErrors(data) {
    //getData

    $('#modErrors').window('setGridObject', data);
    $('#modErrors').window('open');
    //$('#dgErrors').datagrid('loadData', data);
    //loadDataForGrid


}

function accept() {
    if (endEditing()) {
        $('#dg').datagrid('acceptChanges');
    }
}
function reject() {
    $('#dg').datagrid('rejectChanges');
    editIndex = undefined;
}
function getChanges() {
    var rows = $('#dg').datagrid('getChanges');


}
function aggiornaAss(index, value) {
    $('#dg').datagrid('updateRow', {
        index: index,
        row: {
            assicurazione: value
        }
    });
}
function aggiornaContrassegno(index, value) {
    $('#dg').datagrid('updateRow', {
        index: index,
        row: {
            contrassegno: value
        }
    });
}
function deseleziona() {
    $('#dg').datagrid('clearSelections');
}


function forzaAvvioRisorsa(resource, plc) {

    var url = "/ssc/api/resource/command";
    
    var dataActivation = {
        "plc_uid": plc,
        "resource_tag": resource,
        "action": 1
    };

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(dataActivation),      
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
        },
        beforeSend: function () {
            $.loader({
                className: "blue-with-image",
                content: ''
            });
        },
        success: function (respObj) {
            try {
               

                var status = respObj.status;
                
                   
                    switch (status) {
                        case 200:

                            $.messager.alert('Sistema', 'Risorsa  '+resource+' abilitata', 'info');
                            
                            break;
                        
                        default:
                            $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                            console.log(resp);
                            break;
                    }
               

            } catch (error) {
                alert("Errore inaspettato: " + error);
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


    $('#dg').datagrid({
        ctrlSelect: true,
        singleSelect: false,
        striped: true,
        pagination: true,
        pagePosition: 'top',
        checkbox: true,
        selectOnCheck: true,
        scrollOnSelect: true,
        checkOnSelect: true,
        showFooter: true,
        toolbar: $('#tb'),
        //view: detailview,
        idField: 'id',
        onBeforeLoad: function () {
            erroMap.clear();
        },
        onBeforeSelect: function (index, row) {
            return true;
        },
        detailFormatter: function (index, row) {
            return '<div class="ddv" style="padding:5px 0"></div>';
        },
        onExpandRow: function (index, row) {
            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
            buildDetatils(ddv, index, row);
        },
        onLoadSuccess: function (data) {
            $.messager.show({
                title: 'Info',
                msg: 'Caricati ' + data.total + ' records'
            });

        },
        onDblClickCell: function (index, field, value) {

        },
        onEndEdit: function (index, row) {
            console.log(row.deffered);

        },
        onBeforeEdit: function (index, row) {
            if (typeof row.spedizione !== "undefined") {
                return false;
            } else {
                return true;
            }
        },
        onClickRow: function (rowIndex, row) {

            $('#dg').datagrid('selectRow', rowIndex);

        },
        onHeaderContextMenu: function (e, field) {

        },

        columns: [[
            { field: 'ck', checkbox: true },
            { field: 'id', title: "ID", align: 'right', hidden: false },
            { field: 'payload', title: "Payload", align: 'right' },
            { field: 'requestTime', title: "Data Richiesta", align: 'right', hidden: false },
            { field: 'starTime', title: "Inizio", align: 'right', hidden: false },
            { field: 'endTime', title: "Fine", align: 'right', hidden: false },
            {
                field: 'status', title: "Stato", align: 'right', hidden: false,
                //0=attesa,1=avviata,2=terminata,3=interrotta,4=scaduta,5=payload non valido,6=sospesa
                formatter: function (value, row, index) {
                    var status = parseInt(value);
                    var text = "ERRORE";
                    switch (status) {
                        case 0:
                            text = "ATTESA";
                            break
                        case 1:
                            text = "AVVIATA";
                            break;
                        case 2:
                            text = "TEMINATA";
                            break;
                        case 3:
                            text = "INTERROTTA";
                            break;
                        case 4:
                            text = "SCADUTA";
                            break;
                        case 6:
                            text = "SOSPESA";
                            break;



                    }

                    return text;
                }
            },
            { field: 'tag', title: "TAG", align: 'right', hidden: false },
            { field: 'reference', title: "Riferimento", align: 'right', hidden: false },
            { field: 'plcRef', title: "PLC ", align: 'righe', hidden: false },
            { field: 'idaddress', title: "IP Address PLC ", align: 'right', hidden: false },
            { field: 'schedulerId', title: "Scheduling Ref.", align: 'right', hidden: false },
            {
                field: 'receivedInterruptAt', title: "Forza Avvio", align: 'center',
                formatter: function (value, row, index) {
                    return "<button  onclick = forzaAvvioRisorsa(\""+row.tag+"\",\""+row.plcRef+"\")  class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-reload'\" style=\"width:80px'\" value=\"Forza Avvio\">Avvia</button>";
                }
            }
        ]]

    });


    $('#dg').datagrid('enableCellEditing');

    var pager = $('#dg').datagrid('getPager');


    pager.pagination({
        pageNumber: 1,
        pageSize: 50,
        pageList: [20, 50, 100, 200, 250, 300],
        onSelectPage: function (pageNumber, pageSize) {
            setQueryStringOrdini("ordini/ordini.jsp", pager);
            buildTable(pageNumber, pageSize);
        }

    });



    $(window).resize(function () {
        setHeight();
    });
    if (window.history && window.history.pushState) {

        $(window).on('popstate', function () {
            location.reload();
        });
    }
    $('#serach-order').click(function (e) {
        e.preventDefault();



        var pager = $('#dg').datagrid('getPager');
        //var data = [{id: 1, date: '10/10/2018', total: 20}];
        pager.pagination('refresh', {
            total: 0,
            pageNumber: 1
        });
        var options = pager.pagination('options');

        setQueryStringOrdini("public/home.jsp", pager);
        buildTable(options.pageNumber, options.pageSize);
    });

    $('#import-order').click(function (e) {
        aggiornaInfoOrdini();
    });

    $('#reset').click(function (e) {
        e.preventDefault();

    });

    var url_params = populateUrlparams();


    $('#channel').tagbox({
        url: '/ssc/api/resource',
        method: 'get',
        value: 'all',
        valueField: 'id',
        textField: 'tag',
        limitToList: true,
        hasDownArrow: true,
        prompt: 'Selezione Risorsa',
        onRemoveTag: function (value) {

        }
    });


    if (Object.keys(url_params).length > 0) {
        setFieldFormOrdini(url_params);
        buildTable(url_params.page, url_params.pageSize);
    } else {
        $('#serach-order').trigger('click');
    }
    connectToService();

}
);




