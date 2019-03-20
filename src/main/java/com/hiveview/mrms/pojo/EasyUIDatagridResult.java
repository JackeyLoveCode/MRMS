package com.hiveview.mrms.pojo;

import java.util.List;

public class EasyUIDatagridResult {
	private long total;
	private List<?> rows;
	private Integer page;
	private Integer totalPages ;
	private Integer perPageRows = 3;
	private Integer status;
	private String msg;
	public EasyUIDatagridResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EasyUIDatagridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getPerPageRows() {
		return perPageRows;
	}

	public void setPerPageRows(Integer perPageRows) {
		this.perPageRows = perPageRows;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getTotalPages() {
		return (int) (total % perPageRows == 0 ? total / perPageRows:total / perPageRows +1) ;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	@Override
	public String toString() {
		return "EasyUIDatagridResult [total=" + total + ", rows=" + rows + "]";
	}
	
}
