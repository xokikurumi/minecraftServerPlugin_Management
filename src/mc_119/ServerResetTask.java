package mc_119;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ServerResetTask{

	Plugin plugin;

	public ServerResetTask(Plugin plugin) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.plugin = plugin;
	}

	public void check() {
//		Bukkit.broadcastMessage("たすくなのだ");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
//		Bukkit.getLogger().info("task run");

		if(sdf.format(cal.getTime()).equals(plugin.getConfig().getString("autoServerResetTime"))) {
			if(plugin.getConfig().getBoolean("autoServerReset")) {
				Bukkit.getServer().shutdown();
			}
		}
		if(sdf.format(cal.getTime()).equals(plugin.getConfig().getString("autoServerResetMessageTime"))) {
			if(plugin.getConfig().getBoolean("autoServerReset")) {
				Bukkit.getServer().broadcastMessage("§a§l10分後サーバを再起動します。");
			}
		}
	}

}
