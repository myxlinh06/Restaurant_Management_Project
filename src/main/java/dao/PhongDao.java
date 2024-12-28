package dao;

import entities.Phong;
import utils.DBUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDao {
    
    public List<Phong> getAllPhong() throws ClassNotFoundException {
        List<Phong> phongList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM phong";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String tenPhong = rs.getString("ten_phong");
                phongList.add(new Phong(id, tenPhong));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching Phong");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return phongList;
    }

    public boolean addPhong(Phong phong) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO phong (ten_phong) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, phong.getTenPhong());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding Phong");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }
}