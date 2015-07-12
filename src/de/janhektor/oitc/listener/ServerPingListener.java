package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.janhektor.oitc.Main;


public class ServerPingListener implements Listener {

	private Main plugin = Main.getInstance();
	
	@EventHandler
	public void onServerPing (ServerListPingEvent e) {
		e.setMaxPlayers(plugin.maxPlayers);
		if (!this.plugin.ingame && Bukkit.getOnlinePlayers().size() < plugin.maxPlayers) {
			e.setMotd(this.plugin.motds[0]);
			return;
		}
		
		if (!this.plugin.ingame && Bukkit.getOnlinePlayers().size() >= plugin.maxPlayers) {
			e.setMotd(this.plugin.motds[1]);
			return;
		}
		
		if (this.plugin.ingame) {
			e.setMotd(this.plugin.motds[2]);
		}
	}
}
