package de.janhektor.oitc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenaManager {

	private Main plugin;
	private List<Arena> arenas;
	private File file;
	private FileConfiguration cfg;
	
	/*
	 * Arena.<NAME>:
	 *   - world,50,70,100
	 */
	public ArenaManager(Main plugin) {
		this.plugin = plugin;
		this.arenas = new ArrayList<Arena>();
		this.file = new File(this.plugin.getDataFolder(), "arenas.yml");
		this.cfg = YamlConfiguration.loadConfiguration(file);
	}
	
	public void loadDataFile() {
		if (!cfg.isConfigurationSection("Arena.")) {
			cfg.set("Arena." + "test", Arrays.asList("world,0,256,0"));
			save();
		}
		for (String name : cfg.getConfigurationSection("Arena.").getKeys(false)) {
			List<String> spawns = cfg.getStringList("Arena." + name);
			Arena arena = new Arena(name);
			for (String entry : spawns) {
				String[] data = entry.split(",");
				World w = Bukkit.getWorld(data[0]);
				int x = Integer.parseInt(data[1]);
				int y = Integer.parseInt(data[2]);
				int z = Integer.parseInt(data[3]);
				arena.addSpawn(new Location(w, x, y, z));
			}
		}
	}
	
	public void saveArenas() {
		for (Arena arena : this.arenas) {
			List<String> spawns = new ArrayList<String>();
			String name = arena.getName();
			for (Location loc : arena.getSpawns()) {
				spawns.add(loc.getWorld().getName() + "," + loc.getBlockX()
						+ "," + loc.getBlockY() + "," + loc.getBlockZ());
			}
			this.cfg.set("Arena." + name, spawns);
		}
		save();
	}
	
	public boolean isArenaExists(String name) {
		for (Arena arena : this.arenas) {
			if (arena.getName().equals(name)) return true;
		}
		return false;
	}
	
	public void addSpawn(String arenaName, Location loc) {
		if (!isArenaExists(arenaName)) return;
		Arena arena = this.getByName(arenaName);
		arena.addSpawn(loc);
	}
	
	public Arena getByName(String name) {
		for (Arena arena : this.arenas) {
			if (arena.getName().equalsIgnoreCase(name)) return arena;
		}
		return null;
	}
	
	public List<Arena> getArenas() {
		return arenas;
	}
	
	public FileConfiguration getCfg() {
		return cfg;
	}
	
	public File getFile() {
		return file;
	}
	
	public void addArena(Arena arena) {
		this.arenas.add(arena);
	}
	
	public void save() {
		try {
			this.cfg.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
