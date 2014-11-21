package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.janhektor.oitc.Main;

public class LoginListener implements Listener {

	private Main plugin;
	
	public LoginListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerLogin (PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (Bukkit.getOnlinePlayers().length >= plugin.maxPlayers) {
			if (!p.hasPermission(plugin.adminPermission)) {
				e.disallow(Result.KICK_FULL, plugin.prefix + "§cDieses Spiel ist voll!");
			} else {
				e.allow();
			}
		}
	}
}
