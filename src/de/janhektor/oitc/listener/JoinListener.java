package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.janhektor.oitc.Main;
import de.janhektor.oitc.countdown.CountdownLobby;
import de.janhektor.oitc.game.countdown.abstracts.Countdown;

public class JoinListener implements Listener {

	private Main plugin;
	
	public JoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(this.plugin.prefix + "§a" + p.getName() + " hat das Spiel betreten!");
		
		if (Bukkit.getOnlinePlayers().size() >= this.plugin.minPlayers) {
			Countdown countdown = CountdownLobby.getInstance();
			if (!countdown.isRunning()) {
				countdown.start();
			}
		}
		
		p.setScoreboard(this.plugin.voteScoreboard);
	}
}
