package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entities.MenuItem;
import utils.DBUtils;

public class MenuDao {

    // Lấy tất cả các món ăn từ menu
    public List<MenuItem> getAllMenuItems() throws ClassNotFoundException {
        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM menu";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tenMon = rs.getString("ten_mon");
                double gia = rs.getDouble("gia");
                String moTa = rs.getString("mo_ta");
                String nguyenLieu = rs.getString("nguyen_lieu");
                String hinhAnh = rs.getString("hinh_anh");
                String category = rs.getString("category");

                
                menuItems.add(new MenuItem(id, tenMon, gia, moTa, nguyenLieu, hinhAnh, category));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching menu items");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return menuItems;
    }

    // Thêm món mới vào menu
    public boolean addMenuItem(MenuItem menuItem) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO menu (ten_mon, gia, mo_ta, nguyen_lieu, hinh_anh, category) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, menuItem.getTenMon());
            stmt.setDouble(2, menuItem.getGia());
            stmt.setString(3, menuItem.getMoTa());
            stmt.setString(4, menuItem.getNguyenLieu());
            stmt.setString(5, menuItem.getHinhAnh());
            stmt.setString(6, menuItem.getCategory());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding menu item");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Cập nhật món trong menu
    public boolean updateMenuItem(MenuItem menuItem) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "UPDATE menu SET ten_mon = ?, gia = ?, mo_ta = ?, nguyen_lieu = ?, hinh_anh = ?, category = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, menuItem.getTenMon());
            stmt.setDouble(2, menuItem.getGia());
            stmt.setString(3, menuItem.getMoTa());
            stmt.setString(4, menuItem.getNguyenLieu());
            stmt.setString(5, menuItem.getHinhAnh());
            stmt.setString(6, menuItem.getCategory());
            stmt.setInt(7, menuItem.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating menu item");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Xóa món khỏi menu
    public boolean deleteMenuItem(int id) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "DELETE FROM menu WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting menu item");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Tìm kiếm món theo tên
    public List<MenuItem> searchMenuItems(String tenMon) throws ClassNotFoundException {
        List<MenuItem> menuItems = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM menu WHERE ten_mon LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + tenMon + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MenuItem menuItem = new MenuItem(
                    rs.getInt("id"),
                    rs.getString("ten_mon"),
                    rs.getDouble("gia"),
                    rs.getString("mo_ta"),
                    rs.getString("nguyen_lieu"),
                    rs.getString("hinh_anh"),
                    rs.getString("category")
                );
                menuItems.add(menuItem);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching menu items");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return menuItems;
    }
}
