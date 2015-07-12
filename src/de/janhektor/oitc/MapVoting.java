package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class MapVoting {

	private Map<String, Integer> arenas;
	private List<String> voted;
	private Main plugin;
	
	public MapVoting(List<Arena> arenas, Main plugin) {
		this.arenas = new HashMap<String, Integer>();
		this.voted = new ArrayList<String>();
		
		for (Arena a : arenas) {
			this.arenas.put(a.getName().toLowerCase(), 0);
		}
		
		this.plugin = plugin;
		this.initVoting();
	}
	
	public void playerVote(Player p, String arena) {
		if (!this.arenas.containsKey(arena)) {
			return;
		}
		
		this.arenas.put(arena, arenas.get(arena) + 1);
		this.voted.add(p.getName());
		this.plugin.voteScoreboard
				.getObjective(DisplaySlot.SIDEBAR)
				.getScore(arena)
				.setScore(this.plugin.voteScoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(arena).getScore() + 1);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(this.plugin.voteScoreboard);
		}
	}
	
	public boolean hasVoted(Player p) {
		return this.voted.contains(p.getName());
	}
	
	private String getBestArena() {
		String bestArena = null;
		int votes = -1;
		for (String name : this.arenas.keySet()) {
			if (this.arenas.get(name) >= votes) {
				bestArena = name;
				votes = this.arenas.get(name);
			}
		}
		return bestArena;
	}
	
	public boolean isMapInVoting(String name) {
		return this.arenas.containsKey(name);
	}
	
	private void initVoting() {
		this.plugin.voteScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		Objective obj;
		if (this.plugin.voteScoreboard.getObjective("mapvoting") == null) {
			 obj = this.plugin.voteScoreboard.registerNewObjective("mapvoting", "dummy");
		} else {
			obj = this.plugin.voteScoreboard.getObjective("mapvoting");
		}
		
		obj.setDisplayName("§�6Arena Voting");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		for (String arenaName : this.arenas.keySet()) {
			obj.getScore(arenaName).setScore(0);
		}
	}
	
	public void endVoting() {
		String bestArena = this.getBestArena();
		Arena arena = this.plugin.arenaManager.getByName(bestArena);
		
		if (arena == null) {
			arena = this.plugin.arenaManager.getArenas().get(0);
		}
		
		this.plugin.spawnPoints = arena.getSpawns();
		GameUtil.endMapVoting(this.plugin, bestArena, this.arenas.get(bestArena));
	}
}
