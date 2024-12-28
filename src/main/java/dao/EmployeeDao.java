package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import entities.Employee;
import utils.DBUtils;

public class EmployeeDao {

    // Lấy danh sách tất cả người dùng
    public List<Employee> getAllUsers() throws ClassNotFoundException {
        List<Employee> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM users";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	Employee user = new Employee(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getTimestamp("created_at"),
                    rs.getString("quyen")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching users");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return users;
    }

    // Thêm mới người dùng
    public boolean addUser(Employee user) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO users (firstName, lastName, email, userName, password, created_at, quyen) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserName());
            stmt.setString(5, user.getPassword());
            stmt.setTimestamp(6, new Timestamp(user.getCreatedAt().getTime()));
            stmt.setString(7, user.getQuyen());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding user");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Cập nhật thông tin người dùng
    public boolean updateUser(Employee user) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "UPDATE users SET firstName = ?, lastName = ?, email = ?, userName = ?, password = ?, quyen = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUserName());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getQuyen());
            stmt.setInt(7, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating user");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Xóa người dùng
    public boolean deleteUser(int id) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while deleting user");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    // Tìm kiếm người dùng theo tên người dùng
    public List<Employee> searchUsers(String userName) throws ClassNotFoundException {
        List<Employee> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM users WHERE userName LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + userName + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	Employee user = new Employee(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("userName"),
                    rs.getString("password"),
                    rs.getTimestamp("created_at"),
                    rs.getString("quyen")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching users");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return users;
    }

}
