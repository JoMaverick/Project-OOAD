package controller;

import java.util.ArrayList;

import model.MenuItem;

public class MenuItemController {
	
	ArrayList<MenuItem> mi = MenuItem.getAllMenuItems();
	
	public String createMenuItemController(String menuitemname, String menuitemdesc, double menuitemprice) {
		
		if(menuitemname.isBlank()){
			return "Error: MenuItem name cannot be empty.";
		}
		else if(isMenuItemUnique(menuitemname) == 1) {
			return "Error: MenuItem name already exist.";
		}
		else if(morethanTen(menuitemdesc) == 1) {
			return "Error: MenuItem description must be more than 10 characters.";
		}
		else if(priceValidation(menuitemprice) == 1) {
			return "Error: MenuItem price -	Must be a number that is greater than or equal to (>=) 2.5.";
		}
		else {
			MenuItem.createMenuItem(menuitemname, menuitemdesc, menuitemprice);
			return "Create MenuItem Sucess";
		}
	}
	
	public String updateMenuItemController(int menuitemsid, String menuitemname, String menuitemdesc, double menuitemprice) {
		
		if(menuitemname.isBlank()){
			return "Error: MenuItem name cannot be empty.";
		}
		else if(isMenuItemUnique(menuitemname) == 1) {
			return "Error: MenuItem name already exist.";
		}
		else if(morethanTen(menuitemdesc) == 1) {
			return "Error: MenuItem description must be more than 10 characters.";
		}
		else if(priceValidation(menuitemprice) == 1) {
			return "Error: MenuItem price -	Must be a number that is greater than or equal to (>=) 2.5.";
		}
		else {
			MenuItem.updateMenuItem(menuitemsid, menuitemname, menuitemdesc, menuitemprice);
			return "Update MenuItem Sucess";
		}
		
	}
	
	public int isMenuItemUnique(String menuitemname) {
		for (MenuItem menuItem2 : mi) {
			if(menuitemname == menuItem2.getMenuItemName()) {
				return 1;
			}
		}
		return 0;
		
	}
	
	public void deleteMenuItemController(int menuitemid) {
		MenuItem.deleteMenuItem(menuitemid);
	}
	
	public void getMenuItemById(int menuitemid) {
		MenuItem.getMenuItemById(menuitemid);
	}
	
	public void getAllMenuItem() {
		MenuItem.getAllMenuItems();
		
	}
	
	public int morethanTen(String menuitemdesc) {
		if(menuitemdesc.length() < 10 ) {
				return 1;
		}
		return 0;
	}

	
	public int priceValidation(double menuitemprice) {
		if(menuitemprice < 2.5) {
			return 1;
	}
	return 0;
	}
}
