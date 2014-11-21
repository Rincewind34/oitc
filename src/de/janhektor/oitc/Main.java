package de.janhektor.oitc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.janhektor.oitc.commands.CommandOITC;
import de.janhektor.oitc.listener.JoinListener;
import de.janhektor.oitc.listener.LoginListener;
import de.janhektor.oitc.listener.QuitListener;
import de.janhektor.oitc.listener.ServerPingListener;

public class Main extends JavaPlugin {

	
	public int minPlayers;
	public int maxPlayers;
	
	public int lobbyTime;
	public int maxLobbyTime;
	
	public String prefix;
	public String adminPermission;
	public String[] motds = new String[3]; // 0 = Lobby, 1 = Full, 2 = InGame
	
	public boolean ingame = false;
	
	public Countdown countdown;
	
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		getServer().getPluginManager().registerEvents(new QuitListener(this), this);
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerPingListener(this), this);
		
		getCommand("oitc").setExecutor(new CommandOITC(this));
		
		initConfig();
		loadConfigData();
		
		this.countdown = new Countdown(this);
		
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
		cfg.addDefault("LobbyTime", 120);
		cfg.addDefault("AdminPermission", "oitc.*");
		cfg.addDefault("Motd.Lobby", "&aLobby");
		cfg.addDefault("Motd.Full", "&cFull");
		cfg.addDefault("Motd.Ingame", "&cInGame");
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	
	private void loadConfigData() {
		FileConfiguration cfg = getConfig();
		this.prefix = cfg.getString("Prefix").replace("&", "§") + "§r ";
		this.minPlayers = cfg.getInt("MinPlayers");
		this.maxPlayers = cfg.getInt("MaxPlayers");
		this.lobbyTime = cfg.getInt("LobbyTime");
		this.maxLobbyTime = cfg.getInt("LobbyTime");
		this.adminPermission = cfg.getString("AdminPermission");
		this.motds[0] = cfg.getString("Motd.Lobby").replace("&", "§");
		this.motds[1] = cfg.getString("Motd.Full").replace("&", "§");
		this.motds[2] = cfg.getString("Motd.Ingame").replace("&" , "§");
	}
}
