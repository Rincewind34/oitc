package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class MapVoting {

	private Map<String, Integer> arenas;
	private List<String> voted;
	
	public MapVoting(List<Arena> arenas) {
		this.arenas = new HashMap<String, Integer>();
		this.voted = new ArrayList<String>();
		for (Arena a : arenas) {
			this.arenas.put(a.getName().toLowerCase(), 0);
		}
	}
	
	public void playerVote(Player p, String arena) {
		if (!arenas.containsKey(arena)) return;
		arenas.put(arena, arenas.get(arena) + 1);
		voted.add(p.getName());
	}
	
	public boolean hasVoted(Player p) {
		return voted.contains(p.getName());
	}
	
	public String getBestArena() {
		String bestArena = null;
		int votes = 0;
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
}
