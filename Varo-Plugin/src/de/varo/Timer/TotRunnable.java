package de.varo.Timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.varo.Main;

public class TotRunnable implements Runnable {

	Player p;
	public int count = 5;
	public static int taskid;

	public TotRunnable(Player p) {
		this.p = p;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		--count;
		if (this.count == 0) {
			this.p.kickPlayer(Main.getMainConfig().getString("TotesNachricht"));
			this.p.setBanned(true);
			Bukkit.getScheduler().cancelTask(taskid);
		}

	}
}
