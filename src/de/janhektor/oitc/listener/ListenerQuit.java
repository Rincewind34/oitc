package de.janhektor.oitc.listener;

import org.bukkit.event.player.PlayerQuitEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.InfoLayout;

public class ListenerQuit extends DefaultListener {

	public ListenerQuit() {
		super(ListenerQuit.class, PlayerQuitEvent.getHandlerList());
	}

	@ListenerBundle(name = "bundle.all")
	private static void onPlayerQuit(PlayerQuitEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		event.setQuitMessage(layout.prefix + layout.clPos + event.getPlayer().getName() + " hat das Spiel verlassen!");
	}
}
