/* global detailviewErrors */

var editIndxError = undefined;

function buildTableErrorTable() {

    $('#dgErrors').datagrid({
        ctrlSelect: true,
        singleSelect: false,
        striped: true,
        pagination: true,
        pagePosition: 'top',
        checkbox: true,
        selectOnCheck: true,
        scrollOnSelect: true,
        checkOnSelect: false,
        showFooter: true,
        view: detailviewErrors,
        idField: 'id',
        detailFormatter: function (index, row) {
            return '<div class="ddvErrors" style="padding:5px 0"></div>';
        },
        onExpandRow: function (index, row) {
            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddvErrors');
            buildDetatilsErrors(ddv, index, row);
        },
        onLoadSuccess: function (data) {
            $.messager.show({
                title: 'Info',
                msg: 'Errori Caricati ' + data.total
            });
        },
        onDblClickCell: function (index, field, value) {

            if (editIndxError !== index) {
                if (endEditing()) {
                    $(this).datagrid('beginEdit', index);
                    editIndxError = index;
                    var ed = $(this).datagrid('getEditor', {index: index, field: field});
                    if (ed) {
                        $(ed.target).focus();
                    }
                }
            } else {
                if (endEditing()) {
                    $(this).datagrid('beginEdit', index);
                    editIndxError = index;
                    var ed = $(this).datagrid('getEditor', {index: index, field: field});
                    if (ed) {
                        $(ed.target).focus();
                    }
                }
            }
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
        onClickRow: function (rowIndex) {
            if (editIndxError !== undefined) {
                if (editIndxError !== rowIndex) {
                    $(this).datagrid('endEdit', editIndxError);
                }
            }

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
                            return 'background-color:#ffee00;color:red;';
                        }
                    },
                    formatter: function (value, row, index) {
                        var div = $('<div class="ddv">');
                        var table = $('<table class="table" style="width:100%;border-style: none">');
                        table.appendTo(div);
                        var assRow = $('<tr>').appendTo(table);
                        var tdDesc = $('<td  style="border-style: none">').appendTo(assRow);
                        tdDesc.html('<span style="text-align: right;font-size: smaller;font-weight: bold"></span> ' + row.amazonorderid + '');
                        var assRow2 = $('<tr>').appendTo(table);
                        var tdValue = $('<td style="border-style: none">').appendTo(assRow2);
                        tdValue.html('<span style="text-align: right;font-size: smaller;font-weight: bold"></span> ' + row.id + '');
                        return div.html();
                    }},
                {field: 'buyername', title: 'Buyer Name', align: 'right', hidden: true},
                //id: 18823
                //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               {field: 'id', title: 'Internal Order Id', width: 100, align: 'right'},
                {field: 'purchasedate', title: 'Data Ordine', width: 70, align: 'right'},
                {field: 'ordertotal', title: 'Totale', width: 80, align: 'right',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
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
                {field: 'tipdoc', title: 'Tip. Doc', width: 50, align: 'center', hidden: true},
                {field: 'codrep', title: 'Rep', width: 50, align: 'center', hidden: true},
                {field: 'docAs400', title: ' Num Doc', width: 70, align: 'center', hidden: true},
                {field: 'orderstatus', title: 'Stato Ordine', width: 100, align: 'center', hidden: true},
                {field: 'ordertotalccode', title: 'Valuta', width: 50, align: 'center', hidden: true},
                {field: 'peso', title: 'Peso', width: 65, align: 'center', hidden: true,
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&nbsp;Kg';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
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
                {field: 'tipo_incasso_ca', title: 'Tipo Incasso C/A', width: 100, align: 'center', hidden: true,
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
                    editor: {type: 'checkbox', options: {on: 'S', off: 'N'}}},
                {field: 'assicurazione', title: '<a href="#" title="Clicca tasto destro per opzioni" class="easyui-tooltip">Assicuraz.</a>', width: 65, align: 'right',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'address', title: 'address', width: 100, align: 'right', hidden: true},
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
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/tipo_servizio.json',
                            required: true
                        }}
                },
                {field: 'spedizione', title: 'Id Spedizione', width: 100, align: 'right', hidden: true},
                {field: 'spedizioneErrore', title: 'Errore Spedizione', width: 100, align: 'right', hidden: false},
                {field: 'spedizioneErroreDesc', title: 'Desc. Errore', width: 100, align: 'right', hidden: false}
            ]]

    });
    return $('#dgErrors');
}
function buildDetatilsErrors(ddv, index, row) {

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
    address.html(address_info.addressline1 + " " + address_info.addressline2 + " " + address_info.addressline3);
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
    var button = $('<button type="button" id="serach-order" class="btn btn-sm">');
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
                                                    $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + error, 'error');
                                                    console.log(resp);
                                                    break;
                                            }
                                        } else {

                                            var address_info = respObj.result;

                                            var index = $('#dgErrors').datagrid('getRowIndex', address_info.corder);
                                            $('#dgErrors').datagrid('updateRow', {
                                                index: index,
                                                row: {
                                                    address: respObj.result
                                                }
                                            });
                                            $('#dgErrors').datagrid('collapseRow', index);
                                            $('#modInd').window('close');

                                        }

                                    } else {
                                        $.messager.alert('Invio Ordini per Task', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + error, 'error');
                                    }
                                } catch (error) {
                                    $('#modInd').window('close');
                                    console.log(error);
                                    $.loader('close');
                                }
                            },
                            error: function (xhr, status, er) {
                                $('#modInd').window('close');
                                $.loader('close');
                                $.messager.alert('Gestione Errori', 'Errore inaspettato Ajax request: ' + xhr.responseText, 'error');
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
            $('#dgErrors').datagrid('fixDetailRowHeight', index);
        }
    });
    $('#dgErrors').datagrid('fixDetailRowHeight', index);
}
var table_ = null;

$(document).ready(function () {

    $('#modErrors').window({
        width: 800,
        height: 600,
        modal: true,
        title: 'Gestione Errori',
        href: '/webshopping/ordini/gestione_errori.jsp',
        closed: true,
        table: null,
        rows: null,
        onLoad: function () {

            table_ = buildTableErrorTable();
            $('#modErrors').window('getDataForGrid', table_);
        },
        onOpen: function () {
            if (table_ !== null) {
                $('#modErrors').window('getDataForGrid', table_);
            }
        }
    });
});


