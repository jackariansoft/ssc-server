
var picker1 = null;
var picker2 = null;
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

	var url = base_url+"/resource/reservation";

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
		beforeSend: function() {
			$.loader({
				className: "blue-with-image",
				content: ''
			});
		},
		success: function(respObj) {
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
		error: function(resp, status, er) {
			$.loader('close');
			alert('Unexpected result from server, sorry! Devs have been informed');
			//SendEmail(String(resp.responseText));
			//           
		},
		complete: function(rest, status) {
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
/**
	Avvio processo di creazione prenotazione risorsa
 */
function creaPrenotazione() {
	$('#modPrenotazione').window('open');

}
function clearForm() {
	$('#ff').form('clear');
}
function validatFormPrenotazioneConInvio() {
	$('#ff').submit();
}
/**
   Inserimento manuale prenotazione
 */
function aggiungiPrenotazione(plc, resource, start_p, end_p) {
	var data = {
		"plc_uid": plc,
		"resource_tag": resource,
		"start": start_p,
		"end": end_p,
		"payload": "manual",
		"action": 1
	}
	$.ajax({
		url: inserisci_prenotazione,
		type: 'POST',
		data: JSON.stringify(data),
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		beforeSend: function() {
			$.loader({
				className: "blue-with-image",
				content: ''
			});
		},
		success: function(respObj) {
			try {


				var status = respObj.status;


				switch (status) {
					case 200:

						$.messager.alert('Sistema', 'Risorsa  ' + resource + ' prenotazione effettuata', 'info');

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
		error: function(resp, status, er) {
			$.loader('close');
			alert('Unexpected result from server, sorry! Devs have been informed');
			//SendEmail(String(resp.responseText));
			//           
		},
		complete: function(rest, status) {
			$.loader('close');
			//            $('html, body').animate({
			//                scrollTop: $("#rangedate-table").offset().top - 70
			//            }, 2000);
		}
	});
}
/** Forzatura avvio attivazione risorsa */

function forzaAvvioRisorsa(resource, plc) {



	var dataActivation = {
		"plc_uid": plc,
		"resource_tag": resource,
		"action": 1
	};

	$.ajax({
		url: invio_comando,
		type: 'POST',
		data: JSON.stringify(dataActivation),
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		beforeSend: function() {
			$.loader({
				className: "blue-with-image",
				content: ''
			});
		},
		success: function(respObj) {
			try {


				var status = respObj.status;


				switch (status) {
					case 200:

						$.messager.alert('Sistema', 'Risorsa  ' + resource + ' abilitata', 'info');

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
		error: function(resp, status, er) {
			$.loader('close');
			alert('Unexpected result from server, sorry! Devs have been informed');
			//SendEmail(String(resp.responseText));
			//           
		},
		complete: function(rest, status) {
			$.loader('close');
			//            $('html, body').animate({
			//                scrollTop: $("#rangedate-table").offset().top - 70
			//            }, 2000);
		}
	});
}



$(document).ready(function() {


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
		onBeforeLoad: function() {
			erroMap.clear();
		},
		onBeforeSelect: function(index, row) {
			return true;
		},
		detailFormatter: function(index, row) {
			return '<div class="ddv" style="padding:5px 0"></div>';
		},
		onExpandRow: function(index, row) {
			var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
			buildDetatils(ddv, index, row);
		},
		onLoadSuccess: function(data) {
			$.messager.show({
				title: 'Info',
				msg: 'Caricati ' + data.total + ' records'
			});

		},
		onDblClickCell: function(index, field, value) {

		},
		onEndEdit: function(index, row) {
			console.log(row.deffered);

		},
		onBeforeEdit: function(index, row) {
			if (typeof row.spedizione !== "undefined") {
				return false;
			} else {
				return true;
			}
		},
		onClickRow: function(rowIndex, row) {

			$('#dg').datagrid('selectRow', rowIndex);

		},
		onHeaderContextMenu: function(e, field) {

		},

		columns: [[
			{ field: 'ck', checkbox: true },
			{ field: 'id', title: "ID", align: 'right', hidden: false },
			{ field: 'payload', title: "Payload", align: 'center' },
			{ field: 'requestTime', title: "Data Richiesta", align: 'center', hidden: false },
			{ field: 'starTime', title: "Inizio", align: 'center', hidden: false },
			{ field: 'endTime', title: "Fine", align: 'center', hidden: false },
			{
				field: 'status', title: "Stato", align: 'center', hidden: false,
				//0=attesa,1=avviata,2=terminata,3=interrotta,4=scaduta,5=payload non valido,6=sospesa
				formatter: function(value, row, index) {
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
			{ field: 'tag', title: "TAG", align: 'center', hidden: false },
			{ field: 'reference', title: "Riferimento", align: 'center', hidden: false },
			{ field: 'plcRef', title: "PLC ", align: 'center', hidden: false },
			{ field: 'idaddress', title: "IP Address PLC ", align: 'center', hidden: false },
			{ field: 'schedulerId', title: "Scheduling Ref.", align: 'center', hidden: false },
			{
				field: 'receivedInterruptAt', title: "Forza Avvio", align: 'center',
				formatter: function(value, row, index) {
					if(row.status===0){
					return "<button  onclick = forzaAvvioRisorsa(\"" + row.tag + "\",\"" + row.plcRef + "\")  class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-reload'\" style=\"width:80px'\" value=\"Forza Avvio\">Avvia</button>";
					}else{
					 return "";
					}
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
		onSelectPage: function(pageNumber, pageSize) {
			setQueryStringOrdini("ordini/ordini.jsp", pager);
			buildTable(pageNumber, pageSize);
		}

	});



	$(window).resize(function() {
		setHeight();
	});
	if (window.history && window.history.pushState) {

		$(window).on('popstate', function() {
			location.reload();
		});
	}
	$('#serach-order').click(function(e) {
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

	$('#import-order').click(function(e) {
		aggiornaInfoOrdini();
	});

	$('#reset').click(function(e) {
		e.preventDefault();

	});

	var url_params = populateUrlparams();


	$('#channel').tagbox({
		url: '/ssc/api/resource',
		method: 'get',
		value: 'all',
		valueField: 'id',
		textField: 'text',
		limitToList: true,
		hasDownArrow: true,
		prompt: 'Selezione Risorsa',
		onRemoveTag: function(value) {

		}
	});

 
	if (Object.keys(url_params).length > 0) {
		setFieldFormOrdini(url_params);
		buildTable(url_params.page, url_params.pageSize);
	} else {
		$('#serach-order').trigger('click');
	}
	/**
		Impostazione finestra prenotazione manuale risorsa
	 */ 
	$('#modPrenotazione').window({
		href:'risorse/gestione_prenotazione.jsp',
		width:500,
    	height:400,
	    title:'Prenotazione Risorsa',
    	modal:true,
		closed:true,
		onOpen: function () {
			clearForm();
        }
	});
	
	
	
    
	
	
	$( "#ff" ).on( "submit", function( event ) {
  		event.preventDefault();
  		console.log( $( this ).serialize() );
		if($(this).form('enableValidation').form('validate')){
			console.log($('#start_day').datebox('getValue'));
			console.log(($('#start_hour').timepicker('getValue')));
			console.log(($('#end_hour').timepicker('getValue')));
			console.log($('#plc_uid').val());
			console.log($('#resource_tag').combobox('getValue'));
			/* plc, resource, start_p, end_p */
			var start = $('#start_day').datebox('getValue')+" "+$('#start_hour').timepicker('getValue')+":20";
			var end   = $('#start_day').datebox('getValue')+" "+$('#end_hour').timepicker('getValue')+":00";
			
			//Controllo delle date
			
		    var c_start = moment(start,"YYYY-MM-DD HH:mm:ss");   
			var c_end   = moment(end,"YYYY-MM-DD HH:mm:ss");
			var now = moment();
			
			if(c_start.isBefore(now)){
				$.messager.alert('Errore','Data inizio non puo\'essere minore della data corrente.', 'error');
				return;
			}
			if(c_start.isValid()&&c_end.isValid()){//Controllo se le date inserite sono valide
				
				if(c_end.isBefore(c_start)){//L'ora di inizio prenotazione deve essere antecedente l'ora di fine prenotazione
					
					$.messager.alert('Errore','Data inizio e fine prenotazione non sono corrette. L\'ora di inizio prenotazione deve essere antecedente l\'ora di fine prenotazione ', 'error');
					
					
				}else {
					
					aggiungiPrenotazione($('#plc_uid').val(),$('#resource_tag').combobox('getValue'),start,end);
				}
				
			}else{
				$.messager.alert('Errore','Data inizio e fine prenotazione non sono corrette. Controllare immissione dati.', 'error');
			}   
			
			
			///aggiungiPrenotazione($('#plc_uid').val(),$('#resource_tag').combobox('getValue'),start,end);
					}
	});
	
	 $('#start_day').datebox().datebox('calendar').calendar({
                validator: function(date){
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());                    
                    return date>=d1;
                }
      });
	  connectToService();
}
);




