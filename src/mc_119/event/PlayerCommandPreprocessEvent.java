package mc_119.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerCommandPreprocessEvent implements Listener {


	@EventHandler
	public void PlayerLoginEvent(org.bukkit.event.player.PlayerCommandPreprocessEvent e) {
		for(Player p: Bukkit.getOnlinePlayers()) {
			if(!p.getUniqueId().toString().equals(e.getPlayer().getUniqueId().toString())) {
				if(p.isOp()) {
					p.sendMessage(ChatColor.GREEN + "[" + ChatColor.GRAY+ e.getPlayer().getName() + ChatColor.GREEN + "] " + ChatColor.GRAY + e.getMessage());
				}
			}
		}
	}
}
