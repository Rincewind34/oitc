package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;
import de.janhektor.oitc.countdown.CountdownLobby;
import de.janhektor.oitc.game.countdown.abstracts.Countdown;

public class ListenerJoin extends DefaultListener {

	public ListenerJoin() {
		super(ListenerJoin.class, PlayerJoinEvent.getHandlerList());
	}
	
	private static Main plugin = Main.getInstance();
	
	@ListenerBundle(name = "bundle.lobby")
	private static void onJoin(PlayerJoinEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getPlayer();
		
		event.setJoinMessage(layout.prefix + layout.clPos + player.getName() + " hat das Spiel betreten!");
		
		if (Bukkit.getOnlinePlayers().size() >= ListenerJoin.plugin.getMinPlayers()) {
			Countdown countdown = CountdownLobby.getInstance();
			if (!countdown.isRunning()) {
				countdown.start();
			}
		}
		
		player.setScoreboard(ListenerJoin.plugin.getVoteScoreboard());
	}
}
