package de.janhektor.oitc.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.InfoLayout;

public class EntityDamageByEntityListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Entity entity = event.getEntity();
		Entity damager = event.getDamager();
		
		if (entity instanceof Player && damager instanceof Arrow) {
			Player player = (Player) entity;
			Arrow arrow = (Arrow) damager;
			
			if (arrow.getShooter() != null && arrow.getShooter() instanceof Player) {
				player.setHealth(20.0);
				
				Player killer = (Player) arrow.getShooter();
				killer.getInventory().setItem(8, GameUtil.getArrow());
				killer.sendMessage(layout.prefix + layout.clSec + "Du hast " + layout.clPos + player.getName() + layout.clSec + " erschossen!");
			}
		}
	}
	
}
