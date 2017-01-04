package de.varo.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.varo.Main;

public class KickRunnable implements Runnable {

	public int count = Main.getMainConfig().getInt("SpielZeit");
	Player p;
	public int taskid;
	int i;

	public KickRunnable(Player p, int zeit) {
		this.p = p;
		this.count = zeit;

	}

	public void run() {
		count--;
		if (count == 30 | count == 15 | count == 10 | count == 5 | count == 4 | count == 3 | count == 2) {

			Bukkit.broadcastMessage("§6" + p.getName() + "§a wird In§c " + count + " §aSekunden Gekickt!");

		}
		if (count == 1) {
			Bukkit.broadcastMessage("§6" + p.getName() + "§a wird In§c einer §aSekunde Gekickt!");

		}
		if (count == 0) {

			p.kickPlayer(Main.getMainConfig().getString("KickNachricht"));
			Main.getPlayerConfig().set(p.getName() + ".SpielZeit", 0);
			Bukkit.getScheduler().cancelTask(taskid);

		}

	}

}
