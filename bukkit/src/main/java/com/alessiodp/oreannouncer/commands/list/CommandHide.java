package com.alessiodp.oreannouncer.commands.list;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.commands.ICommand;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.logging.LogLevel;
import com.alessiodp.oreannouncer.players.OAPermission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHide implements ICommand {
	private OreAnnouncer plugin;
	
	public CommandHide(OreAnnouncer instance) {
		plugin = instance;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			plugin.getPlayerHandler().sendMessage(sender, Messages.OREANNOUNCER_NOCONSOLE);
			return;
		}
		
		Player player = (Player) sender;
		if (!player.hasPermission(OAPermission.CMD_HIDE.toString())) {
			plugin.getPlayerHandler().sendMessage(sender, Messages.OREANNOUNCER_NOPERMISSION);
			return;
		}
		
		boolean isHidden = false;
		if (plugin.getPlayerHandler().hiddenPlayersContains(player.getUniqueId()))
			isHidden = true;
		
		if (isHidden) {
			plugin.getPlayerHandler().removeHiddenPlayer(player.getUniqueId());
			plugin.getPlayerHandler().sendMessage(player, Messages.HIDEALERT_OFF);
		} else {
			plugin.getPlayerHandler().addHiddenPlayer(player.getUniqueId());
			plugin.getPlayerHandler().sendMessage(player, Messages.HIDEALERT_ON);
		}
		
		LoggerManager.log(LogLevel.MEDIUM, Constants.DEBUG_CMD_HIDE
				.replace("{player}", player.getName())
				.replace("{value}", Boolean.toString(!isHidden)), true);
	}
}
