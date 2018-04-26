package com.alessiodp.oreannouncer.events;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import com.alessiodp.oreannouncer.logging.LogLevel;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.players.OAPermission;
import com.alessiodp.oreannouncer.utils.OreBlock;
import com.alessiodp.oreannouncer.utils.OreUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class BlockBreakListener implements Listener {
	private OreAnnouncer plugin;
	private final String[] originalAxes = {"%x%", "%y%", "%z%"};
	
	public BlockBreakListener(OreAnnouncer instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled())
			return;
		
		if (event.getPlayer().hasPermission(OAPermission.BYPASS_ANNOUNCE.toString())
				|| event.getBlock().hasMetadata(Constants.BLOCK_METADATA))
			return;
		
		OreBlock ore = OreUtils.getOreBlockByMaterial(event.getBlock().getType());
		
		if (ore != null) {
			Player player = event.getPlayer();
			LoggerManager.log(LogLevel.DEBUG, Constants.DEBUG_BLOCKFOUND
					.replace("{ore}", ore.getType().toString())
					.replace("{player}", player.getName()), true);
			
			int lightLevel = event.getPlayer().getLastTwoTargetBlocks(null, 1).get(0).getLightLevel();
			int oreCount = countBlocks(event.getBlock());
			boolean lowLight = false;
			String message = null;
			
			if (ConfigMain.BLOCKS_LIGHTLEVEL_ENABLE && lightLevel <= ore.getLightLevel()) {
				if (ConfigMain.BLOCKS_LIGHTLEVEL_PREVENTBREAK) {
					// Prevent break
					plugin.getPlayerHandler().sendMessage(player, Messages.BLOCKS_PREVENTBREAK);
					event.setCancelled(true);
					return;
				}
				lowLight = true;
			}
			
			if (!ConfigMain.BLOCKS_LIGHTLEVEL_ALERTONLY
					|| !ConfigMain.BLOCKS_LIGHTLEVEL_ENABLE
					|| (ConfigMain.BLOCKS_LIGHTLEVEL_ALERTONLY && lowLight)) {
				// Make the message only if alert-only is false or there is low light
				message = getOreMessage(ore, oreCount);
			}
			
			if (message != null) {
				message = ChatColor.translateAlternateColorCodes('&', insertCoordinates(message, event.getBlock().getLocation())
						.replace("%player%", event.getPlayer().getName())
						.replace("%lightlevel%", Integer.toString(lightLevel))
						.replace("%number%", Integer.toString(oreCount)));
				plugin.getPlayerHandler().broadcastMessage(message);
			} else if (ConfigMain.BLOCKS_ALERTWRONGMSG) {
				LoggerManager.log(LogLevel.BASE, Constants.DEBUG_BLOCKMESSAGE_NOTFOUND
						.replace("{ore}", ore.getType().toString()), true);
			}
			
			event.getBlock().setMetadata(Constants.BLOCK_METADATA, new FixedMetadataValue(plugin, true));
			LoggerManager.log(LogLevel.DEBUG, Constants.DEBUG_BLOCKPLACED
					.replace("{ore}", ore.getType().toString())
					.replace("{player}", event.getPlayer().getName()), true);
		}
	}
	
	private String getOreMessage(OreBlock ore, int oreCount) {
		String ret = null;
		if (ConfigMain.COORDINATES_ENABLE
				&& (Bukkit.getOnlinePlayers().size() >= ConfigMain.COORDINATES_MINPLAYERS)) {
			ret = oreCount > 1 ? ore.getCoordMultiple() : ore.getCoordSingle();
		} else {
			ret = oreCount > 1 ? ore.getMultiple() : ore.getSingle();
		}
		return ret;
	}
	
	private int countBlocks(Block block) {
		int ret = 1;
		block.setMetadata(Constants.BLOCK_METADATA, new FixedMetadataValue(plugin, true));
		for (int x=-1; x <= 1; x++) {
			for (int y=-1; y <= 1; y++) {
				for (int z=-1; z <= 1; z++) {
					Block b = block.getLocation().add(x, y, z).getBlock();
					if ((b.getType().equals(block.getType())) && (!b.hasMetadata(Constants.BLOCK_METADATA))) {
						ret += countBlocks(b);
					}
				}
			}
		}
		return ret;
	}
	
	private String insertCoordinates(String message, Location loc) {
		if (ConfigMain.COORDINATES_RANDOM_ENABLE) {
			// Random coordinates
			HashMap<String, Boolean> axes = new HashMap<>();
			for (String cord : originalAxes) {
				if (message.contains(cord))
					axes.put(cord, false);
			}
			if (axes.size() > 0) {
				// Randomize coordinates
				int hideLimit = ConfigMain.COORDINATES_RANDOM_COUNT > axes.size() ? axes.size() : ConfigMain.COORDINATES_RANDOM_COUNT;
				
				if (hideLimit >= axes.size()) {
					// Hide all
					for (String key : axes.keySet()) {
						axes.put(key, true);
					}
				} else {
					// Hide some of them
					for (int c=0; c < hideLimit; c++) {
						int ran = (int) (Math.random() * originalAxes.length);
						if (axes.get(originalAxes[ran]) == null || axes.get(originalAxes[ran]))
							c--;
						else
							axes.put(originalAxes[ran], true);
					}
				}
				
				// Replace
				for (String key : axes.keySet()) {
					String newCoordinate = "";
					switch (key) {
						case "%x%":
							newCoordinate = Integer.toString(loc.getBlockX());
							break;
						case "%y%":
							newCoordinate = Integer.toString(loc.getBlockY());
							break;
						case "%z%":
							newCoordinate = Integer.toString(loc.getBlockZ());
					}
					if (axes.get(key)) {
						// Hide that coord
						if (newCoordinate.startsWith("-"))
							newCoordinate = newCoordinate.substring(1);
						if (ConfigMain.COORDINATES_RANDOM_OBFUSCATE)
							newCoordinate = new String(new char[newCoordinate.length()]).replace("\0", "0"); // Hide real coords even if hidden
						newCoordinate = ConfigMain.COORDINATES_RANDOM_PREFIX + newCoordinate;
					}
					
					message = message.replace(key, newCoordinate);
					
				}
			}
		} else {
			// Right coordinates
			message = message
					.replace("%x%", Integer.toString(loc.getBlockX()))
					.replace("%y%", Integer.toString(loc.getBlockY()))
					.replace("%z%", Integer.toString(loc.getBlockZ()));
		}
		return message;
	}
}
