package de.janhektor.oitc;

import org.bukkit.Bukkit;

public class Countdown {

	private int taskid1;
	private Main plugin;
	
	public Countdown(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@SuppressWarnings("deprecation")
	public void startLobbyCountdown() {
		this.taskid1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
			
					@Override
					public void run() {
						if (plugin.lobbyTime == 60 || plugin.lobbyTime == 30
								|| plugin.lobbyTime == 15
								|| plugin.lobbyTime == 10
								|| plugin.lobbyTime == 5
								|| plugin.lobbyTime == 4
								|| plugin.lobbyTime == 3
								|| plugin.lobbyTime == 2
								|| plugin.lobbyTime == 1) {

							Bukkit.broadcastMessage(plugin.prefix
									+ "§aDas Spiel startet in "
									+ plugin.lobbyTime
									+ " "
									+ (plugin.lobbyTime == 1 ? "Sekunde"
											: "Sekunden"));
							
							if (plugin.lobbyTime == 10) {
								plugin.mapVoting.endVoting();
							}
						}
						
						if (plugin.lobbyTime <= 0) {
							if (Bukkit.getOnlinePlayers().length >= plugin.minPlayers) {
								
								Bukkit.getScheduler().cancelTask(taskid1);
								GameUtil.startGame(plugin);
								return;
								
							} else {
								plugin.lobbyTime = plugin.maxLobbyTime;
							}
						}

						plugin.lobbyTime--;
					}
			
		}, 0L, 20L);
	}
}
