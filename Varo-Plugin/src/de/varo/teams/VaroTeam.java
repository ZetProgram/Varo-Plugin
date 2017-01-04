package de.varo.teams;

import org.bukkit.entity.Player;

public class VaroTeam {

	public String teamname;
	public Player p1, p2;
	
	public VaroTeam(String teamname, Player p1, Player p2) {
		this.teamname = teamname;
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public String getTeamName(VaroTeam team) {
		return team.teamname;
	}
	
}
