/**
 * Project Name:MRMS
 * File Name:Condition.java
 * Package Name:com.hiveview.mrms.pojo
 * Date:2018年11月18日上午11:01:05
 * Copyright (c) 2018, 1570964489@qq.com All Rights Reserved.
 */

package com.hiveview.mrms.pojo;

/**
 * ClassName:Condition <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2018年11月18日 上午11:01:05 <br/>  
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class Condition {
    private String type;
    private String area;
    private String year;
    //分页
    private Integer page = 1;//当前页
    private Integer rows = 3;//每页记录数
    private Integer total;//记录总条数
    private Integer totalPages;//总页数

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        int totalPages = this.total % this.rows == 0 ? this.total / this.rows : (this.total / this.rows) + 1;
        return totalPages;
    }

    public Integer getPageIndex() {
        return (this.page - 1) * rows;
    }
}
  
