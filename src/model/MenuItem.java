package model;

public class MenuItem {
    private int menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private double menuItemPrice;

    public int getMenuItemId() {
        return menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public double getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public void setMenuItemDescription(String menuItemDescription) {
        this.menuItemDescription = menuItemDescription;
    }

    public void setMenuItemPrice(double menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }
}
