package mc_119.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WeatherChangeEvent implements Listener{

	/*********************
	 * 天候操作
	 *********************/
	@EventHandler
	public void onWeatherChangeEvent(org.bukkit.event.weather.WeatherChangeEvent e) {
//		Bukkit.getServer().broadcastMessage("イベント発生");
		e.setCancelled(true);
	}
}
