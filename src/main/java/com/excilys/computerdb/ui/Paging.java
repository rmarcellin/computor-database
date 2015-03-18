package main.java.com.excilys.computerdb.ui;

/*import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Scanner;

import main.java.com.excilys.computerdb.beans.Computer;*/



// TODO: Auto-generated Javadoc
/**
 * The Class Paging.
 */
public class Paging {
	
/*	*//** The list computer. *//*
	private List<Computer> listComputer;
	
	*//** The size. *//*
	private int size;
	
	*//** The finished. *//*
	private boolean finished;
	
	*//** The page seize. *//*
	private static final int PAGE_SIZE = 5;
	private int pageNbrs;
	private volatile int crrPage;
	
	
	*//**
	 * Instantiates a new paging.
	 *
	 * @param listComputer the list computer
	 *//*
	public Paging(List<Computer> listComputer) {
		this.crrPage = 0;
		this.listComputer = listComputer;
		this.size = listComputer.size();
		if ((this.size % PAGE_SIZE) == 0) {
			this.pageNbrs = this.size / PAGE_SIZE;
		} else {
			this.pageNbrs = (this.size / PAGE_SIZE) + 1;
		}		
	}
	
	public List<Computer> getNextPage() {
		List<Computer> tmp = listComputer.subList(crrPage, (crrPage * PAGE_SIZE));
		crrPage++;
		return tmp;
	}*/
	
}
