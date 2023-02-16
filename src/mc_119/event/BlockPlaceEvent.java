package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mc_119.common.MoveListDB;
import mc_119.model.mc_move;

public class BlockPlaceEvent implements Listener {
	@EventHandler
	public void BlockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();

		mc_move mv = MoveListDB.getList(p.getUniqueId().toString());
		if (!mv.isPermission()) {
			p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED
					+ "You do not have execute permission.");

			e.setCancelled(true);
			return;
		}
		if (b.getType() == Material.SPAWNER) {
			BlockState state = b.getState();
			CreatureSpawner cp = (CreatureSpawner) state;
			cp.setSpawnedType(EntityType.fromName(
					e.getItemInHand().getItemMeta().getLore().get(0).replaceAll("§e", "").replaceAll("§E", "")));
			state.update();
		}
	}

}
