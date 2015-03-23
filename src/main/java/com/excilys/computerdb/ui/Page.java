package com.excilys.computerdb.ui;

import java.util.List;



// TODO: Auto-generated Javadoc
/**
 * The Class Page.
 */
public class Page<Type> {
	
	private static int PAGE_SIZE = 10;
	private static int FOOTER_PAGE_SIZE = 5;

	List<Type> listT;
	
	private int listSize;
	private int infBorder;
	private int supBorder;
	private int pageNumbers;
	private int page;
	private int footerFirstNbr;
	
	public Page (List<Type> listT, int page, int startPageFooter) {
		this.listT = listT;
		this.page = page;
		this.footerFirstNbr = startPageFooter;
		this.listSize = listT.size();		
		if (this.listSize > PAGE_SIZE) {
			this.pageNumbers = this.listSize / PAGE_SIZE;
			if ((this.listSize % PAGE_SIZE) != 0) {
				pageNumbers++;
			}
			supBorder = this.page * PAGE_SIZE;
			infBorder = supBorder - PAGE_SIZE;			
		} else {
			this.pageNumbers = 1;
			infBorder = 0;
			supBorder = listSize;
		}
		
	}
	
	public List<Type> getPageElements () {
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
	
	public int getNextPage() {
		return (footerFirstNbr + FOOTER_PAGE_SIZE - 1) % pageNumbers;
	}
	
}
