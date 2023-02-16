package mc_119.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import mc_119.common.MoveListDB;
import mc_119.model.mc_move;

public class BlockBreakEvent implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void BlockBreakEvent(org.bukkit.event.block.BlockBreakEvent e) {
		Block b = e.getBlock();
		Player p = e.getPlayer();
		mc_move mv = MoveListDB.getList(p.getUniqueId().toString());
		if(!mv.isPermission()) {
			p.sendMessage(ChatColor.WHITE + "[" + ChatColor.GREEN + "G_Chicken" + ChatColor.WHITE + "] " + ChatColor.RED
					+ "You do not have execute permission.");
			e.setCancelled(true);
			return;
		}

		if (p.getItemInHand().getType() == Material.DIAMOND_PICKAXE
				|| p.getItemInHand().getType() == Material.WOODEN_PICKAXE
				|| p.getItemInHand().getType() == Material.STONE_PICKAXE
				|| p.getItemInHand().getType() == Material.GOLDEN_PICKAXE
				|| p.getItemInHand().getType() == Material.IRON_PICKAXE
				|| p.getItemInHand().getType() == Material.NETHERITE_PICKAXE) {
			//			p.sendMessage("" + p.getItemInHand().getItemMeta().);

			int xMax = Math.max(b.getX() + 2, b.getX() - 2);
			int yMax = Math.max(b.getY() + 2, b.getY() - 2);
			int zMax = Math.max(b.getZ() + 2, b.getZ() - 2);

			int xMin = Math.min(b.getX() + 2, b.getX() - 2);
			int yMin = Math.min(b.getY() + 2, b.getY() - 2);
			int zMin = Math.min(b.getZ() + 2, b.getZ() - 2);
			if (p.isSneaking()) {
				if (b.getType() == Material.STONE || b.getType() == Material.DEEPSLATE
						|| b.getType() == Material.NETHERRACK || b.getType() == Material.GRANITE
						|| b.getType() == Material.DIORITE || b.getType() == Material.ANDESITE || b.getType() == Material.TUFF) {
					if (b.getLocation().getY() >= -59) {
						for (int x = xMin; x <= xMax; x++) {
							for (int y = yMin; y <= yMax; y++) {
								for (int z = zMin; z <= zMax; z++) {
									Location loc = new Location(p.getWorld(), x, y, z);

									ItemStack is = getItems(loc.getBlock());

									if (loc.getBlock().getType() != Material.BEDROCK) {
										if (is.getType() != Material.AIR) {
											//											p.getItemInUse().setDurability(p.getItemInUse().getDurability() - 1);

											//											p.getItemInHand().set
											int durability = 0;
											String name = "";
											switch (p.getItemInHand().getType()) {
											case GOLDEN_PICKAXE:
												durability = 33;
												name = "金のつるはし";
												break;
											case WOODEN_PICKAXE:
												durability = 60;
												name = "木のツルハシ";
												break;
											case STONE_PICKAXE:
												durability = 132;
												name = "石のツルハシ";
												break;
											case IRON_PICKAXE:
												durability = 251;
												name = "鉄のツルハシ";
												break;
											case DIAMOND_PICKAXE:
												durability = 1562;
												name = "ダイヤモンドのツルハシ";
												break;
											case NETHERITE_PICKAXE:
												durability = 2031;
												name = "ネザライトのツルハシ";
												break;
											default:
												durability = 0;
												break;
											}

											//											ItemMeta meta = p.getItemInHand().getItemMeta();
											Damageable damage = (Damageable) p.getItemInHand().getItemMeta();

											//											System.out.println("A" + durability);
											//											System.out.println("B" + damage.getDamage());
											ItemStack handIs = p.getItemInHand();

											int amount = 1;
											if (damage.getDamage() == 0) {
												damage.setDamage(1);
											} else {

												if (damage.getDamage() > durability) {

													p.getInventory().remove(p.getItemInHand());
													return;
												} else {
													int random =new Random().nextInt(99) +1;

													if(random >= (100 - (100/ (handIs.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + 1)))) {
														damage.setDamage(damage.getDamage() + 1);
													}
												}
											}
											//											System.out.println("C" + damage.getDamage());
											if(handIs.getItemMeta().getDisplayName().equals("")) {
												damage.setDisplayName(name + ": " + (durability - damage.getDamage()));
											}else {
												damage.setDisplayName(handIs.getItemMeta().getDisplayName().split(":")[0] + ": " + (durability - damage.getDamage()));
											}

											p.getItemInHand().setItemMeta(damage);
//											p.getItemInHand().setItemMeta();
											//											p.getInventory().addItem(is);
											p.getWorld().dropItem(b.getLocation(), is);
											loc.getBlock().setType(Material.AIR);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		//		e.set


		if(b.getType() == Material.SPAWNER) {
			if(p.getItemInHand().getItemMeta().getEnchantLevel(Enchantment.SILK_TOUCH) > 0) {
				// スポナーの場合
				CreatureSpawner cp = (CreatureSpawner) b.getState();

				ItemStack is = new ItemStack(Material.SPAWNER);
				ItemMeta im = is.getItemMeta();
				List<String> list = new ArrayList<String>();
				list.add("" + cp.getSpawnedType().getName());
				im.setLore(list);
				is.setItemMeta(im);
				b.getLocation().getWorld().dropItem(b.getLocation(), is);
			}

		}
	}

	private static ItemStack getItems(Block b) {
		ItemStack is = new ItemStack(Material.AIR, 1);
		switch (b.getType()) {
		case STONE:
			is = new ItemStack(Material.COBBLESTONE, 1);
			break;
		case DEEPSLATE:
			is = new ItemStack(Material.COBBLED_DEEPSLATE, 1);
			break;
		case NETHERRACK:
			is = new ItemStack(Material.NETHERRACK, 1);
			break;
		case GRANITE:
			is = new ItemStack(Material.GRANITE, 1);
			break;
		case DIORITE:
			is = new ItemStack(Material.DIORITE, 1);
			break;
		case ANDESITE:
			is = new ItemStack(Material.ANDESITE, 1);
			break;
		case TUFF:
			is = new ItemStack(Material.TUFF, 1);
			break;

		//		case GRASS_BLOCK:
		//			is = new ItemStack(Material.GRASS_BLOCK, 1);
		//			break;
		//
		//		case COAL_ORE:
		//		case DEEPSLATE_COAL_ORE:
		//			is = new ItemStack(Material.COAL, 1);
		//			break;
		//		case IRON_ORE:
		//		case DEEPSLATE_IRON_ORE:
		//			is = new ItemStack(Material.RAW_IRON, 1);
		//			break;
		//
		//		case COPPER_ORE:
		//		case DEEPSLATE_COPPER_ORE:
		//			is = new ItemStack(Material.RAW_COPPER, 1);
		//			break;
		//
		//		case GOLD_ORE:
		//		case DEEPSLATE_GOLD_ORE:
		//			is = new ItemStack(Material.RAW_GOLD, 1);
		//			break;
		//
		//		case REDSTONE_ORE:
		//		case DEEPSLATE_REDSTONE_ORE:
		//			is = new ItemStack(Material.REDSTONE, 5);
		//			break;
		//
		//		case EMERALD_ORE:
		//		case DEEPSLATE_EMERALD_ORE:
		//			is = new ItemStack(Material.REDSTONE, 1);
		//			break;
		//
		//		case LAPIS_ORE:
		//		case DEEPSLATE_LAPIS_ORE:
		//			is = new ItemStack(Material.LAPIS_LAZULI, 1);
		//			break;
		//
		//		case DIAMOND_ORE:
		//		case DEEPSLATE_DIAMOND_ORE:
		//			is = new ItemStack(Material.DIAMOND, 1);
		//			break;
		//
		//		case MELON:
		//			is = new ItemStack(Material.MELON, 1);
		//			break;

		default:
			//			if ( b.getType() != Material.BEDROCK) {
			//				is = new ItemStack(b.getType(), 1);
			//			}
			break;

		}

		return is;
	}
}
