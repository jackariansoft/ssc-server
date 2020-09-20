<div class="row">

    <div class="col-sm-5" style="margin-left:0px;padding: 0">

        <div>
            <div class="form-group col-sm-3 my-1" id="locationField">              
                <input id="autocomplete"  placeholder="Inserisci indirizzo"    type="text"/>
            </div>          
            <form>
                <div class="form-group col-sm-10 my-1" id="address">
                    <div class="row">
                        <div class="col-sm-7 my-1">
                            <label class="mr-sm-2 form-control-sm " for="indirizzo">Indirizzo</label>
                            <input class="form-control form-control-sm" id="indirizzo" disabled="true"/>
                        </div>
                        <div class="col-sm-3 my-1">
                            <label class="mr-sm-2 form-control-sm " for="civico">Civico</label>
                            <input class="form-control form-control-sm" id="civico" disabled="true"/>
                        </div>
                        <div class="col-sm-7 my-1">
                            <label class="form-control-sm " for="citta">Citt&agrave;</label>
                            <input class="form-control form-control-sm" id="citta" disabled="true"/>
                        </div>
                        <div class="col-sm-3 my-1">
                            <label class="form-control-sm " for="provincia">Provincia</label>
                            <input class="form-control form-control-sm" id="provincia" disabled="true"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-5 my-1">
                            <label class="form-control-sm " for="regione">Regione</label>
                            <input class="form-control form-control-sm" id="regione" disabled="true"/>
                        </div>
                        <div class="col-sm-3 my-1">
                            <label class="form-control-sm " for="cap">CAP</label>
                            <input class="form-control form-control-sm" id="cap" disabled="true"/>
                        </div>
                        <div class="col-sm-2 my-1">
                            <label class="form-control-sm" for="country">Nazione</label>
                            <input class="form-control form-control-sm" id="country" disabled="true"/>
                        </div> 
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="col-sm-5" style="margin-left:0px;padding: 0">
        <div class="row" id="date-filter">
            <form>
                <div class="form-row col-12">
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
                        <label class="mr-sm-2 form-control-sm" for="start">Da</label>
                        <input type="text" id="start" name="start" class="form-control-sm" placeholder="From date"/>
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="end">A</label>
                        <input type="text" id="end" name="end" class="form-control-sm" placeholder="To date"/>    
                    </div>                         
                </div>
            </form>
        </div>
        <div class="row" id="order-filter">
            <form>
                <div class="form-row col-12">

                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-1 form-control-sm" for="status">Stato Ordini</label>
                        <select class="form-control-sm" id="status">
                            <option value="all">All</option>
                            <option value="Pending">Pending</option>
                            <option value="PendingAvailability">Attesa arrivo merce</option>
                            <option value="Unshipped" selected="true">Non spedito</option>
                            <option value="PartiallyShipped">Parzialmente Spedito</option>
                            <option value="Shipped">Spedito</option>
                            <option value="Shipping">In spedizione</option>
                            <option value="Canceled">Cancellato</option>
                            <option value="Unfulfillable">Non spedibile</option>                      
                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="orderlist">Numero Ordine</label>
                        <input type="text" class="form-control-sm" id="orderlist" title="Insesci numero ordini separati da virgola" placeholder="Lista numero ordine">
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="channel">Markeplace</label>
                         <input id="channel"  class="form-control-sm" type="text">
                        <!--<select class="form-control-sm" id="channel" style="margin-left: 5px">
                            <option value="all" code="">Tutti</option>
                            <option value="1" code="MWS">Amazon</option>
                            <option value="6" code="MAGE">Magento</option>
                            <option value="4" code="EBAY">Ebay</option>
                            <option value="8" code="WS">Webshopping</option>
                            <option value="7" code="EP">EPrice</option>
                            <option value="9" code="IBS">IBS</option>
                        </select>  -->
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="channel">Escludi Markeplace</label>
                        <input id="channelExcl"  class="form-control-sm" type="text">
                         <!--
                        <select class="form-control-sm" id="channelExcl" style="margin-left: 5px">
                            <option value="none" code="">Nessuno</option>
                            <option value="1" code="MWS">Amazon</option>
                            <option value="6" code="MAGE">Magento</option>
                            <option value="4" code="EBAY">Ebay</option>
                            <option value="8" code="WS">Webshopping</option>
                            <option value="7" code="EP">EPrice</option>
                            <option value="9" code="IBS">IBS</option>
                        </select>  -->
                    </div>
                    <div class="col-sm-3 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="logistic-status">Stato Spedizione</label>
                        <select class="form-control-sm" id="logistic-status" style="margin-left: 5px">
                            <option value="none">Nessuno</option>
                            <option value="-1" >Attesa Processamento</option>
                            <option value="1" >Confermata</option>
                            <option value="2" >Annullata</option>
                            <option value="3" >Rimandata</option>
                            <option value="4" >Spedita</option>
                        </select> 
                    </div>
                    <div class="col-sm-3 my-1">                     
                        <label class="form-control-sm mr-sm-2" for="cod_rep" title="Codice Reparto">Cod Rep.</label>
                        <input type="text" class="form-control-sm" id="cod_rep" title="Codice Reparto" placeholder="Codice Reparto">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="form-control-sm mr-sm-2" for="tip_doc" title="Tipo Documento">Tip. Doc.</label>
                        <input type="text" class="form-control-sm" id="tip_doc" title="Tipo Documento" placeholder="Tipo Documento">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="form-control-sm mr-sm-2" for="num_doc" title="Numero Documento">Num. Doc.</label>
                        <input type="text" class="form-control-sm" id="num_doc" title="Inserisci lista numero documenti serparati da virgola" placeholder="Lista Numero Documento">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="form-control-sm mr-sm-2" for="skus" title="Numero Documento">Skus</label>
                        <input type="text" class="form-control-sm" id="skus" title="Inserisci lista sku serparati da virgola" placeholder="Lista Sku">
                    </div>
                </div>
                <div class="form-row col-12">
                    <div class="col-auto my-1">                       
                        <label class="mr-sm-2 form-control-sm" for="deferred">Deferred</label>
                        <input type="checkbox" id="deferred" name="deferred">                       
                    </div>
                    <div class="col-auto my-1">
                        <label class="mr-sm-2 form-control-sm " for="skiperror">Prosegui in caso di errore</label>                    
                        <input type="checkbox" name="skiperror" id="skiperror"  title="Prosegui in caso di errore">                      
                    </div>
                    <div class="col-auto my-1">
                        <label class="mr-sm-2 form-control-sm " for="skipalert">Disabilita Notifiche</label>                    
                        <input type="checkbox" name="skipalert" id="skipalert"  title="Skip Row Errors">                      
                    </div>
                    <div class="col-auto my-1">
                        <button type="button" id="import-order" class="btn btn-primary btn-sm">Importazione Info Ordini</button>
                    </div>
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

    <div class="col-sm-7" style="margin-right: 0px;padding: 0" style="display: none">               
        <form>
            <div class="form-row align-items-sm-end" style="display: none">
                <div class="form-group col-sm-3 my-1">
                    <label class="mr-sm-2 form-control-sm" for="departureDepot">Deposit Reference (VABLNP)</label>
                    <input type="text" name="departureDepot" id="departureDepot" class="form-control-sm" id="order" value="67"/>
                </div>
                <div class="form-group col-sm-3 my-1" >
                    <label class="mr-sm-2 form-control-sm" for="customerCode">Customer Reference (VABCCM)</label>
                    <input type="text" name="customerCode" id="customerCode" class="form-control-sm" id="order" value="675560"/>
                </div>  
            </div>
            <div class="form-row align-items-sm-end" style="display: none">
                <div class="form-group col-sm-3 my-1">
                    <label class="mr-sm-2 form-control-sm" for="VABNRS">Serial Number (VABNRS)</label>
                    <input type="text" name="VABNRS" id="VABNRS" class="form-control-sm" id="VABNRS" value="22"/>
                </div>                
                <div class="form-group col-sm-3 my-1">
                    <label class="mr-sm-2 form-control-sm" for="type">Order Reference Type (VABRNM)</label>
                    <select  name="type" class="form-control-sm" id="type">
                        <option value="A">Alfanumeric</option>
                        <option value="N" selected>Numeric</option>
                    </select>
                </div>
                <div class="form-group col-sm-3 my-1">                    
                    <select name="outType" class="form-control-sm" id="outType">                        
                        <option value="B">ZPL in BASE64 (B)</option>
                        <option value="P" selected>PDF in BASE64 (P)</option>
                        <option value="T">TIF in BASE64 (T)</option>
                    </select>
                </div>

            </div>           
        </form>
    </div>
</div>
