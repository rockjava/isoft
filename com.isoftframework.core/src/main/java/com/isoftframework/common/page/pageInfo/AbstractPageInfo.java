package com.isoftframework.common.page.pageInfo;

/*
 * Created on 2005-6-20
 */

import java.util.List;

import com.isoftframework.dao.IDao;
  
abstract public class AbstractPageInfo  {

	/**
	 * 翻页信息,总页数
	 */
	protected int totalPage = 0;
	
	/**
	 * 总条数
	 */
	protected long totalSize;
	/**
	 *  翻页信息,当前页面
	 */
	protected int pageNum = 1;
	/**
	 * 每页显示条数
	 */
	protected int limit;
	/**
	 * 当前页的第一条
	 */
	protected long start;
	/**
	 * 当前页的最后一条
	 */
	protected long end;
	
	protected String querySql=null;
	protected String countSql=null;
	
	
	 
	// 翻页信息,当前页面URL
	private String curURL = "";
 
	
	public AbstractPageInfo() {
		this(10);
		 
	}
	
	public AbstractPageInfo(int limit ) {
		this.limit=limit;
	}
	public AbstractPageInfo(AbstractPageInfo pi) {
		this.pageNum = pi.getPageNum();
		this.limit=pi.getLimit();
		this.totalPage = pi.getTotalPage();
		 
	}

	/**
	 * @return Returns the pageNum.
	 */
	public int getPageNum() {
		if(this.totalSize==0){
			pageNum=0;
		}else{ 
			if(this.pageNum<1){
				this.pageNum=1;
			}else if (this.pageNum > this.totalPage){
				this.pageNum = this.totalPage;
			}
				
		}
		
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            The pageNum to set.
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	 

	/**
	 * @return Returns the totalPage.
	 */
	public int getTotalPage() {
		if(this.totalSize==0){
			totalPage=0;
		}
		return totalPage;
	}

	/**
	 * @param totalPage
	 *            The totalPage to set.
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	

	 

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public long getStart() {
		if(totalSize==0){
			start=-1;
		}else{
			start=(this.getPageNum()-1)*this.getLimit();
		}
		
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		end=this.getPageNum()*this.getLimit()-1;
		//or end=start+this.getLimit()-1;
		if(end>=this.getTotalSize()){
			end= (this.getTotalSize()-1);
		}
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	 

	public String getCountSql() {
		if(countSql==null || countSql.trim().equals(""))
			countSql=this.generateCountSql(this.querySql);
		return countSql;
	}

	public void setCountSql(String countSql) {
		this.countSql = countSql;
	}

	public String getQuerySql() {
		
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getCurURL() {
		return curURL;
	}
	 
	public void setCurURL(String curURL) {
		this.curURL = curURL;
	}
	public int getLastPage(){
		return this.getTotalPage();
	}
	public boolean isLastPage(){
		return this.getPageNum()>=this.getTotalPage();
	}
	
	public boolean isFirstPage(){
		return this.getPageNum()<=1;
	}
	private int computeTotalPage(){
		totalPage=  (int) (((totalSize + limit) - 1) / limit);
		return totalPage;
	}
	
	
	public List compute(IDao dao) throws Exception{
		return compute(  dao,null);
	}
	public List compute(IDao dao,Class clas) throws Exception{
		this.setCountSql(generateCountSql(this.querySql));
		this.totalSize = computeTotalSize(this.getCountSql());
		this.totalPage = computeTotalPage();
		if(totalSize==0)
			return null;
		
		return this.queryWithLimit(querySql, this.getStart(), this.getLimit(),  clas);
		 
	}
	public abstract long computeTotalSize(final String countSql) throws Exception;
	public abstract   String generateCountSql(final String querySql);
	public abstract List queryWithLimit(final String querySql,final long start,final int limit,final Class clas) throws Exception;
	
}