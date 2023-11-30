package model;

public class OrderItem {
    private int orderId;
    private int menuItemId;
    private int quantity;

    public int getOrderId() {
        return orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
