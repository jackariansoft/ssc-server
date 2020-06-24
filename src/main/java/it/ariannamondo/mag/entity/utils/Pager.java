/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.entity.utils;

/**
 *
 * @author jackarian
 */
public class Pager {

    int rows = 0;
    long pageSize = 100;
    int pageNumber = 0;
    int currentPage = 1;
    private String oreder="ASC";

    public Pager() {
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }
    /**
     * Imipostazione pagine totali.
     * @param pageNumber 
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }
    /**
     * Impostazione dimensione della pagina.
     * @param pageSize 
     */
    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * Impostazione numero di pagine.
     * Il metodo deve essere invocato solo dopo che
     * vengono impostate le pagine totali e la dimensione delle pagine.     
     */
    

    public long getCurrentPageStart() {
        return pageSize * (currentPage - 1);
    }
    /**
     * Decremente il valore dell'indice di pagina corrente.
     */
    public void decrementPage() {
        this.currentPage--;
    }
    /**
     * Incrementa di una pagina  valore dell'indice di pagina corrente.
     */
    public void incrementpage() {
        this.currentPage++;
    }
    /**
     * Va alla prima pagina.
     */
    public void setToFirstPage() {
        this.currentPage = 1;
    }
    /**
     * Va all'ultima pagina.
     */
    
    /**
     * Impostazione iniziale.
     */
    public void reset() {
        rows = 0;
        pageSize = 20;
        pageNumber = 0;
        currentPage = 1;

    }

    public String getSort() {
        return oreder;
    }

    public void setSort(String oreder) {
        this.oreder = oreder;
    }
     public void setToLastPage() {
        this.currentPage = getPageNumber();
    }
    
    public long getEnd(){
        return (currentPage*pageSize);
    }
    
    public void setPageNumber() {
        int tmppageNumber  = Math.round(rows/pageSize);
        if (rows % pageSize != 0 && rows > pageSize) {
            this.pageNumber = tmppageNumber +1;
        }else{
           this.pageNumber = tmppageNumber; 
        }
        pageNumber = Math.max(pageNumber, 1);
        pageNumber = Math.min(pageNumber, rows);
    }
    
   public long getStart(){
        
        long start = currentPage-1;
        //int max = Math.max(cpgae, 1);
//        if(max!=1){
//            start= pageSize*max;
//        }
        start= (start*pageSize)+1 ;
        return start;
    }
    public long getOffset(){
        return  (getCurrentPage() - 1)*pageSize;
    }
   
}
