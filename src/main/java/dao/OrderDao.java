package dao;

import entities.Order;
import entities.OrderItem;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    // Lấy tất cả các đơn hàng
    public List<Order> getAllOrders() throws ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM orders";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tableName = rs.getString("table_name");
                Order order = new Order(id, tableName);
                // Lấy các mục trong đơn hàng
                order.setOrderItems(getOrderItemsByOrderId(id));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching orders");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return orders;
    }

    // Lấy các mục trong đơn hàng theo ID của đơn hàng
    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        Connection connection = DBUtils.getConnection();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, orderId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");
            int price = rs.getInt("price");
            orderItems.add(new OrderItem(id, name, quantity, price));
        }
        return orderItems;
    }

    // Thêm một đơn hàng mới
    public boolean addOrder(Order order) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO orders (table_name) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, order.getTableName());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);  // Lấy ID của đơn hàng vừa tạo
                    for (OrderItem item : order.getOrderItems()) {
                        addOrderItem(orderId, item);  // Thêm các mục vào đơn hàng
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error while adding order");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return false;
    }

    // Thêm một mục vào đơn hàng
    public boolean addOrderItem(int orderId, OrderItem item) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String query = "INSERT INTO order_items (order_id, name, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, orderId);
        stmt.setString(2, item.getName());
        stmt.setInt(3, item.getQuantity());
        stmt.setInt(4, item.getPrice());
        return stmt.executeUpdate() > 0;
    }

    // Cập nhật một đơn hàng
    public boolean updateOrder(Order order) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "UPDATE orders SET table_name = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, order.getTableName());
            stmt.setInt(2, order.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Cập nhật các mục trong đơn hàng
                updateOrderItems(order);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error while updating order");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return false;
    }

    // Cập nhật các mục trong đơn hàng
    private void updateOrderItems(Order order) throws SQLException {
        Connection connection = DBUtils.getConnection();
        String deleteQuery = "DELETE FROM order_items WHERE order_id = ?";
        PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery);
        deleteStmt.setInt(1, order.getId());
        deleteStmt.executeUpdate();

        for (OrderItem item : order.getOrderItems()) {
            addOrderItem(order.getId(), item);  // Thêm lại các mục vào đơn hàng
        }
    }

    // Xóa một đơn hàng theo ID
    public boolean deleteOrder(int id) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "DELETE FROM order_items WHERE order_id = ?";
            PreparedStatement deleteItemsStmt = connection.prepareStatement(query);
            deleteItemsStmt.setInt(1, id);
            deleteItemsStmt.executeUpdate();

            String deleteOrderQuery = "DELETE FROM orders WHERE id = ?";
            PreparedStatement deleteOrderStmt = connection.prepareStatement(deleteOrderQuery);
            deleteOrderStmt.setInt(1, id);
            return deleteOrderStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting order");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Xóa mục khỏi đơn hàng
    public boolean deleteOrderItem(int orderId, int itemId) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "DELETE FROM order_items WHERE order_id = ? AND id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, orderId);
            stmt.setInt(2, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting order item");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Tìm kiếm đơn hàng theo tên bàn
    public List<Order> searchOrdersByTableName(String tableName) throws ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM orders WHERE table_name LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + tableName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Order order = new Order(id, rs.getString("table_name"));
                order.setOrderItems(getOrderItemsByOrderId(id));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching orders");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return orders;
    }
}
