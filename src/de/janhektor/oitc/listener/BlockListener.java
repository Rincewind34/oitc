package de.janhektor.oitc.listener;

import org.bukkit.event.block.BlockBreakEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;

public class BlockListener extends DefaultListener<BlockBreakEvent> {

	public BlockListener() {
		super(BlockListener.class, new BlockBreakEvent(null, null));
	}

	@ListenerBundle(name = "game")
	public void onGame(BlockBreakEvent e) {
		if (!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
		System.out.println("HIIIIIIIIIIIIIIIII");
	}
	
	@ListenerBundle(name = "game2")
	public void onGame2(BlockBreakEvent e) {
		if (!e.getPlayer().isOp()) {
			e.setCancelled(true);
		}
		
		System.out.println("YEAAAAAAAAAAAAAAAAAAAAAAAAA");
	}
	
//	@EventHandler
//	public void onBlockPlace(BlockPlaceEvent e) {
//		if (!e.getPlayer().isOp()) {
//			e.setCancelled(true);
//		}
//	}
	
}
