package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;

public class LoginListener implements Listener {

	private Main plugin = Main.getInstance();
	
	@EventHandler
	public void onPlayerLogin (PlayerLoginEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getPlayer();
		
		if (Bukkit.getOnlinePlayers().size() >= this.plugin.getMaxPlayers()) {
			if (!player.hasPermission(this.plugin.getAdminPermission())) {
				event.disallow(Result.KICK_FULL, layout.prefix + layout.clNeg + "§cDieses Spiel ist voll!");
			} else {
				event.allow();
			}
		}
	}
}
