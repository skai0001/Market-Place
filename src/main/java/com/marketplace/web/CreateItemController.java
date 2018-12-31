package com.marketplace.web;

import com.marketplace.ejb.ItemsFacade;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named("createItemController")
@SessionScoped
public class CreateItemController implements Serializable {
	
	private static final String SELECT_CATEGORIES[] = {
		"General Laptop",
		"Gaming Laptop",
		"Business Laptop", 
		"Apple Mac Book", 
		"Microsoft Surface Pro",
		"Tablet And/Or Notebook",
		"Other"
	};
	
	private static final int MAX_IMAGES = 8;

	/*** CHECKLIST: 1 ***/
	// input fields variables
	private String category;
	private String title;
	private String description;
	// output fields variables (errors)
	private String titleError;
	private String descriptionError;
	
	/*** CHECKLIST: 2 ***/
	// input fields variables
	private String images[] = new String[MAX_IMAGES];
	private int mainImageId;
	private String videoURL;
	// output fields variables (errors)
	private String imageError;
	private String videoError;
	
	@EJB
	private ItemsFacade itemsFacade;
	
	public CreateItemController() {
	}
	
	public String[] getCategories() { 
		return SELECT_CATEGORIES; 
	}
	
	/*********************
		INPUT FIELDS GETTER/SETTER
	*********************/
	public String getCategory() { 
		return category; 
	}
	
	public void setCategory(String category) { 
		this.category = category; 
	}
	
	public String getTitle() { 
		return title; 
	}
	
	public void setTitle(String title) { 
		this.title = title; 
	}
	
	public String getDescription() { 
		return description; 
	}
	
	public void setDescription(String description) { 
		this.description = description; 
	}
	
	/*********************
		OUTPUT FIELDS GETTER/SETTER
		-> Error messages
	*********************/
	public String getTitleError() { 
		return titleError; 
	}
	
	public void setTitleError(String titleError) { 
		this.titleError = titleError; 
	}
	
	public String getDescriptionError() { 
		return descriptionError; 
	}
	
	public void setDescriptionError(String descriptionError) { 
		this.descriptionError = descriptionError; 
	}
}
