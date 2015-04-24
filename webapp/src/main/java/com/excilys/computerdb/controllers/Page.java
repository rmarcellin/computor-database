package com.excilys.computerdb.controllers;

import java.util.List;


/**
 * The Class Page.
 */
public class Page<Type> {
	
	private static int FOOTER_PAGE_SIZE = 5;

	List<Type> listT;
	
	private int listSize;
	private int infBorder;
	private int supBorder;
	private int pageNumbers;
	private int page;
	private int footerFirstNbr;
	//private OrderCriteria orderCriteria;
	
	public Page (List<Type> listT, int page, int startPageFooter, int pageSize) {
		this.listT = listT;
		this.page = page;
		this.footerFirstNbr = startPageFooter;
		this.listSize = listT.size();		
		if (this.listSize > pageSize) {
			this.pageNumbers = this.listSize / pageSize;
			if ((this.listSize % pageSize) != 0) {
				pageNumbers++;
			}
			supBorder = this.page * pageSize;
			infBorder = supBorder - pageSize;			
		} else {
			this.pageNumbers = 1;
			infBorder = 0;
			supBorder = listSize;
		}
		
		//orderCriteria = OrderCriteria.NAME_DEFAULT;
		
	}
	
	public List<Type> getPageElements () {
		if (supBorder > listSize) {
			supBorder = listSize;
		}
		return listT.subList(infBorder, supBorder);
	}
	
	public int getPageNumbers () {
		return pageNumbers;
	}
	
	public int getSize () {
		return listSize;
	}
	
	public int getPage () {
		return page;
	}
	
	public boolean isLastPage () {
		return page == pageNumbers;
	}
	
	public boolean isFirstPage () {
		return page == 1;
	}
	
	public int getPageStart () {
		return footerFirstNbr;
	}
	
	public int getPreviousPage () {
		int tmp = footerFirstNbr - FOOTER_PAGE_SIZE;
		if (tmp <= 0) {
			return 1;
		}
		return tmp;
	}
	
	public int getNextPage() {
		int tmp = (footerFirstNbr + FOOTER_PAGE_SIZE - 1) % pageNumbers;
		if( tmp == 0) {
			return 1;
		}
		return tmp;
	}
	
}
