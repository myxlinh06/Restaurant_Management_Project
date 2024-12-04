package dao;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.User;
import utils.DBUtils;


/**
 * The class contains methods to update and retrieve data from database
 * 
 * @author FA
 *
 */
public class UserDao {


    /**
     * The method to insert a new user into database.
     * 
     * @param user an user object.
     * @return true if register successfully.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean registerUser(User user) 
		throws ClassNotFoundException, IOException, SQLException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            CallableStatement callableStatement = 
		connection.prepareCall("{call usp_registerUser(?,?,?,?,?)}");
            int param = 0;
            callableStatement.setString(++param, user.getFirstName());
            callableStatement.setString(++param, user.getLastName());
            callableStatement.setString(++param, user.getEmail());
            callableStatement.setString(++param, user.getUserName());
            callableStatement.setString(++param, user.getPassword());
            int result = callableStatement.executeUpdate();


            if (result > 0) {
                return true;
            }
            return false;


        } finally {
            DBUtils.closeConnection(connection);
        }
    }
    
    
    public boolean loginUser(String userName, String password) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        try {
            // Mở kết nối với cơ sở dữ liệu
            connection = DBUtils.getConnection();

            // Tạo câu lệnh SQL để kiểm tra tên đăng nhập và mật khẩu
            String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userName);
            statement.setString(2, password);

            // Thực thi câu lệnh SQL và kiểm tra kết quả
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // true nếu có kết quả, false nếu không có kết quả
        } finally {
            // Đóng kết nối
            DBUtils.closeConnection(connection);
        }
    }
}
