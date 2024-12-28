package dao;

import entities.Ban;
import utils.DBUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BanDao {
    
    public List<Ban> getBanByPhong(int idPhong) throws ClassNotFoundException {
        List<Ban> banList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM ban WHERE id_phong = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idPhong);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tenBan = rs.getString("ten_ban");
                String status = rs.getString("status");
                banList.add(new Ban(id, idPhong, tenBan, status));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching Ban");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return banList;
    }

    public boolean addBan(Ban ban) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO ban (id_phong, ten_ban, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, ban.getIdPhong());
            stmt.setString(2, ban.getTenBan());
            stmt.setString(3, ban.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding Ban");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    public boolean updateBanStatus(int idBan, String status) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "UPDATE ban SET status = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setInt(2, idBan);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating Ban status");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }
}