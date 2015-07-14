package de.janhektor.oitc.listener;

import org.bukkit.event.player.PlayerPickupItemEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;

public class ListenerPickupItem extends DefaultListener {

	public ListenerPickupItem() {
		super(ListenerPickupItem.class, PlayerPickupItemEvent.getHandlerList());
	}

	@ListenerBundle(name = "bundle.all")
	private static void onItemPickup(PlayerPickupItemEvent e) {
		if (!e.getPlayer().hasPermission("oitc.event.item.pickup")) {
			e.setCancelled(true);
		}
	}
	
}
