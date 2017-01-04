package de.varo.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.varo.Main;

public class WriteEvent implements Listener {

	@EventHandler
	public void writeChat(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();

		String msg = e.getMessage();
		String teamname = Main.getPlayerConfig().getString(p.getName() + ".TeamName");
		if (teamname == null) {
			return;
		}
		e.setFormat("§a[§6" + teamname + "§a]§a " + e.getPlayer().getName() + ": §f" + msg);
	}
}
