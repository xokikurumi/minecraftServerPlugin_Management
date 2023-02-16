package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mc_119.common.MoveListDB;
import mc_119.model.mc_move;

public class PlayerPickupItemEvent implements Listener{
	@EventHandler
	public void PlayerPickupItemEvent(org.bukkit.event.player.PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		mc_move mv = MoveListDB.getList(p.getUniqueId().toString());
		if(!mv.isPermission()) {
			p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
			e.setCancelled(true);
		}
	}

}
