<div class="row">

	<div class="col-sm-5" style="margin-left: 0px; padding: 0">
		<div class="row" id="date-filter">
			<form>
				<div class="form-row col-12">
					<div class="col-sm-3 my-1">
						<label class="mr-sm-2 form-control-sm" for="daterange">Intervallo
							Temporale</label> <select name="daterange" class="form-control-sm"
							id="daterange">
							<option value="last-365">Ultimi 365 giorni</option>
							<option value="last-180">Ultimi 180 giorni</option>
							<option value="last-90">Ultimi 90 giorni</option>
							<option value="last-60">Ultimi 60 giorni</option>
							<option value="last-30" selected="true">Ultimi 30 giorni</option>
							<option value="last-15">Ultimi 15 days</option>
							<option value="last-7">Ultimi 7 days</option>
							<option value="yesterday">Ieri</option>
							<option value="this-month">Questo mese</option>
							<option value="last-month">Ultimo Mese</option>
							<option value="this-year">Questo Anno</option>
							<option value="last-year">Ultimo Anno</option>
							<option value="custom">Configura</option>
						</select>
					</div>
					<div class="col-sm-3 my-1">
						<label class="mr-sm-2 form-control-sm" for="start">Da</label> <input
							type="text" id="start" name="start" class="form-control-sm"
							placeholder="From date" />
					</div>
					<div class="col-sm-3 my-1">
						<label class="mr-sm-2 form-control-sm" for="end">A</label> <input
							type="text" id="end" name="end" class="form-control-sm"
							placeholder="To date" />
					</div>
				</div>
			</form>
		</div>
		<div class="row" id="order-filter">
			<form>
				<div class="col-sm-3 my-1">
					<label class="mr-sm-1 form-control-sm" for="channel">Risorce</label>
					<input id="channel" class="form-control-sm" type="text">
				</div>
				<div class="form-row col-12">

					<div class="col-sm-10 my-1">
						<label class="mr-sm-1 form-control-sm" for="logistic-status">Stato
							Risorse</label> <select class="form-control-sm" id="logistic-status"
							style="margin-left: 5px">
							<option value="none">Nessuno</option>
							<option value="0">Attesa</option>
							<option value="1">Avviata</option>
							<option value="2">Terminata</option>
							<option value="3">Interrotta</option>
							<option value="4">Scaduta</option>
							<option value="6">Sospesa</option>
						</select>
					</div>
				</div>
				<div class="form-row col-12">
					<div class="col-auto my-3">
						<button type="button" id="serach-order"
							class="btn btn-primary btn-sm">Cerca</button>
					</div>
					<div class="col-auto my-3">
						<button type="button" id="reset" class="btn btn-primary btn-sm">Reset</button>
					</div>
				</div>

			</form>
		</div>
	</div>

	<div class="col-sm-7" style="margin-right: 0px; padding: 0">
		<div id="aa" class="easyui-accordion"	style="width: 500px; min-height: 350px">
			<div title="Prenotazione Risorsa"
				data-options="iconCls:'icon-save','fit':true"
				style="overflow: auto; padding: 0px; width: 100%">
				<h3 style="color: #0099FF;">Prenotazione Risorsa</h3>
				<div class="row" style="padding: 0; margin: 0; margin-left: 5px;">
					<div class="col-sm-12">
						<form id="ff" method="post" action="">
							<div class="row" style="padding: 0; margin: 0;">
								<div class="col-sm-5" style="margin-right: 0px; padding: 0">

									<input type="text" class="easyui-datebox" required="required" 
										id="start_day" name="start_day" label="Giorno: "
										labelPosition=style="width: 100%" data-options="formatter:myformatter,parser:myparser">

								</div>
							</div>
							<div class="row">
								<div class="col-sm-5" style="margin-left:15px; margmargin-right: 0px; padding: 0">
									<input class="easyui-timepicker" name="start_hour" required="required" data-options="hour24:true"
										id="start_hour" label="Ora Inizio:" labelPosition="top"
										style="width: 100%">
								</div>
								<div class="col-sm-5" style="margin-left:15px;margin-right: 0px; padding: 0">
									<input class="easyui-timepicker" name="end_hour" id="end_hour" required="required"
										label="Ora Fine:" labelPosition="top" style="width: 100%" data-options="hour24:true">
								</div>
								<div class="col-sm-5" style="margin-left:15px;margin-right: 0px; padding: 0">
									<select class="easyui-combobox" name="resource_tag" id="resource_tag"
										label="Rirsorsa" style="width: 100%" labelPosition="top" required="required"
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
			</div>
		</div>
	</div>
</div>
