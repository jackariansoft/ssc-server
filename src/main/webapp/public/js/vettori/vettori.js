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
function exportToExcel() {
    $('#dg').datagrid('toExcel', 'fatture.xls');
}

function buildTable(page, pageSize) {
    var url = "/webshopping/servizio_spedizioni";
    var requestData = {
        start: $('#start').val(),
        end: $('#end').val(),
        channel: buildDatabaseQueryString(),
        channelExcl: buildQueryStringChannelExclude(),
        orderlist: $('#orderid_list').val(),
        indirizzo: $('#indirizzo').val(),
        civico: $('#civico').val(),
        citta: $('#citta').val(),
        provincia: $('#provincia').val(),
        cap: $('#cap').val(),
        country: $('#country').val(),
        cod_rep: $('#cod_rep').val(),
        tip_doc: $('#tip_doc').val(),
        num_doc: $('#num_doc').val(),
        status: $('#status').val(),
        fatture: $('#num_fatt').val(),
        rif_mitt_num: $('#rif_mitt_num').val(),
        rif_mitt_alfa: $('#rif_mitt_alfa').val(),
        delta_min: $('#delta_min').val(),
        delta_max: $('#delta_max').val(),
        delta_riga_min: $('#delta_riga_min').val(),
        delta_riga_max: $('#delta_riga_max').val(),
        rag_soc:$('#rag_soc').val(),
        cap_list:$('#cap_list').val(),
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
                            pager.pagination('refresh', {
                                total: respObj.totalResult,
                                pageNumber: page
                            });
                        } else {
                            //$('#amount_label').html('&nbsp;0.00&euro;');
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
        onBeforeSelect: function (index, row) {
            return true;
        },
        detailFormatter: function (index, row) {
            return '<div style="padding:2px;position:relative;"><div class="ddv" id="ddv' + row.id + '" style="padding:5px 0;height:800px"></div></div>';
        },
        onExpandRow: function (index, row) {
            if (index !== fatturaDettaglio) {
                if (fatturaDettaglio !== undefined) {
                    $(this).datagrid('collapseRow', fatturaDettaglio);
                }
            }
            fatturaDettaglio = index;
            var ddv = $(this).datagrid('getRowDetail', index).find('div[id=ddv' + row.id + ']');
            buildDettaglioFattura(ddv, index, row,1);
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
                {field: 'id', title: "ID", align: 'right', hidden: true},
                {field: 'vettore', title: "Vettore", width: 50, align: 'right', hidden: false,
                    formatter: function (value, row, index) {

                        return value.sigla;
                    }
                },
                {field: 'ragioneSociale', title: "Ragione Sociale", width: 200, align: 'center', hidden: false,

                },
                {field: 'numero', title: 'Numero', align: 'center'},

                {field: 'dataFattura', title: 'Data Fattura', width: 100, align: 'center',
                    formatter: function (value, row, index) {

                        return convertDate(value);
                    }
                },
                {field: 'imponibile', title: 'Imponibile', width: 100, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'iva', title: 'IVA', width: 100, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'totaleFattura', title: 'Totale', width: 150, align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'totaleCostoSpedizione', width: 150, title: '<a href="#" title="Costo totale della spedizione al netto dei costi variabili." class="easyui-tooltip">Totale Costo Spedizione</a>', align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'totaleVarie', width: 150, title: '<a href="#" title="Totale dei costi variabili" class="easyui-tooltip">Totale Costo Variabile</a>', align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'totaleCostoPresunto', width: 150, title: 'Totale Costo Presunto', align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'delta', title: 'Delta', width: 150, align: 'center',
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
                {field: 'totaleOrdini', title: 'Totale Ordini', align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'incidenza', title: 'Incidenza &percnt;', align: 'center',
                    formatter: function (value, row, index) {
                        var incidenza = (1 - ((row.totaleOrdini - row.totaleFattura) / row.totaleOrdini)) * 100;
                        return formatMoney(incidenza) + '&percnt;';
                    }
                },
                 {field: 'totaleCommissioni', title: 'Commissioni', align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },//totaleFornitori
                {field: 'totaleFornitori', title: 'Fornitori', align: 'center',
                    formatter: function (value, row, index) {

                        return formatMoney(value) + '&euro;';
                    }
                },
                {field: 'margine', title: 'Margine', align: 'center',
                    styler: function (value, row, index) {
                        var margine = parseFloat(value);
                        if (margine <= 0) {
                            return 'background-color:red;color:white;';
                        } else {
                            return 'background-color:green;color:white;';
                        }
                    },
                    formatter: function (value, row, index) {
                        var incidenza = (row.totaleOrdini - (row.totaleFattura+row.totaleCommissioni+row.totaleFornitori));
                        return formatMoney(incidenza) + '&euro;';
                    }
                },
                {field: 'copertura', title: '<a href="#" title="Copertura dei costi di spedizione con margine" class="easyui-tooltip">Copertura</a>', align: 'center',
                    styler: function (value, row, index) {
                        var delta = parseFloat(value);
                        if (delta <= 0) {
                            return 'background-color:red;color:white;';
                        } else {
                            return 'background-color:green;color:white;';
                        }
                    },
                    formatter: function (value, row, index) {
                        
                        return formatMoney(value) + '&euro;';
                    }
                }
            ]]

    });


    var pager = $('#dg').datagrid('getPager');


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

        $('#orderid_list').val('');
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
        $('#num_fatt').val();                
        $('#rif_mitt_num').val('');
        $('#rif_mitt_alfa').val('');        
        $('#rag_soc').val('');        
        $('#delta_min').numberbox('clear');
        $('#delta_max').numberbox('clear');
        $('#delta_riga_min').numberbox('clear');
        $('#delta_riga_max').numberbox('clear');
        $('#cap_list').val('');        
        $("#daterange").val('last-30');
        $("#daterange").trigger('change');
        $("#channel").val('all');
        $("#status").val('Unshipped');
        $("#channelExcl").val('none');



    });
    $('#serach-order').trigger('click');


}
);



