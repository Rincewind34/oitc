package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;
import de.janhektor.oitc.command.dyn.BasicCommand;
import de.janhektor.oitc.command.dyn.BasicCommand.DefaultExecutor;
import de.janhektor.oitc.command.dyn.SimpleCommandSettings;

public class CommandOITC extends BasicCommand implements DefaultExecutor {

	private Main plugin = Main.getInstance();
	
	public CommandOITC() {
		super("oitc", new SimpleCommandSettings(), Main.getInstance());
		
		SimpleCommandSettings settings = new SimpleCommandSettings(new InfoLayout("OITC").prefix);
		settings.setMessageDefault(String.format(settings.getMessageSyntax(), "/oitc [setarenaspawn]"));
		
		super.setAliases("oneinthechamber");
		super.setDefaultExecutor(this);
		super.setDescription("Zeigt eine kleine Hilfe über das Plugin an.");
		super.setPermission(null);
		super.setUsage("/oitc");
		super.setSettings(settings);
		super.registerArgument("setarenaspawn", new ArgumentSetArenaSpawn(this.plugin));
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = new InfoLayout("OITC");
		
		sender.sendMessage(layout.prefix + layout.clPos + "Version: " + this.plugin.getDescription().getVersion());
		sender.sendMessage(layout.prefix + layout.clPos + "Entwickelt von " + this.plugin.getDescription().getAuthors().get(0));
		
		Main.getInstance().handler.unregister("game");
		Main.getInstance().handler.register("game2");
		
		return true;
	}

}
