package com.alessiodp.oreannouncer.commands;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.commands.list.CommandHelp;
import com.alessiodp.oreannouncer.commands.list.CommandHide;
import com.alessiodp.oreannouncer.commands.list.CommandReload;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class CommandDispatcher implements CommandExecutor {
	private OreAnnouncer plugin;
	private static HashMap<String, ICommand> commands = new HashMap<>();
	
	public CommandDispatcher(OreAnnouncer instance) {
		plugin = instance;
	}
	
	public void reloadCommands() {
		commands = new HashMap<>();
		commands.put("help", new CommandHelp(plugin));
		commands.put("reload", new CommandReload(plugin));
		commands.put("hide", new CommandHide(plugin));
		
		plugin.getCommand("oreannouncer").setExecutor(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("oreannouncer")
				|| label.equalsIgnoreCase("oa")) {
			if (args.length > 0) {
				if (commands.containsKey(args[0])) {
					commands.get(args[0]).onCommand(sender, args);
				} else {
					plugin.getPlayerHandler().sendMessage(sender, Messages.OREANNOUNCER_NOTFOUND);
				}
			} else {
				// Print help
				CommandHelp.printHelp(sender);
			}
			return true;
		}
		return false;
	}
}
