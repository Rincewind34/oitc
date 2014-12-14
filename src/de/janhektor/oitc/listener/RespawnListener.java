package de.janhektor.oitc.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.Main;

@SuppressWarnings("deprecation")
public class RespawnListener implements Listener {

	private Main plugin;
	
	public RespawnListener(Main plugin) {
		this.plugin = plugin;
	}
	
	// TODO Leben abziehen, wenn Spieler stirbt (DeathEvent) !!!
	@EventHandler
	public void onPlayerRespawn (PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		p.getInventory().clear();
		p.setHealth(20);
		p.setFoodLevel(20);
		p.setFireTicks(0);
		
		// Give Items to player
		p.getInventory().setItem(0, GameUtil.getBow());
		p.getInventory().setItem(4, GameUtil.getRedstone(plugin.lives.get(p.getName())));
		p.getInventory().setItem(8, GameUtil.getArrow());
		
		p.teleport(plugin.spawnPoints.get(GameUtil.getRnd().nextInt(plugin.spawnPoints.size())));
		p.sendMessage(plugin.prefix + "§7Du konntest respawnen, da du noch §3" + plugin.lives.get(p.getName()) + "§7 Leben hast.");
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
	}
}
