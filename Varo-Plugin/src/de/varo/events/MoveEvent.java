package de.varo.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.varo.Main;

public class MoveEvent implements Listener{

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		Location l1,l2;
		
		if (Main.getInstance().isfreeze.contains(p)) {
			
			l1 = e.getFrom();
			l2 = e.getTo();
			
			if (l1.getX() == l2.getX() & l1.getY() == l2.getY() & l1.getZ() == l2.getZ())  {
				
			} else if (l1.getX() != l2.getX()) {
				e.setTo(p.getLocation());
				
			} else if (l1.getY() != l2.getY()) {
				e.setTo(p.getLocation());
			} else if (l1.getZ() != l2.getZ()) {
				e.setTo(p.getLocation());
			}
		}
	
		
	}
	
}
