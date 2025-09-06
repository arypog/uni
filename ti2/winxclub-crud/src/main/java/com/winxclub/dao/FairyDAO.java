package com.winxclub.dao;

import com.winxclub.model.Fairy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FairyDAO {
	private final String url = "jdbc:postgresql://localhost:5432/winxclubdb";
	private final String user = "postgres";
	private final String password = "postgres";

	private Connection connect() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public void addFairy(Fairy fairy) {
		String sql = "INSERT INTO fairies (name, power, age) VALUES (?, ?, ?)";
		try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, fairy.getName());
			stmt.setString(2, fairy.getPower());
			stmt.setInt(3, fairy.getAge());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Fairy> getAllFairies() {
		List<Fairy> fairies = new ArrayList<>();
		String sql = "SELECT * FROM fairies";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				Fairy fairy = new Fairy(rs.getInt("id"), rs.getString("name"), rs.getString("power"), rs.getInt("age"));
				fairies.add(fairy);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fairies;
	}

	public void updateFairy(Fairy fairy) {
		String sql = "UPDATE fairies SET name=?, power=?, age=? WHERE id=?";
		try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, fairy.getName());
			stmt.setString(2, fairy.getPower());
			stmt.setInt(3, fairy.getAge());
			stmt.setInt(4, fairy.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFairy(int id) {
		String sql = "DELETE FROM fairies WHERE id=?";
		try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
