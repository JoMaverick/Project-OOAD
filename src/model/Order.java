package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private User orderUser;
    private List<OrderItem> orderItems;
    private String orderStatus;
    private String orderDate;
    private int orderTotal;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public User getOrderUser() {
        return orderUser;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
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

    public void setOrderUser(User orderUser) {
        this.orderUser = orderUser;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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

    public static void createOrder(int orderUserId, List<OrderItem> orderItems, String orderDate) {
        String query = "INSERT INTO orders (user_id, order_status, order_date) VALUES (?, ?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderUserId);
            ps.setString(2, "Pending");
            ps.setString(3, orderDate);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating order", e);
        }

        int orderId = 0;
        String query2 = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query2)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderId = rs.getInt("order_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting order id", e);
        }

        for (OrderItem orderItem : orderItems) {
            OrderItem.createOrderItem(orderId, orderItem.getMenuItemId(), orderItem.getQuantity());
        }
    }

    public static String updateOrder(int orderId, List<OrderItem> orderItems, String orderStatus) {
        boolean orderIdExist = false;
        String query2 = "SELECT order_id FROM orders";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query2)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (orderId == rs.getInt("order_id")) {
                    orderIdExist = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting order id", e);
        }

        if (!orderIdExist) {
            return "Update Order Failed";
        } else {
            String query = "UPDATE orders SET order_status = ? WHERE order_id = ?";
            try (Connection connection = Connect.getInstance().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, orderStatus);
                preparedStatement.setInt(2, orderId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error updating order", e);
            }

            for (OrderItem orderItem : orderItems) {
                OrderItem.updateOrderItem(orderId, orderItem.getMenuItemId(), orderItem.getQuantity());
            }

            return "Update Order Success";
        }
    }
}
