package de.janhektor.oitc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemListener implements Listener {

	@EventHandler
	public void onItemPickup (PlayerPickupItemEvent e) {
		if (!e.getPlayer().isOp()) e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemDrop (PlayerDropItemEvent e) {
		if (!e.getPlayer().isOp()) e.setCancelled(true);
	}
}
