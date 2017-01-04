package de.varo.cmds;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import de.varo.Main;
import de.varo.Timer.KickRunnable;
import de.varo.teams.VaroTeam;

public class Commands implements CommandExecutor {

	int startint = 61;
	int countdown;
	int i;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player p = (Player) sender;
		if (args.length == 0) {
			if (cmd.getName().equalsIgnoreCase("varo")) {
				p.sendMessage("§c/varo help");
				return true;
			}
		}
		/*
		 * Der Help Command
		 */
		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("help")) {
				p.sendMessage(Main.getInstance().prefix + "§6Alle Varo Kommandos:\n");
				p.sendMessage("§a/varo help\n");
				p.sendMessage("§a/varo start\n");
				p.sendMessage("§a/varo teams\n");
				p.sendMessage("§a/varo teams create <TeamName> <Player1> <Player2>\n");
				p.sendMessage("§a/varo teams delete <TeamName>\n");
				p.sendMessage("§a/varo worldboarder set <Zahl>\n");
				p.sendMessage("§a/varo zeit\n");
				p.sendMessage("§a/varo reloadconfig\n");
				p.sendMessage("§cPlugin by Zet\n");
				return true;
			}
			/*
			 * Der /reloadconfig Command
			 */
			if (args[0].equalsIgnoreCase("reloadconfig")) {

				Main.getInstance().reloadConfig();
				p.sendMessage(Main.getInstance().prefix + "§aConfig neu geladen!");
				return true;
			}
			/*
			 * Der Start Command
			 */

			if (args[0].equalsIgnoreCase("start")) {

				countdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

					@Override
					public void run() {
						if (startint != 0) {
							if (startint == 61) {
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.setGameMode(GameMode.ADVENTURE);
									all.sendMessage(Main.getInstance().prefix + "§aVaro Countdown ist gestartet!");

								}
							}
							startint--;
							for (Player all : Bukkit.getOnlinePlayers()) {
								all.setLevel(startint);

							}

							if (startint == 60 | startint == 50 | startint == 40 | startint == 30 | startint == 20
									| startint == 10 | startint == 9 | startint == 8 | startint == 7 | startint == 6
									| startint == 5 | startint == 4 | startint == 3 | startint == 2 | startint == 1) {

								for (Player all : Bukkit.getOnlinePlayers()) {
									all.sendMessage("§aVaro startet in" + "§6" + startint);

								}

							}
							if (startint == 2) {
								for (Player all : Bukkit.getOnlinePlayers()) {
									all.getInventory().clear();
									p.getWorld().setTime(0);
								}
							}

						} else {

							for (Player all : Bukkit.getOnlinePlayers()) {
								Bukkit.getScheduler().cancelTask(countdown);
								p.sendMessage("§cStarte den Server nicht neu sonst ist Varo beendet!");
								all.setGameMode(GameMode.SURVIVAL);
								all.setFoodLevel(20);
								all.playSound(all.getLocation(), Sound.LEVEL_UP, 1F, 2F);
								all.setLevel(0);

								Main.getPlayerConfig().set(all.getName() + ".SpielAnzahl", 1);
								Main.safePlayerConfig();

								KickRunnable k = new KickRunnable(all, Main.getMainConfig().getInt("SpielZeit"));
								BukkitScheduler scheduler2 = Bukkit.getServer().getScheduler();
								i = scheduler2.scheduleSyncRepeatingTask(Main.getInstance(), k, 0, 20L);
								k.taskid = i;
								Main.getInstance().playerKickTask.put(all, k);

							}

							Main.getMainConfig().set("Gestartet", true);
							Main.getInstance().saveConfig();
							Bukkit.broadcastMessage(Main.getInstance().prefix + "§aViel Glück und viel Erfolg!");
						}

					}
				}, 0, 20);
				return true;
			}
			/*
			 * Der /teams Command
			 */

			if (args[0].equalsIgnoreCase("teams")) {
				if (Main.getInstance().teams.isEmpty()) {
					p.sendMessage("§cEs sind keine Teams vorhanden!");
					return true;
				} else {
					for (VaroTeam team : Main.getInstance().teams) {
						p.sendMessage("§aHier siehst du alle Teams:\n");
						p.sendMessage("§a[" + team.getTeamName(team) + "]§f = §3" + team.p1.getDisplayName() + " "
								+ team.p2.getDisplayName());
						return true;
					}
				}
			}
		}
		/*
		 * Der /team create Command
		 */
		if (args[0].equalsIgnoreCase("teams")) {
			if (args.length == 1) {
				p.sendMessage("§c/varo team create <TeamName> <Player1> <Player2>\n§c/varo team delete <TeamName>");
			}
			if (args[1].equalsIgnoreCase("create")) {
				if (args.length < 5) {
					p.sendMessage("§c/varo team create <TeamName> <Player1> <Player2>");
					return true;
				}
				if (args.length == 5) {
					String teamName = args[2];
					Player p1 = Bukkit.getPlayerExact(args[3]);
					Player p2 = Bukkit.getPlayerExact(args[4]);
					if (p1 == null) {
						p.sendMessage("§cSpieler: " + args[3] + " wurde nicht Gefunden!");
						return true;
					}
					if (p2 == null) {
						p.sendMessage("§cSpieler: " + args[4] + " wurde nicht Gefunden!");
						return true;
					}
					VaroTeam team = new VaroTeam(teamName, p1, p2);
					Main.getInstance().teams.add(team);
					if (p1.getName() == p2.getName()) {
						p.sendMessage("§cEs müssen verschiedene Spieler in einem Team sein!");
						return true;
					} else if (!(p1.getName() == p2.getName())) {
						Main.getPlayerConfig().set(p1.getName() + ".TeamName", teamName);
						Main.getPlayerConfig().set(p2.getName() + ".TeamName", teamName);
						Main.safePlayerConfig();
						p.sendMessage("§aTeam wurde erfolgreich erstellt!");
						return true;
					}
				}
			}
		}
		/*
		 * Der /team delete Command
		 */
		if (args[0].equalsIgnoreCase("teams")) {
			if (args.length == 1) {
				p.sendMessage("§c/varo team create <TeamName> <Player1> <Player2>\n§c/varo team delete <TeamName>");
			}
			if (args[1].equalsIgnoreCase("delete")) {
				if (args.length < 3) {
					p.sendMessage("§c/Varo team delete <TeamName>");
					return true;
				}
				if (args.length == 3) {
					String teamName = args[3];
					VaroTeam team = null;
					boolean gefunden = false;
					int i;
					for (i = 0; i < Main.getInstance().teams.size(); i++) {
						team = Main.getInstance().teams.get(i);
						if (team.getTeamName(team).equalsIgnoreCase(teamName)) {
							gefunden = true;
							break;
						}
					}
					if (gefunden == true) {
						Main.getInstance().teams.remove(i);
						p.sendMessage("§6Team wurde erfolgreich Gelöscht!");
						return true;
					} else {
						p.sendMessage("§6Team: §e" + teamName + " §6Nicht Gefunden!");
						return true;
					}
				}

			}
		}
		/*
		 * Der /worldboarder set <Zahl> Command
		 */
		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("worldboarder")) {
				if (args[1].equalsIgnoreCase("set")) {
					int zahl = Integer.parseInt(args[2]);
					p.getWorld().getWorldBorder().setCenter(p.getLocation());
					p.getWorld().getWorldBorder().setSize(zahl);
					p.getWorld().getWorldBorder().setDamageAmount(2);
					p.getWorld().getWorldBorder().setDamageBuffer(1);
					p.sendMessage("§aDie Worldboarder wurde gesetzte!");
					return true;
				}
			}
		}
		/*
		 * Der /team delete Command
		 */
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("Zeit")) {
				if (Main.getMainConfig().getBoolean("Gestartet") == true) {
					KickRunnable kr = Main.getInstance().playerKickTask.get(p);
					int minuten = kr.count / 60;

					if (kr.count < 60) {
						p.sendMessage("§4Du wirst in " + kr.count + " Sekunden gekickt!");
						return true;
					} else {
						p.sendMessage("§4Du wirst in " + minuten + " Minuten gekickt!");
						return true;
					}
				} else {
					p.sendMessage("§cVaro ist noch nicht gestartet!");
					return true;
				}
			}
		}

		return false;
	}

}
