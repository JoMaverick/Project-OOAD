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

    public static void createOrderItem(int orderId, int menuItemId, int quantity) {
        String query = "INSERT INTO order_items (order_id, menu_item_id, order_item_quantity) VALUES (?, ?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ps.setInt(2, menuItemId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
