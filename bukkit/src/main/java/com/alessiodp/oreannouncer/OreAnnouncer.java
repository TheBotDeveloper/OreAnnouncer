package com.alessiodp.oreannouncer;

import java.util.logging.Level;

import com.alessiodp.oreannouncer.addons.external.MetricsHandler;
import com.alessiodp.oreannouncer.addons.internal.ADPUpdater;
import com.alessiodp.oreannouncer.commands.CommandDispatcher;
import com.alessiodp.oreannouncer.configuration.ConfigurationManager;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.events.BlockBreakListener;
import com.alessiodp.oreannouncer.events.BlockPlaceListener;
import com.alessiodp.oreannouncer.events.JoinListener;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.players.PlayerHandler;
import com.alessiodp.oreannouncer.storage.DatabaseManager;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.alessiodp.oreannouncer.utils.ConsoleColor;
import com.alessiodp.oreannouncer.logging.LogLevel;

public class OreAnnouncer extends JavaPlugin {
	@Getter private static OreAnnouncer instance;
	
	@Getter private CommandDispatcher commandDispatcher;
	@Getter private ConfigurationManager configurationManager;
	@Getter private DatabaseManager databaseManager;
	@Getter private PlayerHandler playerHandler;
	
	@Override
	public void onEnable() {
		instance = this;
		log(ConsoleColor.GREEN.getCode() + Constants.DEBUG_OA_ENABLING
				.replace("{version}", getDescription().getVersion()));
		handle();
		LoggerManager.log(LogLevel.BASE, Constants.DEBUG_OA_ENABLED
				.replace("{version}", getDescription().getVersion()), true, ConsoleColor.GREEN);
	}
	
	@Override
	public void onDisable() {
		LoggerManager.log(LogLevel.BASE, Constants.DEBUG_OA_DISABLED_LOG, false);
		log(ConsoleColor.GREEN.getCode() + Constants.DEBUG_OA_DISABLED);
	}
	
	private void handle() {
		new LoggerManager(this);
		configurationManager = new ConfigurationManager(this);
		commandDispatcher = new CommandDispatcher(this);
		databaseManager = new DatabaseManager(this);
		playerHandler = new PlayerHandler(this);
		
		getConfigurationManager().reload();
		LoggerManager.reload();
		getDatabaseManager().reload();
		getPlayerHandler().reload();
		getCommandDispatcher().reloadCommands();
		registerListeners();
		
		new MetricsHandler(this);
		
		new ADPUpdater(this);
		ADPUpdater.asyncTaskCheckUpdates();
	}
	
	private void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockBreakListener(this), this);
		pm.registerEvents(new BlockPlaceListener(this), this);
		pm.registerEvents(new JoinListener(this), this);
	}
	
	public void reloadConfiguration() {
		getConfigurationManager().reload();
		LoggerManager.reload();
		getDatabaseManager().reload();
		
		getPlayerHandler().reload();
		getCommandDispatcher().reloadCommands();
		
		ADPUpdater.asyncCheckUpdates();
	}
	
	public void log(String msg) {
		getServer().getLogger().log(Level.INFO, "[" + ConsoleColor.GREEN.getCode() + "OreAnnouncer" + ConsoleColor.RESET.getCode() + "] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)) + ConsoleColor.RESET.getCode());
	}
	public void log(Level level, String msg) {
		getServer().getLogger().log(level, "[" + ConsoleColor.GREEN.getCode() + "OreAnnouncer" + ConsoleColor.RESET.getCode() + "] " + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)) + ConsoleColor.RESET.getCode());
	}
}
