package de.varo.Timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.varo.Main;

public class JoinRunnable implements Runnable {
	int count = 16;
	public static int taskid;
	Player p;

	public JoinRunnable(Player p) {
		this.p = p;
	}

	public void run() {

		if (count == 15 | count == 10 | count == 5 | count == 4 | count == 3 | count == 2) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(ChatColor.AQUA + p.getName() + " §aist In§c " + count + " §aSekunden Verwundbar!");
			}
		}
		if (count == 1) {
			for (Player all : Bukkit.getOnlinePlayers()) {
				all.sendMessage(ChatColor.AQUA + p.getName() + " §aist In§c " + "einer" + " §aSekunde Verwundbar!");
			}
		}
		if (count == 0) {
			Bukkit.broadcastMessage(ChatColor.AQUA + p.getName() + " §aist nun verwundbar!");
			Main.getInstance().isfreeze.remove(0);
			p.setGameMode(GameMode.SURVIVAL);
			Bukkit.getScheduler().cancelTask(taskid);
		}
		if (count == 15 | count == 14 | count == 13 | count == 12 | count == 11 | count == 10 | count == 9 | count == 8
				| count == 7 | count == 6 | count == 5 | count == 4 | count == 3 | count == 2 | count == 1
				| count == 0) {

		}
		count--;
	}
}
