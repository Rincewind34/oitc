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
		e.setMaxPlayers(plugin.getMaxPlayers());
		if (!this.plugin.ingame && Bukkit.getOnlinePlayers().size() < plugin.getMaxPlayers()) {
			e.setMotd(this.plugin.getMotds()[0]);
			return;
		}
		
		if (!this.plugin.ingame && Bukkit.getOnlinePlayers().size() >= plugin.getMaxPlayers()) {
			e.setMotd(this.plugin.getMotds()[1]);
			return;
		}
		
		if (this.plugin.ingame) {
			e.setMotd(this.plugin.getMotds()[2]);
		}
	}
}
