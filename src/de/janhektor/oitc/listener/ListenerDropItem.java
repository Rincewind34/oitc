package de.janhektor.oitc.listener;

import org.bukkit.event.player.PlayerDropItemEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;

public class ListenerDropItem extends DefaultListener {

	public ListenerDropItem() {
		super(ListenerDropItem.class, PlayerDropItemEvent.getHandlerList());
	}
	
	@ListenerBundle(name = "bundle.all")
	private static void onDrop(PlayerDropItemEvent event) {
		if (!event.getPlayer().hasPermission("oitc.event.item.drop")) {
			event.setCancelled(true);
		}
	}
	
}
