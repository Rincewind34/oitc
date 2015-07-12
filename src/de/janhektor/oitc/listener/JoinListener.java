package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;
import de.janhektor.oitc.countdown.CountdownLobby;
import de.janhektor.oitc.game.countdown.abstracts.Countdown;

public class JoinListener implements Listener {

	private Main plugin = Main.getInstance();
	
	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getPlayer();
		
		event.setJoinMessage(layout.prefix + layout.clPos + player.getName() + " hat das Spiel betreten!");
		
		if (Bukkit.getOnlinePlayers().size() >= this.plugin.minPlayers) {
			Countdown countdown = CountdownLobby.getInstance();
			if (!countdown.isRunning()) {
				countdown.start();
			}
		}
		
		player.setScoreboard(this.plugin.voteScoreboard);
	}
}
