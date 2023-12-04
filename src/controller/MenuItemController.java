package controller;

import java.util.ArrayList;

import model.MenuItem;

public class MenuItemController {
	
	public static ArrayList<MenuItem> mi = MenuItem.getAllMenuItems();
	
	public static String createMenuItemController(String menuitemname, String menuitemdesc, double menuitemprice) {
		
		if(menuitemname.isBlank() || menuitemdesc.isBlank() || menuitemprice <= 0){
			return "Error: Field cannot be empty or price cannot be <= 0.";
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
	
	public static String updateMenuItemController(int menuitemsid, String menuitemname, String menuitemdesc, double menuitemprice) {
		
		if(menuitemname.isBlank() || menuitemdesc.isBlank() || menuitemprice <= 0){
			return "Error: Field cannot be empty or price cannot be <= 0.";
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
	
	public static int isMenuItemUnique(String menuitemname) {
		for (MenuItem menuItem2 : mi) {
			if(menuitemname == menuItem2.getMenuItemName()) {
				return 1;
			}
		}
		return 0;
		
	}
	
	public static void deleteMenuItemController(int menuitemid) {
		MenuItem.deleteMenuItem(menuitemid);
	}
	
	public static ArrayList<MenuItem> getMenuItemByIdController(int menuitemid) {
		return MenuItem.getMenuItemById(menuitemid);
	}
	
	public static ArrayList<MenuItem> getAllMenuItemController() {
		return MenuItem.getAllMenuItems();
		
	}
	
	public static int morethanTen(String menuitemdesc) {
		if(menuitemdesc.length() < 10 ) {
				return 1;
		}
		return 0;
	}

	
	public static int priceValidation(double menuitemprice) {
		if(menuitemprice < 2.5) {
			return 1;
	}
	return 0;
	}
}
