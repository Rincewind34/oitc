package de.janhektor.oitc.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;
import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;

public class ListenerLogin extends DefaultListener {

	public ListenerLogin() {
		super(ListenerLogin.class, PlayerLoginEvent.getHandlerList());
	}

	private static Main plugin = Main.getInstance();
	
	@ListenerBundle(name = "bundle.all")
	private static void onPlayerLogin(PlayerLoginEvent event) {
		InfoLayout layout = new InfoLayout("OITC");
		Player player = event.getPlayer();
		
		if (Bukkit.getOnlinePlayers().size() >= ListenerLogin.plugin.getMaxPlayers()) {
			if (!player.hasPermission(ListenerLogin.plugin.getAdminPermission())) {
				event.disallow(Result.KICK_FULL, layout.prefix + layout.clNeg + "§cDieses Spiel ist voll!");
			} else {
				event.allow();
			}
		}
	}
}
