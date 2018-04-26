package com.alessiodp.oreannouncer.events;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import com.alessiodp.oreannouncer.logging.LogLevel;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.players.OAPermission;
import com.alessiodp.oreannouncer.utils.OreBlock;
import com.alessiodp.oreannouncer.utils.OreUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener implements Listener {
	private OreAnnouncer plugin;
	
	public BlockPlaceListener(OreAnnouncer instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled())
			return;
		
		if (ConfigMain.BLOCKS_PREVENTANNOUNCE_ONPLACED
				&& !event.getPlayer().hasPermission(OAPermission.BYPASS_PLACE.toString())) {
			OreBlock ore = OreUtils.getOreBlockByMaterial(event.getBlock().getType());
			
			if (ore != null) {
				event.getBlock().setMetadata(Constants.BLOCK_METADATA, new FixedMetadataValue(plugin, true));
				LoggerManager.log(LogLevel.DEBUG, Constants.DEBUG_BLOCKPLACED
						.replace("{ore}", ore.getType().toString())
						.replace("{player}", event.getPlayer().getName()), true);
			}
		}
	}
}
