package de.janhektor.oitc.listener;

import org.bukkit.event.block.BlockPlaceEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.NBTHelper;

public class ListenerBlockPlace extends DefaultListener {

	public ListenerBlockPlace() {
		super(ListenerBlockPlace.class, BlockPlaceEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.end" })
	private static void onPlaceAll(BlockPlaceEvent event) {
		if (!event.getPlayer().hasPermission("oitc.event.block.place")) {
			event.setCancelled(true);
		}
	}
	
	@ListenerBundle(name = "bundle.game")
	private static void onPlaceGame(BlockPlaceEvent event) {
		if (new NBTHelper(event.getItemInHand()).getBoolean("oitc.redstone") == true) {
			event.setCancelled(true);
			return;
		}
		
		if (!event.getPlayer().hasPermission("oitc.event.block.place")) {
			event.setCancelled(true);
		}
	}
	
}
