package mc_119.event;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import mc_119.common.MoveListDB;
import mc_119.common.MyMapRender;
import mc_119.model.mc_move;

public class PlayerInteractEvent implements Listener {

	@EventHandler //(ignoreCancelled = true)
	public void PlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent e) {
		try {
			Player p = e.getPlayer();
			mc_move mv = MoveListDB.getList(p.getUniqueId().toString());
			if (!mv.isPermission()) {
				//			e.get
				p.sendMessage(
						ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED
								+ "You do not have execute permission.");
				e.setCancelled(true);
				return;
			}

			if (e.getItem().getType().equals(Material.MAP) && (e.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
				if (e.getItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "絵画")) {

					e.getItem().setAmount(e.getItem().getAmount() - 1);

					List<String> lore = new ArrayList<>();

					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					String path = ".\\plugins\\myPlugin\\maps\\";
					String file_name = sdf.format(cal.getTime()) + p.getName() + ".png";
					new File(path).mkdir();
					path = file_name;

					if (e.getItem().getItemMeta().getLore().get(0).startsWith("http")) {
						// 画像をダウンロードする
						try {

							URL url = new URL(e.getItem().getItemMeta().getLore().get(0));

							HttpURLConnection conn = (HttpURLConnection) url.openConnection();

							conn.setAllowUserInteraction(false);
							conn.setInstanceFollowRedirects(true);
							conn.setRequestMethod("GET");

							conn.connect();

							int httpStatusCode = conn.getResponseCode();
							if (httpStatusCode != HttpURLConnection.HTTP_OK) {
								throw new Exception("HTTP Status " + httpStatusCode);
							}

							String contentType = conn.getContentType();
							System.out.println("Content-Type: " + contentType);

							// Input Stream
							DataInputStream dataInStream = new DataInputStream(
									conn.getInputStream());

							// Output Stream
							DataOutputStream dataOutStream = new DataOutputStream(
									new BufferedOutputStream(
											new FileOutputStream(".\\plugins\\myPlugin\\maps\\" + file_name)));

							// Read Data
							byte[] b = new byte[4096];
							int readByte = 0;

							while (-1 != (readByte = dataInStream.read(b))) {
								dataOutStream.write(b, 0, readByte);
							}

							// Close Stream
							dataInStream.close();
							dataOutStream.close();

						} catch (IOException e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
						}

						lore.add(file_name);
						ItemMeta mapMeta = e.getItem().getItemMeta();
						mapMeta.setLore(lore);
						e.getItem().setItemMeta(mapMeta);
					} else {
						file_name = e.getItem().getItemMeta().getLore().get(0);
						ItemMeta mapMeta = e.getItem().getItemMeta();
						lore.add(file_name);
						mapMeta.setLore(lore);
						e.getItem().setItemMeta(mapMeta);
					}

					//新しい地図データを作る
					MapView view = Bukkit.getServer().createMap(e.getPlayer().getWorld());

					//座標と縮尺を設定
					view.setCenterX(0);
					view.setCenterZ(0);
					view.setScale(MapView.Scale.FAR);
					view.addRenderer(new MyMapRender(".\\plugins\\myPlugin\\maps\\" + file_name));
					ItemStack is = new ItemStack(Material.FILLED_MAP);
					MapMeta mm = (MapMeta) is.getItemMeta();

					mm.setMapView(view);
					List<String> maplore = new ArrayList<String>();
					maplore.add("きれいな絵が書かれたイラスト");
					is.setItemMeta(mm);
					mm.setLore(maplore);
					//地図データから地図IDを取り出し、アイテムとしての地図を作成しインベントリに格納
					e.getPlayer().getInventory().addItem(is);

					//インベントリデータの変更をクライアントに通知
					//		        e.getPlayer().updateInventory();
					e.setCancelled(true);
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
		}
	}

	@EventHandler
	public void PlayerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		mc_move mv = MoveListDB.getList(p.getUniqueId().toString());
		if (!mv.isPermission()) {
			p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED
					+ "You do not have execute permission.");
			e.setCancelled(true);
		}
	}
}
