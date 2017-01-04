package de.varo.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import de.varo.Main;

public class KickEvent implements Listener{
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		Player p = e.getPlayer();
		
		if (Main.getMainConfig().getBoolean("Gestartet") == true) {
			
			e.setLeaveMessage(ChatColor.AQUA + p.getName() + " §awurde gekickt!");
			
			
		}
	}
	
}
