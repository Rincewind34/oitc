package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import de.janhektor.oitc.Main;

public class DeathListener implements Listener {

	private Main plugin;
	
	public DeathListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath (PlayerDeathEvent e) {
		Player p = e.getEntity();	
		if (!this.plugin.ingame) return;
		if (this.plugin.lives.containsKey(p.getName())) {
			int lives = plugin.lives.get(p.getName());
			if (lives <= 1) {
				p.kickPlayer("§cDu hast keine Leben mehr!");
			} else {
				this.plugin.lives.put(p.getName(), lives - 1);
			}
		}
		
		if (Bukkit.getOnlinePlayers().size() <= 1) {
			Player winner = Bukkit.getOnlinePlayers().iterator().next();
			winner.setGameMode(GameMode.CREATIVE);
			winner.setVelocity(new Vector(0, 7.5D, 0));
			Bukkit.broadcastMessage(this.plugin.prefix + "§6Die Runde ist beendet!");
			Bukkit.broadcastMessage(this.plugin.prefix + "§6Der Sieger ist " + winner.getName());
			Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, new Runnable() {
				
				@Override
				public void run() {
					Bukkit.shutdown();
				}
			}, 120L);
		}
	}
}
