package main;

import java.util.ArrayList;

import model.MenuItem;

public class test {

	public static void main(String[] args) {
//		MenuItem.createMenuItem("nama1", "desc1", 1);
//		MenuItem.updateMenuItem(1, "nama12", "desc12", 2);
//		MenuItem.deleteMenuItem(1);
//		ArrayList<MenuItem> mn = MenuItem.getAllMenuItems();
//		for (MenuItem menuItem : mn) {
//			System.out.println(menuItem.getMenuItemName());
//			System.out.println(menuItem.getMenuItemDescription());
//			System.out.println(menuItem.getMenuItemPrice());
//		}
		ArrayList<MenuItem> mn = MenuItem.getMenuItemById(2);
		for (MenuItem menuItem : mn) {
			System.out.println(menuItem.getMenuItemName());
			System.out.println(menuItem.getMenuItemDescription());
			System.out.println(menuItem.getMenuItemPrice());
		}

	}

}
