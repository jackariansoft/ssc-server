
var placeSearch, autocomplete;

var componentForm = {
  street_number: 'short_name',
  route: 'long_name',
  locality: 'long_name',
  administrative_area_level_1:'short_name',
  administrative_area_level_2: 'short_name',
  country: 'short_name',
  postal_code: 'short_name'
};
var fieldMap  = new MyMap();
fieldMap.put('street_number','civico')
fieldMap.put('route','indirizzo');
fieldMap.put('locality','citta');
fieldMap.put('administrative_area_level_1','regione');
fieldMap.put('administrative_area_level_2','provincia');
fieldMap.put('country','country');
fieldMap.put('postal_code','cap');

function initAutocomplete() {
  // Create the autocomplete object, restricting the search predictions to
  // geographical location types.
  autocomplete = new google.maps.places.Autocomplete(
      document.getElementById('autocomplete'), {types: ['geocode']});
 autocomplete.setComponentRestrictions({'country': ['it']});
  // Avoid paying for data that you don't need by restricting the set of
  // place fields that are returned to just the address components.
  autocomplete.setFields(['address_component']);

  // When the user selects an address from the drop-down, populate the
  // address fields in the form.
  autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
  // Get the place details from the autocomplete object.
  var place = autocomplete.getPlace();

  for (var component in componentForm) {
    document.getElementById(fieldMap.get(component)).value = '';
    document.getElementById(fieldMap.get(component)).disabled = false;
  }

  // Get each component of the address from the place details,
  // and then fill-in the corresponding field on the form.
  for (var i = 0; i < place.address_components.length; i++) {
    var addressType = place.address_components[i].types[0];
    if (componentForm[addressType]) {
        var val = place.address_components[i][componentForm[addressType]];
        document.getElementById(fieldMap.get(addressType)).value = val;
    }
  }
}

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
//function geolocate() {
//  if (navigator.geolocation) {
//      navigator.geolocation.getCurrentPosition(function(position) {
//      var geolocation = {
//        lat: position.coords.latitude,
//        lng: position.coords.longitude
//      };
//      var circle = new google.maps.Circle(
//          {center: geolocation, radius: position.coords.accuracy});
//          autocomplete.setBounds(circle.getBounds());
//    });
//  }
//}

function modificaIndirizzo(address_info) {
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
                                                $.messager.alert('Sistema', 'Errore Inaspettato. Inviata mail allo sviluppatore: ' + respObj.errorMessage, 'error');
                                                //SendEmail("Unexpected result from server to /report/IntelligenceResult: " + resp);
                                                console.log(resp);
                                                break;
                                        }
                                    } else {

                                        var address_info = respObj.result;

                                        var index = $('#dg').datagrid('getRowIndex', address_info.corder);
                                        $('#dg').datagrid('updateRow', {
                                            index: index,
                                            row: {
                                                address: respObj.result
                                            }
                                        });
                                        $('#dg').datagrid('collapseRow', index);
                                        $('#modInd').window('close');

                                    }

                                } else {
                                    alert('Unexpected result from server, sorry! Devs have been informed');
                                }
                            } catch (error) {
                                $('#modInd').window('close');
                                console.log(error);
                                $.loader('close');
                            }
                        },
                        error: function (resp, status, er) {
                            $('#modInd').window('close');
                            $.loader('close');
                            alert('Unexpected result from server, sorry! Devs have been informed');
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
        },
        onOpen: function () {

        }
    });
    $('#modInd').window('open');
}

function aggiornaInfoOrdini() {
    $('#upload').window({
        width: 800,
        height: 300,
        modal: true,
        title: 'Aggiorna Indirizzo',        
        href: '/webshopping/ordini/gestione_upload_ordini.jsp',
        onLoad: function () {
       
        },
        onOpen: function () {

        }
    });
    
    $('#upload').window('open');
}

