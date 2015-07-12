package de.janhektor.oitc.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.janhektor.oitc.Main;

public class QuitListener implements Listener {

	private Main plugin;
	
	public QuitListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit (PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(this.plugin.prefix + "§a" + p.getName() + " hat das Spiel verlassen!");
	}
}
