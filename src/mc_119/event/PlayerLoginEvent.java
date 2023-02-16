package mc_119.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.Plugin;

import mc_119.common.BanListDB;
import mc_119.common.WhiteListDB;
import mc_119.model.mc_ban;
import mc_119.model.mc_whitelist;

public class PlayerLoginEvent implements Listener {

	private Plugin plugin;

	public PlayerLoginEvent(Plugin plugin) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.plugin = plugin;
	}

	@EventHandler
	public void PlayerLoginEvent(org.bukkit.event.player.PlayerLoginEvent e) {
		Player p = e.getPlayer();

		Bukkit.getServer().getLogger().info("IP: " + e.getAddress().getHostName());
		Bukkit.getServer().getLogger().info("IP: " + e.getRealAddress().getHostAddress());
		Bukkit.getServer().getLogger().info("Player_name: " + p.getName());
		Bukkit.getServer().getLogger().info("UUID: " + p.getUniqueId().toString());

		List<mc_whitelist> uuidList = WhiteListDB.getList();
		List<mc_ban> banList = BanListDB.getList();

		List<String> compList = plugin.getConfig().getStringList("comp_list");

		Bukkit.getServer().getLogger().info("CompList" + compList.size());


		String ipAddress = e.getRealAddress().getHostAddress();
//		String ipAddress = "106.166.217.116";
		// countryCode
		if (ipAddress.equals("127.0.0.1")) {
			Bukkit.getServer().getLogger().info("Login countryCode: LocalHost");
			if(plugin.getConfig().getBoolean("whitelist")) {
				Bukkit.getLogger().info("WHITELIST");
				for (mc_whitelist uuid : uuidList) {
					if (uuid.getUuid().equals(p.getUniqueId().toString())) {
						if(!uuid.isPermission()) {
							Bukkit.getLogger().info( p.getName() + " is Kick");

							e.setKickMessage("Only players who are currently on the white list will be allowed in.\nSorry.\n");
							e.setResult(Result.KICK_WHITELIST);
						}
					}
				}
			}else {
				Bukkit.getLogger().info("NO WHITELIST");
			}


			for (mc_ban uuid : banList) {
				if (uuid.getUuid().equals(p.getUniqueId().toString()) && uuid.isPermission()) {
					StringBuilder builder = new StringBuilder();
					builder.append("I'm sorry.\n");
					builder.append("You cannot enter this server in the country you live in.\n");
					builder.append("Or you may have been banned because you have committed violations on this server in the past.\n");
					p.kickPlayer(builder.toString());
					e.setKickMessage(builder.toString());
					e.setResult(Result.KICK_WHITELIST);
				}
			}
		}else {

			boolean resultBol = false;
			try {

				String result = "";
				URL url = new URL("http://ip-api.com/json/" + ipAddress);

				HttpURLConnection con = (HttpURLConnection) url.openConnection();

				con.connect(); // URL接続
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String tmp = "";

				while ((tmp = in.readLine()) != null) {
					result += tmp;
				}


				for (String comp : compList) {

					if (result.contains(comp)) {
						resultBol = true;
						Bukkit.getServer().getLogger().info("Login countryCode: " + comp);
					}
				}

				in.close();
				con.disconnect();
				if(plugin.getConfig().getBoolean("whitelist")) {
					Bukkit.getLogger().info("WHITELIST");
					for (mc_whitelist uuid : uuidList) {
						if (uuid.getUuid().equals(p.getUniqueId().toString())) {
							resultBol = true;
							if(!uuid.isPermission()) {
								Bukkit.getLogger().info( p.getName() + " is Kick");
								e.setKickMessage("Only players who are currently on the white list will be allowed in.\nSorry.\n");
								e.setResult(Result.KICK_WHITELIST);
							}
						}
					}
				}else {
					Bukkit.getLogger().info("NO WHITELIST");
				}

				for (mc_ban uuid : banList) {
					if (uuid.getUuid().equals(p.getUniqueId().toString()) && uuid.isPermission()) {
						resultBol = false;
					}
				}

				if (!resultBol) {
					StringBuilder builder = new StringBuilder();
					builder.append("I'm sorry.\n");
					builder.append("You cannot enter this server in the country you live in.\n");
					builder.append("Or you may have been banned because you have committed violations on this server in the past.\n");
					p.kickPlayer(builder.toString());
					e.setKickMessage(builder.toString());
					e.setResult(Result.KICK_WHITELIST);
				}
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
	}
}
