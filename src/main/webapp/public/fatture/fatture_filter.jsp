<div class="row">   
    <div class="col-sm-12" style="margin-left:0px;padding: 0">
        <div class="row" id="date-filter">
            <form>
                <div class="form-row align-items-sm-end">
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="daterange">Data Ordine</label>                
                        <select name="daterange"  class="form-control-sm" id="daterange">
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
                        <label class="mr-sm-2 form-control-sm" for="start">Da</label>
                        <input type="text" id="start" name="date_start" class="form-control-sm" placeholder="From date"/>
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="end">A</label>
                        <input type="text" id="end" name="date_end" class="form-control-sm" placeholder="To date"/>    
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="date_order">Escludi</label>
                        <input type="checkbox" id="date_order" name="date_order" title="La spunta esclude la data degli ordini dai parametri di ricerca">    
                    </div>
                </div>
            </form>
        </div>
        <div class="row" id="date-filter-fatture">
            <form>
                <div class="form-row align-items-sm-end">
                    <div class="col-sm-3">
                        <label class="mr-sm-2 form-control-sm" for="daterange-fatture">Data Fattura</label>                
                        <select name="daterange_fatture"  class="form-control-sm" id="daterange-fatture">
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
                        <label class="mr-sm-2 form-control-sm" for="start_fatture">Da</label>
                        <input type="text" id="start_fatture" name="start_fatture" class="form-control-sm" placeholder="Da"/>
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="end_fatture">A</label>
                        <input type="text" id="end_fatture" name="end_fatture" class="form-control-sm" placeholder="A"/>    
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="date_fattura">Escludi</label>
                        <input type="checkbox" id="date_fattura" checked="true" name="date_fattura" title="La spunta esclude la data delle fatture dai parametri di ricerca">    
                    </div>
                </div>
            </form>
        </div>
        <div class="row" id="fatture-filter">
            <form>
                <div class="form-row" style="width: 60%;">                  
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="orderidlist">Ordine su Market Place</label>
                        <input type="text" class="form-control-sm" id="orderidlist" title="Insesci ordini separati da virgola" placeholder="Lista numero ordine">
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="inChannel">Markeplace</label>
                        <input id="inChannel"  class="form-control-sm" type="text">
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="form-control-sm" for="outChannel">Escludi Markeplace</label>
                        <input id="outChannel"  class="form-control-sm" type="text">
                        
                    </div>
                    <div class="col-sm-2 my-1">
                        <label class="mr-sm-2 form-control-sm" for="status">Stato Ordini</label>
                        <select class="form-control-sm" id="status">
                            <option value="all" selected="true">All</option>
                            <option value="Pending">Pending</option>
                            <option value="PendingAvailability">Pending Availability</option>
                            <option value="Unshipped">Unshipped</option>
                            <option value="PartiallyShipped">Partially Shipped</option>
                            <option value="Shipped">Shipped</option>
                            <option value="Shipping">Shipping</option>
                            <option value="Canceled">Canceled</option>
                            <option value="Unfulfillable">Unfulfillable</option>                      
                        </select> 
                    </div>
                </div>

                <div class="form-row" style="width: 60%;">
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-2 form-control-sm" for="doc_type">Tipo Documento</label>
                        <select class="form-control-sm" id="doc_type">
                            <option value="all" code="">Tutti</option>
                            <option value="CO" code="CO">CO</option>
                            <option value="FI" code="FI">FT</option>                            
                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="fattura_fornitore">Solo Fattura Vettore</label>
                        <input type="checkbox" id="fattura_fornitore" name="fattura_fornitore" title="La spunta esclude gli ordini non fatturati dal vettore">    
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="fattura_deferred">Solo #</label>
                        <input type="checkbox" id="fattura_deferred" name="fattura_fornitore" title="La spunta esclude gli ordini non fatturati dal vettore">    
                    </div>
                </div>
                <div class="form-row" style="width: 60%;">                    
                    
                    <div class="col-sm-3 my-1 ">
                        <label class="mr-sm-2 form-control-sm" for="ordine_int" title="Lista oridine documento">Lista Ordine Interno</label>
                        <input type="text" class="form-control-sm my-1" id="ordine_int"  name="ordine_int" title="Inserisci lista numeri ordine interno separati da virgola" placeholder="Lista ordine interno">
                    </div>                    
                    <div class="col-sm-3 my-1 ">
                        <label class="mr-sm-2 form-control-sm" for="rif_mitt_num" title="Lista numero documento">Lista numero documento</label>
                        <input type="text" class="form-control-sm my-1" id="rif_mitt_num" title="Inserisci lista numeri documento separati da virgola" placeholder="Lista riferimento mittente numerico">
                    </div>                                       
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="num_fatt" title="Lista Fattura Interna">Lista num. fatt. interna</label>
                        <input type="text" class="form-control-sm my-1" id="num_fatt" title="Inserisci lista numero fatture serparate da virgola" placeholder="Lista Numero Fatture Interne">
                    </div>                    
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="rag_soc" title="Ragione sociale cliente che ha effettuato ordine su markeplace">Ragione sociale cliente</label>
                        <input type="text" class="form-control-sm my-1" id="rag_soc" title="Inserisci ragione sociale cliente che ha effettuato ordine su market place" placeholder="Ragione sociale cliente">
                    </div>
                </div>                
                <div class="form-row align-items-sm-end">                                                           
                    <div class="col-auto my-1">
                        <button type="button" id="serach-order" class="btn btn-primary btn-sm">Cerca</button>
                    </div>
                    <div class="col-auto my-1">
                        <button type="button" id="reset" class="btn btn-primary btn-sm">Reset</button>
                    </div>
                </div>
            </form>
        </div>             
    </div>
</div>
