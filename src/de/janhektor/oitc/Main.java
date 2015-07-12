package de.janhektor.oitc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import de.janhektor.listener.bundle.ListenerHandler;
import de.janhektor.oitc.commands.CommandOITC;
import de.janhektor.oitc.commands.CommandVote;
import de.janhektor.oitc.listener.BlockListener;
import de.janhektor.oitc.listener.DeathListener;
import de.janhektor.oitc.listener.EntityDamageByEntityListener;
import de.janhektor.oitc.listener.FoodListener;
import de.janhektor.oitc.listener.ItemListener;
import de.janhektor.oitc.listener.JoinListener;
import de.janhektor.oitc.listener.LoginListener;
import de.janhektor.oitc.listener.QuitListener;
import de.janhektor.oitc.listener.RespawnListener;
import de.janhektor.oitc.listener.ServerPingListener;

public class Main extends JavaPlugin {

	private static Main instance;
	
	public static Main getInstance() {
		return Main.instance;
	}
	
	private int minPlayers;
	private int maxPlayers;
	
	private int lobbyTime;
	private int maxLives;
	
	private String adminPermission;
	private String[] motds = new String[3]; // 0 = Lobby, 1 = Full, 2 = InGame
	
	private List<Location> spawnPoints = new ArrayList<Location>();
	
	private Map<String, Integer> lives = new HashMap<String, Integer>();
	
	private ArenaManager arenaManager;
	private MapVoting mapVoting;
	private Scoreboard voteScoreboard;
	
	public boolean ingame = false;
	
	private InfoLayout layout;
	
	public ListenerHandler handler;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		this.layout = new InfoLayout("OITC");
	}
	
	@Override
	public void onEnable() {
		ListenerHandler handler = new ListenerHandler();
		handler.add(new JoinListener());
		handler.add(new QuitListener());
		handler.add(new LoginListener());
		handler.add(new ServerPingListener());
		handler.add(new RespawnListener());
		handler.add(new DeathListener());
		handler.add(new BlockListener());
		handler.add(new FoodListener());
		handler.add(new ItemListener());
		handler.add(new EntityDamageByEntityListener());
		handler.create();
		handler.register("game");
		
		this.handler = handler;
		
//		super.getServer().getPluginManager().registerEvents(new JoinListener(), this);
//		super.getServer().getPluginManager().registerEvents(new QuitListener(), this);
//		super.getServer().getPluginManager().registerEvents(new LoginListener(), this);
//		super.getServer().getPluginManager().registerEvents(new ServerPingListener(), this);
//		super.getServer().getPluginManager().registerEvents(new RespawnListener(), this);
//		super.getServer().getPluginManager().registerEvents(new DeathListener(), this);
//		super.getServer().getPluginManager().registerEvents(new BlockListener(), this);
//		super.getServer().getPluginManager().registerEvents(new FoodListener(), this);
//		super.getServer().getPluginManager().registerEvents(new ItemListener(), this);
//		super.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
		
		this.initConfig();
		this.loadConfigData();
		
		new CommandOITC().create();
		new CommandVote().create();
		
		this.arenaManager = new ArenaManager(this);
		this.arenaManager.loadDataFile();
		this.mapVoting = new MapVoting(arenaManager.getArenas(), this);
		
		Bukkit.getConsoleSender().sendMessage(this.layout.prefix + layout.clPos + "Plugin aktiviert - Developed by Janhektor");
	}
	
	@Override
	public void onDisable() {
		this.arenaManager.saveArenas();
		Bukkit.getConsoleSender().sendMessage(this.layout.prefix + layout.clNeg + "Plugin deaktiviert - Developed by Janhektor");
	}
	
	private void initConfig() {
		FileConfiguration cfg = getConfig();
		cfg.addDefault("MinPlayers", 2);
		cfg.addDefault("MaxPlayers", 16);
		cfg.addDefault("LobbyTime", 120);
		cfg.addDefault("Lives", 5);
		cfg.addDefault("AdminPermission", "oitc.*");
		cfg.addDefault("Motd.Lobby", "&aLobby");
		cfg.addDefault("Motd.Full", "&cFull");
		cfg.addDefault("Motd.Ingame", "&cInGame");
		cfg.options().copyDefaults(true);
		saveConfig();
	}
	
	private void loadConfigData() {
		FileConfiguration cfg = getConfig();
		this.minPlayers = cfg.getInt("MinPlayers");
		this.maxPlayers = cfg.getInt("MaxPlayers");
		this.lobbyTime = cfg.getInt("LobbyTime");
		this.adminPermission = cfg.getString("AdminPermission");
		this.motds[0] = cfg.getString("Motd.Lobby").replace("&", "§");
		this.motds[1] = cfg.getString("Motd.Full").replace("&", "§");
		this.motds[2] = cfg.getString("Motd.Ingame").replace("&" , "§");
		this.maxLives = cfg.getInt("Lives");
	}
	
	public ArenaManager getArenaManager() {
		return this.arenaManager;
	}
	
	public MapVoting getMapVoting() {
		return this.mapVoting;
	}
	
	public Scoreboard getVoteScoreboard() {
		return this.voteScoreboard;
	}
	
	public void setVoteScoreboard(Scoreboard voteScoreboard) {
		this.voteScoreboard = voteScoreboard;
	}
	
	public int getMinPlayers() {
		return this.minPlayers;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public Map<String, Integer> getLives() {
		return this.lives;
	}
	
	public String[] getMotds() {
		return this.motds;
	}
	
	public List<Location> getSpawnPoints() {
		return this.spawnPoints;
	}
	
	public void setSpawnPoints(List<Location> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}
	
	public String getAdminPermission() {
		return this.adminPermission;
	}
	
	public int getLobbyTime() {
		return this.lobbyTime;
	}
	
	public int getMaxLives() {
		return this.maxLives;
	}
}
