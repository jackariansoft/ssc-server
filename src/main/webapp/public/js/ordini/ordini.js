/* global easyloader, detailview, parseInt */
function getmodificaIndirizzo(index) {
    $('#dg').datagrid('selectRow', index);
    var row = $('#dg').datagrid('getSelected');
    modificaIndirizzo(row.address);
}

function createShippingDoc() {

    var rows = [];
    var uptdates = $('#dg').datagrid('getChanges');
    if (uptdates.length > 0) {
        $.messager.alert('Invio Ordini per Task', 'Non hai salvato le modifiche!', 'info');
        return;
    }

    if ($('input[name="enableSelection"]').is(":checked")) {
        rows = $('#dg').datagrid('getSelections');

        if (rows.length === 0) {
            $.messager.alert('Invio Ordini per Task', 'Non hai selezionato nessuna riga!', 'info');
            return false;
        }

    } else {
        rows = $('#dg').datagrid('getRows');
    }
    if (rows.length > 0) {
        $.ajax({
            url: '/webshopping/shipping',
            type: 'POST',
            data: {
                orders: JSON.stringify(rows),
                departureDepot: $('#departureDepot').val(),
                customerCode: $('#customerCode').val(),
                VABNRS: $('#VABNRS').val(),
                type: $('#type').val(),
                outType: $('#outType').val(),
                skiperror: $('#skiperror').is(':checked'),
                note: $('#noteSpedizione').val()

            },
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
                                case 'BATCH_IN_PROGRESS':
                                    alert('Batch in corso. Riprovare a breve');
                                    break;
//                                case 'ORDER_SHIPPING_CREATION_ERROR':
//                                    var noApproved = JSON.parse(resp.noApproved);
//                                    if (typeof noApproved !== "undefined") {
//                                        if (noApproved.length > 0) {
//                                            $.each(noApproved, function (index, order) {
//                                                var index = $('#dg').datagrid('getRowIndex', order.id);
//                                                $('#dg').datagrid('updateRow', {
//                                                    index: index,
//                                                    row: {
//                                                        spedizioneErrore: order.spedizioneErrore,
//                                                        spedizioneErroreDesc: order.spedizioneErroreDesc
//                                                    }
//                                                });
//                                            });
//                                            showErrors(noApproved);
//                                        }
//                                    }
//                                    //$('#dg').datagrid('reload');
                                    break;
                                case 'WEBSERICES_ERROR':
                                    $.messager.alert('Invio Ordini per Task', 'Errore Webservice Servizio Spedizione. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                    break;
                                default :

                                    $.messager.alert('Invio Ordini per Task', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                    //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
                                    var noApproved = JSON.parse(resp.noApproved);
                                    if (typeof noApproved !== "undefined") {
                                        if (noApproved.length > 0) {
                                            $.each(noApproved, function (index, order) {
                                                var index = $('#dg').datagrid('getRowIndex', order.id);
                                                $('#dg').datagrid('updateRow', {
                                                    index: index,
                                                    row: {
                                                        spedizioneErrore: order.spedizioneErrore,
                                                        spedizioneErroreDesc: order.spedizioneErroreDesc
                                                    }
                                                });
                                            });
                                            showErrors(noApproved);
                                        }
                                    }
                                    break;
                            }
                        } else {

                            if (respObj.result) {
                                //$('#skipalert').prop("checked", false);
                                var noApproved = JSON.parse(resp.noApproved);
                                var approved = JSON.parse(resp.approved);

//                                if (typeof approved !== "undefined") {
//                                    $.each(approved, function (index, order) {
//                                        var index = $('#dg').datagrid('getRowIndex', order.id);
//                                        $('#dg').datagrid('updateRow', {
//                                            index: index,
//                                            row: {
//                                                spedizione: 1,
//                                                orderstatus: order.orderstatus
//                                            }
//                                        });
//                                    });
//                                }



                                //$('#dg').datagrid('acceptChanges');



                                if ($('#skiperror').is(':checked')) {

                                    $.messager.alert('Invio Ordini per Task', 'Task creato', 'info');

                                    if (typeof noApproved !== "undefined") {

                                        if (noApproved.length > 0) {
                                            $.each(noApproved, function (index, order) {
                                                var index = $('#dg').datagrid('getRowIndex', order.id);
                                                $('#dg').datagrid('updateRow', {
                                                    index: index,
                                                    row: {
                                                        spedizioneErrore: order.spedizioneErrore,
                                                        spedizioneErroreDesc: order.spedizioneErroreDesc
                                                    }
                                                });
                                            });
                                            showErrors(noApproved);
                                        }
                                    }

                                } else {
                                    if (typeof noApproved !== "undefined") {

                                        if (noApproved.length > 0) {
                                            $.each(noApproved, function (index, order) {
                                                var index = $('#dg').datagrid('getRowIndex', order.id);
                                                $('#dg').datagrid('updateRow', {
                                                    index: index,
                                                    row: {
                                                        spedizioneErrore: order.spedizioneErrore,
                                                        spedizioneErroreDesc: order.spedizioneErroreDesc
                                                    }
                                                });
                                            });
                                            showErrors(noApproved);
                                        }
                                    } else {
                                        if (typeof approved !== "undefined" && approved.length > 0) {
                                            $.messager.alert('Invio Ordini per Task', 'Task creato: spedizioni ' + approved.length, 'info');
                                        }
                                    }

                                }



                                $('#serach-order').trigger('click');

                            }

                        }
                    }
                } catch (error) {
                    $.messager.alert('Invio Ordini per Task', 'Errore inaspettato Ajax request: ' + error, 'error');
                    $.loader('close');
                }

            },
            error: function (xhr, status, er) {
                $.loader('close');
                $.messager.alert('Invio Ordini per Task', 'Errore inaspettato Ajax request: ' + xhr.responseText, 'error');
                //SendEmail(String(resp.responseText));
                //           
            },
            complete: function (rest, status) {
                $.loader('close');

            }
        });
    }
}
function exportToExcel() {
    $('#dg').datagrid('toExcel', 'ordini.xls');
}
function aggiornaStatoOrdini() {

    var rows = [];


    var rows = $('#dg').datagrid('getChanges');
    if (rows.length === 0) {
        $.messager.alert('Aggiorna Stato Ordini', 'Nessun ordine modificato', 'info');
        return false;
    } else
    if (rows.length > 0) {
        $.ajax({
            url: '/webshopping/aggiorna_ordini',
            type: 'POST',
            data: {
                orders: JSON.stringify(rows),
            },
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
                                    alert('Session expired. You must sign in again.');
                                    location.reload();
                                    break;
                                case 'BAD_REQUEST':
                                    $.messager.alert('Sistema', 'Bad Request:' + respObj.errorMessage, 'error');
                                    break;
                                default :

                                    $.messager.alert('Aggiornamento Ordini', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');

                                    break;
                            }
                        } else {

                            $('#dg').datagrid('acceptChanges');
                            $.messager.alert('Aggiornamento Ordini', 'Aggiornamento avvenuto con successo', 'info');

                        }
                    }
                } catch (error) {
                    $.messager.alert('Aggiornamento Ordini', 'Errore inaspettato Ajax request: ' + error, 'error');
                    $.loader('close');
                }

            },
            error: function (xhr, status, er) {
                $.loader('close');
                $.messager.alert('Aggiornamento Ordini', 'Errore inaspettato Ajax request: ' + xhr.responseText, 'error');

            },
            complete: function (rest, status) {
                $.loader('close');

            }
        });
    } else {

    }
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

    var url = "/webshopping/ordini";

    var requestData = {
        start: getParameterByName('start'),
        end: getParameterByName('end'),
        channel: buildDatabaseQueryString('channel'),
        channelExcl: buildQueryStringChannelExclude('channelExcl'),
        orderlist: $('#orderlist').val(),
        indirizzo: $('#indirizzo').val(),
        civico: $('#civico').val(),
        citta: $('#citta').val(),
        provincia: $('#provincia').val(),
        cap: $('#cap').val(),
        country: $('#country').val(),
        cod_rep: $('#cod_rep').val(),
        tip_doc: $('#tip_doc').val(),
        num_doc: $('#num_doc').val(),
        skus: $('#skus').val(),
        status: getParameterByName('status'),
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
        success: function (resp) {
            try {
                erroMap.clear();
                setFieldFormOrdini(populateUrlparams());
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
                        var pager = $('#dg').datagrid('getPager');


                        if (respObj.totalResult > 0) {


                            $('#amount_label').html('&nbsp;' + formatMoney(respObj.totalAmount) + '&euro;');
                            $('#dg').datagrid('loadData', respObj.result);
                            pager.pagination('refresh', {
                                total: respObj.totalResult,
                                pageNumber: getParameterByName('page')
                            });

                        } else {
                            $('#amount_label').html('&nbsp;0.00&euro;');
                            $('#dg').datagrid('loadData', []);

                            pager.pagination('refresh', {
                                total: 0,
                                pageNumber: 1
                            });
                            alert('No Result');

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

function buildDetatils(ddv, index, row) {

    var current_row = row;
    var address_info = current_row.address;
    var row_content = $('<div class="row" style="width:1000px">');
    var cell_address = $('<div class="col-lg-8">');
    var cell_details = $('<div class="col-lg-8">');
    cell_address.appendTo(row_content);
    cell_details.appendTo(row_content)
    var content = $('<div class="content">');
    content.appendTo(cell_address);
    /**
     * 
     * Contact Info
     */
    var contact = $('<div class="contact">');
    contact.appendTo(content);
    var cliename = $('<div class="cliname">');
    cliename.appendTo(contact);
    cliename.html(address_info.cliname);
    var address = $('<div class="address">');
    address.appendTo(contact);
    address.html(address_info.addressline1 + " Nota 1:" + address_info.addressline2 + " Nota 2:" + address_info.addressline3);
    var cap = $('<div class="address">');
    cap.appendTo(contact);
    cap.html('CAP:&nbsp;' + address_info.postalcode);
    var city = $('<div class="address">');
    city.appendTo(contact);
    city.html('Citt&aacute;:&nbsp;' + address_info.city);
    var prov = $('<div class="address">');
    prov.appendTo(contact);
    prov.html('Provinicia:&nbsp;' + address_info.stateorregion);
    var stato = $('<div class="address">');
    stato.appendTo(contact);
    stato.html('Stato:&nbsp;' + address_info.countrycode);
    var phone = $('<div class="phone">');
    phone.appendTo(contact);
    phone.html('Cell/Tel:&nbsp;' + address_info.phone);
    var email = $('<div class="email">'); //countrycode
    email.appendTo(contact);
    email.html('Email:&nbsp;' + address_info.email);
    var divbutton = $('<div class="btn">');
    divbutton.appendTo(contact);
    var button = $('<button type="button" id="modifica-indirizzo" class="btn btn-sm">');
    button.html('Modifica');
    button.click(function () {

        $('#modInd').window({
            width: 800,
            height: 600,
            modal: true,
            title: 'Modifica Indirizzo',
            tools: [{
                    iconCls: 'icon-save',
                    handler: function () {

                        var postData = $('#ff').serialize();
                        //console.log(postData);
                        $.ajax({
                            url: '/webshopping/modAddres',
                            type: 'POST',
                            data: postData,
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
                                                    $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                                    //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
                                                    //console.log(resp);
                                                    break;
                                            }
                                        } else {

                                            var address_info = respObj.result;

                                            var index = $('#dg').datagrid('getRowIndex', address_info.corder);
                                            $('#dg').datagrid('updateRow', {
                                                index: index,
                                                row: {
                                                    address: respObj.result
                                                }
                                            });
                                            $('#dg').datagrid('collapseRow', index);
                                            $('#modInd').window('close');

                                        }

                                    } else {
                                        alert('Unexpected result from server, sorry! Devs have been informed');
                                    }
                                } catch (error) {
                                    $('#modInd').window('close');
                                    console.log(error);
                                    $.loader('close');
                                }
                            },
                            error: function (resp, status, er) {
                                $('#modInd').window('close');
                                $.loader('close');
                                alert('Unexpected result from server, sorry! Devs have been informed');
                                //SendEmail(String(resp.responseText));

                            },
                            complete: function (resp, status) {
                                $.loader('close');

                            }

                        });


                    }
                }],
            href: '/webshopping/ordini/form_indirizzo.jsp',
            onLoad: function () {
                $('#ff').form('load', {
                    name: address_info.cliname,
                    email: address_info.email,
                    addressline1: address_info.addressline1,
                    addressline2: address_info.addressline2,
                    addressline3: address_info.addressline3,
                    postalcode: address_info.postalcode,
                    city: address_info.city,
                    stateorregion: address_info.stateorregion,
                    countrycode: address_info.countrycode,
                    phone: address_info.phone,
                    id: address_info.corder
                });
            },
            onOpen: function () {

            }
        });
        $('#modInd').window('open');
    });
    button.appendTo(divbutton);
    ddv.panel({
        height: 100 + (30 * 1) + 50,
        border: false,
        cache: false,
        content: row_content,
        onLoad: function () {
            $('#dg').datagrid('fixDetailRowHeight', index);
        }
    });
    $('#dg').datagrid('fixDetailRowHeight', index);
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

$(document).ready(function () {

    $('#mAssicurazione').menu({
        onClick: function (item) {
            switch (item.name) {
                case 'updateAll':
                    var rows = $('#dg').datagrid('getRows');
                    $.each(rows, function (index, row) {
                        aggiornaAss(index, row.totaleAssicuirbile);
                    });
                    break;
                case 'reset':
                    var rows = $('#dg').datagrid('getRows');
                    $.each(rows, function (index, row) {
                        aggiornaAss(index, parseFloat('0.00'));
                    });
                    break;
            }

        }
    });
    $('#mContrassegno').menu({
        onClick: function (item) {
            switch (item.name) {
                case 'updateAll':
                    var rows = $('#dg').datagrid('getRows');
                    $.each(rows, function (index, row) {
                        aggiornaContrassegno(index, row.ordertotal);
                    });
                    break;
                case 'reset':
                    var rows = $('#dg').datagrid('getRows');
                    $.each(rows, function (index, row) {
                        aggiornaContrassegno(index, parseFloat('0.00'));
                    });
                    break;
            }
        }
    });
    //mContrassegno
    //easyloader.load(['layout', 'panel', 'resizable', 'linkbutton', 'pagination', 'datagrid', 'tooltip','numberbox'],
    //       function () {        // load the specified module                            
//    $('#cc').layout({
//        onCollapse: function (collapesed) {
//            var center = $(this).layout('panel', 'center');
//            if (center) {
//                $('#dg').datagrid('resize', {witdh: '100%'});
//            }
//        }
//    });
//    setHeight();
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
        view: detailview,
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
            if (!$('#skipalert').is(":checked")) {
                if (!erroMap.isEmpty()) {
                    $.messager.alert({
                        title: 'Attenzione!!!',
                        msg: 'Numero ordini ' + +erroMap.size() + ' non spedibili per quantit&aacute; 0',
                        fn: function () {
                            var toShow = [];
                            erroMap.each(function (k, row, index) {
                                toShow.push(row);
                            });
                            showErrors(toShow);
                        }
                    });
                }
            }
        },
        onDblClickCell: function (index, field, value) {

//            if (editIndex !== index) {
//                if (endEditing()) {
//                    $(this).datagrid('beginEdit', index);
//                    editIndex = index;
//                    var ed = $(this).datagrid('getEditor', {index: index, field: field});
//                    if (ed) {
//                        $(ed.target).focus();
//                    }
//                }
//            } else {
//                if (endEditing()) {
//                    $(this).datagrid('beginEdit', index);
//                    editIndex = index;
//                    var ed = $(this).datagrid('getEditor', {index: index, field: field});
//                    if (ed) {
//                        $(ed.target).focus();
//                    }
//                }
//            }
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
//            if (editIndex !== undefined) {
//                if (editIndex !== rowIndex) {
//                    $(this).datagrid('endEdit', editIndex);
//                }
//            }
            $('#dg').datagrid('selectRow', rowIndex);

        },
        onHeaderContextMenu: function (e, field) {
            e.preventDefault();
            switch (field) {
                case 'contrassegno':
                    $('#mContrassegno').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                    break;
                case 'assicurazione':
                    $('#mAssicurazione').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                    break;
            }
        }, //marketplace, rif ordine, data, totale, n°, descrizione prod, peso, colli, contrassegno, assicurazione, provincia, altri dettagli ordine, tp doc, rep, num doc, tutto il resto dei campi

        columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: "ID", align: 'right', hidden: true,
                    styler: function (value, row, index) {
                        //console.log(row.spedizione);
                        if (row.noQty === true) {
                            erroMap.put(index, row);
                        }
                        if (typeof row.spedizione !== "undefined") {
                            return 'background-color:#ffee00;color:green;';
                        } else if (row.spedizioneErrore === true) {
                            return 'background-color:#ffee00;color:red;';
                        }
                    }
                },
                {field: 'deffered', title: '<a href="#" title="Impostazione Deferred: R = deferred;NR = non deferred" class="easyui-tooltip">Imposta R</a>', align: 'center',
                    formatter: function (value, rowm, index) {

                        if (value === 'false') {
                            return 'NR';
                        } else if (value === 'true') {
                            return 'R';
                        } else if (value === true) {
                            return 'R';
                        } else if (value === false) {
                            return 'NR';
                        }

                    },
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/imposta_deferred.json',
                            required: true
                        }
                        //editor: {type: 'checkbox', options: {on: 'true', off: 'false'}
                    }},
                {field: 'channel', title: 'Marketplace', width: 80, align: 'center',
                    formatter: function (value, rowm, index) {
                        var text = 'Not Found'
                        switch (value) {
                            case 1:
                                text = 'AMAZON';
                                break;
                            case 4:
                                text = 'EBAY';
                                break;
                            case 6:
                                text = 'MAGENTO';
                                break;
                            case 7:
                                text = 'EPRICE';
                                break;
                            case 8:
                                text = 'WEBSHOPPING';
                                break;
                            case 9:
                                text = 'IBS';
                                break;
                        }
                        return text;
                    }},
                {field: 'amazonorderid', title: 'Riferimento Ordine', width: 150, align: 'center',
                    styler: function (value, row, index) {
                        if (row.noQty === true) {
                            return 'background-color:red;';
                        } else if (typeof row.spedizione !== "undefined") {
                            return 'background-color:#ffee00;color:green;';
                        } else if (row.spedizioneErrore === true) {
                            return 'background-color:red;';
                        }
                    },
                    formatter: function (value, row, index) {
                        var div = $('<div class="ddv">');
                        var table = $('<table class="table" style="width:100%;border-style: none">');
                        table.appendTo(div);
                        var assRow = $('<tr>').appendTo(table);
                        var tdDesc = $('<td  style="border-style: none">').appendTo(assRow);
                        if (row.spedizioneErrore === true) {
                            tdDesc.html('<span style="text-align: right;font-size: smaller;font-weight: bold;background-color:red"></span>\n\
                                         <a href="#" title="' + row.spedizioneErroreDesc + '" class="easyui-tooltip">' + row.amazonorderid + '</a>');
                        } else if (row.noQty === true) {
                            tdDesc.html('<span style="text-align: right;font-size: smaller;font-weight: bold;background-color:red"></span>\n\
                                         <a href="#" title="Nessuna quantit&agrave;" class="easyui-tooltip">' + row.amazonorderid + '</a>');
                        } else {
                            tdDesc.html('<span style="text-align: right;font-size: smaller;font-weight: bold"></span> ' + row.amazonorderid + '');
                        }
                        var assRow2 = $('<tr>').appendTo(table);
                        var tdValue = $('<td style="border-style: none">').appendTo(assRow2);
                        if (row.spedizioneErrore === true) {

                            tdValue.html('<span style="text-align: right;font-size: smaller;font-weight: bold;background-color:#ffee00;color:red"></span>\n\
                                          <a href="#" title="' + row.spedizioneErroreDesc + '" class="easyui-tooltip">' + row.id + '</a>');
                        } else if (row.noQty === true) {
                            tdValue.html('<span style="text-align: right;font-size: smaller;font-weight: bold;background-color:#ffee00;color:red"></span>\n\
                                          <a href="#" title="Nessuna quantit&agrave;" class="easyui-tooltip">' + row.id + '</a>');
                        } else {
                            tdValue.html('<span style="text-align: right;font-size: smaller;font-weight: bold"></span> ' + row.id + '');
                        }

                        return div.html();
                    }},
                {field: 'purchasedate', title: 'Data Ordine', width: 70, align: 'center',
                    formatter: function (value, row, index) {

                        return convertDate(value);
                    }},
                {field: 'created', title: 'Data Creazione', width: 70, align: 'center', hidden: true},
                {field: 'ordertotal', title: 'Totale', width: 80, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'buyername', title: 'Buyer Name', align: 'right', hidden: true},
                {field: 'shippingprice', title: 'Spedizione', width: 70, align: 'right',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }},

                {field: 'numberofitemsshipped', title: 'Qt', width: 50, align: 'center'},
                {field: 'address', title: '<a href="#" title="Se la lunghezza dell\'indirizzo eccede i 35 caratteri, correggere inserendo il resto dell\'indirizzo nelle note." class="easyui-tooltip">Info Cliente</a>', align: 'center',
                    formatter: function (value, row, index) {
                        var modify = false;
                        var contact = '<div class="contact">';
                        var address_info = row.address;

                        if (address_info.cliname !== undefined && address_info.cliname.length > 0) {
                            if (address_info.cliname.length > 35) {
                                modify = true;
                                contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="La lunghezza della ragione sociale eccede i 35 caratteri, correggere inserendo il resto dell\'indirizzo nelle note aprendo il dettaglio, tasto + della riga." class="easyui-tooltip">' + address_info.cliname + "</a></div>";
                            } else {
                                contact += '<div  style="color:black;">' + address_info.cliname + "</div>";
                            }
                        } else {
                            modify = true;
                            contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="Campo Mancante" class="easyui-tooltip">Inserire Ragione Sociale</a></div>';
                        }
                        if (address_info.addressline1 !== undefined && address_info.addressline1.length > 0) {

                            if (address_info.addressline1.length > 35) {
                                modify = true;
                                contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="La lunghezza dell\'indirizzo eccede i 35 caratteri, correggere inserendo il resto dell\'indirizzo nelle note aprendo il dettaglio, tasto + della riga." class="easyui-tooltip">' + address_info.addressline1 + "</a></div>";
                                contact += '<div  ' + (address_info.addressline2.length > 0 ? 'style="background-color:#ffee00;color:black"' : '') + '>Nota 1:' + address_info.addressline2 + '</div>';
                                contact += '<div  ' + (address_info.addressline3.length > 0 ? 'style="background-color:#ffee00;color:black"' : '') + '>Nota 2:' + address_info.addressline3 + '</div>';
                            } else {
                                contact += '<div  style="color:black;">' + address_info.addressline1 + "</div>";
                                contact += '<div  ' + (address_info.addressline2.length > 0 ? 'style="background-color:#ffee00;color:black"' : '') + '>Nota 1:' + address_info.addressline2 + '</div>';
                                contact += '<div  ' + (address_info.addressline3.length > 0 ? 'style="background-color:#ffee00;color:black"' : '') + '>Nota 2:' + address_info.addressline3 + '</div>';
                            }
                        } else {
                            modify = true;
                            contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="Campo Mancante" class="easyui-tooltip">Inserire Indrizzo</a></div>';
                        }
                        if (address_info.postalcode !== undefined && address_info.postalcode.length > 0) {
                            if (address_info.postalcode.length > 9) {
                                modify = true;
                                contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="La lunghezza del CAP eccede i 9 caratteri, correggere inserendo il resto dell\'indirizzo nelle note aprendo il dettaglio, tasto + della riga." class="easyui-tooltip">' + address_info.postalcode + "</a></div>";
                            } else {
                                contact += '<div  style="color:black;">' + address_info.postalcode + "</div>";
                            }
                        } else {
                            modify = true;
                            contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="Campo Mancante" class="easyui-tooltip">Inserire CAP</a></div>';
                        }
                        //FINE CAP
                        if (address_info.city !== undefined && address_info.city.length > 0) {

                            if (address_info.city.length > 35) {
                                modify = true;
                                contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="La lunghezza della localita\' eccede i 35 caratteri, correggere inserendo il resto dell\'indirizzo nelle note aprendo il dettaglio, tasto + della riga." class="easyui-tooltip">' + address_info.city + "</a></div>";
                            } else {
                                contact += '<div  style="color:black;">' + address_info.city + "</div>";
                            }
                        } else {
                            modify = true;
                            contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="Campo Mancante" class="easyui-tooltip">Inserire Citt&agrave;</a></div>';
                        }
                        //FINE LOC
                        if (address_info.stateorregion !== undefined && address_info.stateorregion.length > 0) {
                            if (address_info.stateorregion.length > 2) {
                                modify = true;
                                contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="La lunghezza della provincia eccede i 2 caratteri,correggere, tasto + della riga. Oppure il sistema tentera\' la correzione automatica" class="easyui-tooltip">' + address_info.stateorregion + "</a></div>";
                            } else {
                                contact += '<div  style="color:black;">' + address_info.stateorregion + "</div>";
                            }
                        } else {
                            modify = true;
                            contact += '<div  style="background-color:#ffee00;color:black"><a href="#" title="Campo Mancante" class="easyui-tooltip">Inserire Provincia</a></div>';
                        }
                        //FINE PROVINCIA
                        if (modify) {
                            contact += '<div  style="color:black;"><button type="button" id="modifica-indirizzo" class="btn btn-sm"  onclick="getmodificaIndirizzo(' + index + ')">Modifica</button></div>';
                        }
                        return contact;
                    }},
                {field: 'items', title: 'Dettaglio Ordine', width: 300, align: 'left',
                    styler: function (value, row, index) {
                        var dim = value.length;
                        var i = 0;
                        var total_qt = 0;
                        for (i = 0; i < dim; i++) {
                            var item = value[i];
                            total_qt += parseInt(item.quantityordered);
                        }
                        if (total_qt > 1) {
                            return 'background-color:#ffee00;color:red;';
                        }
                    },
                    formatter: function (value, row, index) {
                        var div = $('<div class="ddv">');
                        var table = $('<table class="table" style="width:100%">');
                        table.appendTo(div);
                        var dim = value.length;
                        var i = 0;
                        var total_qt = 0;
                        for (i = 0; i < dim; i++) {

                            var deck = $('<tr>').appendTo(table);
                            var cardTitle = $('<td style="text-align: left;font-weight: bold;font-size: smaller;padding:0px">').appendTo(deck);
                            var cardText = $('<td>').appendTo(deck);
                            var item = value[i];
                            total_qt += parseInt(item.quantityordered);
                            //<a href="#" title="Ordine Fornitore ( ' + (row.spedizionePK.tipoDoc + row.spedizionePK.codRep + row.spedizionePK.docId) + ')" class="easyui-tooltip">' + row.spedizionePK.orderId + '</a>
                            cardTitle.html('<a href="#" title="' + item.title + '" class="easyui-tooltip">' + item.title.substring(0, 15) + '...</a>');
                            cardText.html('<span style="text-align: left;font-size: smaller;font-weight: bold">Sku:</span> ' + item.sellersku + ' - <span style="text-align: left;font-size: smaller;font-weight: bold">Qty:</span> ' + item.quantityordered + '- <span style="text-align: left;font-size: smaller;font-weight: bold">Price</span>: ' + formatMoney(item.itemprice) + '&euro;');
                            var stockRow = $('<tr>').appendTo(table);
                            var stocPrice = $('<td colspan="2">').appendTo(stockRow);
                            stocPrice.html('<span style="text-align: left;font-size: smaller;font-weight: bold">Prezzo Acq.:</span> ' + formatMoney(item.prezzo) + '&euro; - <span style="text-align: left;font-size: smaller;font-weight: bold">IVA:</span> ' + formatMoney(item.ammontareIva) + '&euro; - <span style="text-align: left;font-size: smaller;font-weight: bold">Prezzo Ivato</span>: ' + formatMoney(item.prezzoIvato) + '&euro;');
                        }
                        var assRow = $('<tr>').appendTo(table);
                        var ass = $('<td colspan="2">').appendTo(assRow);
                        ass.html('<span style="text-align: left;font-size: smaller;font-weight: bold">Totale Assicurabile:</span> ' + formatMoney(row.totaleAssicuirbile) + '&euro; \n\
                                      <a href="javascript:void(0);" class="l-btn l-btn-small l-btn-plain" onclick="aggiornaAss(' + index + ',' + row.totaleAssicuirbile + ')">\n\
                                      <span class="l-btn-left l-btn-icon-left">\n\
                                      <span class="l-btn-text l-btn-empty">&nbsp;</span><span class="l-btn-icon icon-large-smartart tooltip-f" title="Aggiorna campo assicurazione con il valore ' + formatMoney(row.totaleAssicuirbile) + '&euro;">&nbsp;</span></span></a>');

                        return div.html();
                    }
                },
                {field: 'peso', title: 'Peso', width: 65, align: 'center',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&nbsp;Kg';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'colli', title: 'Colli', width: 35, align: 'center',
                    editor: {type: 'numberbox', options: {precision: 0}}},
                {field: 'contrassegno', title: '<a href="#" title="Clicca tasto destro per opzioni" class="easyui-tooltip">C/Assegno</a>', width: 65, align: 'center',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'assicurazione', title: '<a href="#" title="Clicca tasto destro per opzioni" class="easyui-tooltip">Assicuraz.</a>', width: 65, align: 'right',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'tipdoc', title: 'Tip. Doc', width: 50, align: 'center',
                    editor: {type: 'textbox', options: {precision: 2}}
                },
                {field: 'codrep', title: 'Rep', width: 50, align: 'center',
                    editor: {type: 'textbox', options: {precision: 2}}
                },
                {field: 'docAs400', title: ' Num Doc', width: 70, align: 'center',
                    editor: {type: 'numberbox', options: {precision: 0}}
                },
                {field: 'orderstatus', title: 'Stato Ordine', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        return '<div>' + value + '</div>';
                    },
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/stato_ordini.json?v=10',
                            required: true
                        }}
                },
                {field: 'ordertotalccode', title: 'Valuta', width: 50, align: 'center'},

                {field: 'iscontrassegno', title: 'Contrassegno', width: 25, align: 'right', hidden: true,
                    formatter: function (value, rowm, index) {
                        if (value === 'N') {
                            return '<input type="checkbox" value="" disabled>';
                        } else {
                            return '<input type="checkbox" checked disabled>';
                        }
                    },
                    editor: {type: 'checkbox', options: {on: 'S', off: 'N'}}},

                {field: 'tipo_incasso_ca', title: 'Tipo Incasso C/A', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        var result = 'Not Found';
                        switch (value) {
                            case 'blank':
                                result = 'Contanti';
                                break;
                            case 'BM':
                                result = 'Assegno Bancario Int .Mitt.';
                                break;
                            case 'CM':
                                result = 'Assegno Circolare Int. Mitt';
                                break;
                            case 'BB':
                                result = 'Assegno Bancario Int. Corriere con Manleva';
                                break;
                            case 'OM':
                                result = 'Assegno Int. Mitt. Originale';
                                break;
                            case 'OC':
                                result = 'Assegno Circolare Int. Mitt. Originale';
                                break;
                        }
                        return result;
                    },
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/incasso.json',
                            required: true
                        }}},
                {field: 'opt_assicura', title: 'Assicura', width: 100, align: 'center', hidden: true,
                    formatter: function (value, rowm, index) {
                        if (value === 'N') {
                            return '<input type="checkbox" value="" disabled>';
                        } else {
                            return '<input type="checkbox" checked disabled>';
                        }
                    },
                    editor: {type: 'checkbox', options: {on: 'S', off: 'N'}}
                },
                {field: 'gg_cc1', title: 'gg_cc1', width: 100, align: 'right', hidden: true},
                {field: 'gg_cc2', title: 'gg_cc2', width: 100, align: 'right', hidden: true},
                {field: 'tipo_tratt_merci', title: 'Trattamento Merce', width: 100, align: 'right', hidden: true},
                {field: 'tipo_bolla', title: 'Tipo Bolla', width: 100, align: 'center', hidden: false,
                    formatter: function (value, row, index) {
                        var result = 'Not Found';
                        switch (value) {
                            case '1':
                                result = 'FRANCO';
                                break;
                            case '2':
                                result = 'ASSEGNATO';
                                break;
                            case '4':
                                result = 'FRANCO+C/ASSEGNATO';
                                break;
                            case '6':
                                result = 'ASSEGNATO+C/ASSEGNATO';
                                break;
                        }
                        return result;
                    },
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/codici_bolla.json',
                            required: true
                        }}
                },
                {field: 'tipo_servizio', title: 'Tipo Servizio', width: 100, align: 'center', hidden: false,
                    formatter: function (value, row, index) {
                        var result = 'Not Found';
                        switch (value) {
                            case 'C':
                                result = 'EXPRESS';
                                break;
                            case 'D':
                                result = 'DISTRIBUZIONE';
                                break;
                            case 'E':
                                result = 'PRIORITY';
                                break;
                            case 'H':
                                result = 'SERVIZIO H 10:30';
                                break;
                        }
                        return result;
                    },
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/tipo_servizio.json',
                            required: true
                        }}
                },

                {field: 'spedizione', title: 'Id Spedizione', width: 100, align: 'right', hidden: true},
                {field: 'spedizioneErrore', title: 'Errore Spedizione', width: 100, align: 'right', hidden: true},
                {field: 'spedizioneErroreDesc', title: 'Desc. Errore', width: 100, align: 'right', hidden: true},
                {field: 'commissione', title: 'Commissione', width: 100, align: 'right', hidden: false,
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }},
                {field: 'costoSpedizionePres', title: 'Costo Spedizione Pres', width: 100, align: 'right', hidden: false,
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'costoSpedizione', title: 'Costo Spedizione', width: 100, align: 'right', hidden: false,
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
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
        },
        buttons: [{
                iconCls: 'icon-shipper',
                handler: function () {
                    createShippingDoc();
                }
            }]

    });

    $('.icon-shipper').tooltip({content: 'Invio dati per la creazione del task di spedizione.<br> Solo le righe con i valori corretti saranno salvate'});
    $("input[name='skiperror']").tooltip({content: 'Se selezionato, crea task spedizione con i soli dati corretti tralasciando quelli in errore.'});

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

        setQueryStringOrdini("ordini/ordini.jsp", pager);
        buildTable(options.pageNumber, options.pageSize);
    });

    $('#import-order').click(function (e) {
        aggiornaInfoOrdini();
    });

    $('#reset').click(function (e) {
        e.preventDefault();

        $('#orderlist').val('');
        $('#cod_rep').val('');
        $('#tip_doc').val('');
        $('#num_doc').val('');

        $('#indirizzo').val('');
        $('#civico').val('');
        $('#citta').val('');
        $('#provincia').val('');
        $('#regione').val('');
        $('#cap').val('');
        $('#country').val('');

        $('#deferred').attr("checked", false);
        $('#skiperror').attr("checked", false);
        $("#daterange").val('last-30');
        $("#daterange").trigger('change');
        //$("#channel").val('all');
        $("#status").val('Unshipped');
        //$("#channelExcl").val('none');
        
        $("#channel").tagbox('clear');
        $("#channel").tagbox('setValue', 'all');
        $("#channelExcl").tagbox('clear');
        $("#channelExcl").tagbox('setValue', 'none');
        
        $("#skus").val('');



    });

    var url_params = populateUrlparams();


    $('#channel').tagbox({
        url: '/webshopping/json/marketplace.json',
        method: 'get',
        value: 'all',
        valueField: 'id',
        textField: 'text',
        limitToList: true,
        hasDownArrow: true,
        prompt: 'Selezione Marketplace',
        onRemoveTag: function (value) {

        }
    });
    $('#channelExcl').tagbox({
        url: '/webshopping/json/marketplace_out.json',
        method: 'get',
        value: 'none',
        valueField: 'id',
        textField: 'text',
        limitToList: true,
        hasDownArrow: true,
        prompt: 'Selezione Marketplace',
        onRemoveTag: function (value) {

        }
    });

    if (Object.keys(url_params).length > 0) {
        //console.log(url_params);
        setFieldFormOrdini(url_params);
        buildTable(url_params.page, url_params.pageSize);
    } else {
        $('#serach-order').trigger('click');
    }





}
);




