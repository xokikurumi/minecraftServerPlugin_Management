package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mc_119.common.MoveListDB;
import mc_119.model.mc_move;

public class HangingBreakEvent implements Listener{

	@EventHandler
	public void HangingBreakEvent(org.bukkit.event.hanging.HangingBreakByEntityEvent e) {

		if (e.getRemover().getType() == EntityType.PLAYER) {
			Player p = (Player) e.getRemover();
			mc_move mv = MoveListDB.getList(p.getUniqueId().toString());
			if(!mv.isPermission()) {
				p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");

				e.setCancelled(true);
			}
		}
	}
}
