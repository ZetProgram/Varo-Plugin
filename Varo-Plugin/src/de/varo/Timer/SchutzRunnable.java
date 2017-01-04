package de.varo.Timer;

import de.varo.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SchutzRunnable implements Runnable {

   int count = Main.getMainConfig().getInt("Schutzzeit");
   public int taskid;
   Player p;


   public SchutzRunnable(Player p, int zeit) {
      this.p = p;
      this.count = zeit;
   }

   public void run() {
      if(count == Main.getMainConfig().getInt("Schutzzeit")) {
         Bukkit.broadcastMessage("ßaSchutzzeit hat begonnen! Sie l√§uft " + count + " Sekunden lang");
      }

      --count;
      if(count == 0) {
         Bukkit.broadcastMessage("ßaSchutzzeit zu Ende! Auf in den Kampf");
         Bukkit.getScheduler().cancelTask(taskid);
         Main.getMainConfig().set("AnfangSchutzzeit", false);
      }

   }
}
