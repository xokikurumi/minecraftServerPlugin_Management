package mc_119.event;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class InventoryMoveItemEvent implements Listener{
	
	@EventHandler
	public void event(org.bukkit.event.inventory.InventoryMoveItemEvent e) {
		ItemStack is = e.getItem();
		Location loc = e.getInitiator().getLocation();
//		loc.setX(loc.getX() + 1);
		ItemFrame itemFrame;
		GlowItemFrame glowItemFrame;
		for(Entity entity : loc.getWorld().getNearbyEntities(loc, 0.1, 0.1, 0.1)) {
			// 対象のEntityがアイテムフレームの場合
			if(entity.getType().equals(EntityType.ITEM_FRAME)) {
				itemFrame = (ItemFrame) entity;
				if(itemFrame.getItem().getType().equals(is.getType()) ){
					e.setCancelled(true);
				}
			}
			if(entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {
				glowItemFrame = (GlowItemFrame) entity;
				if(glowItemFrame.getItem().getType().equals(is.getType()) ){
					e.setCancelled(true);
				}
			}
		}
	}
}
