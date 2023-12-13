package controller;

import java.util.List;

import model.MenuItem;
import model.OrderItem;

public class OrderItemController {
    public static String createOrderItem(String orderId, MenuItem menuItem, String quantity) {
        if (menuItem == null) {
            return "Error: Menu item must be chosen.";
        } else if (quantity.isBlank()) {
            return "Error: Quantity must be filled.";
        } else if (Integer.parseInt(quantity) < 1) {
            return "Error: Quantity cannot be below 1.";
        } else {
            OrderItem.createOrderItem(Integer.parseInt(orderId), menuItem, Integer.parseInt(quantity));
            return "Create order item sucess.";
        }
    }

    public static String updateOrderItem(String orderId, MenuItem menuItem, String quantity) {
        if (orderId.isBlank()) {
            return "Error: Order must be chosen.";
        } else if (quantity.isBlank()) {
            return "Error: Quantity must be filled.";
        } else if (Integer.parseInt(quantity) < 0) {
            return "Error: Quantity must greater equal than 0.";
        } else if (Integer.parseInt(quantity) == 0) {
            deleteOrderItem(orderId, menuItem.getMenuItemId());
            return "Delete order item sucess.";
        } else {
            OrderItem.updateOrderItem(Integer.parseInt(orderId), menuItem, Integer.parseInt(quantity));
            return "Update order item sucess.";
        }
    }

    public static void deleteOrderItem(String orderId, int menuItemId) {
        OrderItem.deleteOrderItem(Integer.parseInt(orderId), menuItemId);
    }

    public static List<OrderItem> getAllOrderItemsByOrderId(String orderId) {
        return OrderItem.getAllOrderItemsByOrderId(Integer.parseInt(orderId));
    }
}
