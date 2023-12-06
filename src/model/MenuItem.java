package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuItem {
    private int menuItemId;
    private String menuItemName;
    private String menuItemDescription;
    private double menuItemPrice;

    public MenuItem(int menuItemId, String menuItemName, String menuItemDescription, double menuItemPrice) {
        super();
        this.menuItemId = menuItemId;
        this.menuItemName = menuItemName;
        this.menuItemDescription = menuItemDescription;
        this.menuItemPrice = menuItemPrice;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public String getMenuItemDescription() {
        return menuItemDescription;
    }

    public double getMenuItemPrice() {
        return menuItemPrice;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public void setMenuItemDescription(String menuItemDescription) {
        this.menuItemDescription = menuItemDescription;
    }

    public void setMenuItemPrice(double menuItemPrice) {
        this.menuItemPrice = menuItemPrice;
    }

    public static String createMenuItem(String menuItemName, String menuItemDescription, double menuItemPrice) {
        String query = "INSERT INTO menu_items (menuitem_id, menuitem_name, menuitem_description, menuitem_price) VALUES (?, ?, ?, ?)";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, 0);
            ps.setString(2, menuItemName);
            ps.setString(3, menuItemDescription);
            ps.setDouble(4, menuItemPrice);
            ps.executeUpdate();
            return "Create MenuItem Sucess";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Create MenuItem Failed";
        }
    }

    public static String updateMenuItem(int menuItemId, String menuItemName, String menuItemDescription,
            double menuItemPrice) {
        String query = "UPDATE menu_items SET menuitem_name = ?, menuitem_description = ?, menuitem_price = ? WHERE menuitem_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, menuItemName);
            preparedStatement.setString(2, menuItemDescription);
            preparedStatement.setDouble(3, menuItemPrice);
            preparedStatement.setInt(4, menuItemId);
            preparedStatement.executeUpdate();
            return "Update MenuItem Sucess";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Update MenuItem Failed";
        }

    }

    public static void deleteMenuItem(int menuItemId) {
        String query = "DELETE FROM menu_items WHERE menuitem_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, menuItemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<MenuItem> getAllMenuItems() {
        ArrayList<MenuItem> menuitemlist = new ArrayList<>();
        String query = "SELECT * FROM menu_items";
        try (Connection connection = Connect.getInstance().getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("menuitem_id");
                String name = resultSet.getString("menuitem_name");
                String desc = resultSet.getString("menuitem_description");
                double price = resultSet.getDouble("menuitem_price");

                MenuItem menuitems = new MenuItem(id, name, desc, price);
                menuitemlist.add(menuitems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuitemlist;
    }

    public static MenuItem getMenuItemById(int menuItemsId) {
        MenuItem menuItem;
        String query = "SELECT * FROM menu_items WHERE menuitem_id = ?";
        try (Connection connection = Connect.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, menuItemsId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("menuitem_id");
                String name = resultSet.getString("menuitem_name");
                String desc = resultSet.getString("menuitem_description");
                double price = resultSet.getDouble("menuitem_price");

                menuItem = new MenuItem(id, name, desc, price);

                return menuItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
