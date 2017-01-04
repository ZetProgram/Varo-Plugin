package de.varo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.varo.Main;
import de.varo.Timer.KickRunnable;

public class QuitEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (Main.getMainConfig().getBoolean("Gestartet")) {
			KickRunnable kr = Main.getInstance().playerKickTask.get(p);
			int zeit = kr.count;
			Main.getPlayerConfig().set(e.getPlayer().getName() + ".SpielZeit", zeit);
			Main.safePlayerConfig();
			Main.getInstance().playerKickTask.remove(p);
			Bukkit.getScheduler().cancelTask(kr.taskid);
		}
		e.setQuitMessage(
				Main.getInstance().prefix + ChatColor.AQUA + e.getPlayer().getName() + " hat den Server verlassen");
	}

}
