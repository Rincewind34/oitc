package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class MapVoting {

	private Map<String, Integer> arenas;
	private List<String> voted;
	private Main plugin;
	
	private boolean running;
	
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
		this.plugin.getVoteScoreboard()
				.getObjective(DisplaySlot.SIDEBAR)
				.getScore(arena)
				.setScore(this.plugin.getVoteScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(arena).getScore() + 1);
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(this.plugin.getVoteScoreboard());
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
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		Objective obj = scoreboard.getObjective("mapvoting");
		
		if (obj == null) {
			obj = scoreboard.registerNewObjective("mapvoting", "dummy");
		}
		
		obj.setDisplayName("§6Arena Voting");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for (String arenaName : this.arenas.keySet()) {
			obj.getScore(arenaName).setScore(0);
		}
		
		this.plugin.setVoteScoreboard(scoreboard);
		this.running = true;
	}
	
	public void endVoting() {
		this.running = false;
		
		String bestArena = this.getBestArena();
		Arena arena = this.plugin.getArenaManager().getByName(bestArena);
		
		if (arena == null) {
			arena = this.plugin.getArenaManager().getArenas().get(0);
		}
		
		this.plugin.setSpawnPoints(arena.getSpawns());
		GameUtil.endMapVoting(this.plugin, bestArena, this.arenas.get(bestArena));
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public Map<String, Integer> getArenas() {
		return this.arenas;
	}
}
