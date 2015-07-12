package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;

public class DeathListener implements Listener {

	private Main plugin = Main.getInstance();
	
	@EventHandler
	public void onPlayerDeath (PlayerDeathEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getEntity();	
		
		if (!this.plugin.ingame) {
			return;
		}
		
		event.getDrops().clear();
		
		if (this.plugin.getLives().containsKey(player.getName())) {
			int lives = plugin.getLives().get(player.getName());
			if (lives <= 1) {
				player.kickPlayer("§cDu hast keine Leben mehr!");
			} else {
				this.plugin.getLives().put(player.getName(), lives - 1);
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
