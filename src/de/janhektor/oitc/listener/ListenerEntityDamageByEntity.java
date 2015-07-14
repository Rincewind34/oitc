package de.janhektor.oitc.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.InfoLayout;

public class ListenerEntityDamageByEntity extends DefaultListener {
	
	public ListenerEntityDamageByEntity() {
		super(ListenerEntityDamageByEntity.class, EntityDamageByEntityEvent.getHandlerList());
	}
	
	@ListenerBundle(name = { "bundle.lobby", "bundle.end" })
	private static void onDamageAll(EntityDamageByEntityEvent event) {
		event.setCancelled(true);
	}
	
	@ListenerBundle(name = "bundle.game")
	private static void onDamageGame(EntityDamageByEntityEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		
		if (entity instanceof Player && damager instanceof Arrow) {
			Player player = (Player) entity;
			Arrow arrow = (Arrow) damager;
			
			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				Player killer = (Player) arrow.getShooter();
				player.setHealth(0.0);
				killer.sendMessage(layout.prefix + layout.clSec + "Du hast " + layout.clPos + player.getName() + layout.clSec + " erschossen!");
			}
		}
	}
	
}
