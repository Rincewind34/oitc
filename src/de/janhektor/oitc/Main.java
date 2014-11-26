package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import de.janhektor.oitc.commands.CommandOITC;
import de.janhektor.oitc.commands.CommandSetArenaSpawn;
import de.janhektor.oitc.commands.CommandVote;
import de.janhektor.oitc.listener.ArrowShotListener;
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
	
	public List<Location> spawnPoints = new ArrayList<Location>();
	
	public ArenaManager arenaManager;
	public MapVoting mapVoting;
	public Scoreboard voteScoreboard;
	
	public boolean ingame = false;
	public boolean arrayTrail;
	
	public Countdown countdown;
	
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		getServer().getPluginManager().registerEvents(new QuitListener(this), this);
		getServer().getPluginManager().registerEvents(new LoginListener(this), this);
		getServer().getPluginManager().registerEvents(new ServerPingListener(this), this);
		getServer().getPluginManager().registerEvents(new ArrowShotListener(this), this);
		
		getCommand("oitc").setExecutor(new CommandOITC(this));
		getCommand("setarenaspawn").setExecutor(new CommandSetArenaSpawn(this));
		getCommand("vote").setExecutor(new CommandVote(this));
		
		initConfig();
		loadConfigData();
		
		this.countdown = new Countdown(this);
		this.arenaManager = new ArenaManager(this);
		this.arenaManager.loadDataFile();
		this.mapVoting = new MapVoting(arenaManager.getArenas(), this);
		
		System.out.println("[OITC] Plugin aktiviert - Developed by Janhektor");
	}
	
	@Override
	public void onDisable() {
		this.arenaManager.saveArenas();
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
		cfg.addDefault("ArrowTrail", true);
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
		this.arrayTrail = cfg.getBoolean("ArrowTrail");
	}
}
