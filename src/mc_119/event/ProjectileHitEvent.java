package mc_119.event;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MonsterEggs;
import org.bukkit.util.Vector;

public class ProjectileHitEvent implements Listener{

	@EventHandler
	public void PlayerMoveEvent (org.bukkit.event.entity.ProjectileHitEvent e) {
		try {
			if(e.getEntityType() == EntityType.EGG) {
				// 卵の場合
				ItemStack is = new ItemStack(Material.getMaterial(e.getHitEntity().getName().toUpperCase().replaceAll(" ", "_") + "_SPAWN_EGG"));
				MonsterEggs egg = new MonsterEggs();

//				egg.setMaterial(Material.getMaterial(e.getHitEntity().getName() + ""));
				double y = new Random().nextInt(100) / 100 + 0.5;
				e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), is).setVelocity(new Vector(0, y, 0));
				e.getHitEntity().getWorld().playEffect(e.getHitEntity().getLocation(), Effect.MOBSPAWNER_FLAMES, 0, 10);
				e.getHitEntity().getWorld().playSound(e.getHitEntity().getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
				e.getEntity().remove();
				e.getHitEntity().remove();

				e.setCancelled(true);

			}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	}
}
