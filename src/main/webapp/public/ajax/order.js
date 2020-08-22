function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}
function custom_show_response(responseData) {

    var content = $('<div class="container">');
    var general = $('<div class="row"><div class="col-lg-3">').appendTo(content);
    var form = $('<form name="Test" method="post" action="sendOrder">').appendTo(general);
    var api = $('<div class="form-group">\n\
                <label for="departureDepot">Api Version</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.apiVersion + '"/>\n\
                </div>').appendTo(form);
    var timeIt = $('<div class="form-group">\n\
                <label for="departureDepot">Curre Time Italy</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.currentTimeItaly + '"/>\n\
                </div>').appendTo(form);
    var timeUTC = $('<div class="form-group">\n\
                <label for="departureDepot">Curre Time UTC</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.currentTimeUTC + '"/>\n\
                </div>').appendTo(form);


    if (responseData.errorInfo && responseData.errorInfo.errorCode !== 0) {
        var errorInfo = $('<div class="row"><div class="col-lg-3">').appendTo(content);
        var formErro = $('<form name="Test" method="post" action="BartoliniShipmentLabel">').appendTo(errorInfo);
        var errorCode = $('<div class="form-group">\n\
                <label for="departureDepot">Error Code</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.errorInfo.errorCode + '"/>\n\
                </div>').appendTo(formErro);
        var errorCodeDesc = $('<div class="form-group">\n\
                <label for="departureDepot">Error Description</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.errorInfo.errorCodeDesc + '"/>\n\
                </div>').appendTo(formErro);
        var errorMessage = $('<div class="form-group">\n\
                <label for="departureDepot">Error Message</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.errorInfo.errorMessage + '"/>\n\
                </div>').appendTo(formErro);
        var errorSeverity = $('<div class="form-group">\n\
                <label for="departureDepot">Error Severity</label>\n\
                <input type="text" name="departureDepot" id="departureDepot" class="form-control" value="' + responseData.errorInfo.errorSeverity + '"/>\n\
                </div>').appendTo(formErro);
    }

    $('<div></div>').append(content).dialog({
        width: 700,
        title: 'Response',
        resizable: true,
        maxWidth: 1000,
        maxHeight: 500,
        modal: true,
        draggable: false,
        buttons: {
            'Ok': function () {

                $(this).dialog('close');
            }
        },
        closeOnEscape: true,
        closeText: "show",
        close: function () {

            $(this).dialog('destroy').remove();
        }
    });
}
$(document).ready(function () {

    $('#btn-order').click(function (e) {
        e.preventDefault();
        var url = "/webshopping/sendOrder";
        var requestData = {
            departureDepot: $('#departureDepot').val(),
            customerCode: $('#customerCode').val(),
            order: $('#order').val(),
            type: $('#type').val(),
            outType: $('#outType').val(),
            offsety:$('#offsety').val(),
            offsetx:$('#offsetx').val()
        };

        $.ajax({
            url: url,
            type: 'POST',
            data: requestData,
            success: function (resp) {
                try {
                    if (isJson(resp.Response)) {

                        var respObj = JSON.parse(resp.Response);
                        var result = respObj.response;
                        var errorcode = result.fault;
                        if (errorcode) {
                            console.log(respObj)
                        } else {

                            if (isJson(resp.Results)) {

                                var brtRes = JSON.parse(resp.Results);

                                custom_show_response(brtRes.brtvasRoutingAndLabelResponse);
                                console.log(brtRes);
                            }

                        }
                    }
                } catch (error) {
                    console.log(error);

                }

            }});
    });
    $('#btn-order-creation').click(function (e) {
        e.preventDefault();
        var url = "/BrtSoap/sendOrder";
        var requestData = {
            departureDepot: $('#departureDepot').val(),
            customerCode: $('#customerCode').val(),
            order: $('#order').val(),
            type: $('#type').val(),
            outType: $('#outType').val(),
            offsety:$('#offsety').val(),
            offsetx:$('#offsetx').val()
        };

        $.ajax({
            url: url,
            type: 'POST',
            data: requestData,
            success: function (resp) {
                try {
                    if (isJson(resp.Response)) {

                        var respObj = $.parseJSON(resp.Response);
                        var result = respObj.response;
                        var errorcode = result.fault;
                        if (errorcode) {
                            console.log(respObj)
                        } else {

                            if (isJson(resp.Results)) {

                                var brtResponce = $.parseJSON(resp.Results);
                                console.log(brtResponce);
                                custom_show_response(brtResponce.brtvasGetShipmentLabelResponse);
                            }

                        }
                    }
                } catch (error) {
                    console.log(error);
                    //  $.loader('close');
                }

            }});
    });
});

