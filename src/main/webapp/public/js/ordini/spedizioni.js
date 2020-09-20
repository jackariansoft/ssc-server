/* global currentBatch, detailview */
$.fn.enterKey = function (fnc) {
    return this.each(function () {
        $(this).keypress(function (e) {
            var keycode = (e.keyCode ? e.keyCode : e.which);
            if (keycode === 13) {
                fnc.call(this, e);
            }
        })
    })
};

function exportToExcel(){
    $('#dg').datagrid('toExcel','spedizion.xls');
}

function stampEtichette() {
    
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Invio Stampa Etichette Corriere', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        $.ajax({
            url: '/webshopping/printBatchLabel',
            type: 'POST',
            data: {
                orders: JSON.stringify(row),
                batch: JSON.stringify(row.batchId),
                selections: JSON.stringify(rows),
                mode: 'printer'

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

                                    $.messager.alert('Invio stampa', 'Batch in corso. Riprovare a breve!', 'error');
                                    break;
                                case 'FILE_SHIPPING_CREATION_ERROR':
                                    $.messager.alert('Invio stampa', 'Errore creazione file corriere!', 'error');
                                    break;
                                default :
                                    $.messager.alert('Invio stampa', 'Errore inaspettato. Inviata email allo sviluppatore:'+ respObj.errorMessage, 'error');
                                    //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
                                    //console.log(resp);
                                    break;
                            }
                        } else {
                            $.messager.alert('Invio stampa', 'Invio stampa etichette corriere effettuato con successo!', 'info');
                        }
                    }
                } catch (error) {
                    console.log(error);
                    alert('Unexpected result from server, sorry! Devs have been informed:' + error);
                    $.loader('close');
                }

            },
            error: function (xhr, status, er) {
                $.loader('close');
                $.messager.alert('Stampa Etichette', 'Errore inaspettato Ajax request: ' + xhr.responseText, 'error');
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
    } else {
        $.messager.alert('Attenzione', 'Nessun task selezionato');
    }
}
function stampaEtichetteZip() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Invio Stampa Etichette Corriere', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        var data = {
            orders: JSON.stringify(row),
            batch: JSON.stringify(row.batchId),
            mode: 'zip',
            selections: JSON.stringify(rows).replace(/[']/g, ' ')

        };
        ajax_download('/webshopping/printBatchLabelZip', data);
    }

}
function refreshTask(batchId) {


    $.ajax({
        url: '/webshopping/gettask',
        type: 'POST',
        data: {

            batch: batchId,
            mode: 'complete'

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
                                $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
                                //console.log(resp);
                                break;
                        }
                    } else {
                        if (respObj.totalResult > 0) {
                            var index = $('#dg').datagrid('getRowIndex', batchId);
                            $('#dg').datagrid('collapseRow', index);
                            $('#dg').datagrid('updateRow', {
                                index: index,
                                row: respObj.result
                            });
                            $('#dg').datagrid('acceptChanges');
                        }
                    }
                }
            } catch (error) {
                console.log(error);
                $.loader('close');
            }

        },
        error: function (xhr, status, er) {
            $.loader('close');
            $.messager.alert('Aggiornamento task', 'Errore inaspettato Ajax request: ' + xhr.responseText, 'error');
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
function spostaSpedizioni() {

    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Sposta Spedizione', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }

        $.ajax({
            url: '/webshopping/sposta_spedizioni',
            type: 'POST',
            data: {
                selections: JSON.stringify(rows)
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
                                    $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                    //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
                                    //console.log(resp);
                                    break;
                            }
                        } else {

                            $.messager.alert('Sposta spedizioni', 'Spostamento effettuato con successo!', 'info');
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

}
function invioListaSpedizioni() {

    var rows = $('#dg').datagrid('getChanges');
    if (rows.length > 0) {
        alert('Salvare mofiche tack prima di inviare');
    } else {
        var row = $('#dg').datagrid('getSelected');
        if (row) {
            $.ajax({
                url: '/webshopping/brtservice',
                type: 'POST',
                data: {
                    orders: JSON.stringify(row),
                    batch: JSON.stringify(row.batchId),
                    mode: 'complete'

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
                                        alert('Session expired. You must sign in again.');
                                        location.reload();
                                        break;
                                    case 4:
                                        alert('bad Request:' + respObj.errorMessage);
                                        break;
                                    case 12:
                                        $.messager.alert('Invio Task Corriere', error, 'error');
                                        break;
                                    default :
                                        
                                        $.messager.alert('Invio Task Corriere', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                        break;
                                }
                            } else {

                                $.messager.alert('Invio Task Corriere', 'Task inviato con successo!', 'info');
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
    }
}
function invioTrackerId() {

    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Invio Traker Id Amazon', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }


        $.ajax({
            url: '/webshopping/invio_trackerid',
            type: 'POST',
            data: {
                selections: JSON.stringify(rows).replace(/[']/g, ' ')

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
                                    alert('Session expired. You must sign in again.');
                                    location.reload();
                                    break;
                                case 4:
                                    alert('bad Request:' + respObj.errorMessage);
                                    break;
                                case 12:
                                    $.messager.alert('Invio Traker Id Amazon', error, 'error');
                                    break;
                                default :
                                    $.messager.alert('Invio Traker Id Amazon', 'Errore Inaspettato! Email inviata allo sviluppatore', 'error');
                                    break;
                            }
                        } else {

                            $.messager.alert('Invio Traker Id Amazon', 'Invio Traker  Amazon avvenuto con successo!', 'info');
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

}

function ajax_download(url, data) {
    var $iframe,
            iframe_doc,
            iframe_html;
    if (($iframe = $('#download_iframe')).length === 0) {
        $iframe = $('<iframe id="download_iframe" style="display: none" src="about:blank"></iframe>').appendTo("body");
    }

    iframe_doc = $iframe[0].contentWindow || $iframe[0].contentDocument;
    if (iframe_doc.document) {
        iframe_doc = iframe_doc.document;
    }

    iframe_html = "<html><head></head><body><form method='POST' action='" + url + "'>"

    Object.keys(data).forEach(function (key) {
        var input = "<input type='hidden' name='" + key + "' value='" + (data[key] === null ? "" : data[key]) + "'>"
        iframe_html += input;
        //console.log(input);
    });
    iframe_html += "</form></body></html>";
    iframe_doc.open();
    iframe_doc.write(iframe_html);
    $(iframe_doc).find('form').submit();
}
function combinedExport() {
    var row = $('#dg').datagrid('getSelected');

    if (row) {

        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Stampa lista spedizioni', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        var data = {
            orders: JSON.stringify(row),
            batch: JSON.stringify(row.batchId),
            mode: 'csv',
            selections: JSON.stringify(rows).replace(/[']/g, ' ')

        };
        ajax_download('/webshopping/stampa_combo', data);
    }
}
function exportSpedizione() {
    var row = $('#dg').datagrid('getSelected');

    if (row) {

        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Stampa lista spedizioni', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        var data = {
            orders: JSON.stringify(row),
            batch: JSON.stringify(row.batchId),
            mode: 'csv',
            selections: JSON.stringify(rows).replace(/[']/g, ' ')

        };
        ajax_download('/webshopping/sp_stampa_galiano', data);
    }
}
function exportBorderau() {
    var row = $('#dg').datagrid('getSelected');

    if (row) {

        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Stampa lista Borderau', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        var data = {
            orders: JSON.stringify(row),
            batch: JSON.stringify(row.batchId),
            mode: 'csv',
            selections: JSON.stringify(rows).replace(/[']/g, ' ')

        };
        ajax_download('/webshopping/printBatchFile', data);
    }
}


function stampaEtichetteGalianoZip() {
    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Invio Stampa Etichette Galiano', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        var data = {
            orders: JSON.stringify(row),
            batch: JSON.stringify(row.batchId),
            mode: 'zip',
            selections: JSON.stringify(rows).replace(/[']/g, ' ')

        };
        ajax_download('/webshopping/printBatchLabelGaliano', data);
    }
}

function stampaEtichetteGaliano(mode) {

    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Invio Stampa Etichette Galiano', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        $.ajax({
            url: '/webshopping/printBatchLabelGaliano',
            type: 'POST',
            data: {
                orders: JSON.stringify(row),
                batch: JSON.stringify(row.batchId),
                mode: mode,
                selections: JSON.stringify(rows)

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
                                case 'FILE_SHIPPING_CREATION_ERROR':
                                    alert('Errore invio file BRT');
                                    break;
                                default :
                                    $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');

                                    break;
                            }
                        } else {
                            $.messager.alert('Invio stampa', 'Invio stampa etichette fornitore effettuato con successo!', 'info');
                        }
                    } else {
                        alert('Error parsing response. Informa lo sviluppatore di questo errore inaspettato');
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
    } else {
        $.messager.alert('Attenzione', 'Nessun task selezionato', 'info');
    }
}
function stampaEtichetteSoloGaliano() {

    var row = $('#dg').datagrid('getSelected');
    if (row) {
        var rows = [];
        if ($('input[name="enableSelection"]').is(":checked")) {
            rows = $('#ddv' + currentBatch).datagrid('getSelections');
            if (rows.length === 0) {
                $.messager.alert('Invio Stampa Etichette Galiano', 'Non hai selezionato nessuna riga!', 'info');
                return false;
            }
        }
        $.ajax({
            url: '/webshopping/printBatchLabelGaliano',
            type: 'POST',
            data: {
                orders: JSON.stringify(row),
                batch: JSON.stringify(row.batchId),
                mode: 'galiano',
                selections: JSON.stringify(rows)

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
                                case 'FILE_SHIPPING_CREATION_ERROR':
                                    alert('Errore invio file BRT');
                                    break;
                                default :
                                    $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');

                                    break;
                            }
                        } else {
                            $.messager.alert('Invio stampa', 'Invio stampa etichette fornitore effettuato con successo!', 'info');
                        }
                    } else {
                        alert('Error parsing response. Informa lo sviluppatore di questo errore inaspettato');
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
    } else {
        $.messager.alert('Attenzione', 'Nessun task selezionato', 'info');
    }
}
function setHeight() {
//    var footer = $("#link-nav");
//    var order_filter = $("#order-filter");
//    var date_filter = $("#date-filter");
//    var c = $('#cc');
////    var bottom  = $(window).height() - c.height();
////    alert(bottom);
//    var p = c.layout('panel', 'center'); // get the center panel                                                                      
//    c.layout('resize', {
//        height: $(window).height() - (order_filter.height() + date_filter.height() + footer.height() + 30)
//    });
}
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
function buildTable(page, pageSize) {
    var url = "/webshopping/spedizioni";
    var requestData = {
        start: $('#start').val(),
        end: $('#end').val(),
        channel: buildDatabaseQueryString(),
        orderlist: $('#orderidlist').val(),
        cod_rep: $('#cod_rep').val(),
        tip_doc: $('#tip_doc').val(),
        num_doc: $('#num_doc').val(),
        status: $('#status').val(),
        logisticStatus: $('#logistic-status').val(),
        task:$('#task').val(),
        page: page === 0 ? 1 : page,
        pageSize: pageSize,
        mode: 'task'
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
                               $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
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
                                pageNumber: page
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


var editIndex = undefined;
var lastExpanded = undefined;

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
function acceptTask() {
    if (endEditing()) {
        var rows = $('#dg').datagrid('getChanges');
        savaBatchsStatus(rows);
    }
}
function rejectTask() {
    $('#dg').datagrid('rejectChanges');
    editIndex = undefined;
}
function getChangesTask() {
    var rows = $('#dg').datagrid('getChanges');
    if (rows.length > 0) {

    }

}
function savaBatchsStatus(rows) {
    if (rows.length > 0) {
        $.ajax({
            url: '/webshopping/aggiorna_task',
            type: 'POST',
            data: {
                tasks: JSON.stringify(rows)
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
                                   $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                    console.log(resp);
                                    break;
                            }
                        } else {
                            $('#dg').datagrid('acceptChanges');
                            $.messager.alert('Aggiornamento task', 'Aggiornamento avvenuto con successo con successo!', 'info');
                            $('#dg').datagrid('reload');
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
}

$(document).ready(function () {

    $('#dg').datagrid({
        ctrlSelect: true,
        singleSelect: true,
        striped: true,
        pagination: true,
        pagePosition: 'top',
        checkbox: true,
        selectOnCheck: true,
        checkOnSelect: false,
        showFooter: true,
        toolbar: $('#tb'),
        view: detailview,
        idField: 'batchId',
        detailFormatter: function (index, row) {
            return '<div style="padding:2px;position:relative;"><div id="ddv' + row.batchId + '"></div></div>';
        },
        onExpandRow: function (index, row) {
            if (index !== lastExpanded) {
                if (lastExpanded !== undefined) {
                    $(this).datagrid('collapseRow', lastExpanded);
                }
            }
            lastExpanded = index;
            var ddv = $(this).datagrid('getRowDetail', index).find('div[id=ddv' + row.batchId + ']');
            buildDetatils(ddv, index, row);

        },
        onCollapseRow: function (index, row) {
            var ddv = $(this).datagrid('getRowDetail', index).find('div[id=ddv' + row.batchId + ']');
            var modifiche = ddv.datagrid('getChanges');
            if (modifiche.length > 0) {
                $.messager.confirm('Salva modifiche', 'Hai effettuato delle modifiche. Vuoi salvarle?', function (r) {
                    if (r) {
                        saveStatusSpedizioni(modifiche);
                    }
                });
            }
            if (lastExpanded === index) {
                lastExpanded = undefined;
            }
        },
        onLoadSuccess: function (data) {
            $.messager.show({
                title: 'Info',
                msg: 'Caricati ' + data.total + ' task(s)'
            });
        },
        onDblClickCell: function (index, field, value) {

            if (editIndex !== index) {
                if (endEditing()) {
                    $(this).datagrid('beginEdit', index);
                    editIndex = index;
                    var ed = $(this).datagrid('getEditor', {index: index, field: field});
                    if (ed) {
                        $(ed.target).focus();
                    }
                }
            } else {
                if (endEditing()) {
                    $(this).datagrid('beginEdit', index);
                    editIndex = index;
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

        },
        onClickRow: function (rowIndex) {
            if (editIndex !== undefined) {
                if (editIndex !== rowIndex) {
                    $(this).datagrid('endEdit', editIndex);
                }
            }

        },
        frozenColumns: [[
            ]],
        columns: [[
                {field: 'ck', checkbox: true},
                {field: 'batchId', title: "ID", align: 'right',
                    styler: function (value, row, index) {
                        //console.log(row.spedizione);
                        if (typeof row.spedizione !== "undefined") {
                            return 'background-color:#ffee00;color:green;';
                        } else if (row.spedizioneErrore === true) {
                            return 'background-color:#ffee00;color:red;';
                        }
                    }
                },
                {field: 'dataIns', title: '<a href="#" title="Data inserimento" class="easyui-tooltip">Data Ins.</a>', width: 150, align: 'center',
                    styler: function (value, row, index) {
                        if (row.numeroSpedizioni === row.spedite) {
                            return 'background-color:green;color:aliceblue;';
                        }
                    }},
                {field: 'dataAgg', title: '<a href="#" title="Data ultimo aggiornamento" class="easyui-tooltip">Data Agg.</a>', align: 'right', hidden: true},
                {field: 'dataEsecuzione', title: '<a href="#" title="Data esecuzione prevista" class="easyui-tooltip">Data Invio prev.</a>', width: 100, align: 'right'},
                {field: 'oraEsecuzione', title: '<a href="#" title="Ora esecuzione prevista" class="easyui-tooltip">Ora Invio prev.</a>', width: 100, align: 'right'},
                {field: 'numeroSpedizioni', title: 'Spezioni Totali', width: 100, align: 'center'},
                {field: 'righeConfermate', title: 'Confermate', width: 100, align: 'center'},
                {field: 'righeAnnullate', title: 'Annullate', width: 100, align: 'center'},
                {field: 'spedite', title: 'Spedite', width: 100, align: 'center'},
                {field: 'attesa', title: 'Attesa', width: 100, align: 'center'},
                {field: 'rimandate', title: 'Rimandate', width: 100, align: 'center'},
                {field: 'stato', title: 'Stato', width: 150, align: 'right',
                    formatter: function (value, rowm, index) {
                        var text = 'Not Found';
                        var test = parseInt(value);
                        switch (test) {
                            case 0:
                                text = 'Attesa ';
                                break;
                            case - 1:
                                text = 'Annullato';
                                break;
                            case 2:
                                text = 'Sospeso';
                                break;
                            case 1:
                                text = 'Eseguito';
                                break;
                            case 4:
                                text = 'Spedito'
                        }
                        return text;
                    },
                    editor: {type: 'combobox', options: {
                            valueField: 'inc_id',
                            textField: 'inc_type',
                            method: 'get',
                            url: '/webshopping/json/stato_task.json?v=2',
                            required: true
                        }}},
                {field: 'totale_contrassegno', title: 'Totale Contrassegno', width: 150, align: 'center',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'totale_assicurato', title: 'Totale Assicurato', width: 100, align: 'center',
                    formatter: function (value, row, index) {
                        return formatMoney(value) + '&euro;';
                    },
                    editor: {type: 'numberbox', options: {precision: 2}}},
                {field: 'noteSpedizioniere', title: 'Note', width: 200, align: 'center',
                    editor: {type: 'textbox'}},
                {field: 'noteCreatore', title: 'Note per Spedizioniere', width: 200, align: 'center'}


            ]]

    });
    var pager = $('#dg').datagrid('getPager');
    pager.pagination({
        pageNumber: 1,
        pageSize: 20,
        pageList: [20, 50, 100, 200, 250, 300],
        onSelectPage: function (pageNumber, pageSize) {
            buildTable(pageNumber, pageSize);
        },
        buttons: [{
                iconCls: 'icon-shipper',
                handler: function () {
                    invioListaSpedizioni();
                }
            },
        ]

    });
    $('.icon-shipper').tooltip({
        content: 'Invio conferma  spedizione'});
//            });

    $(window).resize(function () {
        setHeight();
    });
    $('#serach-order').click(function (e) {
        e.preventDefault();
        var pager = $('#dg').datagrid('getPager');
        //var data = [{id: 1, date: '10/10/2018', total: 20}];
        pager.pagination('refresh', {
            total: 0,
            pageNumber: 1
        });
        var options = pager.pagination('options');
        //console.log(options);
        //$('#dg').datagrid('loadData', data);
        buildTable(options.pageNumber, options.pageSize);
    });
    $('#reset').click(function (e) {
        e.preventDefault();

        $('#orderid_list').val('');
        $('#cod_rep').val('');
        $('#tip_doc').val('');
        $('#task').numberbox('setValue',null);
        
//        $('#indirizzo').val('');
//        $('#civico').val('');
//        $('#citta').val('');
//        $('#provincia').val('');
//        $('#regione').val('');
//        $('#cap').val('');
//        $('#country').val('');
        
        
        $("#daterange").val('last-30');
        $("#daterange").trigger('change');
        $("#channel").val('all');       
        $("#logistic-status").val('0');
        


    });
    $('#serach-order').trigger('click');


}
);
$(window).enterKey(function () {
    endEditing();
});



