package dao;

import model.PowerpuffGirl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PowerpuffGirlDAO {
    private final String url = "jdbc:postgresql://localhost:5432/powerpuffdb";
    private final String user = "postgres";
    private final String password = "postgres";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }


    public void addGirl(PowerpuffGirl girl) {
        String sql = "INSERT INTO powerpuff_girls(name, superpower, age) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, girl.getName());
            pstmt.setString(2, girl.getSuperpower());
            pstmt.setInt(3, girl.getAge());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<PowerpuffGirl> getAllGirls() {
        List<PowerpuffGirl> girls = new ArrayList<>();
        String sql = "SELECT * FROM powerpuff_girls";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                girls.add(new PowerpuffGirl(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("superpower"),
                        rs.getInt("age")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return girls;
    }


    public void updateGirl(PowerpuffGirl girl) {
        String sql = "UPDATE powerpuff_girls SET name=?, superpower=?, age=? WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, girl.getName());
            pstmt.setString(2, girl.getSuperpower());
            pstmt.setInt(3, girl.getAge());
            pstmt.setInt(4, girl.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteGirl(int id) {
        String sql = "DELETE FROM powerpuff_girls WHERE id=?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
