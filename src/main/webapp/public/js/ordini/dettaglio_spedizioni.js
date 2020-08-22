/* global detailSpedizioniview */
editIndexSped = undefined;
$.fn.enterKey = function (fnc) {
    return this.each(function () {
        $(this).keypress(function (e) {
            var keycode = (e.keyCode ? e.keyCode : e.which);
            if (keycode === 13) {
                fnc.call(this, e);
            }
        })
    })
}
function endEditingSpedizioni() {
    if (editIndexSped === undefined) {
        return true;
    }
    if ($('#ddv' + currentBatch).datagrid('validateRow', editIndexSped)) {
        $('#ddv' + currentBatch).datagrid('endEdit', editIndexSped);
        editIndexSped = undefined;
        return true;
    } else {
        return false;
    }
}
function acceptSpedizioni() {
    if (endEditingSpedizioni()) {
        var rows = $('#ddv' + currentBatch).datagrid('getChanges');
        if (rows.length > 0) {
            saveStatusSpedizioni(rows);
        } else {
            $.messager.alert('Aggiorna Stato Spedizioni', 'Nessuna spedizione modificata', 'info');
            return false;
        }
    }
}

function rejectSpedizioni() {

    $('#ddv' + currentBatch).datagrid('rejectChanges');
    editIndexSped = undefined;
}

function getChangesSpedizioni() {
    var rows = $('#ddv' + currentBatch).datagrid('getChanges');
    alert('Righe Modificate ' + rows.length);
}
function saveStatusSpedizioni(rows) {

    $.ajax({
        url: '/webshopping/updatespedizioni',
        type: 'POST',
        data: {
            spedizioni: JSON.stringify(rows)

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
                            default :
                                $.messager.alert('Invio Ordini per Task', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');

                                break;
                        }
                    } else {
                        $('#ddv' + currentBatch).datagrid('acceptChanges');
                        $.messager.alert('Salva aggiornamento spedizioni', 'Spedizioni aggiornate con successo!', 'info');
                        refreshTask(currentBatch);
                    }
                }
            } catch (error) {

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
var currentBatch = undefined;
function buildDetatilsSpedizione(ddv, index, row) {

    $.ajax({
        url: '/webshopping/spedizionidettaglio',
        type: 'POST',
        data: {spedizione: row.id, type: 'json', mode: 'spedizioni'},
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
                                $.messager.alert('Invio stampa', 'Errore inaspettato. Inviata email allo sviluppatore:' + respObj.errorMessage, 'error');
                                break;
                        }
                    } else {

                        if (respObj.result) {

                            ddv.datagrid({
                                ctrlSelect: true,
                                singleSelect: false,
                                striped: true,
                                pagination: false,
                                pagePosition: 'top',
                                checkbox: true,
                                selectOnCheck: true,
                                scrollOnSelect: true,
                                checkOnSelect: false,
                                showFooter: true,
                                idField: 'id',
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
                                        msg: 'Load ' + data.total + ' records successfully'
                                    });
                                },
                                onDblClickCell: function (index, field, value) {

                                },
                                onEndEdit: function (index, row) {


                                },
                                onBeforeEdit: function (index, row) {
                                    if (typeof row.spedizione !== "undefined") {
                                        return false;
                                    } else {
                                        return true;
                                    }
                                },
                                onClickRow: function (rowIndex, row) {


                                },
                                onHeaderContextMenu: function (e, field) {

                                },
                                columns: [[
                                        {field: 'ck', checkbox: true},
                                        {field: 'id', title: "ID", align: 'right', hidden: true,
                                            styler: function (value, row, index) {
                                                //console.log(row.spedizione);
                                                if (typeof row.spedizione !== "undefined") {
                                                    return 'background-color:#ffee00;color:green;';
                                                } else if (row.spedizioneErrore === true) {
                                                    return 'background-color:#ffee00;color:red;';
                                                }
                                            }
                                        },
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
                                        {field: 'amazonorderid', title: 'Riferimento Ordine', width: 150, align: 'left',
                                            styler: function (value, row, index) {
                                                if (row.noQty === true) {
                                                    return 'background-color:#ffee00;color:red;';
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
                                                } else {
                                                    tdDesc.html('<span style="text-align: right;font-size: smaller;font-weight: bold"></span> ' + row.amazonorderid + '');
                                                }
                                                var assRow2 = $('<tr>').appendTo(table);
                                                var tdValue = $('<td style="border-style: none">').appendTo(assRow2);
                                                if (row.spedizioneErrore === true) {
                                                    tdValue.html('<span style="text-align: right;font-size: smaller;font-weight: bold;background-color:#ffee00;color:red"></span>\n\
                                          <a href="#" title="' + row.spedizioneErroreDesc + '" class="easyui-tooltip">' + row.id + '</a>');
                                                } else {
                                                    tdValue.html('<span style="text-align: right;font-size: smaller;font-weight: bold"></span> ' + row.id + '');
                                                }

                                                return div.html();
                                            }},
                                        {field: 'buyername', title: 'Buyer Name', align: 'right', hidden: true},
                                        //id: 18823
                                        //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               {field: 'id', title: 'Internal Order Id', width: 100, align: 'right'},
                                        {field: 'purchasedate', title: 'Data Ordine', width: 70, align: 'right'},
                                        {field: 'ordertotal', title: 'Totale', width: 80, align: 'right',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }},
                                        {field: 'shippingprice', title: 'Spedizione', width: 70, align: 'right',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }},
                                        {field: 'numberofitemsshipped', title: 'N.', width: 50, align: 'center'},
                                        {field: 'items', title: 'Dettaglio Ordine', width: 300, align: 'left',
                                            formatter: function (value, row, index) {
                                                var div = $('<div class="ddv">');
                                                var table = $('<table class="table" style="width:100%">');
                                                table.appendTo(div);
                                                var dim = value.length;
                                                var i = 0;
                                                for (i = 0; i < dim; i++) {

                                                    var deck = $('<tr>').appendTo(table);
                                                    var cardTitle = $('<td style="text-align: left;font-weight: bold;font-size: smaller;padding:0px">').appendTo(deck);
                                                    var cardText = $('<td>').appendTo(deck);
                                                    var item = value[i];
                                                    cardTitle.html(item.title.substring(0, 15) + '...');
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
                                        {field: 'tipdoc', title: 'Tip. Doc', width: 50, align: 'center'},
                                        {field: 'codrep', title: 'Rep', width: 50, align: 'center'},
                                        {field: 'docAs400', title: ' Num Doc', width: 70, align: 'center'},
                                        {field: 'orderstatus', title: 'Stato Ordine', width: 100, align: 'center'},
                                        {field: 'ordertotalccode', title: 'Valuta', width: 50, align: 'center'},
                                        {field: 'peso', title: 'Peso', width: 65, align: 'center', hidden: true,
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + '&nbsp;Kg';
                                            },
                                        },
                                        {field: 'colli', title: 'Colli', width: 35, align: 'center', hidden: true,
                                            editor: {type: 'numberbox', options: {precision: 0}}},
                                        {field: 'iscontrassegno', title: 'Contrassegno', width: 25, align: 'right', hidden: true,
                                            formatter: function (value, rowm, index) {
                                                if (value === 'N') {
                                                    return '<input type="checkbox" value="" disabled>';
                                                } else {
                                                    return '<input type="checkbox" checked disabled>';
                                                }
                                            },
                                            editor: {type: 'checkbox', options: {on: 'S', off: 'N'}}},
                                        {field: 'contrassegno', title: '<a href="#" title="Clicca tasto destro per opzioni" class="easyui-tooltip">C/Assegno</a>', width: 65, align: 'center',
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + '&euro;';
                                            },
                                            editor: {type: 'numberbox', options: {precision: 2}}},
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
                                        },
                                        {field: 'opt_assicura', title: 'Assicura', width: 100, align: 'center', hidden: true,
                                            formatter: function (value, rowm, index) {
                                                if (value === 'N') {
                                                    return '<input type="checkbox" value="" disabled>';
                                                } else {
                                                    return '<input type="checkbox" checked disabled>';
                                                }
                                            },
                                        },
                                        {field: 'assicurazione', title: '<a href="#" title="Clicca tasto destro per opzioni" class="easyui-tooltip">Assicuraz.</a>', width: 65, align: 'right',
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + '&euro;';
                                            },
                                        },
                                        {field: 'address', title: 'address', width: 100, align: 'right', hidden: true},
                                        {field: 'gg_cc1', title: 'gg_cc1', width: 100, align: 'right', hidden: true},
                                        {field: 'gg_cc2', title: 'gg_cc2', width: 100, align: 'right', hidden: true},
                                        {field: 'tipo_tratt_merci', title: 'Trattamento Merce', width: 100, align: 'right', hidden: true},
                                        {field: 'tipo_bolla', title: 'Tipo Bolla', width: 100, align: 'center', hidden: true,
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
                                        },
                                        {field: 'tipo_servizio', title: 'Tipo Servizio', width: 100, align: 'center', hidden: true,
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
                                        },
                                        {field: 'deffered', title: 'Imposta R', align: 'center', hidden: true,
                                            formatter: function (value, rowm, index) {

                                                if (value === "false") {
                                                    return '<input type="checkbox" value="" disabled>';
                                                } else if (value === "true") {
                                                    return '<input type="checkbox" checked disabled>';
                                                } else if (value === true) {
                                                    return '<input type="checkbox" checked disabled>';
                                                } else {
                                                    return '<input type="checkbox" value="" disabled>';
                                                }
                                            },
                                        },
                                        {field: 'spedizione', title: 'Id Spedizione', width: 100, align: 'right', hidden: true},
                                        {field: 'spedizioneErrore', title: 'Errore Spedizione', width: 100, align: 'right', hidden: true},
                                        {field: 'spedizioneErroreDesc', title: 'Desc. Errore', width: 100, align: 'right', hidden: true}
                                    ]]

                            });
                            //console.log(respObj.result);
                            ddv.datagrid('loadData', respObj.result);
                            var batch_row = $('#ddv' + currentBatch)
                            batch_row.datagrid('fixDetailRowHeight', index);
                            batch_row.datagrid('resize', {width: $(window).width()});
                            //$('#dg').datagrid('fixDetailRowHeight', index);
                            //$('#dg').datagrid('resize', {width: $(window).width()});
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
        }
    });
}
function buildDetatils(ddv, index, row) {
    currentBatch = row.batchId;
    editIndexSped = undefined;
    $.ajax({
        url: '/webshopping/spedizioni',
        type: 'POST',
        data: {
            batchId: row.batchId,
            channel: buildDatabaseQueryString(),
            orderlist: $('#orderidlist').val(),
            cod_rep: $('#cod_rep').val(),
            tip_doc: $('#tip_doc').val(),
            num_doc: $('#num_doc').val(),
            status: $('#status').val(),
            logisticStatus: $('#logistic-status').val(),
            type: 'json', mode: 'spedizioni'},
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
                                $.messager.alert('Invio stampa', 'Errore inaspettato. Inviata email allo sviluppatore:' + respObj.errorMessage, 'error');
                                break;
                        }
                    } else {

                        if (respObj.result) {

                            ddv.datagrid({
                                ctrlSelect: true,
                                singleSelect: false,
                                striped: true,
                                pagination: false,
                                pagePosition: 'top',
                                checkbox: true,
                                selectOnCheck: true,
                                checkOnSelect: false,
                                showFooter: true,
                                view: detailSpedizioniview,
                                idField: 'id',
                                detailFormatter: function (index, row) {
                                    return '<div style="padding:2px;position:relative;"><div id="ddvv' + row.id + '"></div></div>';
                                },
                                onExpandRow: function (index, row) {

                                    var ddv = $(this).datagrid('getRowDetail', index).find('div[id=ddvv' + row.id + ']');
                                    buildDetatilsSpedizione(ddv, index, row);
                                },
                                toolbar: [{
                                        iconCls: 'icon-save',
                                        plain: true,
                                        text: 'Conferma',
                                        handler: function () {
                                            acceptSpedizioni();
                                        }
                                    }, {
                                        iconCls: 'icon-undo',
                                        plain: true,
                                        text: 'Cancella Modifiche',
                                        handler: function () {
                                            rejectSpedizioni();
                                        }
                                    },
                                    {
                                        iconCls: 'easyui-linkbutton icon-search ',
                                        plain: true,
                                        text: 'Modifche effettutate',
                                        handler: function () {
                                            getChangesSpedizioni();
                                        }
                                    },
                                    {
                                        iconCls: 'easyui-linkbutton ui-button ui-icon ui-icon-document',
                                        plain: true,
                                        text: 'Scarica List Ordini Galiano',
                                        handler: function () {
                                            exportSpedizione();
                                        }
                                    },
//                                    {
//                                        iconCls: 'easyui-linkbutton ui-button ui-icon ui-icon-document',
//                                        plain: true,
//                                        text: 'Stampa Etichette Galiano (ZIP)',
//                                        handler: function () {
//                                            stampaEtichetteGalianoZip();
//                                        }
//                                    },
                                    {
                                        iconCls: 'easyui-linkbutton ui-button ui-icon ui-icon-document',
                                        plain: true,
                                        text: 'Stampa Etichette Galiano-Corriere',
                                        handler: function () {
                                            stampaEtichetteGaliano('combo');
                                        }
                                    },
                                    {
                                        iconCls: 'easyui-linkbutton ui-button ui-icon ui-icon-document',
                                        plain: true,
                                        text: 'Stampa Etichette Galiano',
                                        handler: function () {
                                            stampaEtichetteSoloGaliano();
                                        }
                                    },
                                    {
                                        iconCls: 'icon-print',
                                        plain: true,
                                        text: 'Stampa Etichette Corriere',
                                        handler: function () {
                                            //stampEtichette();
                                            stampaEtichetteGaliano('vettore');
                                        }
                                    },
//                                    {
//                                        iconCls: 'icon-print',
//                                        plain: true,
//                                        text: 'Crea Zip Etichette Corriere ',
//                                        handler: function () {
//                                            stampaEtichetteZip();
//                                        }
//                                    },
                                    {
                                        iconCls: 'icon-filter',
                                        plain: true,
                                        text: 'Sposta spedizioni ',
                                        handler: function () {
                                            var row = $('#dg').datagrid('getSelected');

                                            if (row) {

                                                var rows = [];
                                                if ($('input[name="enableSelection"]').is(":checked")) {
                                                    rows = $('#ddv' + currentBatch).datagrid('getSelections');
                                                    if (rows.length === 0) {
                                                        $.messager.alert('Sposta spedizioni', 'Non hai selezionato nessuna riga!', 'info');
                                                        return false;
                                                    }
                                                } else {
                                                    rows = $('#ddv' + currentBatch).datagrid('getData');
                                                }
                                                gestisciSpostamentoSpedizioni(rows, currentBatch);
                                            }
                                        }
                                    },
                                    {
                                        iconCls: 'icon-print',
                                        plain: true,
                                        text: 'Stampa Borderau',
                                        handler: function () {
                                            exportBorderau();
                                        }
                                    },
                                    {
                                        iconCls: 'icon-print',
                                        plain: true,
                                        text: 'Stampa Borderau-Lista Galiano',
                                        handler: function () {
                                            combinedExport();
                                        }
                                    }
                                ],
                                onRowContextMenu: function (e, index, row) {
                                    $('#modMenuRighe').menu('show',
                                            {left: e.pageX,
                                                top: e.pageY})
                                },
                                onHeaderContextMenu: function (e, field) {
                                    e.preventDefault();
                                    switch (field) {
                                        case 'stato':
                                            $('#mStatus').menu('show', {
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
                                },
                                onLoadSuccess: function (data) {
                                    $.messager.show({
                                        title: 'Spedizioni',
                                        msg: 'Caricati ' + data.total + ' record'
                                    });

                                },
                                onDblClickCell: function (index, field, value) {

                                    if (editIndexSped !== index) {
                                        if (endEditingSpedizioni()) {
                                            $(this).datagrid('beginEdit', index);
                                            editIndexSped = index;
                                            var ed = $(this).datagrid('getEditor', {index: index, field: field});
                                            if (ed) {
                                                $(ed.target).focus();
                                            }
                                        }
                                    } else {
                                        if (endEditingSpedizioni()) {
                                            $(this).datagrid('beginEdit', index);
                                            editIndexSped = index;
                                            var ed = $(this).datagrid('getEditor', {index: index, field: field});
                                            if (ed) {
                                                $(ed.target).focus();
                                            }
                                        }
                                    }
                                },
                                onEndEdit: function (rowIndex, row, changes) {

                                    if (editIndexSped !== undefined) {
                                        if (editIndexSped !== rowIndex) {
                                            $(this).datagrid('endEdit', editIndexSped);
                                        }
                                    }
                                },
                                onBeforeEdit: function (index, row) {

                                },
                                onClickRow: function (rowIndex) {
                                    if (editIndexSped !== undefined) {
                                        if (editIndexSped !== rowIndex) {

                                            endEditingSpedizioni();
                                        }
                                    }
                                },
                                onClickCell(index, field, value) {


                                    if (field === 'vabrma') {
                                        var toolTest = $('<a id="ilink" spedizione="' + row.vabrma + '" href="javascript:void(0)">' + row.vabrma + '</a>');
                                        toolTest.tooltip({
                                            content: $('<div></div>'),
                                            position: 'right',
                                            onShow: function () {
                                                $(this).tooltip('arrow').css('left', 20);
                                                $(this).tooltip('tip').css('left', $(this).offset().left);
                                            }
                                            ,
                                            onUpdate: function (cc) {
                                                cc.panel({
                                                    width: 500,
                                                    height: 'auto',
                                                    border: false,
                                                    href: '/webshopping/infospedizione?ref=' + $(this).attr('spedizione')
                                                });
                                            }});
                                    }

                                },
                                columns: [[
                                        {field: 'ck', checkbox: true},
                                        {field: 'spedizionePK', title: 'Ordine Int.', hidden: false, align: 'center',
                                            formatter: function (value, row, index) {
                                                //console.log(row.spedizionePK);orderId\":26809,\"docId\":4567362,\"codRep\":\"AM\",\"tipoDoc\":\"VR\
                                                return '<a href="#" title="Ordine Fornitore ( ' + (row.spedizionePK.tipoDoc + row.spedizionePK.codRep + row.spedizionePK.docId) + ')" class="easyui-tooltip">' + row.spedizionePK.orderId + '</a>';
                                            }
                                        },
                                        {field: 'channel_id', title: 'Ordine Marketplace', hidden: false, align: 'center',
                                            formatter: function (value, row, index) {
                                                var info = "";

                                                info += '<div><a href="#" title="Id oridine sul marketplace" class="easyui-tooltip">' + value + '</a></div>';
                                                if (row.trackerid === undefined) {
                                                    info += '<div>Nessun Trakerid</div>';
                                                } else {
                                                    info += '<div><a href="https://vas.brt.it/vas/sped_det_show.hsm?referer=sped_numspe_par.htm&Nspediz=' + row.trackerid + '&RicercaNumeroSpedizione=Ricerca" title="Traker Spedizione"  target="_blank">' + row.trackerid + '</a></div>';
                                                }
                                                return info;
                                            }
                                        },
                                        {field: 'stato', title: 'Stato', width: 100, align: 'center',
                                            formatter: function (value, rowm, index) {
                                                var text = 'Not Found';
                                                var test = parseInt(value);
                                                switch (test) {
                                                    case - 1:
                                                        text = 'Attesa';
                                                        break;
                                                    case 1:
                                                        text = 'Confermata';
                                                        break;
                                                    case 2:
                                                        text = 'Annullata';
                                                        break;
                                                    case 3:
                                                        text = 'Rimandata';
                                                        break;
                                                    case 4:
                                                        text = 'Spedita';
                                                        break;
                                                }
                                                return text;
                                            },
                                            editor: {type: 'combobox', options: {
                                                    valueField: 'inc_id',
                                                    textField: 'inc_type',
                                                    method: 'get',
                                                    url: '/webshopping/json/stato_spedizioni.json',
                                                    required: true
                                                }}},
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
                                                }
                                                return text;
                                            }},
                                        {field: 'vabrma', title: 'Rif. Mitt', width: 100, align: 'center'}
                                        ,
                                        {field: 'vablnp', title: 'Filiale Part.', width: 100, align: 'center'},
                                        {field: 'vabaas', title: 'Anno', width: 40, align: 'center'},
                                        {field: 'vabmgs', title: 'Giorno', width: 45, align: 'center'},
                                        {field: 'vabnsp', title: 'N.', width: 40, align: 'center'},
                                        {field: 'vabias', title: 'Importo Ass.', width: 100, align: 'center',
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + '&euro;';
                                            }},
                                        {field: 'vabvas', title: 'Valuta  Ass.', width: 100, align: 'center'},
                                        {field: 'vabncl', title: 'N.Colli', width: 45, align: 'center'},
                                        {field: 'vabpkg', title: 'Peso', width: 45, align: 'center',
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + 'Kg';
                                            }},
                                        {field: 'vabtsp', title: 'Tipo Serv. Bolle', width: 100, align: 'center'
                                            , formatter: function (value, row, index) {
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
                                            }},
                                        {field: 'vablna', title: 'Filiale Arrivo', width: 80, align: 'center'},
                                        {field: 'vabrsd', title: 'Ragione Sociale', width: 100, align: 'center',
                                            formatter: function (value, rowm, index) {
                                                return rowm.vabrsd + ' ' + rowm.vabrd2;
                                            }},
                                        {field: 'vabind', title: 'Indirizzo', width: 100, align: 'center'},
                                        {field: 'vabcad', title: 'CAP', width: 100, align: 'center'},
                                        {field: 'vablod', title: 'Localit&agrave;', width: 100, align: 'center'},
                                        {field: 'vabprd', title: 'Prov.', width: 100, align: 'center'},
                                        {field: 'vabnzd', title: 'Naz.', width: 50, align: 'center',
                                            formatter: function (value, rowm, index) {
                                                if (value.trim() === '') {
                                                    return 'I';
                                                } else
                                                    return value;
                                            }},
                                        {field: 'vabcas', title: 'C/Ass', width: 45, align: 'center',
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + '&euro;';
                                            }},
                                        {field: 'cinc', title: '<a href="#" title="Imposta contrassegno come incassato" class="easyui-tooltip">C/Incas.</a>', width: 50, align: 'right',
                                            formatter: function (value, rowm, index) {

                                                if (value === "false") {
                                                    return '<input type="checkbox" value="" disabled>';
                                                } else if (value === "true") {
                                                    return '<input type="checkbox" checked disabled>';
                                                } else if (value === true) {
                                                    return '<input type="checkbox" checked disabled>';
                                                } else {
                                                    return '<input type="checkbox" value="" disabled>';
                                                }
                                            },
                                            editor: {type: 'checkbox', options: {on: true, off: false}
                                            }
                                        },
                                        {field: 'parcelId', title: 'Parcel', width: 150, align: 'center', hidden: true},

                                        {field: 'totalOrder', title: 'Totale Ordine', width: 100, align: 'center',
                                            formatter: function (value, row, index) {
                                                return formatMoney(value) + '&euro;';
                                            }}

                                    ]]});
                            ddv.datagrid('loadData', respObj.result);
                            $('#dg').datagrid('fixDetailRowHeight', index);
                            $('#dg').datagrid('resize', {width: $(window).width()});
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
        }
    });
}
function aggiornaStato(index, stato, batch) {
    $('#ddv' + batch).datagrid('updateRow', {
        index: index,
        row: {
            stato: stato
        }
    });
    //var index = $('#dg').datagrid('getRowIndex', batch);
    //var rows =  $('#dg').datagrid('getRows', index)

}
$(document).ready(function () {


    $('#modMenuRighe').menu({
        onClick: function (item) {
            switch (item.name) {
                case 'modifica':
                    break;
                case 'viewOrder':
                    break;
            }
        }
    });
    $('#mStatus').menu({
        onClick: function (item) {
            switch (item.name) {
                case 'comfirmAll':
                    if ($('input[name="enableSelection"]').is(":checked")) {
                        var rows = $('#ddv' + currentBatch).datagrid('getSelections');
                        if (rows.length > 0) {
                            $.each(rows, function (index, row) {
                                var row_index = $('#ddv' + currentBatch).datagrid('getRowIndex', row.id);
                                aggiornaStato(row_index, 1, row.batchid);
                            });
                        } else {
                            $.messager.alert('Modifica Stato', 'Non hai selezionato nessuna riga!', 'info');
                        }
                    } else {
                        var rows = $('#ddv' + currentBatch).datagrid('getRows');
                        $.each(rows, function (index, row) {

                            aggiornaStato(index, 1, row.batchid);
                        });
                    }
                    break;
                case 'suspendAll':
                    if ($('input[name="enableSelection"]').is(":checked")) {
                        var rows = $('#ddv' + currentBatch).datagrid('getSelections');
                        if (rows.length > 0) {
                            $.each(rows, function (index, row) {
                                var row_index = $('#ddv' + currentBatch).datagrid('getRowIndex', row.id);
                                aggiornaStato(row_index, 3, row.batchid);
                            });
                        } else {
                            $.messager.alert('Modifica Stato', 'Non hai selezionato nessuna riga!', 'info');
                        }
                    } else {

                        var rows = $('#ddv' + currentBatch).datagrid('getRows');
                        $.each(rows, function (index, row) {

                            aggiornaStato(index, 3, row.batchid);
                        });
                    }
                    break;
                case 'attendAll':
                    if ($('input[name="enableSelection"]').is(":checked")) {
                        var rows = $('#ddv' + currentBatch).datagrid('getSelections');
                        if (rows.length > 0) {
                            $.each(rows, function (index, row) {
                                var row_index = $('#ddv' + currentBatch).datagrid('getRowIndex', row.id);
                                aggiornaStato(row_index, -1, row.batchid);
                            });
                        } else {
                            $.messager.alert('Modifica Stato', 'Non hai selezionato nessuna riga!', 'info');
                        }
                    } else {
                        var rows = $('#ddv' + currentBatch).datagrid('getRows');
                        $.each(rows, function (index, row) {

                            aggiornaStato(index, -1, row.batchid);
                        });
                    }
                    break;
                case 'disableAll':
                    if ($('input[name="enableSelection"]').is(":checked")) {
                        var rows = $('#ddv' + currentBatch).datagrid('getSelections');
                        if (rows.length > 0) {
                            $.each(rows, function (index, row) {
                                var row_index = $('#ddv' + currentBatch).datagrid('getRowIndex', row.id);
                                aggiornaStato(row_index, 2, row.batchid);
                            });
                        } else {
                            $.messager.alert('Modifica Stato', 'Non hai selezionato nessuna riga!', 'info');
                        }
                    } else {
                        var rows = $('#ddv' + currentBatch).datagrid('getRows');
                        $.each(rows, function (index, row) {
                            aggiornaStato(index, 2, row.batchid);
                        });
                    }
                    break;
                case 'sendAll':
                    if ($('input[name="enableSelection"]').is(":checked")) {
                        var rows = $('#ddv' + currentBatch).datagrid('getSelections');
                        if (rows.length > 0) {
                            $.each(rows, function (index, row) {
                                var row_index = $('#ddv' + currentBatch).datagrid('getRowIndex', row.id);
                                aggiornaStato(row_index, 4, row.batchid);
                            });
                        } else {
                            $.messager.alert('Modifica Stato', 'Non hai selezionato nessuna riga!', 'info');
                        }
                    } else {
                        var rows = $('#ddv' + currentBatch).datagrid('getRows');
                        $.each(rows, function (index, row) {
                            aggiornaStato(index, 4, row.batchid);
                        });
                    }
                    break;
            }

        }
    });
    $('#mContrassegno').menu({
        onClick: function (item) {
            switch (item.name) {
                case 'updateAll':
                    var rows = $('#ddv' + currentBatch).datagrid('getRows');
                    $.each(rows, function (index, row) {
                        aggiornaContrassegno(index, row.ordertotal);
                    });
                    break;
                case 'reset':
                    var rows = $('#ddv').datagrid('getRows');
                    $.each(rows, function (index, row) {
                        aggiornaContrassegno(index, parseFloat('0.00'));
                    });
                    break;
            }
        }
    });
});
$(document).enterKey(function () {
    endEditingSpedizioni();
})
