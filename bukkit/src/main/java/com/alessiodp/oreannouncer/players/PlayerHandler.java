package com.alessiodp.oreannouncer.players;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.logging.LogLevel;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PlayerHandler {
	private OreAnnouncer plugin;
	private List<UUID> listHiddenPlayers;
	
	public PlayerHandler(OreAnnouncer instance) {
		plugin = instance;
	}
	
	public void reload() {
		listHiddenPlayers = plugin.getDatabaseManager().getHideAlert();
	}
	
	public boolean hiddenPlayersContains(UUID uuid) {
		return listHiddenPlayers.contains(uuid);
	}
	
	public void addHiddenPlayer(UUID uuid) {
		listHiddenPlayers.add(uuid);
		plugin.getDatabaseManager().saveHideAlert(listHiddenPlayers);
	}
	
	public void removeHiddenPlayer(UUID uuid) {
		listHiddenPlayers.remove(uuid);
		plugin.getDatabaseManager().saveHideAlert(listHiddenPlayers);
	}
	
	public void sendMessage(CommandSender sender, String str) {
		if (sender instanceof Player) {
			sendPlayerMessage((Player) sender, str);
		} else {
			sender.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', str)));
		}
	}
	private void sendPlayerMessage(Player player, String str) {
		if (!str.isEmpty()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
		}
	}
	
	public void broadcastMessage(String message) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (p.hasPermission(OAPermission.SEE.toString())) {
				if (!hiddenPlayersContains(p.getUniqueId())) {
					p.sendMessage(message);
				}
			}
		}
		LoggerManager.log(LogLevel.BASIC, ChatColor.stripColor(message), true);
	}
}
