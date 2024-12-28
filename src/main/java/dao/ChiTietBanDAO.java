package dao;

import entities.ChiTietBan;
import utils.DBUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietBanDAO {
    
    public List<ChiTietBan> getChiTietBanByBan(int idBan) throws ClassNotFoundException {
        List<ChiTietBan> chiTietBanList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "SELECT * FROM chi_tiet_ban WHERE id_ban = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idBan);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idPhong = rs.getInt("id_phong");
                int idMon = rs.getInt("id_mon");
                int soLuong = rs.getInt("so_luong");
                double donGia = rs.getDouble("don_gia");
                chiTietBanList.add(new ChiTietBan(id, idPhong, idBan, idMon, soLuong, donGia));
            }
        } catch (SQLException e) {
            System.out.println("Error while fetching Chi_tiet_ban");
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(connection);
        }
        return chiTietBanList;
    }

    public boolean addChiTietBan(ChiTietBan chiTietBan) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "INSERT INTO chi_tiet_ban (id_phong, id_ban, id_mon, so_luong, don_gia) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, chiTietBan.getIdPhong());
            stmt.setInt(2, chiTietBan.getIdBan());
            stmt.setInt(3, chiTietBan.getIdMon());
            stmt.setInt(4, chiTietBan.getSoLuong());
            stmt.setDouble(5, chiTietBan.getDonGia());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while adding Chi_tiet_ban");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }

    public boolean updateChiTietBan(ChiTietBan chiTietBan) throws ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBUtils.getConnection();
            String query = "UPDATE chi_tiet_ban SET so_luong = ?, don_gia = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, chiTietBan.getSoLuong());
            stmt.setDouble(2, chiTietBan.getDonGia());
            stmt.setInt(3, chiTietBan.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error while updating Chi_tiet_ban");
            e.printStackTrace();
            return false;
        } finally {
            DBUtils.closeConnection(connection);
        }
    }
}