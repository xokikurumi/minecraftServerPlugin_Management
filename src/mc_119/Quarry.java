package mc_119;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Quarry extends BukkitRunnable{
	public Quarry() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void run() {
		String[] files = lib.getQuarry();
		for (String f : files) {
			String[] location = f.split(",");

			dig(location);


		}

	}

	private static void dig(String[] location) {
//		Bukkit.getServer().broadcastMessage("A-A");
//		Thread thread = new Thread(new Runnable() {
//
//			@Override
//			public void run() {
				Location loc = new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]),
						Double.parseDouble(location[2]), Double.parseDouble(location[3]));
				Location chestLoc = new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]),
						Double.parseDouble(location[2]) + 1, Double.parseDouble(location[3]));
				Location last = new Location(Bukkit.getWorld(location[0]), Double.parseDouble(location[1]) + 15, -59,
						Double.parseDouble(location[3]) + 15);

				Chest c = (Chest) chestLoc.getBlock().getState();
//				Dropper dr = (Dropper) loc.getBlock().getState();

				loc.setY(loc.getY() - 1);


				// TODO 自動生成されたメソッド・スタブ
				for (int y = loc.getBlockY(); last.getBlockY() <= y; y--) {
					int minX = Math.min(loc.getBlockX(), last.getBlockX());
					int maxX = Math.max(loc.getBlockX(), last.getBlockX());

					for (int x = minX; x <= maxX; x++) {
						int minZ = Math.min(loc.getBlockZ(), last.getBlockZ());
						int maxZ = Math.max(loc.getBlockZ(), last.getBlockZ());


						for (int z = minZ; z <= maxZ; z++) {
							Location quarryLoc = new Location(Bukkit.getWorld(location[0]), x, y, z);

							if (quarryLoc.getBlock().getType() != Material.AIR && quarryLoc.getBlock().getType() != Material.WATER && quarryLoc.getBlock().getType() != Material.LAVA ) {
								// インベントリーに空きがあるとき
								if(c.getInventory().firstEmpty() == -1) {

									return;
								}else {
//									Bukkit.getServer().broadcastMessage("B");

									c.getInventory().addItem(getItems(quarryLoc.getBlock()));

									quarryLoc.getBlock().setType(Material.AIR);
									return;
								}
							}
						}
					}
				}
//			}
////		});
////
////
////		thread.run();
	}


	private static ItemStack getItems(Block b) {
		ItemStack is = new ItemStack(Material.COBBLESTONE, 1);
		switch (b.getType()) {
		case STONE:
			is = new ItemStack(Material.COBBLESTONE, 1);
			break;
		case DEEPSLATE:
			is = new ItemStack(Material.COBBLED_DEEPSLATE, 1);
			break;

		case GRASS_BLOCK:
			is = new ItemStack(Material.GRASS_BLOCK, 1);
			break;

		case COAL_ORE:
		case DEEPSLATE_COAL_ORE:
			is = new ItemStack(Material.COAL, 1);
			break;
		case IRON_ORE:
		case DEEPSLATE_IRON_ORE:
			is = new ItemStack(Material.RAW_IRON, 1);
			break;

		case COPPER_ORE:
		case DEEPSLATE_COPPER_ORE:
			is = new ItemStack(Material.RAW_COPPER, 1);
			break;

		case GOLD_ORE:
		case DEEPSLATE_GOLD_ORE:
			is = new ItemStack(Material.RAW_GOLD, 1);
			break;

		case REDSTONE_ORE:
		case DEEPSLATE_REDSTONE_ORE:
			is = new ItemStack(Material.REDSTONE, 5);
			break;

		case EMERALD_ORE:
		case DEEPSLATE_EMERALD_ORE:
			is = new ItemStack(Material.REDSTONE, 1);
			break;

		case LAPIS_ORE:
		case DEEPSLATE_LAPIS_ORE:
			is = new ItemStack(Material.LAPIS_LAZULI, 1);
			break;

		case DIAMOND_ORE:
		case DEEPSLATE_DIAMOND_ORE:
			is = new ItemStack(Material.DIAMOND, 1);
			break;

		case MELON:
			is = new ItemStack(Material.MELON, 1);
			break;

		default:
			is = new ItemStack(b.getType(), 1);
			break;

		}

		return is;
	}
}
