package mc_119.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.OfflinePlayer;

import mc_119.model.mc_display_name;

public class DisplayNameListDB {

	public static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/minecraft?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true";
	public static final String USERID = "root";
	public static final String PASSWORD = "root";
	public static final String SQL = "SELECT name, display, uuidFROM mc_display_name";
	public static final String SQL_WHERE = "SELECT name, display, uuid FROM mc_display_name WHERE uuid ='${0}'";
//	public static final String SQL_UPDATE = "UPDATE mc_display_name SET name='${0}',uuid='${1}',permission=${2}, display='${3}'";
//	public static final String SQL_INSERT = "INSERT INTO mc_display_name (name, uuid, permission) VALUES ('${0}','${1}', true)";

	public static Connection con = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static mc_display_name getList(OfflinePlayer offlinePlayer) {
		mc_display_name mcw = new mc_display_name();
		try {
			con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);

			PreparedStatement ps = con.prepareStatement(SQL_WHERE.replaceAll("\\$\\{0\\}", offlinePlayer.getUniqueId().toString()));

			ResultSet rs = ps.executeQuery();


			while(rs.next()) {


				mcw.setName(rs.getString("name"));
				mcw.setUuid(rs.getString("uuid"));
				mcw.setDisplay(rs.getString("display"));
//				uuidList.add(mcw);
			}
			rs.close();
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		return mcw;
	}

	public static void save(OfflinePlayer offlinePlayer, String name) {

		boolean check = false;
		boolean permission = true;

		try {
			con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);

			PreparedStatement ps = con.prepareStatement(SQL_WHERE.replaceAll("\\$\\{0\\}", offlinePlayer.getUniqueId().toString()));

			ResultSet rs = ps.executeQuery();


			while(rs.next()) {

				check = true;
			}
			rs.close();
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		if(check) {
			// UPDATE
			try {
				con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);
				String sql = "UPDATE mc_display_name SET ";
//				sql += "name = '" + offlinePlayer.getName() + "',";
//				sql += "uuid = '" + offlinePlayer.getUniqueId().toString() + "', ";
				sql += "display = '" + name + "' ";
				sql += "WHERE ";
				sql += "uuid = '" + offlinePlayer.getUniqueId().toString() + "'";
				PreparedStatement ps = con.prepareStatement(sql);

				ps.executeUpdate();
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else {
			// INSERT
			try {
				con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);
				String sql = "INSERT INTO mc_display_name (name,uuid,display) VALUES(";
				sql += "'" + offlinePlayer.getName() + "',";
				sql += "'" + offlinePlayer.getUniqueId().toString() + "',";
				sql += "'" + name + "'";
				sql += ")";
				PreparedStatement ps = con.prepareStatement(sql);

				ps.executeUpdate();
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
}
