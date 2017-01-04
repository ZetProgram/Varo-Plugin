package de.varo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.varo.Timer.KickRunnable;
import de.varo.cmds.Commands;
import de.varo.events.DamageEvent;
import de.varo.events.DeathEvent;
import de.varo.events.JoinEvent;
import de.varo.events.MoveEvent;
import de.varo.events.QuitEvent;
import de.varo.events.WriteEvent;
import de.varo.teams.VaroTeam;

public class Main extends JavaPlugin {

	public ArrayList<Player> isfreeze = new ArrayList<>();
	public ArrayList<VaroTeam> teams = new ArrayList<>();
	public HashMap<Player, KickRunnable> playerKickTask = new HashMap<>();
	public ArrayList<Player> spieler = new ArrayList<>();

	public String prefix = "§6[Varo] ";

	private static Main main;

	@Override
	public void onEnable() {

		main = this;

		CreateConfig();
		
		Zeit();

		safePlayerConfig();

		registerCommands();
		registerEvents();
		
		System.out.println("[Varo-Plugin] Started " + getDescription().getVersion());
	}
	
	@Override
	public void onDisable() {
		
		Main.getMainConfig().set("Gestartet", false);
		Main.getInstance().saveConfig();
		
	}

	public static Main getInstance() {
		return main;
	}

	public static YamlConfiguration getMainConfig() {

		return (YamlConfiguration) Main.getInstance().getConfig();

	}

	public void CreateConfig() {

		Main.getInstance().reloadConfig();
		Main.getInstance().getConfig().options().header("Die ist die config.yml vom Varo-Plugin");
		Main.getInstance().getConfig().addDefault("SpielAnzahl", 1);
		Main.getInstance().getConfig().addDefault("SpielZeit", 1800);
		Main.getInstance().getConfig().addDefault("Gestartet", false);
		Main.getInstance().getConfig().addDefault("TodesSound", true);
		Main.getInstance().getConfig().addDefault("KickNachricht", "§cDu hast keine Zeit mehr für Heute! komme Morgen wieder!");
		Main.getInstance().getConfig().addDefault("TotesNachricht", "§cDu bist ausgeschieden! Varo ist für dich vorbei!");
		Main.getInstance().getConfig().addDefault("Schutzzeit", 60);
		Main.getInstance().getConfig().addDefault("AnfangSchutzzeit", false);
		Main.getInstance().getConfig().options().copyDefaults(true);
		Main.getInstance().saveConfig();

	}

	public static YamlConfiguration getPlayerConfig() {

		return PlayerConfig.cfg;

	}

	public static void safePlayerConfig() {

		PlayerConfig.savecfg();

	}

	public void registerEvents() {

		getServer().getPluginManager().registerEvents(new MoveEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new WriteEvent(), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(), this);
		getServer().getPluginManager().registerEvents(new DamageEvent(), this);
		getServer().getPluginManager().registerEvents(new QuitEvent(), this);

	}

	public void registerCommands() {

		getCommand("varo").setExecutor(new Commands());

	}
	
	public void Zeit() {
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(System.currentTimeMillis());

		int stunde = cal.get(Calendar.HOUR_OF_DAY);
		
		if (stunde == 0) {
			
			Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Ein Neuer Tag hat begonnen!");
			
			for (OfflinePlayer all : Bukkit.getOfflinePlayers()) {
			getPlayerConfig().set(all.getName() + ".SpielAnzahl", 0);
			}
			for (Player all : Bukkit.getOnlinePlayers()) {
			getPlayerConfig().set(all.getName() + ".SpielAnzahl", 0);
			}
			
		}
	}

}
