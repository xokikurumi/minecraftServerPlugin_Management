package mc_119.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PrepareItemCraftEvent implements Listener {


	@EventHandler
	public void event(org.bukkit.event.inventory.PrepareItemCraftEvent e) {
		try {
			ItemStack result = e.getRecipe().getResult();

//			List<String> recipes = new ArrayList<String>();
			Map<String, Integer> recipeMap = new HashMap<String, Integer>();
			for(int r = 1; r <= 9 ; r++) {
				boolean check = false;
				String itemName = e.getView().getItem(r).getType().name();
				if(!itemName.equalsIgnoreCase("AIR")) {
					for(String str : recipeMap.keySet()) {
						if(str.equals(itemName)) {
							check = true;
							break;
						}
					}

					if(check) {
						recipeMap.put(itemName, recipeMap.get(itemName));
					}else {
						recipeMap.put(itemName, 1);
					}
				}
//				recipes.add(e.getView().getItem(r).getType().name());
			}

			if(result != null) {
				Location loc = e.getInventory().getLocation();
				for(Entity entity : loc.getWorld().getNearbyEntities(loc, 1, 1, 1)) {
					if(entity.getType().equals(EntityType.ITEM_FRAME) || entity.getType().equals(EntityType.GLOW_ITEM_FRAME)) {
						Bukkit.getLogger().info(entity.getName());
						ItemFrame itemFrame = (ItemFrame) entity;
						itemFrame.setItem(result);
						itemFrame.setCustomName("AutoCraft");
//						itemFrame.setCustomNameVisible(true);
						entity.getLocation().getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
//						e.getView().close();
						e.getView().getPlayer().sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.GREEN + "AutoCraftのレシピが「" +ChatColor.WHITE + result.getType().name() + ChatColor.GREEN  + "」に設定しました。");
					}
				}
			}

		}catch (Exception er) {
			// TODO: handle exception
		}
	}
}
