package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private User orderUser;
    private List<OrderItem> orderItems;
    private String orderStatus;
    private String orderDate;
    private double orderTotal;

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

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public static void createOrder(User orderUser, List<OrderItem> orderItems, String orderDate) {
        String query = "INSERT INTO orders (user_id, order_status, order_date) VALUES (?, ?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderUser.getUserId());
            ps.setString(2, "Pending");
            ps.setDate(3, java.sql.Date.valueOf(orderDate));
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
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting order id", e);
        }

        for (OrderItem orderItem : orderItems) {
            OrderItem.createOrderItem(orderId, orderItem.getMenuItem(), orderItem.getQuantity());
        }
    }

    public static String updateOrder(int orderId, List<OrderItem> orderItems, String orderStatus) {
        boolean isOrderIdExist = false;
        List<Order> orders = Order.getAllOrders();
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                isOrderIdExist = true;
            }
        }

        if (!isOrderIdExist) {
            return "Error: Order id not found.";
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
                OrderItem.updateOrderItem(orderId, orderItem.getMenuItem(), orderItem.getQuantity());
            }

            return "Update order sucess.";
        }
    }

    public static void deleteOrder(int orderId) {
        List<OrderItem> orderItems = OrderItem.getAllOrderItemsByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            OrderItem.deleteOrderItem(orderId, orderItem.getMenuItem().getMenuItemId());
        }

        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting order", e);
        }
    }

    public static List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        String query = "SELECT * FROM orders AS o JOIN users AS u ON o.user_id = u.user_id JOIN order_items AS oi ON oi.order_id = o.order_id WHERE o.user_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                User orderUser = new User();
                orderUser.setUserId(resultSet.getInt("user_id"));
                orderUser.setUserRole(resultSet.getString("user_role"));
                orderUser.setUserName(resultSet.getString("user_name"));
                orderUser.setUserEmail(resultSet.getString("user_email"));
                orderUser.setUserPassword(resultSet.getString("user_password"));
                order.setOrderUser(orderUser);
                order.setOrderStatus(resultSet.getString("order_status"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                order.setOrderDate(sdf.format(resultSet.getDate("order_date")));
                orders.add(order);
            }
            resultSet.close();
            for (Order order : orders) {
                orderItems = OrderItem.getAllOrderItemsByOrderId(order.getOrderId());
                for (OrderItem orderItem : orderItems) {
                    total += orderItem.getMenuItem().getMenuItemPrice() * orderItem.getQuantity();
                }
                order.setOrderItems(orderItems);
                order.setOrderTotal(total);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting orders", e);
        }
        return orders;
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        String query = "SELECT * FROM orders AS o JOIN users AS u ON o.user_id = u.user_id JOIN order_items AS oi ON oi.order_id = o.order_id";
        try (Connection connection = Connect.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                User orderUser = new User();
                orderUser.setUserId(resultSet.getInt("user_id"));
                orderUser.setUserRole(resultSet.getString("user_role"));
                orderUser.setUserName(resultSet.getString("user_name"));
                orderUser.setUserEmail(resultSet.getString("user_email"));
                orderUser.setUserPassword(resultSet.getString("user_password"));
                order.setOrderUser(orderUser);
                order.setOrderStatus(resultSet.getString("order_status"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                order.setOrderDate(sdf.format(resultSet.getDate("order_date")));
                orders.add(order);
            }
            resultSet.close();
            for (Order order : orders) {
                orderItems = OrderItem.getAllOrderItemsByOrderId(order.getOrderId());
                for (OrderItem orderItem : orderItems) {
                    total += orderItem.getMenuItem().getMenuItemPrice() * orderItem.getQuantity();
                }
                order.setOrderItems(orderItems);
                order.setOrderTotal(total);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting orders", e);
        }
        return orders;
    }

    public static Order getOrderById(int orderId) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        String query = "SELECT * FROM orders AS o JOIN users AS u ON o.user_id = u.user_id JOIN order_items AS oi ON oi.order_id = o.order_id WHERE o.order_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                order.setOrderId(resultSet.getInt("order_id"));
                User orderUser = new User();
                orderUser.setUserId(resultSet.getInt("user_id"));
                orderUser.setUserRole(resultSet.getString("user_role"));
                orderUser.setUserName(resultSet.getString("user_name"));
                orderUser.setUserEmail(resultSet.getString("user_email"));
                orderUser.setUserPassword(resultSet.getString("user_password"));
                order.setOrderUser(orderUser);
                order.setOrderStatus(resultSet.getString("order_status"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                order.setOrderDate(sdf.format(resultSet.getDate("order_date")));
            }
            resultSet.close();
            orderItems = OrderItem.getAllOrderItemsByOrderId(order.getOrderId());
            for (OrderItem orderItem : orderItems) {
                total += orderItem.getMenuItem().getMenuItemPrice() * orderItem.getQuantity();
            }
            order.setOrderItems(orderItems);
            order.setOrderTotal(total);
        } catch (SQLException e) {
            throw new RuntimeException("Error getting order", e);
        }
        return order;
    }
}
