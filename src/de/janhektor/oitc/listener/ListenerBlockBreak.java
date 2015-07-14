package de.janhektor.oitc.listener;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;

public class ListenerBlockBreak extends DefaultListener {

	public ListenerBlockBreak() {
		super(ListenerBlockBreak.class, BlockExpEvent.getHandlerList());
	}
	
	@ListenerBundle(name = "bundle.all")
	private static void onBlockBreak(BlockBreakEvent event) {
		if (!event.getPlayer().hasPermission("oitc.event.block.break")) {
			event.setCancelled(true);
		}
	}
	
}
