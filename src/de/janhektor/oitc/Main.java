package de.janhektor.oitc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.janhektor.oitc.commands.CommandOITC;
import de.janhektor.oitc.listener.JoinListener;
import de.janhektor.oitc.listener.QuitListener;

public class Main extends JavaPlugin {

	
	public int minPlayers;
	public int maxPlayers;
	
	public String prefix;
	
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		getServer().getPluginManager().registerEvents(new QuitListener(this), this);
		
		getCommand("oitc").setExecutor(new CommandOITC(this));
		
		initConfig();
		loadConfigData();
		System.out.println("[OITC] Plugin aktiviert - Developed by Janhektor");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[OITC] Plugin deaktiviert - Developed by Janhektor");
	}
	
	
	
	
	private void initConfig() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("Prefix", "&6[OITC]");
		cfg.addDefault("MinPlayers", 2);
		cfg.addDefault("MaxPlayers", 16);
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	
	private void loadConfigData() {
		FileConfiguration cfg = getConfig();
		this.prefix = cfg.getString("Prefix").replace("&", "§") + "§r ";
		this.minPlayers = cfg.getInt("MinPlayers");
		this.maxPlayers = cfg.getInt("MaxPlayers");
	}
}
