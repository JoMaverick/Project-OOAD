package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private int receiptId;
    private Order receiptOrder;
    private double receiptPaymentAmount;
    private String receiptPaymentDate;
    private String receiptPaymentType;

    public int getReceiptId() {
        return receiptId;
    }

    public Order getReceiptOrder() {
        return receiptOrder;
    }

    public double getReceiptPaymentAmount() {
        return receiptPaymentAmount;
    }

    public String getReceiptPaymentDate() {
        return receiptPaymentDate;
    }

    public String getReceiptPaymentType() {
        return receiptPaymentType;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public void setReceiptOrder(Order receiptOrder) {
        this.receiptOrder = receiptOrder;
    }

    public void setReceiptPaymentAmount(double receiptPaymentAmount) {
        this.receiptPaymentAmount = receiptPaymentAmount;
    }

    public void setReceiptPaymentDate(String receiptPaymentDate) {
        this.receiptPaymentDate = receiptPaymentDate;
    }

    public void setReceiptPaymentType(String receiptPaymentType) {
        this.receiptPaymentType = receiptPaymentType;
    }

    public static void createReceipt(Order receiptOrder, String receiptPaymentType, double receiptPaymentAmount,
            String receiptPaymentDate) {
        String query = "INSERT INTO receipts (payment_type, payment_amount, payment_date) VALUES (?, ?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, receiptPaymentType);
            ps.setDouble(2, receiptPaymentAmount);
            ps.setDate(3, java.sql.Date.valueOf(receiptPaymentDate));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating receipt", e);
        }

        int receiptId = 0;
        String query2 = "SELECT receipt_id FROM receipts ORDER BY receipt_id DESC LIMIT 1";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query2)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                receiptId = rs.getInt("receipt_id");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting receipt id", e);
        }

        String query3 = "INSERT INTO receipt_details (receipt_id, order_id) VALUES (?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query3)) {
            ps.setInt(1, receiptId);
            ps.setInt(2, receiptOrder.getOrderId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating receipt details", e);
        }
    }

    public static void updateReceipt(int orderId, String receiptPaymentType, double receiptPaymentAmount,
            String receiptPaymentDate) {
        int receiptId = 0;
        String query = "SELECT receipt_id FROM receipt_details WHERE order_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                receiptId = rs.getInt("receipt_id");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting receipt id", e);
        }

        String query2 = "UPDATE receipts SET payment_type = ?, payment_amount = ?, payment_date = ? WHERE receipt_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query2)) {
            ps.setString(1, receiptPaymentType);
            ps.setDouble(2, receiptPaymentAmount);
            ps.setDate(3, java.sql.Date.valueOf(receiptPaymentDate));
            ps.setInt(4, receiptId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating receipt", e);
        }
    }

    public static void deleteReceipt(int orderId) {
        int receiptId = 0;
        String query = "SELECT receipt_id FROM receipt_details WHERE order_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                receiptId = rs.getInt("receipt_id");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting receipt id", e);
        }

        String query2 = "DELETE FROM receipt_details WHERE receipt_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query2)) {
            ps.setInt(1, receiptId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting receipt details", e);
        }

        String query3 = "DELETE FROM receipts WHERE receipt_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query3)) {
            ps.setInt(1, receiptId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting receipt", e);
        }
    }

    public static Receipt getReceiptById(int receiptId) {
        Receipt receipt = new Receipt();
        String query = "SELECT * FROM receipts WHERE receipt_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, receiptId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                receipt.setReceiptId(rs.getInt("receipt_id"));
                receipt.setReceiptPaymentType(rs.getString("payment_type"));
                receipt.setReceiptPaymentAmount(rs.getDouble("payment_amount"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                receipt.setReceiptPaymentDate(sdf.format(rs.getDate("payment_date")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting receipt", e);
        }

        String query2 = "SELECT * FROM receipt_details WHERE receipt_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query2)) {
            ps.setInt(1, receiptId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                receipt.setReceiptOrder(Order.getOrderById(rs.getInt("order_id")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting receipt details", e);
        }

        return receipt;
    }

    public static List<Receipt> getAllReceipts() {
        List<Receipt> receipts = new ArrayList<>();
        String query = "SELECT * FROM receipts";
        try (Connection connection = Connect.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Receipt receipt = new Receipt();
                receipt.setReceiptId(resultSet.getInt("receipt_id"));
                receipt.setReceiptPaymentType(resultSet.getString("payment_type"));
                receipt.setReceiptPaymentAmount(resultSet.getDouble("payment_amount"));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                receipt.setReceiptPaymentDate(sdf.format(resultSet.getDate("payment_date")));
                receipts.add(receipt);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error getting receipts", e);
        }

        for (Receipt receipt : receipts) {
            String query2 = "SELECT * FROM receipt_details WHERE receipt_id = ?";
            try (Connection connection = Connect.getInstance().getConnection();
                    PreparedStatement ps = connection.prepareStatement(query2)) {
                ps.setInt(1, receipt.getReceiptId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    receipt.setReceiptOrder(Order.getOrderById(rs.getInt("order_id")));
                }
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error getting receipt details", e);
            }
        }
        return receipts;
    }
}
