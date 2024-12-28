package dao;

import entities.Customer;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    // Lấy tất cả khách hàng
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String hoTen = rs.getString("hoTen");
                String phone = rs.getString("phone");
                String diaChi = rs.getString("diaChi");
                String tinh = rs.getString("tinh");
                String huyen = rs.getString("huyen");
                String xa = rs.getString("xa");

                customers.add(new Customer(id, email, hoTen, phone, diaChi, tinh, huyen, xa));
            }
        }

        return customers;
    }

    // Thêm khách hàng
    public boolean addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO customer(email, hoTen, phone, diaChi, tinh, huyen, xa) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getEmail());
            stmt.setString(2, customer.getHoTen());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getDiaChi());
            stmt.setString(5, customer.getTinh());
            stmt.setString(6, customer.getHuyen());
            stmt.setString(7, customer.getXa());

            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật thông tin khách hàng
    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE customer SET email = ?, hoTen = ?, phone = ?, diaChi = ?, tinh = ?, huyen = ?, xa = ? WHERE id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getEmail());
            stmt.setString(2, customer.getHoTen());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getDiaChi());
            stmt.setString(5, customer.getTinh());
            stmt.setString(6, customer.getHuyen());
            stmt.setString(7, customer.getXa());
            stmt.setInt(8, customer.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa khách hàng
    public boolean deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM customer WHERE id = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    //Tìm kiếm
    public List<Customer> searchCustomers(String keyword) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer WHERE hoTen LIKE ? OR email LIKE ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Thiết lập giá trị cho tham số trong câu lệnh SQL trước khi thực thi câu lệnh
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("hoTen"),
                        rs.getString("phone"),
                        rs.getString("diaChi"),
                        rs.getString("tinh"),
                        rs.getString("huyen"),
                        rs.getString("xa")
                    );
                    customers.add(customer);
                }
            }
        }
        return customers;
    }

 }
