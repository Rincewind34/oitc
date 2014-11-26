package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.janhektor.oitc.Main;

public class JoinListener implements Listener {

	private Main plugin;
	
	public JoinListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(plugin.prefix + "�a" + p.getName() + " hat das Spiel betreten!");
		
		if (Bukkit.getOnlinePlayers().length >= plugin.minPlayers) {
			plugin.countdown.startLobbyCountdown();
		}
		
		p.setScoreboard(plugin.voteScoreboard);
	}
}
