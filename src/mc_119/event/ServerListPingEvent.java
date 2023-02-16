package mc_119.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ServerListPingEvent implements Listener {

	private Plugin pl;

	public ServerListPingEvent(Plugin plugin) {
		// TODO 自動生成されたコンストラクター・スタブ
		pl = plugin;
	}

	@EventHandler
	public void event(org.bukkit.event.server.ServerListPingEvent e) {
		pl.getLogger().info("[ServerListPingEvent] Address: " + e.getAddress());
		pl.getLogger().info("[ServerListPingEvent] HostName: " + e.getHostname());
		e.setMotd(pl.getConfig().getString("serverListMessage"));
		
		String ipAddress = e.getAddress().toString();
//		String ipAddress = "106.166.217.116";
		// countryCode
		String result = "";
		if (ipAddress.equals("127.0.0.1")) {
			Bukkit.getServer().getLogger().info("Login countryCode: LocalHost");
			
		}else {

			boolean resultBol = false;
			try {

				
				URL url = new URL("http://ip-api.com/json/" + ipAddress);

				HttpURLConnection con = (HttpURLConnection) url.openConnection();

				con.connect(); // URL接続
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String tmp = "";

				while ((tmp = in.readLine()) != null) {
					result += tmp;
				}

				pl.getLogger().info("[ServerListPingEvent] HostName: " + result);

				in.close();
				con.disconnect();
				
			} catch (MalformedURLException e1) {
				// TODO 自動生成された catch ブロック
				Bukkit.getServer().getLogger().info(e1.getMessage());
				e1.printStackTrace();

			} catch (IOException e1) {
				Bukkit.getServer().getLogger().info(e1.getMessage());
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}

		}
		
		for(Player p : pl.getServer().getOnlinePlayers()) {
			if(p.isOp()) {
				p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "ServerListPingEvent Log");
				p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.AQUA + "HOST NAME: " + e.getHostname());
				p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.AQUA + "ADDRESS  : " + e.getAddress());
				p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.AQUA + "COMP     : " + result);
			}
		}
	}
}
