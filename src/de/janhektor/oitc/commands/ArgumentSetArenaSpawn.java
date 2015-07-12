package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.janhektor.oitc.Arena;
import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;
import de.janhektor.oitc.command.dyn.Argument;

public class ArgumentSetArenaSpawn extends Argument<Main> {

	public ArgumentSetArenaSpawn(Main plugin) {
		super(plugin);
	}

	@Override
	public String getSyntax() {
		return "/oitc setarenaspawn <arenaname>";
	}

	@Override
	public String getPermission() {
		return "oitc.setarenaspawn";
	}

	@Override
	public boolean isOnlyForPlayer() {
		return true;
	}

	@Override
	public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = new InfoLayout("OITC");
		
		Player player = (Player) sender;
		
		if (args.length != 2) {
			return false;
		}
		
		String arena = args[1].toLowerCase();
		
		if (!super.plugin.arenaManager.isArenaExists(arena)) {
			super.plugin.arenaManager.addArena(new Arena(arena));
			player.sendMessage(layout.prefix + layout.clPos + "Die Arena " + arena + " existierte noch nicht und wurde soeben erstellt.");
		}
		
		this.plugin.arenaManager.addSpawn(arena, player.getLocation());
		player.sendMessage(layout.prefix + layout.clPos + "Ein Spawn-Punkt in Arena " + arena + " wurde erfolgreich gesetzt.");
		
		return true;
	}

}
