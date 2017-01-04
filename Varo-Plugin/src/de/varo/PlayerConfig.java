package de.varo;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerConfig {

	public static File file = new File("plugins/Varo", "players.yml");
	public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

	public void registerConfig() {

		cfg.options().header("Hier kannst du alle Spieler mit ihren Einstelleungen sehen!");
		cfg.options().copyDefaults(true);
		savecfg();

	}

	public static void savecfg() {
		try {
			cfg.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
