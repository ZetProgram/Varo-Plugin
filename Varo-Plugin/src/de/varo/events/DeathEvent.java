package de.varo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;

import de.varo.Main;
import de.varo.Timer.KickRunnable;
import de.varo.Timer.TotRunnable;

public class DeathEvent implements Listener {

	int i;

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {

		Player p = e.getEntity();

		if (Main.getMainConfig().getBoolean("Gestartet") == true) {

			for (Player all : Bukkit.getOnlinePlayers()) {
				if (Main.getMainConfig().getBoolean("TodesSound") == true) {
					all.playSound(p.getLocation(), Sound.WITHER_DEATH, 20F, 20F);

					if (e.getEntity().getKiller() == e.getEntity()) {

						e.setDeathMessage(ChatColor.RED + e.getEntity().getName() + ChatColor.AQUA + " wurde von "
								+ ChatColor.GOLD + e.getEntity().getKiller().getName() + ChatColor.AQUA + " getötet!");
					}
					Bukkit.getScheduler().cancelTask(((KickRunnable) Main.getInstance().playerKickTask.get(p)).taskid);
					Main.getInstance().playerKickTask.remove(e.getEntity());
					TotRunnable t = new TotRunnable(p);
					BukkitScheduler scheduler1 = Bukkit.getServer().getScheduler();
					i = scheduler1.scheduleSyncRepeatingTask(Main.getInstance(), t, 0L, 20L);
					TotRunnable.taskid = i;

				}
			}
		}
	}

}
