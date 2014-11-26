package de.janhektor.oitc.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.Main;

public class ArrowShotListener implements Listener {

	private Main plugin;
	private Map<Integer, Integer> taskids;
	private int id;
	
	public ArrowShotListener(Main plugin) {
		this.plugin = plugin;
		this.taskids = new HashMap<Integer, Integer>();
		this.id = 0;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onArrowShoot (EntityShootBowEvent e) {
		if (!plugin.arrayTrail) return;
		if (e.getEntityType() == EntityType.PLAYER) {
			try {
				if (e.getBow().getItemMeta().getDisplayName().equals(GameUtil.getBow().getItemMeta().getDisplayName())) {
					
					final Entity arrow = e.getProjectile();
					
					this.id++;
					this.taskids.put(arrow.getEntityId(), this.id);
					Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
						
						@Override
						public void run() {
							if (arrow.isOnGround() || arrow.isDead()) {
								int taskid = ArrowShotListener.this.taskids.get(arrow.getEntityId());
								ArrowShotListener.this.taskids.remove(arrow.getEntityId());
								Bukkit.getScheduler().cancelTask(taskid);
								return;
							}
							
							Bukkit.getScheduler().runTask(plugin, new Runnable() {
								
								@Override
								public void run() {
									arrow.getWorld().playEffect(arrow.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
								}
							});
						}
					}, 1L, 1L);
				}
			} catch (Exception ex) {
				return;
			}
		}
	}
}
