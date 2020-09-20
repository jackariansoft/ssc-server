/* global detailview */
var fatturaDettaglio = undefined;
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

function buildDatabaseQueryString() {
    var staticLink = '';
    var site_option = $('#inChannel').tagbox('getValues');
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
    var site_option = $('#outChannel').tagbox('getValues');
    var entrys = new Array();
    if (site_option === "none") {
        staticLink += 'none';
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
function exportToExcel() {
    //$('#dg').datagrid('toExcel', 'fatture.xls');
    var requestData = {
        start: $('#start').val(),
        end: $('#end').val(),
        channel: buildDatabaseQueryString(),
        channelExcl: buildQueryStringChannelExclude(),
        orderlist: $('#orderidlist').val(),
        ordine_int: $('#ordine_int').val(),
//        citta: $('#citta').val(),
//        provincia: $('#provincia').val(),
//        cap: $('#cap').val(),
//        country: $('#country').val(),
        cod_rep: $('#cod_rep').val(),
        tip_doc: $('#tip_doc').val(),
        num_doc: $('#num_doc').val(),
        status: $('#status').val(),
        fatture: $('#num_fatt').val(),
        rif_mitt_num: $('#rif_mitt_num').val(),
        rag_soc: $('#rag_soc').val(),
        doc_type: $('#doc_type').val(),
        start_fatture: $('#start_fatture').val(),
        end_fatture: $('#end_fatture').val(),
        date_fattura: $('#date_fattura').is(':checked'),
        date_order: $('#date_order').is(':checked'),
        fattura_fornitore: $('#fattura_fornitore').is(':checked'),
        page: 1,
        pageSize: 10000
    };
    ajax_download('/webshopping/service_esporta_dettaglio_contabilita', requestData);
}

function buildTable(page, pageSize) {
    var url = "/webshopping/service_dettaglio_contabilita";
    var requestData = {
        start: $('#start').val(),
        end: $('#end').val(),
        channel: buildDatabaseQueryString(),
        channelExcl: buildQueryStringChannelExclude(),
        orderlist: $('#orderidlist').val(),
        ordine_int: $('#ordine_int').val(),
//        citta: $('#citta').val(),
//        provincia: $('#provincia').val(),
//        cap: $('#cap').val(),
//        country: $('#country').val(),
        cod_rep: $('#cod_rep').val(),
        tip_doc: $('#tip_doc').val(),
        num_doc: $('#num_doc').val(),
        status: $('#status').val(),
        fatture: $('#num_fatt').val(),
        rif_mitt_num: $('#rif_mitt_num').val(),
        rag_soc: $('#rag_soc').val(),
        doc_type: $('#doc_type').val(),
        start_fatture: $('#start_fatture').val(),
        end_fatture: $('#end_fatture').val(),
        date_fattura: $('#date_fattura').is(':checked'),
        date_order: $('#date_order').is(':checked'),
        fattura_fornitore: $('#fattura_fornitore').is(':checked'),
        deferred:$('#fattura_deferred').is(':checked'),
        page: page === 0 ? 1 : page,
        pageSize: pageSize
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
                        var pager = $('#dg').datagrid('getPager');


                        if (respObj.totalResult > 0) {


                            //$('#amount_label').html('&nbsp;' + formatMoney(respObj.totalAmount) + '&euro;');

                            $('#dg').datagrid('loadData', respObj.result);
                            $('#dg').datagrid('reloadFooter', );

                            $('#amount_label').html('&nbsp;' + formatMoney(respObj.totaleOrdini) + '&euro;');
                            $('#amount_margine_1_label').html('&nbsp;' + formatMoney(respObj.totaleMargine1) + '&euro;');
                            $('#amount_margine_2_label').html('&nbsp;' + formatMoney(respObj.totaleMargine2) + '&euro;');
                            $('#amount_commissione_label').html('&nbsp;' + formatMoney(respObj.totaleCommissione) + '&euro;');
                            $('#amount_margine_perc_1_label').html('&nbsp;' + formatMoney(respObj.totaleMarginePerc2) + '&percnt;');
                            $('#amount_marg_perc_label').html('&nbsp;' + formatMoney(respObj.totaleMarginePerc) + '&percnt;');
                            $('#amount_fatture_label').html('&nbsp;' + formatMoney(respObj.totaleFatture) + '&euro;');
                            $('#amount_costi_label').html('&nbsp;' + formatMoney(respObj.totaleCosti) + '&euro;');
                            $('#amount_fornitori_label').html('&nbsp;' + formatMoney(respObj.totaleFornitore) + '&euro;');
                            $('#amount_trasporto_label').html('&nbsp;' + formatMoney(respObj.totaleTrasporto) + '&euro;');
                            $('#amount_trasporto_pres_label').html('&nbsp;' + formatMoney(respObj.totaleTrasportoPresunto) + '&euro;');

                            pager.pagination('refresh', {
                                total: respObj.totalResult,
                                pageNumber: page
                            });


                        } else {
                            //$('#amount_label').html('&nbsp;0.00&euro;');
                            //disableFilter

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
            $.messager.alert('Sistema', 'Errore inaspettato. Inviata mail allo sviluppatore: ' + er, 'error');

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
        filterMatchingType: 'all',
        clientPaging: false,
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
        idField: 'ordine_interno',
        val: function (row, field, formattedValue) {
            return row[field];
        },
        onBeforeSelect: function (index, row) {
            return true;
        },
        detailFormatter: function (index, row) {
            //return '<div style="padding:2px;position:relative;"><div class="ddv" id="ddv' + row.id + '" style="padding:5px 0;height:800px"></div></div>';
        },
        onExpandRow: function (index, row) {
//            if (index !== fatturaDettaglio) {
//                if (fatturaDettaglio !== undefined) {
//                    $(this).datagrid('collapseRow', fatturaDettaglio);
//                }
//            }
//            fatturaDettaglio = index;
//            var ddv = $(this).datagrid('getRowDetail', index).find('div[id=ddv' + row.id + ']');
//            buildDettaglioFattura(ddv, index, row,1);
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


        },
        onBeforeEdit: function (index, row) {

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

        },

        columns: [[
                {field: 'ck', checkbox: true},
                {field: 'deferred', title: "#", width: 20, align: 'center',
                    formatter: function (value, row, index) {
                        if(value===true){
                            return '#';
                        }else{
                            return ' ';
                        }
                    }
                },
                {field: 'ordine_interno', title: "Ordine Interno", width: 100, align: 'right', hidden: false},
                {field: 'channel', title: "Marketplace", width: 85, align: 'right', hidden: false,
                    formatter: function (value, row, index) {

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
                    }
                },
                {field: 'stato_ordine', title: "Stato", width: 70, align: 'center', hidden: false,

                },
                {field: 'data_ordine', title: 'Data Ordine', width: 80, align: 'center',
                    formatter: function (value, row, index) {

                        return convertDate(value);
                    }
                },
                {field: 'tipdoc', title: 'Tip. Doc', width: 50, align: 'center',

                },
                {field: 'codrep', title: 'Rep', width: 50, align: 'center',

                },
                {field: 'doc_fornitore', title: ' Num Doc', width: 70, align: 'center',

                },
                {field: 'ragsoc', title: 'Cliente', width: 250, align: 'center',

                },
                {field: 'totale_ordine', title: 'Tot. Ordine', width: 80, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'totale_fornitore', title: 'Tot. Fornitore', width: 80, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'margine_1', title: 'I Margine', width: 80, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'margine_perc_1', title: 'Margine %', width: 80, align: 'center',
                    formatter: function (value, row, index) {
                        if (typeof value === 'undefined') {

                            return 'n.v.';
                        } else {
                            return formatMoney(value) + '&percnt;';
                        }
                    }
                },
                //Tot. Costi               

                {field: 'commissione', title: 'Commissione', width: 100, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'costo_trasporto_reale', title: 'Trasporto', width: 100, align: 'center',

                    styler: function (value, row, index) {

                        if (typeof value === 'undefined') {

                            return 'font-weight:bold';
                        } else {
                            return 'font-weight:normal';
                        }
                    },
                    formatter: function (value, row, index) {
                        if (typeof value === 'undefined') {
                            if (typeof row.costo_spedizione_pres === 'undefined') {
                                return 'n.v.';
                            } else {
                                return formatMoney(row.costo_spedizione_pres) + '&euro;';
                            }
                        } else {
                            return formatMoney(value) + '&euro;';
                        }
                    }
                },
                {field: 'tot_costi', title: 'Tot. Costi', width: 80, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'margine_2', title: 'II Margine', width: 80, align: 'center',
                    formatter: function (value, row, index) {
                        if (typeof value === 'undefined') {

                            return 'n.v.';
                        } else {
                            return formatMoney(value) + '&euro;';
                        }
                    }
                },
                {field: 'margine_perc_2', title: 'Margine %', width: 80, align: 'center',
                    formatter: function (value, row, index) {
                        if (typeof value === 'undefined') {

                            return 'n.v.';
                        } else {
                            return formatMoney(value) + '&percnt;';
                        }
                    }
                },
                {field: 'doc_type', width: 50, title: 'Tipo', align: 'center',

                },
                {field: 'numero_fattura', width: 80, title: 'N. Documento', align: 'center',

                },
                {field: 'totale_fattura', width: 100, title: 'Tot. Documento', align: 'center',
                    formatter: function (value, row, index) {
                        if (typeof value === 'undefined') {
                            return 'n.v.';
                        } else {
                            return formatMoney(value) + '&euro;';
                        }
                    }
                },

                {field: 'data_fattura', width: 100, title: 'Data Fattura', align: 'center',
                    formatter: function (value, row, index) {
                        if (typeof value === 'undefined') {
                            return 'n.v.';
                        } else {
                            return convertDate(value);
                        }
                    }
                }

            ]]

    });

    var pager = $('#dg').datagrid('getPager');
    $('#dg').datagrid('enableFilter',
            [

                {
                    field: 'totale_ordine',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'totale_fornitore',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'margine_1',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'commissione',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'costo_trasporto_reale',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'costo_trasporto_reale',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'tot_costi',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'margine_2',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'margine_perc',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'numero_fattura',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
                {
                    field: 'totale_fattura',
                    type: 'numberbox',
                    options: {precision: 2},
                    op: ['equal', 'notequal', 'less', 'greater']
                },
            ]
            );



    pager.pagination({
        pageNumber: 1,
        pageSize: 50,
        pageList: [20, 50, 100, 200, 250, 300],
        onSelectPage: function (pageNumber, pageSize) {
            buildTable(pageNumber, pageSize);
        }

    });
    $('.icon-shipper').tooltip({content: 'Invio dati per la creazione del task di spedizione.<br> Solo le righe con i valori corretti saranno salvate'});
    $("input[name='skiperror']").tooltip({content: 'Se selezionato, crea task spedizione con i soli dati corretti tralasciando quelli in errore.'});

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
        buildTable(options.pageNumber, options.pageSize);
    });

    $('#reset').click(function (e) {
        e.preventDefault();

        $('#orderidlist').val('');
        $('#cod_rep').val('');
        $('#tip_doc').val('');
        $('#num_doc').val('');
//        $('#indirizzo').val('');
//        $('#civico').val('');
//        $('#citta').val('');
//        $('#provincia').val('');
//        $('#regione').val('');
//        $('#cap').val('');
//        $('#country').val('');
        $('#num_fatt').val();
        $('#rif_mitt_num').val('');
//        $('#rif_mitt_alfa').val('');
        $('#rag_soc').val('');
//        $('#delta_min').numberbox('clear');
//        $('#delta_max').numberbox('clear');
//        $('#delta_riga_min').numberbox('clear');
//        $('#delta_riga_max').numberbox('clear');
//        $('#cap_list').val('');
        $("#daterange").val('');
        $("#daterange").trigger('');
        $("#channel").val('all');
        $("#status").val('all');
        $("#inChannel").tagbox('clear');
        $("#inChannel").tagbox('setValue', 'all');
        $("#outChannel").tagbox('clear');
        $("#outChannel").tagbox('setValue', 'none');


    });
    $('#inChannel').tagbox({
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
    $('#outChannel').tagbox({
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
    $('#serach-order').trigger('click');


}
);



