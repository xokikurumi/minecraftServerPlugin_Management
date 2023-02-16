package mc_119.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import mc_119.lib;

public class ChunkUnloadEvent implements Listener{



	@EventHandler
	public void event(org.bukkit.event.world.ChunkUnloadEvent e) {
		String[] files = lib.getQuarry();
		for (String f : files) {
			String[] location = f.split(",");

			Location loc = new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]), Double.parseDouble(location[3]));

			if(loc.getChunk().equals(e.getChunk())) {
//				Bukkit.broadcastMessage("EVENT!!");

				return;
			}



		}
	}
}
