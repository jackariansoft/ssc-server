/**
 * 
 */

var stompClient = null;

function setConnected(connected) {
   console.log("Connesso");
}

function connectToService() {
    var socket = new SockJS('/ssc/prenostazione-risorse');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/info', function (update) {
            aggiornaMessagio(update);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    
}
function aggiornaMessagio(message){
	
	if(isJson(message.body)){
		var obj = JSON.parse(message.body);
		var type = obj.type;
		var plc_source = obj.plc_source;
		var body = obj.body;
		var title = obj.title;
		if($('#plc').val()===plc_source){
			
			switch(type){
				case 0:
				$('#message-title').attr('class','alert alert-danger');
				break;
				case 1:
				$('#message-title').attr('class','alert alert-info');
				break;
				case 2:
				$('#message-title').attr('class','alert alert-warning');
				break;
			}
			$('#message-title').html(title);
			$('#message-body').html(body);
		}
	}else{
		console.log(message);
	}
}

$(document).ready(function () {
	
	connectToService();
	
	
	});
	
	
	