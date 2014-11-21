package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.janhektor.oitc.Main;


public class ServerPingListener implements Listener {

	private Main plugin;
	
	public ServerPingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerPing (ServerListPingEvent e) {
		e.setMaxPlayers(plugin.maxPlayers);
		if (!plugin.ingame && Bukkit.getOnlinePlayers().length < plugin.maxPlayers) {
			e.setMotd(plugin.motds[0]);
			return;
		}
		
		if (!plugin.ingame && Bukkit.getOnlinePlayers().length >= plugin.maxPlayers) {
			e.setMotd(plugin.motds[1]);
			return;
		}
		
		if (plugin.ingame) {
			e.setMotd(plugin.motds[2]);
		}
	}
}
