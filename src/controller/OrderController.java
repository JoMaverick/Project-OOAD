package controller;

import java.util.List;

import model.Order;
import model.OrderItem;
import model.User;

public class OrderController {
    public static void createOrder(User orderUser, List<OrderItem> orderItems, String orderDate) {
        Order.createOrder(orderUser, orderItems, orderDate);
    }

    public static String updateOrder(String orderId, List<OrderItem> orderItems, String orderStatus) {
        if (orderId.isBlank()) {
            return "Error: Order ID cannot be empty.";
        }

        String message = Order.updateOrder(Integer.parseInt(orderId), orderItems, orderStatus);
        if (message.equals("Update order sucess.")) {
            return orderStatus;
        } else {
            return message;
        }
    }

    public static String deleteOrder(String orderId) {
        if (orderId.isBlank()) {
            return "Error: Order must be chosen.";
        } else {
            Order.deleteOrder(Integer.parseInt(orderId));
            return "Delete order sucess.";
        }
    }

    public static List<Order> getOrdersByCustomerId(int customerId) {
        return Order.getOrdersByCustomerId(customerId);
    }

    public static List<Order> getAllOrders() {
        return Order.getAllOrders();
    }

    public static Order getOrderById(int orderId) {
        return Order.getOrderById(orderId);
    }
}
