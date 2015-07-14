package de.janhektor.oitc.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;

public class ListenerRespawn extends DefaultListener {

	public ListenerRespawn() {
		super(ListenerRespawn.class, PlayerRespawnEvent.getHandlerList());
	}

	private static Main plugin = Main.getInstance();
	
	@ListenerBundle(name = "bundle.game")
	private static void onPlayerRespawn(PlayerRespawnEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setFireTicks(0);
		
		player.getInventory().setItem(0, GameUtil.getBow());
		player.getInventory().setItem(4, GameUtil.getRedstone(ListenerRespawn.plugin.getLives().get(player.getName())));
		player.getInventory().setItem(8, GameUtil.getArrow());
		
		event.setRespawnLocation(ListenerRespawn.plugin.getSpawnPoints().get(GameUtil.getRnd().nextInt(ListenerRespawn.plugin.getSpawnPoints().size())));
		player.sendMessage(layout.prefix + layout.clSec + "Du konntest respawnen, da du noch " + layout.clHiLi + plugin.getLives().get(player.getName())
				+ layout.clSec + " Leben hast.");
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
	}
}
