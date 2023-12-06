package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public static void createOrderItem(int orderId, int menuItemId, int quantity) {
        String query = "INSERT INTO order_items (order_id, menuitem_id, order_item_quantity) VALUES (?, ?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating order item", e);
        }
    }

    public static void updateOrderItem(int orderId, int menuItemId, int quantity) {
        String query = "UPDATE order_items SET order_item_quantity = ? WHERE order_id = ? AND menuitem_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setInt(2, orderId);
            ps.setInt(3, menuItemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating order item", e);
        }
    }

    public static void deleteOrderItem(int orderId, int menuItemId) {
        String query = "DELETE FROM order_items WHERE order_id = ? AND menuitem_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order item", e);
        }
    }

    public static List<OrderItem> getAllOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setMenuItemId(rs.getInt("menuitem_id"));
                orderItem.setQuantity(rs.getInt("order_item_quantity"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting order items", e);
        }
        return orderItems;
    }
}
