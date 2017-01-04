package de.varo.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.scheduler.BukkitScheduler;

import de.varo.Main;
import de.varo.Timer.JoinRunnable;
import de.varo.Timer.KickRunnable;

public class JoinEvent implements Listener {

	static int i;

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (Main.getMainConfig().getBoolean("Gestartet")) {
			Main.getInstance().isfreeze.add(e.getPlayer());
			JoinRunnable jr = new JoinRunnable(p);
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			i = scheduler.scheduleSyncRepeatingTask(Main.getInstance(), jr, 0L, 20L);
			JoinRunnable.taskid = i;
			if (Main.getPlayerConfig().getInt(e.getPlayer().getName() + ".SpielZeit") != 0) {
				int k = Main.getPlayerConfig().getInt(e.getPlayer().getName() + ".SpielZeit");
				e.setJoinMessage(Main.getInstance().prefix + e.getPlayer().getName() + " ist reconnectet: " + k
						+ " Sekunden verbleiben!");
				KickRunnable scheduler2 = new KickRunnable(p,
						Main.getPlayerConfig().getInt(e.getPlayer().getName() + ".SpielZeit"));
				BukkitScheduler anzahl = Bukkit.getServer().getScheduler();
				i = anzahl.scheduleSyncRepeatingTask(Main.getInstance(), scheduler2, 0L, 20L);
				scheduler2.taskid = i;
				Main.getInstance().playerKickTask.put(e.getPlayer(), scheduler2);
			} else {
				KickRunnable kr = new KickRunnable(p, Main.getMainConfig().getInt("SpielZeit"));
				BukkitScheduler scheduler1 = Bukkit.getServer().getScheduler();
				i = scheduler1.scheduleSyncRepeatingTask(Main.getInstance(), kr, 0L, 20L);
				kr.taskid = i;
				Main.getInstance().playerKickTask.put(e.getPlayer(), kr);
				int zeit = Main.getPlayerConfig().getInt(e.getPlayer().getName() + ".SpielAnzahl");
				Main.getPlayerConfig().set(e.getPlayer().getName() + ".SpielZeit", ++zeit);
				Main.safePlayerConfig();
				e.setJoinMessage(Main.getInstance().prefix + e.getPlayer().getName() + " hat den Server betreten!");
			}
		}

	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (Main.getMainConfig().getBoolean("Gestartet")) {
			if (Main.getPlayerConfig().getInt(e.getPlayer().getName() + ".SpielAnzahl") == Main.getMainConfig()
					.getInt("SpielAnzahl")) {
				int joinanzahl = Main.getPlayerConfig().getInt(e.getPlayer().getName() + ".SpielAnzahl");
				if (joinanzahl == Main.getMainConfig().getInt("SpielAnzahl")) {
					e.disallow(Result.KICK_OTHER, Main.getMainConfig().getString("KickNachricht"));
				} else {
					e.allow();
				}
			}

			if (p.isBanned()) {
				String bannmsg = Main.getMainConfig().getString("TotesNachricht");
				e.disallow(Result.KICK_BANNED, bannmsg);
			}
		}

	}

}
