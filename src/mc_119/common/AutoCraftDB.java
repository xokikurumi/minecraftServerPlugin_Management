package mc_119.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

import mc_119.model.mc_whitelist;

public class  AutoCraftDB {

	public static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/minecraft?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true";
	public static final String USERID = "root";
	public static final String PASSWORD = "root";
	private static final String SQL = "SELECT loc_x, loc_y, loc_z, item_result, item_result_cnt, item_1, item_1_cnt, item_2, item_2_cnt, item_3, item_3_cnt, item_4, item_4_cnt, item_5, item_5_cnt, item_6, item_6_cnt, item_7, item_7_cnt, item_8, item_8_cnt, item_9, item_9_cnt FROM mc_auto_craft";
	private static final String SQL_WHERE = "SELECT loc_x, loc_y, loc_z, item_result, item_result_cnt, item_1, item_1_cnt, item_2, item_2_cnt, item_3, item_3_cnt, item_4, item_4_cnt, item_5, item_5_cnt, item_6, item_6_cnt, item_7, item_7_cnt, item_8, item_8_cnt, item_9, item_9_cnt FROM mc_auto_craft WHERE loc_x = ${1} AND loc_y = ${2} AND loc_z = ${3}";
	private static final String SQL_INSERT = "INSERT INTO mc_auto_craft (loc_x,loc_y,loc_z,item_result,item_result_cnt,item_1,item_1_cnt,item_2,item_2_cnt,item_3,item_3_cnt,item_4,item_4_cnt,item_5,item_5_cnt,item_6,item_6_cnt,item_7,item_7_cnt,item_8,item_8_cnt,item_9,item_9_cnt) VALUES( loc_x,  loc_y,  loc_z,  item_result,  item_result_cnt,  item_1,  item_1_cnt,  item_2,  item_2_cnt,  item_3,  item_3_cnt,  item_4,  item_4_cnt,  item_5,  item_5_cnt,  item_6,  item_6_cnt,  item_7,  item_7_cnt,  item_8,  item_8_cnt,  item_9,  item_9_cnt)";
	private static final String SQL_UPDATE = "UPDATE mc_auto_craft SET loc_x = ${1}, loc_y = ${2}, loc_z = ${3}, item_result = ${4}, item_result_cnt = ${5}, item_1 = '${6}', item_1_cnt = ${7}, item_2 = '${8}', item_2_cnt = ${9}, item_3 = '${10}', item_3_cnt = ${11}, item_4 = '${12}', item_4_cnt = ${13}, item_5 = '${14}', item_5_cnt = ${15}, item_6 = '${16}', item_6_cnt = ${17}, item_7 = '${18}', item_7_cnt = ${19}, item_8 = '${20}', item_8_cnt = ${21}, item_9 = '${22}', item_9_cnt = ${23} WHERE id = ${24}";

	public static Connection con = null;
	public static PreparedStatement ps = null;
	public static ResultSet rs = null;

	public static List<mc_whitelist> getList() {
		List<mc_whitelist> uuidList = new ArrayList<mc_whitelist>();
		try {
			con = DriverManager.getConnection(CONNECT_STRING, USERID, PASSWORD);

			PreparedStatement ps = con.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();


			while(rs.next()) {
				mc_whitelist mcw = new mc_whitelist();

				mcw.setName(rs.getString("name"));
				mcw.setUuid(rs.getString("uuid"));
				mcw.setPermission(rs.getBoolean("permission"));
				uuidList.add(mcw);
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


		return uuidList;
	}

	public static void save(OfflinePlayer offlinePlayer) {

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

				PreparedStatement ps = con.prepareStatement(SQL_UPDATE.replaceAll("\\$\\{0\\}", offlinePlayer.getName()).replaceAll("\\$\\{1\\}", offlinePlayer.getUniqueId().toString()) + new Boolean(!permission).toString() + " WHERE uuid = '" + offlinePlayer.getUniqueId().toString() + "'");

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
