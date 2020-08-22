/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mude.srl.ssc.entity.utils;


import java.math.BigDecimal;
import java.util.Date;
import mude.srl.ssc.entity.Users;

/**
 *
 * @author jackarian
 */
public class Request {

    private Date start;
    private Date end;
    private Date start_fatture;
    private Date end_fatture;
    private String doc_type;
    private String channel;
    private String orderList;
    private String orderStatus;
    private Boolean deferred;
    private Boolean date_fattura;
    private Boolean date_order;
    private String logisticStatus;
    private Long batchId;
    private Long spedizione;
    private String channelExcl;
    private String skus;
    private Long fatturaSpezione;
    private String indirizzo;
    private String civico;
    private String citta;
    private String provincia;
    private String provinciaEstesa;
    private String cap;
    private String country;
    private String codRep;
    private String tipDoc;
    private String numDoc;
    private String cap_list;
    private BigDecimal delta_min;
    private BigDecimal delta_max;
    private BigDecimal delta_riga_min;
    private BigDecimal delta_riga_max;
    private String rag_soc;
    private String rif_mitt_num;
    private String rif_mitt_alfa;
    private String fatture;
    private String ordine_int;
    private boolean paginate  = true;
    private boolean fatturaVettore=false;
    private boolean fatturaDeferred=false;
   
    
    

    public boolean isFatturaDeferred() {
        return fatturaDeferred;
    }

    public void setFatturaDeferred(boolean fatturaDeferred) {
        this.fatturaDeferred = fatturaDeferred;
    }

    
    public boolean isFatturaVettore() {
        return fatturaVettore;
    }

    public void setFatturaVettore(boolean fatturaVettore) {
        this.fatturaVettore = fatturaVettore;
    }

   
    

    public boolean isPaginate() {
        return paginate;
    }

    public void setPaginate(boolean paginate) {
        this.paginate = paginate;
    }
    
    

    public String getOrdine_int() {
        return ordine_int;
    }

    public void setOrdine_int(String ordine_int) {
        this.ordine_int = ordine_int;
    }
    
    

    public Date getStart_fatture() {
        return start_fatture;
    }

    public void setStart_fatture(Date start_fatture) {
        this.start_fatture = start_fatture;
    }

    public Date getEnd_fatture() {
        return end_fatture;
    }

    public void setEnd_fatture(Date end_fatture) {
        this.end_fatture = end_fatture;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public Boolean getDate_fattura() {
        return date_fattura;
    }

    public void setDate_fattura(Boolean date_fattura) {
        this.date_fattura = date_fattura;
    }

    public Boolean getDate_order() {
        return date_order;
    }

    public void setDate_order(Boolean date_order) {
        this.date_order = date_order;
    }
    
    
    

    public String getFatture() {
        return fatture;
    }

    public void setFatture(String fatture) {
        this.fatture = fatture;
    }
    
    

    public String getRag_soc() {
        return rag_soc;
    }

    public void setRag_soc(String rag_soc) {
        this.rag_soc = rag_soc;
    }

    public String getRif_mitt_num() {
        return rif_mitt_num;
    }

    public void setRif_mitt_num(String rif_mitt_num) {
        this.rif_mitt_num = rif_mitt_num;
    }

    public String getRif_mitt_alfa() {
        return rif_mitt_alfa;
    }

    public void setRif_mitt_alfa(String rif_mitt_alfa) {
        this.rif_mitt_alfa = rif_mitt_alfa;
    }

    
    public String getCap_list() {
        return cap_list;
    }

    public void setCap_list(String cap_list) {
        this.cap_list = cap_list;
    }

    public BigDecimal getDelta_min() {
        return delta_min;
    }

    public void setDelta_min(BigDecimal delta_min) {
        this.delta_min = delta_min;
    }

    public BigDecimal getDelta_max() {
        return delta_max;
    }

    public void setDelta_max(BigDecimal delta_max) {
        this.delta_max = delta_max;
    }

    public BigDecimal getDelta_riga_min() {
        return delta_riga_min;
    }

    public void setDelta_riga_min(BigDecimal delta_riga_min) {
        this.delta_riga_min = delta_riga_min;
    }

    public BigDecimal getDelta_riga_max() {
        return delta_riga_max;
    }

    public void setDelta_riga_max(BigDecimal delta_riga_max) {
        this.delta_riga_max = delta_riga_max;
    }
    
    

    public Long getFatturaSpezione() {
        return fatturaSpezione;
    }

    public void setFatturaSpezione(Long fatturaSpezione) {
        this.fatturaSpezione = fatturaSpezione;
    }

    
    public String getSkus() {
        return skus;
    }

    public void setSkus(String skus) {
        this.skus = skus;
    }

    
    public String getProvinciaEstesa() {
        return provinciaEstesa;
    }

    public void setProvinciaEstesa(String provinciaEstesa) {
        this.provinciaEstesa = provinciaEstesa;
    }

    
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCodRep() {
        return codRep;
    }

    public void setCodRep(String codRep) {
        this.codRep = codRep;
    }

    public String getTipDoc() {
        return tipDoc;
    }

    public void setTipDoc(String tipDoc) {
        this.tipDoc = tipDoc;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }
    
    
    

    public String getChannelExcl() {
        return channelExcl;
    }

    public void setChannelExcl(String channelExcl) {
        this.channelExcl = channelExcl;
    }
    
    
    

    private long pageSize;

    public Long getSpedizione() {
        return spedizione;
    }

    public void setSpedizione(Long spedizione) {
        this.spedizione = spedizione;
    }

    
    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    
    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    Long channelOrderid;

    public Long getChannelOrderid() {
        return channelOrderid;
    }

    public void setChannelOrderid(Long channelOrderid) {
        this.channelOrderid = channelOrderid;
    }

    private Users user;

    public Users getUser() {
        return user;
    }

    public Boolean getDeferred() {
        return deferred;
    }

    /**
     *
     * @param deferred
     */
    public void setDeferred(Boolean deferred) {
        this.deferred = deferred;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    Pager pager;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getLogisticStatus() {
        return logisticStatus;
    }

    public void setLogisticStatus(String logisticStatus) {
        this.logisticStatus = logisticStatus;
    }

}
