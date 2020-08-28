/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.rest.common;

import java.io.Serializable;

/**
 *
 * @author jackarian
 * @param <T>
 */
public class PaginationInfo<T> implements Serializable {

    private Long currentPage;
    private Long pageSize;
    private Long lastPage;
    private Long totalPage;
    private Long totalRows;
    private Long from;
    private Long to;
    private T data;

    public void init() {
        currentPage = 1L;
        lastPage = 1L;
        totalPage = 0L;
        totalRows = 0L;
        from = 0L;
        to = 0L;
        data = null;
                
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
    
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getLastPage() {
        return lastPage;
    }

    public void setLastPage(Long lastPage) {
        this.lastPage = lastPage;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Long totalRows) {
        this.totalRows = totalRows;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }
    
     public long getEnd(){
        return (currentPage*pageSize);
    }
    
    public void setPageNumber() {
        int tmppageNumber  = Math.round(totalRows/pageSize);
        if (totalRows % pageSize != 0 && totalRows > pageSize) {
            this.totalPage = Long.valueOf(tmppageNumber +1);
        }else{
           this.totalPage = Long.valueOf(tmppageNumber); 
        }
        totalPage = Math.max(totalPage, 1);
        totalPage = Math.min(totalPage, totalRows);
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
