package model;

public class Order {
    private int orderId;
    private int orderUserId;
    private int orderItemId;
    private String orderStatus;
    private String orderDate;
    private int orderTotal;

    public int getOrderId() {
        return orderId;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }
}
