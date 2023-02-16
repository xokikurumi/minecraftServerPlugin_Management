package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HangingPlaceEvent implements Listener{

	@EventHandler
	public void HangingPlaceEvent(org.bukkit.event.hanging.HangingPlaceEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission("myplugin.place")) {
			p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
			e.setCancelled(true);
		}
	}
}
