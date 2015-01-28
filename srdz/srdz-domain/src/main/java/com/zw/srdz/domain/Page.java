package com.zw.srdz.domain;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 分页查询
 * @author cdzhangwei3
 *
 */
@SuppressWarnings("unused")
public class Page {

	private final static int PAGEBAR_MAX_SIZE = 5;//最多显示多少个分页按钮
	private int previousPage;//前一页
	private int nextPage;//下一页
	private int allCount;//总条数
	private int pageSize;//每页显示条数
	private int currentPage;//当前页
	private int allPage;//总页数
	private int start;//开始条目
	private int end;//结束条目
	private List<Integer> pagebars = Lists.newArrayList();//pagebar中应用该显示的页数
	@SuppressWarnings("rawtypes")
	private List items;
	
	public int getPreviousPage() {
		if(currentPage <=1){
			return 1;
		}
		return currentPage-1;
	}
	
	public int getNextPage() {
		if(currentPage >= getAllPage()){
			return allPage;
		}
		return currentPage+1;
	}
	
	public Page(int currentPage){
		this.currentPage = currentPage;
		this.pageSize = 10;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getAllPage() {
		if(allCount%pageSize ==0){
			return allCount/pageSize;
		}
		return (allCount+pageSize)/pageSize;
	}
	
	public int getStart() {
		return (currentPage-1)*pageSize;
	}
	public int getEnd() {
		return currentPage*10;
	}
	
	public int getAllCount() {
		return allCount;
	}
	
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	
	public List<Integer> getPagebars() {
		int allPage = getAllPage();
		if(allPage < PAGEBAR_MAX_SIZE){
			convertPagers(1, allPage);
		}else{
			int start = currentPage;
			int end = (start +PAGEBAR_MAX_SIZE-1)>allPage ? allPage :(start +PAGEBAR_MAX_SIZE-1);
			start = (end+1-start)<PAGEBAR_MAX_SIZE ? (start-(PAGEBAR_MAX_SIZE-(end+1-start))): start;
			convertPagers(start, end);
		}
//		else if(currentPage <= PAGEBAR_MAX_SIZE){
//			convertPagers(1, PAGEBAR_MAX_SIZE);
//		}else{
//			int start = (currentPage-PAGEBAR_MAX_SIZE)+1;//每次向前移动一位
//			convertPagers(start, start+PAGEBAR_MAX_SIZE-1);
//		}
		
		return pagebars;
	}
	
	private void convertPagers(int start ,int end){
		for(int i= start;i<=end;i++){
			pagebars.add(i);
		}
	}
	
	public <E> void setItems(List<E> items) {
		this.items = items;
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> getItems() {
		return items;
	}
}
