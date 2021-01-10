

function initTree(page, pageSize) {





	$.ajax({
		url: list_plc,
		type: 'GET',
		beforeSend: function() {
			$.loader({
				className: "blue-with-image",
				content: ''
			});
		},
		success: function(respObj) {
			try {


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



					if (respObj.totalResult > 0) {

						$('#plc_list').tree('loadData', respObj.result);


					} else {

						$('#plc_list').datagrid('loadData', []);


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

		},
		complete: function(rest, status) {
			$.loader('close');

		}
	});
}
/**
	Attivazione manuale della risorsa 
 */
function attiva() {
	var t = $('#plc_list');
	var node = t.tree('getSelected');
    var parent = t.tree('getParent',node.target);
	
	if(parent!==null&&node!==null){
		invioComandoRisorsa(node.text,parent.text,1);
	}

}
/**
 	Disattivazione manuale della risorsa
 */
function disattiva() {
	var t = $('#plc_list');
	var node = t.tree('getSelected');
    var parent = t.tree('getParent',node.target);
	
	if(parent!==null&&node!==null){
		invioComandoRisorsa(node.text,parent.text,2);
	}
}
function invioComandoRisorsa(resource, plc,stato) {

	

	var dataActivation = {
		"plc_uid": plc,
		"resource_tag": resource,
		"action": stato
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

						$.messager.alert('Sistema', 'Risorsa  ' + resource +( (stato==1)?' abilitata':' disabilitata'), 'info');

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

	$('#plc_list').tree({
		onClick: function(node) {
			alert(node.nodeType);  // alert node text property when clicked
		},
		onContextMenu: function(e, node) {
			e.preventDefault();
			$(this).tree('select', node.target);
			if (node.nodeType === 'resource') {
				$('#mpresource').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			}

		},
		'dnd': true
	});
	initTree(null, null);

});