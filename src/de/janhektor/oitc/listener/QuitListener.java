package de.janhektor.oitc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.janhektor.oitc.InfoLayout;

public class QuitListener implements Listener {

	@EventHandler
	public void onPlayerQuit (PlayerQuitEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		event.setQuitMessage(layout.prefix + layout.clPos + event.getPlayer().getName() + " hat das Spiel verlassen!");
	}
}
