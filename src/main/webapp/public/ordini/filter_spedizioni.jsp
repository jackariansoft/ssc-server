<div class="row">           
    <div class="col-sm-5" style="margin-left:0px;padding: 0">
        <div class="row" id="date-filter" >
            <form>
                <div class="form-row align-items-sm-end">
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="daterange">Intervallo Temporale</label>                
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
                        <label class="mr-sm-2 form-control-sm" for="date-start">From</label>
                        <input type="text" id="start" name="date_start" class="form-control-sm" placeholder="From date"/>
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="date-end">To</label>
                        <input type="text" id="end" name="date_end" class="form-control-sm" placeholder="To date"/>    
                    </div>                         
                </div>
            </form>
        </div>
        <div class="row" id="order-filter">
            <form>
                <div class="form-row align-items-sm-end">

                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="logistic-status">Stato Spedizione</label>
                        <select class="form-control-sm" id="logistic-status">

                            <option value="all">All</option>
                            <option value="0" selected="true">Attesa Processamento</option>                            
                            <option value="2" >Sospeso</option>
                            <option value="1">Processato</option>
                            <option value="-1">Annullato</option>
                            <option value="4">Spedito</option>


                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="orderlist">Lista Ordini</label>
                        <input type="text" class="form-control-sm" id="orderidlist" title="Inserisci una lista di ordini separati da virgola: Es:404-0715888-1949138,402-8815647-7099562,406-5909470-2033920" placeholder="List ordini su marketplace">
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="channel">Markeplace</label>
                        <select class="form-control-sm" id="channel" style="margin-left: 5px">
                            <option value="all" code="">All</option>
                            <option value="1" code="MWS">Amazon</option>
                            <option value="6" code="MAGE">Magento</option>
                            <option value="4" code="EBAY">Ebay</option>
                            <option value="8" code="WS">Webshopping</option>
                            <option value="7" code="EP">EPrice</option>
                            <option value="9" code="IBS">IBS</option>
                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="channel">Escludi Markeplace</label>
                        <select class="form-control-sm" id="channelExcl" style="margin-left: 5px">
                            <option value="all" code="">All</option>
                            <option value="1" code="MWS">Amazon</option>
                            <option value="6" code="MAGE">Magento</option>
                            <option value="4" code="EBAY">Ebay</option>
                            <option value="8" code="WS">Webshopping</option>
                            <option value="7" code="EP">EPrice</option>
                            <option value="9" code="IBS">IBS</option>
                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="status">Stato Ordine</label>
                        <select class="form-control-sm" id="status">
                            <option value="all" selected="true">All</option>
                            <option value="Pending">Pending</option>
                            <option value="PendingAvailability">Pending Availability</option>
                            <option value="Unshipped">Unshipped</option>
                            <option value="PartiallyShipped">Partially Shipped</option>
                            <option value="Shipped">Shipped</option>
                            <option value="Canceled">Canceled</option>
                            <option value="Unfulfillable">Unfulfillable</option>                      
                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="cod_rep" title="Codice Reparto">Cod Rep.</label>
                        <input type="text" class="form-control-sm" id="cod_rep" title="Codice Reparto" placeholder="Codice Reparto">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="tip_doc" title="Tipo Documento">Tip. Doc.</label>
                        <input type="text" class="form-control-sm" id="tip_doc" title="Tipo Documento" placeholder="Tipo Documento">
                    </div>
                    <div class="col-sm-2 my-1">
                        <label class="form-control-sm" for="num_doc" title="Numero Documento">Num. Doc.</label>
                        <input type="text" class="form-control-sm" id="num_doc" title="Inserisci lista numero documenti serparati da virgola" placeholder="Lista Numero Documento">
                    </div>
                    <div class="col-sm-2 my-1">
                        <label class="mr-sm-2 form-control-sm" for="task" title="Task ID">Task ID</label>
                        <input type="text" class="easyui-numberbox" id="task"  data-options="min:0,precision:0">

                    </div>                    
                </div>
            </form>
            <div class="col-sm-2">
                <button type="button" id="serach-order" class="btn btn-primary btn-sm mr-sm-2 form-control-sm">Cerca</button>
            </div>
            <div class="col-sm-2">
                <button type="button" id="reset" class="btn btn-primary btn-sm mr-sm-2 form-control-sm">Reset</button>
            </div>
        </div>
    </div>             
</div>