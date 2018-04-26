package com.alessiodp.oreannouncer.commands.list;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.commands.ICommand;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.utils.ConsoleColor;
import com.alessiodp.oreannouncer.logging.LogLevel;
import com.alessiodp.oreannouncer.players.OAPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReload implements ICommand {
	private OreAnnouncer plugin;
	
	public CommandReload(OreAnnouncer instance) {
		plugin = instance;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!sender.hasPermission(OAPermission.CMD_RELOAD.toString())) {
			plugin.getPlayerHandler().sendMessage(sender, Messages.OREANNOUNCER_NOPERMISSION);
			return;
		}
		
		plugin.reloadConfiguration();
		plugin.getPlayerHandler().sendMessage(sender, Messages.OREANNOUNCER_CONFIGURATIONRELOADED);
		
		LoggerManager.log(LogLevel.BASIC, Constants.DEBUG_CMD_RELOAD, (sender instanceof Player), ConsoleColor.GREEN);
	}
}
