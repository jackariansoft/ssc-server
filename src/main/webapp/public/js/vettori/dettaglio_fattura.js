var currentId = undefined;
/* global detailDettaglioFatturaview, scrollviewDettaglioFattura, parseFloat */
function exportToExcelDettaglio() {
    $('#ddv' + currentId).datagrid('toExcel', 'ordini.xls');
}
function buildDetatilsSpedizione(ddv, index, row) {

    $.ajax({
        url: '/webshopping/spedizionidettaglio',
        type: 'POST',
        data: {spedizione: row.spedizioneInt, type: 'json', mode: 'spedizioni'},
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
                            var batch_row = $('#ddv' + currentId);
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
function  buildDettaglioFattura(ddv, index, row, page) {
    currentId = row.id;
    var requestData = {
        fattura: currentId,
        channel: buildDatabaseQueryString(),
        channelExcl: buildQueryStringChannelExclude(),
        orderlist: $('#orderid_list').val(),
        indirizzo: $('#indirizzo').val(),
        civico: $('#civico').val(),
        citta: $('#citta').val(),
        provincia: $('#provincia').val(),
        cap: $('#cap').val(),
        country: $('#country').val(),
        rif_mitt_num: $('#rif_mitt_num').val(),
        rif_mitt_alfa: $('#rif_mitt_alfa').val(),
        num_doc: $('#num_doc').val(),
        status: $('#status').val(),
        fatture: $('#num_fatt').val(),
        rag_soc: $('#rag_soc').val(),
        delta_min: $('#delta_min').val(),
        delta_max: $('#delta_max').val(),
        delta_riga_min: $('#delta_riga_min').val(),
        delta_riga_max: $('#delta_riga_max').val(),
        cap_list: $('#cap_list').val(),
        page: page === 0 ? 1 : page,
        pageSize: 100
    };
    $.ajax({
        url: '/webshopping/service_dettaglio_fatture',
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
                                $.messager.alert('Invio stampa', 'Errore inaspettato. Inviata email allo sviluppatore:' + respObj.errorMessage, 'error');
                                break;
                        }
                    } else {

                        if (respObj.result) {

                            ddv.datagrid({
                                ctrlSelect: true,
                                singleSelect: false,
                                striped: true,
                                pagination: true,
                                pagePosition: 'top',
                                checkbox: true,
                                selectOnCheck: false,
                                scrollOnSelect: true,
                                checkOnSelect: false,
                                showFooter: true,
                                toolbar: $('#tb-view'),
                                view: detailDettaglioFatturaview,
                                idField: 'id',
                                onBeforeSelect: function (index, row) {
                                    return true;
                                },
                                detailFormatter: function (index, row) {
                                    return '<div class="ddv" style="padding:5px 0"></div>';
                                },
                                onExpandRow: function (index, row) {
                                    var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
                                    buildDetatilsSpedizione(ddv, index, row);
                                },
                                onLoadSuccess: function (data) {

                                    $('html,body').animate({
                                        scrollTop: 250
                                    }, 'slow');
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

                                },
                                onClickRow: function (rowIndex, row) {


                                },
                                onHeaderContextMenu: function (e, field) {

                                },
                                columns: [[
                                        {field: 'ck', checkbox: true},
                                        {field: 'id', title: "ID", align: 'right', hidden: true,
                                            formatter: function (value, row, index) {

                                                return value;
                                            }

                                        },
                                        {field: 'filialePartenza', title: 'Partenza', width: 80, align: 'center',
                                          formatter: function (value, rowm, index) {
                                            var filiale  =  parseInt(value);
                                               if(filiale!==67 && filiale !==164){
                                                  return 'RESO' 
                                               }else{
                                                   return value;
                                               }
                                            }
                                        },
                                        {field: 'market', title: 'Marketplace', width: 80, align: 'center',
                                            styler: function (value, row, index) {
                                                var delta = parseInt(value);
                                                if (delta < 0) {
                                                    return 'background-color:red;color:white;';
                                                }
                                            },
                                            formatter: function (value, rowm, index) {
                                                var text = 'RESO';
                                                //console.log(rowm.filialePartenza);
                                               var filiale  =  parseInt(rowm.filialePartenza);
                                                if (filiale !== 67 && filiale !==164) {
                                                    return text;
                                                } else {
                                                    switch (parseInt(value)) {
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
                                                        default :
                                                            text = 'Giacenza'
                                                            break
                                                    }
                                                    return text;
                                                }
                                            }},

                                        {field: 'ragSocDest', title: 'Rag.Soc.Dest', width: 150, align: 'center'},
                                        {field: 'locaDest', title: 'Comune', width: 150, align: 'center'},
                                        {field: 'provDest', title: 'Pro', width: 27, align: 'center'},
                                        {field: 'numeroColli', title: 'Colli', width: 50, align: 'center'},
                                        {field: 'items', title: 'Dettaglio', width: 300, align: 'center',
                                            formatter: function (value, row, index) {

                                                if (typeof value === 'undefined')
                                                    return  'Nessun dettaglio';

                                                var dim = value.length;

                                                if (dim > 0) {
                                                    var div = '<div class="row">';
                                                    for (var i = 0; i < dim; i++) {
                                                        try {
                                                            var item = value[i];
                                                            div += '<div class="col-12"><a href="#" title="' + item.descrizione + '" class="easyui-tooltip">' + item.descrizione.substring(0, 30) + '...</a></div>';
                                                            div += '<div class="col-12"><a href="#" title="' + item.sellersku + '" class="easyui-tooltip">&nbsp;SKU:' + item.sellersku + '</a></div>';
                                                        } catch (exception) {
                                                            console.log(exception);
                                                            console.log(item);
                                                            //alert(item.descrizione);
                                                        }

                                                    }
                                                    div += '</div>';
                                                    return div;

                                                } else {
                                                    return 'Nessun dettaglio';
                                                }

                                            }
                                        },
                                        {field: 'costo', title: '<a href="#" title="Costo applicato del vettore" class="easyui-tooltip">Costo</a>', width: 70, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'totaleCostoVaribile', title: '<a href="#" title="Costi variabili" class="easyui-tooltip">C.V.</a>', width: 70, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'imponibile', title: '<a href="#" title="Imponibile" class="easyui-tooltip">Imp.</a>', width: 70, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'totaleCostiSpedizione', title: 'Totale', width: 70, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'costoPresunto', title: 'Costo Presunto', width: 120, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'delta', title: 'Delta', width: 100, align: 'center',
                                            styler: function (value, row, index) {
                                                var delta = parseFloat(value);
                                                if (delta <= 0) {
                                                    return 'background-color:green;color:white;';
                                                } else {
                                                    return 'background-color:red;color:white;';
                                                }
                                            },
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'costoFornitore', title: 'Costo Fornitore', width: 100, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'commissioneMarket', title: 'Commissione', width: 100, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'totaleOrdine', title: 'Totale Ordine', width: 100, align: 'center',
                                            formatter: function (value, row, index) {

                                                return formatMoney(value) + '&euro;';
                                            }
                                        },
                                        {field: 'margine', title: '<a href="#" title="Colonna calcolata sottraendo tutti costi dal totale Totale Ordine" class="easyui-tooltip">Margine</a>', width: 100, align: 'center',

                                            styler: function (value, row, index) {
                                                var delta = parseFloat(value);
                                                if (delta >= 0) {
                                                    return 'background-color:green;color:white;';
                                                } else {
                                                    return 'background-color:red;color:white;';
                                                }
                                            },
                                            formatter: function (value, row, index) {//                                               

                                                return formatMoney(parseFloat(value).toFixed(2)) + '&euro;';
                                            }
                                        },
                                        {field: 'copertura', title: '<a href="#" title="Copertura margine del costo di spedizione" class="easyui-tooltip">Copertura</a>', width: 100, align: 'center',

                                            styler: function (value, row, index) {
                                                var delta = parseFloat(value);
                                                if (delta >= 0) {
                                                    return 'background-color:green;color:white;';
                                                } else {
                                                    return 'background-color:red;color:white;';
                                                }
                                            },
                                            formatter: function (value, row, index) {//                                               

                                                return formatMoney(parseFloat(value).toFixed(2)) + '&euro;';
                                            }
                                        }

                                    ]]

                            });
                            var pager = ddv.datagrid('getPager');
                            pager.pagination({
                                pageNumber: 1,
                                pageSize: 100,
                                pageList: [20, 50, 100, 200, 250, 300],
                                onSelectPage: function (pageNumber, pageSize) {
                                    buildDettaglioFatturaPager(ddv, pageNumber, pageSize);
                                }

                            });

                            ddv.datagrid('loadData', respObj.result);
                            pager.pagination('refresh', {
                                total: respObj.totalResult,
                                pageNumber: page
                            });

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
function  buildDettaglioFatturaPager(ddv, page, pageSize) {

    var requestData = {
        fattura: currentId,
        channel: buildDatabaseQueryString(),
        channelExcl: buildQueryStringChannelExclude(),
        orderlist: $('#orderid_list').val(),
        indirizzo: $('#indirizzo').val(),
        civico: $('#civico').val(),
        citta: $('#citta').val(),
        provincia: $('#provincia').val(),
        cap: $('#cap').val(),
        country: $('#country').val(),
        rif_mitt_num: $('#rif_mitt_num').val(),
        rif_mitt_alfa: $('#rif_mitt_alfa').val(),
        num_doc: $('#num_doc').val(),
        status: $('#status').val(),
        fatture: $('#num_fatt').val(),
        rag_soc: $('#rag_soc').val(),
        delta_min: $('#delta_min').val(),
        delta_max: $('#delta_max').val(),
        delta_riga_min: $('#delta_riga_min').val(),
        delta_riga_max: $('#delta_riga_max').val(),
        cap_list: $('#cap_list').val(),
        page: page === 0 ? 1 : page,
        pageSize: pageSize
    };
    $.ajax({
        url: '/webshopping/service_dettaglio_fatture',
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
                                $.messager.alert('Invio stampa', 'Errore inaspettato. Inviata email allo sviluppatore:' + respObj.errorMessage, 'error');
                                break;
                        }
                    } else {
                        var pager = ddv.datagrid('getPager');
                        if (respObj.result) {

                            ddv.datagrid('loadData', respObj.result);
                            pager.pagination('refresh', {
                                total: respObj.totalResult,
                                pageNumber: page
                            });
                        } else {

                            ddv.datagrid('loadData', []);
                            pager.pagination('refresh', {
                                total: 0,
                                pageNumber: 1
                            });
                            alert('No Result');
                        }

                        $('#dg').datagrid('fixDetailRowHeight', index);
                        $('#dg').datagrid('resize', {width: $(window).width()});

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



