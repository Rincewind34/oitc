package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.janhektor.oitc.Main;

public class CommandVote implements CommandExecutor {

	private Main plugin;
	
	/*
	 * Syntax des Befehls:
	 *       /vote <arena>
	 */
	public CommandVote(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(plugin.prefix + "§cDu musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) cs;
		
		if (args.length != 1) {
			p.sendMessage(plugin.prefix + "§cSyntax: /vote <arena>");
			return true;
		}
		
		if (plugin.mapVoting.hasVoted(p)) {
			p.sendMessage(plugin.prefix + "§cDu hast bereits eine Stimme abgegeben!");
			return true;
		}
		
		String arenaName = args[0].toLowerCase();
		if (!plugin.mapVoting.isMapInVoting(arenaName)) {
			p.sendMessage(plugin.prefix + "§cDiese Map ist nicht im Voting!");
			return true;
		}
		
		plugin.mapVoting.playerVote(p, arenaName);
		p.sendMessage(plugin.prefix + "§aVielen Dank für deine Stimme!");
		
		
		
		return false;
	}

}
