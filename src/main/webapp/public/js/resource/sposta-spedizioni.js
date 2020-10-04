function mostraOpzioni() {
    $('#moves').hide();
    $('#opzioni').show();
}
function spostaSpedizioniTaskSelezione() {
    //getData
    var rows = $('#dgMoves').datagrid('getData');
    if (rows) {
        if (rows.length === 0) {
            $.messager.alert('Sposta spedizioni', 'Non hai selezionato nessuna riga!', 'info');
            return false;
        }
        var task = $('#cc1').combobox('getValue');
        if (!$.isNumeric(task)) {
            $.messager.alert('Sposta Spedizioni', 'Seleziona un task valido', 'error');
        } else {
            actionSpostaSpedizioniTaskSelezione('notask', task,rows);
        }
    }
}
function actionSpostaSpedizioniTaskSelezione(mode, taskid, rows) {

    $.ajax({
        url: '/webshopping/sposta_spedizioni',
        type: 'POST',
        data: {
            selections: JSON.stringify(rows).replace(/[']/g, ' '),
            mode: mode,
            task: taskid

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
                    var isfault = respObj.fault;
                    if (isfault) {
                        var error = respObj.errorDescription;
                        var errorType = respObj.errorType;
                        switch (errorType) {
                            case 2:
                                $.messager.alert('Sposta Spedizioni', 'Sessione scaduta!', 'info');
                                location.reload();
                                break;
                            case 4:
                                $.messager.alert('Sposta Spedizioni', error, 'error');
                                break;
                            case 12:
                                $.messager.alert('Sposta Spedizioni', error, 'error');
                                break;
                            default :
                                $.messager.alert('Sposta Spedizioni', 'Errore Inaspettato! Email inviata allo sviluppatore', 'error');
                                break;
                        }
                    } else {

                        $.messager.alert('Sposta Spedizioni', ' avvenuto con successo!', 'info');
                    }
                }
            } catch (error) {

                $.loader('close');
                $.messager.alert('Sposta Spedizioni', error, 'info');
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
function rimuoviSpedizioni() {
    var rows = $('#dgMoves').datagrid('getSelections');
    if (rows) {
        if (rows.length === 0) {
            $.messager.alert('Sposta spedizioni', 'Non hai selezionato nessuna riga!', 'info');
            return false;
        } else {
            var indexs = [];
            $.each(rows, function (index, row) {
                try {
                    //var row_index = $('#dgMoves').datagrid('getRowIndex', row.id);
                    indexs[index] = row;
                    //$('#dgMoves').datagrid('deleteRow', row_index);
                    //$('#dgMoves').datagrid('acceptChanges');
                    //console.log(rows);
                } catch (e) {
                    console.log(e);
                }
            });
            $.each(indexs, function (index, row) {
                try {
                    var row_index = $('#dgMoves').datagrid('getRowIndex', row.id);
                    indexs[index] = row;
                    $('#dgMoves').datagrid('deleteRow', row_index);
                    //$('#dgMoves').datagrid('acceptChanges');
                    //console.log(rows);
                } catch (e) {
                    console.log(e);
                }
            });
        }
    }
}
function buildTableMoves() {
    $('#dgMoves').datagrid({
        ctrlSelect: true,
        singleSelect: false,
        striped: true,
        pagination: false,
        pagePosition: 'top',
        checkbox: true,
        selectOnCheck: true,
        checkOnSelect: false,
        showFooter: true,
        idField: 'id',
        toolbar: tbmov,
//        toolbar: [{
//                iconCls: 'icon-save',
//                plain: true,
//                text: 'Conferma e Scegli un task esistente',
//                handler: function () {
//                    mostraOpzioni();
//                }
//            },
//            {
//                iconCls: 'icon-save',
//                plain: true,
//                text: 'Conferma e genera un nuovo task',
//                handler: function () {
//                    generaNuovoTask();
//                },
//            },
//            {
//                iconCls: 'icon-remove',
//                plain: true,
//                text: 'Rimuovi Selezionate',
//                handler: function () {
//                    rimuoviSpedizioni();
//                },
//            }
//
//        ],
        detailFormatter: function (index, row) {
            return '<div style="padding:2px;position:relative;"><div id="mvv' + row.id + '"></div></div>';
        },
        onExpandRow: function (index, row) {


        },
        onLoadSuccess: function (data) {
            $.messager.show({
                title: 'Sposta Spedizioni',
                msg: 'Caricati ' + data.total + ' record'
            });
        },
        onDblClickCell: function (index, field, value) {


        },
        onEndEdit: function (rowIndex, row, changes) {


        },
        onBeforeEdit: function (index, row) {

        },
        onClickRow: function (rowIndex) {

        },
        onClickCell(index, field, value) {

        },

        frozenColumns: [[{field: 'ck', checkbox: true},
                {field: 'spedizionePK', title: 'Ordine Int.', hidden: false, align: 'center',
                    formatter: function (value, row, index) {
                        //console.log(row.spedizionePK);orderId\":26809,\"docId\":4567362,\"codRep\":\"AM\",\"tipoDoc\":\"VR\
                        return '<a href="#" title="Ordine Fornitore ( ' + (row.spedizionePK.tipoDoc + row.spedizionePK.codRep + row.spedizionePK.docId) + ')" class="easyui-tooltip">' + row.spedizionePK.orderId + '</a>';
                    }
                },
                {field: 'vabrma', title: 'Rif. Mitt', width: 100, align: 'center'}
            ]],
        columns: [[

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
                {field: 'totalOrder', title: 'Totale Ordine', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&euro;';
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
                        }
                        return text;
                    }}
            ]]});
    return $('#dgMoves');
}

table_moves = undefined;
function gestisciSpostamentoSpedizioni(data, currentbatch) {
    //getData

    $('#modMoves').window('setGridObject', data);
    $('#modMoves').window('open');
    $('#cc1').combobox('reload', '/webshopping/spedizioni_attive?batchId=' + currentbatch);


}
$(document).ready(function () {

    $('#modMoves').window({
        //width: 800,
        height: 600,
        modal: true,
        title: 'Gestione Spostamento Spedizioni',
        href: '/webshopping/ordini/gestione_moves.jsp',
        closed: true,
        table: null,
        rows: null,
        onLoad: function () {

            table_moves = buildTableMoves();
            $('#modMoves').window('getDataForGrid', table_moves);
        },
        onOpen: function () {
            if (table_moves !== undefined) {
                $('#modMoves').window('getDataForGrid', table_moves);
            } else {
                table_moves = buildTableMoves();
                $('#modMoves').window('getDataForGrid', table_moves);
            }
        }
    });
}
);
