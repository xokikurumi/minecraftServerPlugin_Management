package mc_119;



import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
//import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import mc_119.common.BanListDB;
import mc_119.common.DisplayNameListDB;
import mc_119.common.MoveListDB;
import mc_119.common.WhiteListDB;
import mc_119.event.BlockBreakEvent;
import mc_119.event.BlockPlaceEvent;
import mc_119.event.HangingBreakEvent;
import mc_119.event.HangingPlaceEvent;
import mc_119.event.PlayerBucketEmptyEvent;
import mc_119.event.PlayerCommandPreprocessEvent;
import mc_119.event.PlayerInteractEvent;
import mc_119.event.PlayerJoinEvent;
import mc_119.event.PlayerLoginEvent;
import mc_119.event.PlayerMoveEvent;
import mc_119.event.PlayerPickupItemEvent;
import mc_119.event.PlayerQuitEvent;
import mc_119.event.ProjectileHitEvent;
import mc_119.event.ServerListPingEvent;
import mc_119.event.WeatherChangeEvent;
import mc_119.module.GeyserDownload;

public class main extends JavaPlugin implements Listener {




	private int cnt = 0;
	private ServerResetTask srt;

//	SQLiteLib sqlite;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();

		//ProjectileHitEvent

		this.getServer().getPluginManager().registerEvents(new ProjectileHitEvent(), this);
		this.getServer().getPluginManager().registerEvents(new WeatherChangeEvent(), this);

		this.getServer().getPluginManager().registerEvents(new BlockBreakEvent(), this);
		this.getServer().getPluginManager().registerEvents(new BlockPlaceEvent(), this);

		this.getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerLoginEvent(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerBucketEmptyEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerPickupItemEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);

		this.getServer().getPluginManager().registerEvents(new PlayerQuitEvent(), this);


		this.getServer().getPluginManager().registerEvents(new HangingPlaceEvent(), this);
		this.getServer().getPluginManager().registerEvents(new HangingBreakEvent(), this);
//		this.getServer().getPluginManager().registerEvents(new ExplosionPrimeEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCommandPreprocessEvent(), this);

		// 自動クラフト系
//		this.getServer().getPluginManager().registerEvents(new PrepareItemCraftEvent(), this);
		
		
		// 額縁のアイテムフィルタ
		
		// サーバー系
		this.getServer().getPluginManager().registerEvents(new ServerListPingEvent(this), this);




		BukkitScheduler bs = this.getServer().getScheduler();


		ItemStack is = new ItemStack(Material.SPONGE,1);
//		is.setAmount(1);

		NamespacedKey costomKey  = new NamespacedKey(this, "sponge");
		ShapedRecipe customRecipe = new ShapedRecipe(is);

		customRecipe.shape("SWS","WSW", "SWS");
		customRecipe.setIngredient('S', Material.STRING);
		customRecipe.setIngredient('W', Material.WHITE_WOOL);

		this.getServer().addRecipe(customRecipe);

		srt = new ServerResetTask(this);
		bs.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				// TODO 自動生成されたメソッド・スタブ

				// サーバの再起動をチェックする
				srt.check();
				if(cnt == 20) {
					// アイテムをクラフトする
					cnt = 0;
				}
			}
		}, 0L, 1L);

	}

	// Fired when plugin is disabled
	@Override
	public void onDisable() {
//		this.getLogger().info("サーバ再起動");
//		Runtime rt = Runtime.getRuntime();
//		try {
//			rt.exec("G:\\minecraft_server\\1.19.2\\テスト鯖\\run.bat");
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
		
		GeyserDownload.download();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		if(sender instanceof Player) {
			Player pl = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("ghelp")) {
				if(pl.hasPermission("myplugin.ghelp")) {
					pl.sendMessage(ChatColor.GREEN + "==========[HELP]==========");
					pl.sendMessage(ChatColor.AQUA + "/HOME          " + ChatColor.GREEN + "スポーン地点に戻ります");
					pl.sendMessage(ChatColor.AQUA + "/HEAD <name>   " + ChatColor.GREEN + "<name>の頭を取得することができます");
					pl.sendMessage(ChatColor.AQUA + "/NAME <名前>   " + ChatColor.GREEN + "自身の表示名を<名前>に変更できます");
					pl.sendMessage(ChatColor.AQUA + "/IMG <URL>     " + ChatColor.GREEN + "白紙の地図を手に持った状態で、実行すると絵画に変更されます。");
					pl.sendMessage(ChatColor.GREEN + "              その後絵画を右クリックすることで<URL>の画像が描画されます");
					pl.sendMessage(ChatColor.AQUA + "/PING          " + ChatColor.GREEN + "コマンド実行時のサーバとの応答時間を取得できます.");
					pl.sendMessage(ChatColor.GREEN + "==========================");
				}else {
					pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
				}
			}

			if (cmd.getName().equalsIgnoreCase("home")) {
				if(pl.hasPermission("myplugin.home")) {
					if (pl.getBedSpawnLocation() == null) {
						pl.teleport(getServer().getWorld("world").getSpawnLocation());
					} else {
						pl.teleport(pl.getBedSpawnLocation());
					}
				}else {
					pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
				}
			}

			if (cmd.getName().equalsIgnoreCase("head")) {
				if(pl.hasPermission("myplugin.head")) {
					if (args.length == 1) {

						ItemStack is = new ItemStack(Material.PLAYER_HEAD);
						ItemMeta meta = is.getItemMeta();
						OfflinePlayer op = getServer().getOfflinePlayer(args[0]);
						SkullMeta sm = (SkullMeta) meta;
						sm.setOwningPlayer(op);
						is.setItemMeta(sm);
						is.setAmount(1);
						pl.getInventory().addItem(is);
					}
				}else {
					pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
				}
			}

			if(cmd.getName().equalsIgnoreCase("name")) {
				if(pl.hasPermission("myplugin.name")) {
					if(args.length == 1) {
						DisplayNameListDB.save(pl, args[0].replaceAll("&", "§") + ChatColor.WHITE);
						((Player) sender).setDisplayName(args[0].replaceAll("&", "§") + ChatColor.WHITE);
						((Player) sender).setPlayerListName(args[0].replaceAll("&", "§") + ChatColor.WHITE);
						((Player) sender).setCustomNameVisible(true);
						((Player) sender).setCustomName(args[0]);
					}
				}else {
					pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
				}
			}

			if(cmd.getName().equalsIgnoreCase("IMG")) {
//				if(args.length == 2) {
				if(pl.hasPermission("myplugin.img")) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(p.getItemInHand().getType() == Material.MAP) {
							if(args.length == 1) {
								ItemStack is = p.getItemInHand();
								ItemMeta im = is.getItemMeta();
								List<String> list = new ArrayList<String>();
								list.add(args[0]);
								im.setLore(list);
								im.setDisplayName(ChatColor.AQUA + "絵画");
								is.setItemMeta(im);
								p.setItemInHand(is);
							}
						}else {
							sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + "白紙の地図を持った状態で実行してください。");
						}
					}
				}else {
					pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
				}
//				}
			}

			if(cmd.getName().equalsIgnoreCase("ping")) {
				if(pl.hasPermission("myplugin.ping")) {
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.AQUA + "PING: " + pl.getPing()  + "ms");
				}else {
					pl.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
				}
			}
		}



		if (cmd.getName().equalsIgnoreCase("adm")) {
			if (sender.hasPermission("myplugin.adm")) {
				if(args.length == 0) {
					sender.sendMessage("adm reload");
					sender.sendMessage("adm ban <player>");
					sender.sendMessage("adm whitelist add <player>");
					sender.sendMessage("adm whitelist on");
					sender.sendMessage("adm whitelist off");
					sender.sendMessage("adm action <player> <true/false>");
				}else

				if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?") ) {
					sender.sendMessage("adm reload");
					sender.sendMessage("adm ban <player>");
					sender.sendMessage("adm whitelist add <player>");
					sender.sendMessage("adm whitelist on");
					sender.sendMessage("adm whitelist off");
					sender.sendMessage("adm action <player> <true/false>");
				}else

				if (args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
					sender.sendMessage("reload confi.");
					sender.sendMessage("");
				}else

				if (args[0].equalsIgnoreCase("ban")) {
					if(args.length >= 2) {
						for(int r = 1 ; r <= args.length ; r++) {
							BanListDB.save(Bukkit.getOfflinePlayer(args[r]));
							if(Bukkit.getOfflinePlayer(args[r]).isOnline()) {
								Bukkit.getPlayer(args[r]).kickPlayer("You BAN!!");
								sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + args[r] + ChatColor.AQUA + "をBANしました。");
							}
						}
					}else {

					}
				}else

				if (args[0].equalsIgnoreCase("action")) {
					MoveListDB.save(Bukkit.getOfflinePlayer(args[1]), new Boolean(args[2]));
					sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + args[1] + ChatColor.AQUA + "を" + (Boolean.parseBoolean(args[2]) ? ChatColor.GREEN + "許可" + ChatColor.AQUA : ChatColor.RED+ "禁止" + ChatColor.AQUA) +"しました。");
				}else

				if (args[0].equalsIgnoreCase("whitelist")) {
					if(args[1].equalsIgnoreCase("add")) {
						WhiteListDB.save(Bukkit.getOfflinePlayer(args[2]));
						sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + args[2] + ChatColor.AQUA + "をWhitelistに追加・変更しました。");
					}

					if(args[1].equalsIgnoreCase("on")) {
						this.getConfig().set("whitelist", true);
						this.saveConfig();
//						this.reloadConfig();
						sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.AQUA + "Whitelistを" + ChatColor.GREEN + "有効" + ChatColor.AQUA + "にしました。");
					}
					if(args[1].equalsIgnoreCase("off")) {
						this.getConfig().set("whitelist", false);
						this.saveConfig();
						sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.AQUA + "Whitelistを" + ChatColor.RED + "無効" + ChatColor.AQUA + "にしました。");
//						this.reloadConfig();
					}
				}
			}else {
				sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "Gチキ" + ChatColor.WHITE + "] " + ChatColor.RED + "You do not have execute permission.");
			}
		}

		return true;
	}
}
