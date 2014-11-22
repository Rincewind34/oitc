package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class Arena {

	private String name;
	private List<Location> spawns;
	
	public Arena(String name) {
		this.name = name;
		this.spawns = new ArrayList<Location>();
	}
	
	public String getName() {
		return name;
	}
	
	public List<Location> getSpawns() {
		return spawns;
	}
	
	public void addSpawn(Location loc) {
		this.spawns.add(loc);
	}
}
