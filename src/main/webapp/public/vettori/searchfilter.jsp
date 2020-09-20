<div class="row">   
    <div class="col-sm-6" style="margin-left:0px;padding: 0">
        <div class="row" id="date-filter">
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
                        <label class="mr-sm-2 form-control-sm" for="date-start">Da</label>
                        <input type="text" id="start" name="date_start" class="form-control-sm" placeholder="From date"/>
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="date-end">A</label>
                        <input type="text" id="end" name="date_end" class="form-control-sm" placeholder="To date"/>    
                    </div>                         
                </div>
            </form>
        </div>
        <div class="row" id="fatture-filter">
            <form>
                <div class="form-row align-items-lg-end">                  
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="order_list">Ordine su Market Place</label>
                        <input type="text" class="form-control-sm" id="orderid_list" title="Insesci ordini separati da virgola" placeholder="Lista numero ordine">
                    </div>
                    <div class="col-sm-2 my-1">            
                        <label class="mr-sm-1 form-control-sm" for="channel">Markeplace</label>
                        <select class="form-control-sm" id="channel">
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
                        <label class="mr-sm-1 form-control-sm" for="channelExcl">Escludi Markeplace</label>
                        <select class="form-control-sm" id="channelExcl">
                            <option value="none" code="">All</option>
                            <option value="1" code="MWS">Amazon</option>
                            <option value="6" code="MAGE">Magento</option>
                            <option value="4" code="EBAY">Ebay</option>
                            <option value="8" code="WS">Webshopping</option>
                            <option value="7" code="EP">EPrice</option>
                             <option value="9" code="IBS">IBS</option>
                        </select> 
                    </div>                    
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="rif_mitt_num" title="Lista riferimento mittente numerico">Lista rif. mittente numerico</label>
                        <input type="text" class="form-control-sm my-1" id="rif_mitt_num" title="Inserisci lista riferimento mittente numerico separato da virgola" placeholder="Lista riferimento mittente numerico">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="rif_mitt_alfa" title="Lista riferimento mittente numerico">Lista rif. mittente alfanumerico</label>
                        <input type="text" class="form-control-sm" id="rif_mitt_alfa" title="Inserisci lista riferimento mittente numerico separata da virgola" placeholder="Lista riferimento mittente alfanumerico">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="num_fatt_vett" title="Numero Fattura Vettore">Lista num. fatt. vettore</label>
                        <input type="text" class="form-control-sm" id="num_fatt_vett" title="Inserisci lista numero fatture serparate da virgola" placeholder="Lista Numero Fatture Interne">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="num_fatt" title="Numero Fattura Interna">Lista num. fatt. interna</label>
                        <input type="text" class="form-control-sm" id="num_fatt" title="Inserisci lista numero fatture serparate da virgola" placeholder="Lista Numero Fatture Interne">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="cap_list" title="Numero Fattura">Lista CAP</label>
                        <input type="text" class="form-control-sm" id="cap_list" title="Inserisci lista CAP separati da virgola" placeholder="Lista Cap">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="rag_soc" title="Ragione sociale cliente che ha effettuato ordine su markeplace">Ragione sociale cliente</label>
                        <input type="text" class="form-control-sm" id="rag_soc" title="Inserisci ragione sociale cliente che ha effettuato ordine su market place" placeholder="Ragione sociale cliente">
                    </div>
                </div>
                <div class="form-row align-items-lg-end">                    
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="delta_min" title="Delta fattura da">Delta fattura da</label>
                        <input type="text" class="easyui-numberbox form-control-sm" data-options="precision:2,groupSeparator:' ',decimalSeparator:',',suffix:'&euro;',width:'100%'" id="delta_min" title="Inserisci valore delta minimo" placeholder="Inserisci valore delta minimo">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="delta_max" title="Delta fattura a">Delta fattura  a</label>
                        <input type="text" class="easyui-numberbox form-control-sm" data-options="precision:2,groupSeparator:' ',decimalSeparator:',',suffix:'&euro;',width:'100%'" id="delta_max" title="Inserisci valore delta massimo" placeholder="Inserisci valore delta massimo">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="delta_riga_min" title="Delta riga fattura da">Delta riga da</label>
                        <input type="text" class="easyui-numberbox form-control-sm" data-options="precision:2,groupSeparator:' ',decimalSeparator:',',suffix:'&euro;',width:'100%'" id="delta_riga_min" title="Inserisci valore delta minimo" placeholder="Inserisci valore delta minimo">
                    </div>
                    <div class="col-sm-3 my-1">
                        <label class="mr-sm-2 form-control-sm" for="delta_riga_max" title="Delta riga fattura a">Delta riga a</label>
                        <input type="text" class="easyui-numberbox form-control-sm" data-options="precision:2,groupSeparator:' ',decimalSeparator:',',suffix:'&euro;',width:'100%'" id="delta_riga_max" title="Inserisci valore delta massimo" placeholder="Inserisci valore delta massimo">
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
