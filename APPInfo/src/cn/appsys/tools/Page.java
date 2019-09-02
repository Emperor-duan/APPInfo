package cn.appsys.tools;

import java.util.*;
import cn.appsys.pojo.*;

public class Page {
	private int totalCount;		
	private int currentPageNo = 1;	
	private int pageSize = 5;		
	private int totalPageCount; 
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		if(totalCount>0){
			this.totalCount = totalCount;
			this.totalPageCount = (totalCount%this.pageSize==0)?totalCount/this.pageSize:totalCount/this.pageSize+1;
		}
	}
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if(this.pageSize>0){
			this.pageSize = pageSize;
		}
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
}
