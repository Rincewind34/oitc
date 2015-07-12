package de.janhektor.oitc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.janhektor.oitc.InfoLayout;
import de.janhektor.oitc.Main;
import de.janhektor.oitc.command.dyn.BasicCommand;
import de.janhektor.oitc.command.dyn.BasicCommand.DefaultExecutor;
import de.janhektor.oitc.command.dyn.SimpleCommandSettings;

public class CommandVote extends BasicCommand implements DefaultExecutor {

	private Main plugin = Main.getInstance();
	
	public CommandVote() {
		super("vote", new SimpleCommandSettings(), Main.getInstance());
		
		SimpleCommandSettings settings = new SimpleCommandSettings(new InfoLayout("OITC").prefix);
		settings.setMessageDefault("Tippe /vote fuer die Hilfe ein.");
		
		super.setDefaultExecutor(this);
		super.setDescription("Ein Command, um fuer eine Map zu voten.");
		super.setPermission(null);
		super.setUsage("/vote <arenaname>");
		super.setSettings(settings);
	}

	@Override
	public boolean onExecute(CommandSender sender, Command cmd, String label, String[] args) {
		InfoLayout layout = new InfoLayout("OITC");
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(layout.prefix + this.getSettings().getMessageOnlyPlayer());
			return true;
		}
		
		if (args.length == 1) {
			Player p = (Player) sender;
			
			if (this.plugin.mapVoting.hasVoted(p)) {
				p.sendMessage(layout.prefix + layout.clNeg + "Du hast bereits eine Stimme abgegeben!");
				return true;
			}
			
			String arenaName = args[0].toLowerCase();
			if (!this.plugin.mapVoting.isMapInVoting(arenaName)) {
				p.sendMessage(layout.prefix + layout.clNeg + "Diese Map ist nicht im Voting!");
				return true;
			}
			
			this.plugin.mapVoting.playerVote(p, arenaName);
			p.sendMessage(layout.prefix + layout.clNeg + "Vielen Dank fuer deine Stimme!");
			
			return true;
		} else {
			layout.newBarrier();
			layout.addElement("Syntax: /vote <arenaname>", false);
			layout.addComent(" - " + this.getDescription(), false);
			layout.newCategory("Maps zum voten");
			
			for (String arena : Main.getInstance().mapVoting.getArenas().keySet()) {
				layout.addComent(arena, false);
			}
			
			layout.newBarrier();
			layout.send(sender);
			return true;
		}
	}
	
}
