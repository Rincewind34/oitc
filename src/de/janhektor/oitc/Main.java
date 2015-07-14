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
import de.janhektor.oitc.listener.ListenerBlockBreak;
import de.janhektor.oitc.listener.ListenerBlockPlace;
import de.janhektor.oitc.listener.ListenerDeath;
import de.janhektor.oitc.listener.ListenerEntityDamageByEntity;
import de.janhektor.oitc.listener.ListenerFood;
import de.janhektor.oitc.listener.ListenerJoin;
import de.janhektor.oitc.listener.ListenerLogin;
import de.janhektor.oitc.listener.ListenerPickupItem;
import de.janhektor.oitc.listener.ListenerQuit;
import de.janhektor.oitc.listener.ListenerRespawn;
import de.janhektor.oitc.listener.ListenerServerPing;

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
	
	private boolean ingame = false;
	
	private InfoLayout layout;
	
	private ListenerHandler handler;
	
	@Override
	public void onLoad() {
		Main.instance = this;
		this.layout = new InfoLayout("OITC");
	}
	
	@Override
	public void onEnable() {
		ListenerHandler handler = new ListenerHandler();
		handler.add(new ListenerBlockBreak());
		handler.add(new ListenerBlockPlace());
		handler.add(new ListenerDeath());
		handler.add(new ListenerEntityDamageByEntity());
		handler.add(new ListenerFood());
		handler.add(new ListenerJoin());
		handler.add(new ListenerLogin());
		handler.add(new ListenerPickupItem());
		handler.add(new ListenerQuit());
		handler.add(new ListenerRespawn());
		handler.add(new ListenerServerPing());
		handler.create();
		handler.register(GameState.LOBBY.bundleName());
		handler.register("bundle.all");
		
		this.handler = handler;
		
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
	
	public boolean isIngame() {
		return this.ingame;
	}
	
	public void setIngame(boolean ingame) {
		this.ingame = ingame;
	}
	
	public ListenerHandler getHandler() {
		return this.handler;
	}
}
