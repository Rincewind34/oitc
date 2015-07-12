package de.janhektor.oitc.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockBreak (BlockBreakEvent e) {
		if (!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace (BlockPlaceEvent e) {
		if (!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
	}
	
}
