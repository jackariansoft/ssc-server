
<div class="row" style="padding: 0; margin: 0; margin-left: 0px;margin-top: 50px">
	<div class="col-sm-12" style="padding: 0; margin: 0;">
		<form id="ff" method="post" action="">
			<div class="row" style="padding: 0; margin: 0;">
				<div class="col-sm-6" style="margin-right: 0px; padding: 0">

					<input type="text" class="easyui-datebox" required="required"
						id="start_day" name="start_day" label="Giorno: "
						labelPosition=style=
						"width: 100%" data-options="formatter:myformatter,parser:myparser">

				</div>
			</div>
			<div class="row">
				<div class="col-sm-5"
					style="margin-left: 15px; margmargin-right: 0px; padding: 0">
					<input class="easyui-timepicker" name="start_hour"
						required="required" data-options="hour24:true" id="start_hour"
						label="Ora Inizio:" labelPosition="top" style="width: 100%">
				</div>
				<div class="col-sm-5"
					style="margin-left: 15px; margin-right: 0px; padding: 0">
					<input class="easyui-timepicker" name="end_hour" id="end_hour"
						required="required" label="Ora Fine:" labelPosition="top"
						style="width: 100%" data-options="hour24:true">
				</div>
				<div class="col-sm-5"
					style="margin-left: 15px; margin-right: 0px; padding: 0">
					<select class="easyui-combobox" name="resource_tag"
						id="resource_tag" label="Rirsorsa" style="width: 100%"
						labelPosition="top" required="required"
						data-options="valueField:'text',textField:'text',url:'/ssc/api/resource',method: 'get',
				onSelect: function(rec){
          		  
            		$('#plc_uid').val(rec.plc_uid);
        			}">
					</select>
				</div>
			</div>
			<input type="hidden" name="plc_uid" id="plc_uid">
		</form>
		<div style="text-align: center; padding: 5px 0">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="validatFormPrenotazioneConInvio()" style="width: 80px">Submit</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="clearForm()" style="width: 80px">Clear</a>
		</div>
	</div>
</div>
