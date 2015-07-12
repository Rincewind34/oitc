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
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath (PlayerDeathEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getEntity();	
		
		if (!this.plugin.ingame) {
			return;
		}
		
		event.getDrops().clear();
		
		if (this.plugin.lives.containsKey(player.getName())) {
			int lives = plugin.lives.get(player.getName());
			if (lives <= 1) {
				player.kickPlayer("§cDu hast keine Leben mehr!");
			} else {
				this.plugin.lives.put(player.getName(), lives - 1);
			}
		}
		
		if (Bukkit.getOnlinePlayers().size() <= 1) {
			Player winner = Bukkit.getOnlinePlayers().iterator().next();
			winner.setGameMode(GameMode.CREATIVE);
			winner.setVelocity(new Vector(0, 7.5D, 0));
			
			Bukkit.broadcastMessage(layout.prefix + layout.clSec + "Die Runde ist beendet!");
			Bukkit.broadcastMessage(layout.prefix + layout.clSec + "Der Sieger ist " + winner.getName());
			
			Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
				
				@Override
				public void run() {
					Bukkit.shutdown();
				}
				
			}, 120L);
		}
	}
}
