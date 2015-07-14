package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;

public class ListenerDeath extends DefaultListener {

	public ListenerDeath() {
		super(ListenerDeath.class, PlayerDeathEvent.getHandlerList());
	}

	private static Main plugin = Main.getInstance();
	
	@ListenerBundle(name = "bundle.game")
	private static void onPlayerDeath(PlayerDeathEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getEntity();	
		
		if (!ListenerDeath.plugin.isIngame()) {
			return;
		}
		
		event.getDrops().clear();
		event.setDeathMessage("");
		
		String msg = layout.prefix + layout.clSec + "Der Spieler " + layout.clNeg + player.getName() + layout.clSec + " ";
		
		if (player.getKiller() != null) {
			Player killer = player.getKiller();
			msg = msg + "wurde von " + layout.clPos + killer.getName() + layout.clSec + " ";
			if (player.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {
				msg = msg + "erschlagen";
			} else if (player.getLastDamageCause().getCause() == DamageCause.PROJECTILE) {
				msg = msg + "erschossen";
			} else {
				msg = msg + "getoetet";
			}
			System.out.println(player.getLastDamageCause().getCause());
			killer.getInventory().setItem(8, GameUtil.getArrow());
		} else {
			if (player.getLastDamageCause() != null && player.getLastDamageCause().getCause() != null && player.getLastDamageCause().getCause() == DamageCause.CUSTOM) {
				msg = msg + "ist gestorben";
			} else {
				msg = "";
			}
		}
		
		Bukkit.broadcastMessage(msg + (msg.isEmpty() ? "" : "."));
		
		if (ListenerDeath.plugin.getLives().containsKey(player.getName())) {
			int lives = plugin.getLives().get(player.getName());
			if (lives <= 1) {
				player.kickPlayer(layout.clNeg + "Du hast keine Leben mehr!");
			} else {
				ListenerDeath.plugin.getLives().put(player.getName(), lives - 1);
			}
		}
		
		if (Bukkit.getOnlinePlayers().size() <= 1) {
			Player winner = Bukkit.getOnlinePlayers().iterator().next();
			winner.setGameMode(GameMode.CREATIVE);
			winner.setVelocity(new Vector(0, 7.5D, 0));
			
			Bukkit.broadcastMessage(layout.prefix + layout.clSec + "Die Runde ist beendet!");
			Bukkit.broadcastMessage(layout.prefix + layout.clSec + "Der Sieger ist " + winner.getName());
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(1000L * 20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					Bukkit.shutdown();
				}
			}).run();;
		}
	}
}
