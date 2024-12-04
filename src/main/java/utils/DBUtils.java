package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/rm_project?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Thay thế bằng username của bạn
    private static final String PASSWORD = ""; // Thay thế bằng password của bạn

    // Phương thức kết nối đến CSDL
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Tải driver
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // Lấy kết nối
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed! Check output console.");
            e.printStackTrace();
        }
        return connection;
    }

    // Phương thức đóng kết nối
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close(); // Đóng kết nối
            } catch (SQLException e) {
                System.out.println("Error closing connection.");
                e.printStackTrace();
            }
        }
    }
}
