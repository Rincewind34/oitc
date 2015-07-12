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
			cs.sendMessage(this.plugin.prefix + "§cDu musst ein Spieler sein!");
			return true;
		}
		Player p = (Player) cs;
		
		if (args.length != 1) {
			p.sendMessage(this.plugin.prefix + "§cSyntax: /vote <arena>");
			return true;
		}
		
		if (this.plugin.mapVoting.hasVoted(p)) {
			p.sendMessage(this.plugin.prefix + "§cDu hast bereits eine Stimme abgegeben!");
			return true;
		}
		
		String arenaName = args[0].toLowerCase();
		if (!this.plugin.mapVoting.isMapInVoting(arenaName)) {
			p.sendMessage(this.plugin.prefix + "§cDiese Map ist nicht im Voting!");
			return true;
		}
		
		this.plugin.mapVoting.playerVote(p, arenaName);
		p.sendMessage(this.plugin.prefix + "§aVielen Dank fuer deine Stimme!");
		
		
		
		return false;
	}

}
