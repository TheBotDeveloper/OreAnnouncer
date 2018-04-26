package com.alessiodp.oreannouncer.commands.list;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.commands.ICommand;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import com.alessiodp.oreannouncer.players.OAPermission;
import org.bukkit.command.CommandSender;

public class CommandHelp implements ICommand {
	private static OreAnnouncer plugin;
	
	public CommandHelp(OreAnnouncer instance) {
		plugin = instance;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		printHelp(sender);
	}
	
	public static void printHelp(CommandSender sender) {
		if (!sender.hasPermission(OAPermission.CMD_HELP.toString())) {
			plugin.getPlayerHandler().sendMessage(sender, Messages.OREANNOUNCER_NOPERMISSION);
			return;
		}
		
		for (String line : Messages.OREANNOUNCER_HELP) {
			plugin.getPlayerHandler().sendMessage(sender, line);
		}
	}
}
