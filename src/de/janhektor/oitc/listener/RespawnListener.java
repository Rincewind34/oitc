package de.janhektor.oitc.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;

public class RespawnListener implements Listener {

	private Main plugin = Main.getInstance();
	
	@EventHandler
	public void onPlayerRespawn (PlayerRespawnEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		
		player.getInventory().setItem(0, GameUtil.getBow());
		player.getInventory().setItem(4, GameUtil.getRedstone(this.plugin.getLives().get(player.getName())));
		player.getInventory().setItem(8, GameUtil.getArrow());
		
		player.teleport(this.plugin.getSpawnPoints().get(GameUtil.getRnd().nextInt(this.plugin.getSpawnPoints().size())));
		player.sendMessage(layout.prefix + layout.clSec + "Du konntest respawnen, da du noch " + layout.clHiLi + plugin.getLives().get(player.getName())
				+ layout.clSec + " Leben hast.");
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
	}
}
