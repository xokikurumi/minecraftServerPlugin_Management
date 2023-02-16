package mc_119.event;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent  implements Listener{


	public PlayerQuitEvent() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@EventHandler
	public void event(org.bukkit.event.player.PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.YELLOW + p.getCustomName() + ChatColor.AQUA + " が退出した");
	}
}
