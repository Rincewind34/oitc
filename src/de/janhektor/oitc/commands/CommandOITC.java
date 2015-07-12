package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.janhektor.oitc.Main;
import de.janhektor.oitc.command.dyn.BasicCommand;
import de.janhektor.oitc.command.dyn.BasicCommand.DefaultExecutor;
import de.janhektor.oitc.command.dyn.SimpleCommandSettings;

public class CommandOITC extends BasicCommand implements DefaultExecutor {

	private Main plugin = Main.getInstance();
	
	public CommandOITC() {
		super("oitc", new SimpleCommandSettings(), Main.getInstance());
		
		SimpleCommandSettings settings = new SimpleCommandSettings(Main.getInstance().prefix);
		settings.setMessageDefault(String.format(settings.getMessageSyntax(), "/oitc"));
		
		super.setAliases("oneinthechamber");
		super.setDefaultExecutor(this);
		super.setDescription("Displays a simple info abount the plugin.");
		super.setPermission(null);
		super.setUsage("/oitc");
		super.setSettings(settings);
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(this.plugin.prefix + "§aVersion: " + this.plugin.getDescription().getVersion());
		sender.sendMessage(this.plugin.prefix + "§aEntwickelt von " + this.plugin.getDescription().getAuthors().get(0));
		
		return true;
	}

}
