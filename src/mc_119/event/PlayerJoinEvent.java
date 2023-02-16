package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import mc_119.common.DisplayNameListDB;
import mc_119.model.mc_display_name;

public class PlayerJoinEvent implements Listener{

	private Plugin pl;

	public PlayerJoinEvent(Plugin plugin) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.pl = plugin;
	}

	@EventHandler
	public void PlayerJoinEvent (org.bukkit.event.player.PlayerJoinEvent e) {
//		Bukkit.getServer().get

		Player p = e.getPlayer();


		mc_display_name mdn = DisplayNameListDB.getList(p);
		p.setDisplayName(mdn.getDisplay());
		p.setPlayerListName(mdn.getDisplay());
		p.setCustomName(mdn.getDisplay());
		p.setCustomNameVisible(true);

		e.setJoinMessage(ChatColor.YELLOW + p.getDisplayName() + ChatColor.AQUA + "が参加");
		for(String msg : pl.getConfig().getStringList("login_message")) {
			p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.WHITE + msg);
		}

		if(p.hasPermission("myplugin.adm")) {
			p.sendMessage(ChatColor.AQUA + "===============================================");
			p.sendMessage("whitelist: " + (pl.getConfig().getBoolean("whitelist")? ChatColor.GREEN + "有効" : ChatColor.RED + "無効"));
			p.sendMessage("ログイン可能な国: ");
			for(String str: pl.getConfig().getStringList("comp_list")) {
				p.sendMessage("    - " + str);
			}
			p.sendMessage("サーバ停止時刻: " + pl.getConfig().getString("autoServerReset"));
			p.sendMessage(ChatColor.AQUA + "===============================================");
		}


//		Bukkit.getServer().getLogger().info("Host Name: "+ p.getAddress().getHostString());
	}
}
