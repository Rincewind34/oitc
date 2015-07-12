package de.janhektor.oitc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Countdown {

	private int taskid1;
	private Main plugin;
	
	public Countdown(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@SuppressWarnings("deprecation")
	public void startLobbyCountdown() {
		this.taskid1 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, new Runnable() {
			
					@Override
					public void run() {
						if (Countdown.this.plugin.lobbyTime == 60 || Countdown.this.plugin.lobbyTime == 30
								|| Countdown.this.plugin.lobbyTime == 15
								|| Countdown.this.plugin.lobbyTime == 10
								|| Countdown.this.plugin.lobbyTime == 5
								|| Countdown.this.plugin.lobbyTime == 4
								|| Countdown.this.plugin.lobbyTime == 3
								|| Countdown.this.plugin.lobbyTime == 2
								|| Countdown.this.plugin.lobbyTime == 1) {

							Bukkit.broadcastMessage(Countdown.this.plugin.prefix + "§aDas Spiel startet in "
									+ Countdown.this.plugin.lobbyTime + " "
									+ (Countdown.this.plugin.lobbyTime == 1 ? "Sekunde" : "Sekunden"));
							
							if (Countdown.this.plugin.lobbyTime == 10) {
								Countdown.this.plugin.mapVoting.endVoting();
							}
						}
						
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.setLevel(Countdown.this.plugin.lobbyTime);
							p.setExp((float) Countdown.this.plugin.lobbyTime / (float) Countdown.this.plugin.maxLobbyTime); 
						}
						
						if (Countdown.this.plugin.lobbyTime <= 0) {
							if (Bukkit.getOnlinePlayers().size() >= Countdown.this.plugin.minPlayers) {
								
								Bukkit.getScheduler().cancelTask(taskid1);
								GameUtil.startGame(Countdown.this.plugin);
								return;
								
							} else {
								Countdown.this.plugin.lobbyTime = Countdown.this.plugin.maxLobbyTime;
							}
						}

						Countdown.this.plugin.lobbyTime--;
					}
			
		}, 0L, 20L);
	}
}
