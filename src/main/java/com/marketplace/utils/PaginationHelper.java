package com.marketplace.utils;

import java.util.ArrayList;
import java.util.List;


public class PaginationHelper<T> {
	
	private int pageSize;
	private int numPages;
	private int currentPage;
	private List<T> data;
	
	public PaginationHelper() {
		pageSize = 0;
		numPages = 0;
		currentPage = 0;
		data = null;
	}
	
	public PaginationHelper(int pageSize, List<T> data) {
		this.pageSize = pageSize;
		this.data = data;
		
		currentPage = 0;
		numPages = (data.size() / pageSize) + ((data.size() % pageSize != 0) ? 1 : 0);
	}
	
	public int getCurrentPage() {
		return currentPage + 1;
	}
	
	public boolean setCurrentPage(int page) {
		if (page >= 1 && page <= numPages) {
			currentPage = page - 1;
			return true;
		}
		
		return false;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public int getNumPages() {
		return numPages;
	}
	
	public int getNumItems() {
		return data.size();
	}
	
	public int getPageFirstItemIndex() {
		return (currentPage * pageSize) + 1;
	}
	
	public int getPageLastItemIndex() {		
		if (currentPage + 1 == numPages) {
			return data.size();
		}
		
		return (currentPage * pageSize) + pageSize;
	}
	
	public T getItemObject(int index) {
		return data.get(index);
	}

	public <T> List<T> pageObjects() {
		List<T> pageData = new ArrayList<>();
		
		for (int i = 0, j = 0; i < pageSize && (j = ((currentPage * pageSize) + i)) < data.size(); i++) {
			pageData.add((T) data.get(j));
		}
		
		return pageData;
	}
	
	public List<Integer> pageNumbers() {
		int current = currentPage + 1,
			last = numPages,
			delta = 2,
			left = current - delta,
			right = current + delta + 1;
		
		List<Integer> numbers = new ArrayList<>();
		List<Integer> numbersWithDots = new ArrayList<>();

		for (int i = 1; i <= last; i++) {
			if (i == 1 || i == last || i >= left && i < right) {
				numbers.add(i);
			}
		}
		
		int lastpage = 0;
		for (int i : numbers) {
			if (lastpage > 0) {
				if (i - lastpage == 2) {
					numbersWithDots.add(lastpage + 1);
				} else if (i - lastpage != 1) {
					numbersWithDots.add(-1);
				}
			}
			
			numbersWithDots.add(i);
			lastpage = i;
		}
		
		return numbersWithDots;
	}
	
	public void close() {
		data.clear();
		
		pageSize = 0;
		numPages = 0;
		currentPage = 0;
		data = null;
	}
}
