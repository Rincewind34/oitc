package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.server.ServerListPingEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.Main;

public class ListenerServerPing extends DefaultListener {

	public ListenerServerPing() {
		super(ListenerServerPing.class, ServerListPingEvent.getHandlerList());
	}

	private static Main plugin = Main.getInstance();
	
	@ListenerBundle(name = "bundle.all")
	private static void onServerPing (ServerListPingEvent e) {
		e.setMaxPlayers(ListenerServerPing.plugin.getMaxPlayers());
		
		if (!ListenerServerPing.plugin.isIngame() && Bukkit.getOnlinePlayers().size() < ListenerServerPing.plugin.getMaxPlayers()) {
			e.setMotd(ListenerServerPing.plugin.getMotds()[0]);
		} else if (!ListenerServerPing.plugin.isIngame() && Bukkit.getOnlinePlayers().size() >= ListenerServerPing.plugin.getMaxPlayers()) {
			e.setMotd(ListenerServerPing.plugin.getMotds()[1]);
		} else if (ListenerServerPing.plugin.isIngame()) {
			e.setMotd(ListenerServerPing.plugin.getMotds()[2]);
		}
	}
}
