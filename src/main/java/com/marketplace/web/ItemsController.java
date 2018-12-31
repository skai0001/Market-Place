package com.marketplace.web;

import com.marketplace.ejb.ItemsFacade;
import com.marketplace.ejb.UsersFacade;
import com.marketplace.entity.Items;
import com.marketplace.entity.Users;
import com.marketplace.utils.PaginationHelper;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named("itemsController")
@SessionScoped
public class ItemsController implements Serializable {
	public static final String CATEGORIES[] = {
		"All Laptop",
		"General Laptop",
		"Gaming Laptop",
		"Business Laptop", 
		"Apple Mac Book", 
		"Microsoft Surface Pro",
		"Tablet And/Or Notebook",
		"Other"
	};

	private static final int PAGE_SIZE = 5; // how many items per page
	
	// pagination variables
	private PaginationHelper<Items> pagination;
	private List<Items> currentPageData;
	private List<Integer> currentPageNumbers;
	private Items selectedItem;
	
	// filter variables
	private int currentCategory;
	private int currentMinPrice;
	private int currentMaxPrice;
	
	@EJB private ItemsFacade itemsFacade;
	
	@Inject private UsersController usersController;
	
	public ItemsController() {
	}
	
	/*********************
		LOAD ITEMS LIST
		-> Loads all items according to the applied filters by user in "index.xhtml" page
	*********************/
	private void prepareContent(int category, int minPrice, int maxPrice) {
		int previousPage = 1;
		
		if (pagination != null) {
			previousPage = pagination.getCurrentPage();

			pagination.close();
		}
		
		pagination = new PaginationHelper(PAGE_SIZE, (List<Items>) itemsFacade.findWithFilters(category, minPrice, maxPrice));
		pagination.setCurrentPage(previousPage);
		
		currentPageData = pagination.pageObjects();
		currentPageNumbers = pagination.pageNumbers();
	}
	
	/*********************
		FILTER BAR METHODS
		-> These methods are used to create our filter bar on right side of "index.xhtml" page
	*********************/
	public String[] getCategories() { 
		return CATEGORIES; 
	}
	
	public int getCategoryCount(String category) {
		for (int i = 0; i < CATEGORIES.length; i++) {
			if (CATEGORIES[i].equalsIgnoreCase(category) == true) {
				return itemsFacade.countByCategory(i);
			}
		}
		
		return 0; 
	}
	
	public int getTotalCount() {
		return itemsFacade.count();
	}
	
	public String getCurrentCategory() { 
		return CATEGORIES[currentCategory]; 
	}
	
	public void setCurrentCategory(int categoryId) {
		currentCategory = categoryId;
	}
	
	public int getCurrentMinPrice() { 
		return currentMinPrice; 
	}
	
	public void setCurrentMinPrice(int min) { 
		currentMinPrice = min; 
	}
	
	public int getCurrentMaxPrice() { 
		return currentMaxPrice; 
	}
	
	public void setCurrentMaxPrice(int max) { 
		currentMaxPrice = max; 
	}
	
	// called when user changes the category filter
	public void onChangeCategory(String category) {
		for (int i = 0; i < CATEGORIES.length; i++) {
			if (category.equalsIgnoreCase(CATEGORIES[i]) == true) {
				currentCategory = i;
				break;
			}
		}
		
		prepareContent(currentCategory, currentMinPrice, currentMaxPrice);
	}
	
	// called when user applies price range filter
	public void onChangePriceRange(int min, int max) {
		prepareContent(currentCategory, min, max);
	}
	
	// called when user resets filters
	public void onResetFilters() {
		setCurrentCategory(0); 
		setCurrentMinPrice(0); 
		setCurrentMaxPrice(0);
		prepareContent(0, 0, 0);
	}
	
	/*********************
		PAGINATION METHODS
		-> These methods are used to create our pages(not the item cards) and buttons in "index.xhtml" page
	*********************/
	public PaginationHelper getPagination() {
		if (pagination == null) {
			setCurrentCategory(0); 
			setCurrentMinPrice(0); 
			setCurrentMaxPrice(0);
			prepareContent(0, 0, 0);
		}
		
		return pagination;
	}
	
	public List<Items> getCurrentPageData() {
		return currentPageData;
	}

	public List<Integer> getCurrentPageNumbers() {
		return currentPageNumbers;
	} 
	
	// called when user clicks on a page number
	public void onChangePage(int pageNumber) {
		if (pagination.setCurrentPage(pageNumber) == true) {
			currentPageData = pagination.pageObjects();
			currentPageNumbers = pagination.pageNumbers();
		}
	}

	// called when a user clicks "Next" button to goto next page
	public void onNextPage() {
		if (pagination.setCurrentPage(pagination.getCurrentPage() + 1) == true) {
			currentPageData = pagination.pageObjects();
			currentPageNumbers = pagination.pageNumbers();
		}
	}

	// called when a user clicks "Previous" button to goto previous page
	public void onPreviousPage() {
		if (pagination.setCurrentPage(pagination.getCurrentPage() - 1) == true) {
			currentPageData = pagination.pageObjects();
			currentPageNumbers = pagination.pageNumbers();
		}
	}
	
	/*********************
		ITEMS CARDS METHODS
		-> These methods are used to create our items cards in "index.xhtml" page
	*********************/
	public Items getSelectedItem() {
		if (selectedItem == null) {
			selectedItem = currentPageData.get(0);
		}
		
		return selectedItem;
	}
	
	public void setSelectedItem(Items selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String getItemOwnerName(Items item) {
		Users owner = usersController.getFacade().find(item.getPostOwnerId());
		return (owner == null) ? "unknown" : owner.getName();
	}
	
	public String getItemLocation(Items item) {
		return ((item.getAddress() != null) ? (item.getAddress() + ", ") : "") +  item.getCity() + ", " + item.getProvince() + ((item.getPostalCode() != null) ? (" " + item.getPostalCode()) : "") + ", " + item.getCountry();
	}

	public String getItemMainImage(Items item) {
		return "https://images-na.ssl-images-amazon.com/images/I/61aZwj3rIyL._SX425_.jpg";
	}
	
	public String getItemTimeElapsed(Items item) {
		long difference = System.currentTimeMillis() - item.getPostInitTimestamp().getTime();
		
		long value = TimeUnit.MILLISECONDS.toDays(difference);
		if (value != 0) {
			return value + " days ago";
		}
		
		value = TimeUnit.MILLISECONDS.toHours(difference);
		if (value != 0) {
			return value + " hours ago";
		}
		
		value = TimeUnit.MILLISECONDS.toMinutes(difference);
		if (value != 0) {
			return value + " minutes ago";
		}
		
		value = TimeUnit.MILLISECONDS.toSeconds(difference);
		return value + " seconds ago";
	}
	
	public String abrevateText(String text, int maxLength) {
		return (text.length() <= maxLength) ? (text) : (text.substring(0, maxLength - 3) + " ...");
	}

	// called when a user clicks on an item's save button
	public void onSaveItem(Items item) {
	}
	
	// called when a user clicks on an item's edit button (only owner of item can do this)
	public void onEditItem(Items item) {
	}
}
