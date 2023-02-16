package mc_119.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.OfflinePlayer;

import mc_119.model.mc_move;

public class MoveListDB {

	public static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/minecraft?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true";
	public static final String USERID = "root";
	public static final String PASSWORD = "root";
	public static final String SQL = "SELECT name, uuid, permission FROM mc_move";
	public static final String SQL_WHERE = "SELECT name, uuid, permission FROM mc_ban WHERE uuid ='${0}'";
	public static final String SQL_UPDATE = "UPDATE mc_move SET name='${0}',uuid='${1}',permission=";
	public static final String SQL_INSERT = "INSERT INTO mc_move (name, uuid, permission) VALUES ('${0}','${1}', true)";

	public static Connection con = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static mc_move getList(String uuid) {
		mc_move m = new mc_move();
		try {
			con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);

			PreparedStatement ps = con.prepareStatement(SQL + " WHERE uuid = '" + uuid + "'");

			ResultSet rs = ps.executeQuery();



			rs.next();

			m.setName(rs.getString("name"));
			m.setUuid(rs.getString("uuid"));
			m.setPermission(rs.getBoolean("permission"));


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
			m.setPermission(false);
		}


		return m;
	}
	public static void save(OfflinePlayer offlinePlayer , boolean type) {

		boolean check = false;
		boolean permission = true;

		try {
			con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);

			PreparedStatement ps = con.prepareStatement(SQL_WHERE.replaceAll("\\$\\{0\\}", offlinePlayer.getUniqueId().toString()));

			ResultSet rs = ps.executeQuery();


			while(rs.next()) {

				check = true;

				permission = rs.getBoolean("permission");
//				mc_whitelist mcw = new mc_whitelist();
//
//				mcw.setName(rs.getString("name"));
//				mcw.setUuid(rs.getString("uuid"));
//				mcw.setPermission(rs.getBoolean("permission"));
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


		if(check) {
			// UPDATE
			try {
				con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);

				PreparedStatement ps = con.prepareStatement(SQL_UPDATE.replaceAll("\\$\\{0\\}", offlinePlayer.getName()).replaceAll("\\$\\{1\\}", offlinePlayer.getUniqueId().toString()) + new Boolean(type).toString() + " WHERE uuid = '" + offlinePlayer.getUniqueId().toString() + "'");

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

				PreparedStatement ps = con.prepareStatement(SQL_INSERT.replaceAll("\\$\\{0\\}", offlinePlayer.getName()).replaceAll("\\$\\{1\\}", offlinePlayer.getUniqueId().toString()));

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
