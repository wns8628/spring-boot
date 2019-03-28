package com.douzone.mysite.vo;

public class PaginationVo {

	private int totalboardcount;
	private int totalpage;
	private int startboard;
	private int pageNo; //url 페이지번호
	private int startPage;
	private int endPage;
	private int manyboard; //한페이지당 보여줄 게시글 
	private int limitcount; //게시글이 많아도 몇개의 페이지번호를 보여줄것인지
	private String kwd; //검색했을때 페이징위해
		
	public int getLimitcount() {
		return limitcount;
	}
	public void setLimitcount(int limitcount) {
		this.limitcount = limitcount;
	}

	public String getKwd() {
		return kwd;
	}
	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getStartboard() {
		return startboard;
	}
	public int getManyboard() {
		return manyboard;
	}
	public void setManyboard(int manyboard) {
		this.manyboard = manyboard;
	}
	public void setStartboard(int startboard) {
		this.startboard = startboard;
	}
	public int getTotalboardcount() {
		return totalboardcount;
	}
	public void setTotalboardcount(int totalboardcount) {
		this.totalboardcount = totalboardcount;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	
	
}
