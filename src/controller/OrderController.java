package controller;

import java.util.List;

import model.Order;
import model.OrderItem;
import model.User;

public class OrderController {
    public static void createOrder(User orderUser, List<OrderItem> orderItems, String orderDate) {
        Order.createOrder(orderUser, orderItems, orderDate);
    }

    public static String updateOrder(int orderId, List<OrderItem> orderItems, String orderStatus) {
        return Order.updateOrder(orderId, orderItems, orderStatus);
    }

    public static void deleteOrder(int orderId) {
        Order.deleteOrder(orderId);
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
