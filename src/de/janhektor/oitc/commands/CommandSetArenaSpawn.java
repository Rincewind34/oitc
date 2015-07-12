package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.janhektor.oitc.Arena;
import de.janhektor.oitc.Main;

public class CommandSetArenaSpawn implements CommandExecutor {

	private Main plugin;
	
	/*
	 * Syntax des Befehls:
	 *     /setarenaspawn <arenaName>
	 */
	public CommandSetArenaSpawn(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(this.plugin.prefix + "§cDu musst ein Spieler sein.");
			return true;
		}
		Player p = (Player) cs;
		
		if (args.length != 1) {
			p.sendMessage(this.plugin.prefix + "§cSyntax: /setarenaspawn <name>");
			return true;
		}
		
		String arena = args[0].toLowerCase();
		
		if (!this.plugin.arenaManager.isArenaExists(arena)) {
			this.plugin.arenaManager.addArena(new Arena(arena));
			p.sendMessage(plugin.prefix + "§aDie Arena " + arena + " existierte noch nicht und wurde soeben erstellt.");
		}
		
		this.plugin.arenaManager.addSpawn(arena, p.getLocation());
		p.sendMessage(this.plugin.prefix + "§aEin Spawn-Punkt in Arena " + arena + " wurde erfolgreich gesetzt.");
		
		return false;
	}

}
