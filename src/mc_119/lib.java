package mc_119;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class lib {



	/**************
	 * クアーリー保存
	 * ***********/
	public static boolean saveQuarry(Location loc) {
		File file = new File(".\\plugins\\myPlugin\\quarry");
		if(!file.exists()) {
			file.mkdirs();
			Bukkit.getLogger().info("MAKE DIR");
		}

		Bukkit.getLogger().info("CREATE_DATE A");

		file = new File(".\\plugins\\myPlugin\\quarry\\" + loc.getWorld().getName()  + "," +loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		try {
			file.createNewFile();
			Bukkit.getLogger().info("CREATE_DATE");
			return true;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		Bukkit.getLogger().info("CREATE_DATE ERROR");

		return false;
	}


	public static void deleteQuarry(Location loc) {
		File file = new File(".\\plugins\\myPlugin\\quarry\\" + loc.getWorld().getName()  + "," +loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
		file.delete();
	}


	public static String[] getQuarry() {
		File file = new File(".\\plugins\\myPlugin\\quarry");
		String[] fileNameList = file.list();

		return fileNameList;

	}
}
