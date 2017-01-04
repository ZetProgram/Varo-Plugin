package de.varo.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import de.varo.Main;

public class DamageEvent implements Listener {

	@EventHandler
	public void onDMG(EntityDamageEvent e) {
		Entity en = e.getEntity();

		if (en instanceof Player) {
			Player p = (Player) en;

			if (Main.getInstance().isfreeze.contains(p))
				e.setCancelled(true);
		}
		if (Main.getMainConfig().getBoolean("AnfangSchutzzeit") == true) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void on(EntityDamageByEntityEvent e) {
		Entity dmg = e.getDamager();

		if (dmg instanceof Player) {
			Player p = (Player) dmg;

			if (Main.getInstance().isfreeze.contains(p)) {
				e.setCancelled(true);
			}
			if (Main.getPlayerConfig().getString(e.getEntity().getName() + ".TeamName") == Main.getPlayerConfig()
					.getString(dmg.getName() + ".TeamName")) {
				e.setCancelled(true);
			}

			if (Main.getMainConfig().getBoolean("AnfangSchutzzeit") == true) {
				e.setCancelled(true);
			}
		}
	}

}
