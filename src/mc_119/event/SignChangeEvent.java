package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SignChangeEvent implements Listener {
	public SignChangeEvent() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@EventHandler
	public void event(org.bukkit.event.block.SignChangeEvent e) {
		Player p = e.getPlayer();
		Sign s = (Sign) e.getBlock().getState();
		Block b = e.getBlock();
		if (b.getType() == Material.ACACIA_WALL_SIGN || b.getType() == Material.ACACIA_WALL_HANGING_SIGN
				|| b.getType() == Material.BAMBOO_SIGN || b.getType() == Material.BAMBOO_WALL_HANGING_SIGN
				|| b.getType() == Material.BIRCH_WALL_HANGING_SIGN || b.getType() == Material.BIRCH_WALL_SIGN
				|| b.getType() == Material.CRIMSON_SIGN || b.getType() == Material.CRIMSON_WALL_HANGING_SIGN
				|| b.getType() == Material.DARK_OAK_WALL_HANGING_SIGN || b.getType() == Material.DARK_OAK_WALL_SIGN
				|| b.getType() == Material.JUNGLE_WALL_HANGING_SIGN || b.getType() == Material.JUNGLE_WALL_SIGN
				|| b.getType() == Material.MANGROVE_WALL_HANGING_SIGN || b.getType() == Material.MANGROVE_WALL_SIGN
				|| b.getType() == Material.OAK_WALL_HANGING_SIGN || b.getType() == Material.OAK_WALL_SIGN
				|| b.getType() == Material.SPRUCE_WALL_HANGING_SIGN || b.getType() == Material.SPRUCE_WALL_SIGN
				|| b.getType() == Material.WARPED_WALL_HANGING_SIGN || b.getType() == Material.WARPED_WALL_SIGN) {
			if(e.getLine(0).equalsIgnoreCase("ChestLink")) {
				e.setLine(0, ChatColor.GREEN + "[ChestLink]");
				if(!e.getLine(1).isEmpty()) {
					e.setLine(1, ChatColor.AQUA + e.getLine(1));
					p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.AQUA + "グループ名[" + e.getLine(1) + "]を作成しました。");

//					p.sendMessage("" + s.get);
					p.sendMessage("" + p.getFacing().toString());


				}else {
					p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + "グループ名が指定されていません。");
					e.setCancelled(true);
				}
			}



			if(e.getLine(0).equalsIgnoreCase("AutoCraft")) {
				e.setLine(0, ChatColor.GREEN + "[AutoCraft]");
				if(!e.getLine(1).isEmpty()) {
					e.setLine(1, ChatColor.AQUA + e.getLine(1));
					p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.AQUA + "グループ名[" + e.getLine(1) + "]を作成しました。");
//					p.sendMessage("" + b.get);


				}else {
					p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + "グループ名が指定されていません。");
					e.setCancelled(true);
				}
			}
		}
	}
}
