package com.alessiodp.oreannouncer.events;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.addons.internal.ADPUpdater;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	private OreAnnouncer plugin;
	
	public JoinListener(OreAnnouncer instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		ADPUpdater.alertPlayer(event.getPlayer());
	}
}
