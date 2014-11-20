package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.janhektor.oitc.Main;

public class CommandOITC implements CommandExecutor {

	private Main plugin;
	
	public CommandOITC(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		sender.sendMessage(plugin.prefix + "§aVersion: " + plugin.getDescription().getVersion());
		sender.sendMessage(plugin.prefix + "§aEntwickelt von " + plugin.getDescription().getAuthors().get(0));
		
		return false;
	}

}
