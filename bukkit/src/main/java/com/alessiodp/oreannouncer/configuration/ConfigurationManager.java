package com.alessiodp.oreannouncer.configuration;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import com.alessiodp.oreannouncer.utils.ConsoleColor;
import com.alessiodp.oreannouncer.utils.OreBlock;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationManager {
	private OreAnnouncer plugin;
	
	public ConfigurationManager(OreAnnouncer instance) {
		plugin = instance;
	}
	
	public void reload() {
		new ConfigMain();
		new Messages();
		
		reloadConfigMain();
		reloadMessages();
	}
	
	private void reloadConfigMain() {
		File cfgFile = new File(plugin.getDataFolder(), "config.yml");
		if (!cfgFile.exists()) {
			plugin.saveResource("config.yml", true);
		}
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);
		
		if (cfg.getInt("dont-edit-this.config-version") < Constants.VERSION_CONFIG)
			plugin.log(Constants.CONFIGURATION_OUTDATED);
		
		loadConfigMain(cfg);
	}
	private void loadConfigMain(FileConfiguration cfg) {
		ConfigMain.OREANNOUNCER_UPDATES_CHECK = cfg.getBoolean("oreannouncer.updates.check", ConfigMain.OREANNOUNCER_UPDATES_CHECK);
		ConfigMain.OREANNOUNCER_UPDATES_WARN = cfg.getBoolean("oreannouncer.updates.warn", ConfigMain.OREANNOUNCER_UPDATES_WARN);
		
		ConfigMain.BLOCKS_PREVENTANNOUNCE_ONPLACED = cfg.getBoolean("blocks.prevent-announce-on-block-placed", ConfigMain.BLOCKS_PREVENTANNOUNCE_ONPLACED);
		ConfigMain.BLOCKS_LIGHTLEVEL_ENABLE = cfg.getBoolean("blocks.light-level.enable", ConfigMain.BLOCKS_LIGHTLEVEL_ENABLE);
		ConfigMain.BLOCKS_LIGHTLEVEL_ALERTONLY = cfg.getBoolean("blocks.light-level.alert-only-if-lower", ConfigMain.BLOCKS_LIGHTLEVEL_ALERTONLY);
		ConfigMain.BLOCKS_LIGHTLEVEL_PREVENTBREAK = cfg.getBoolean("blocks.light-level.prevent-break", ConfigMain.BLOCKS_LIGHTLEVEL_PREVENTBREAK);
		ConfigMain.BLOCKS_ALERTWRONGMSG = cfg.getBoolean("blocks.alert-wrong-messages", ConfigMain.BLOCKS_ALERTWRONGMSG);
		handleBlocks(cfg, "blocks.list");
		
		ConfigMain.STORAGE_FILENAME = cfg.getString("storage.file-name", ConfigMain.STORAGE_FILENAME);
		
		ConfigMain.COORDINATES_ENABLE = cfg.getBoolean("coordinates.enable", ConfigMain.COORDINATES_ENABLE);
		ConfigMain.COORDINATES_MINPLAYERS = cfg.getInt("coordinates.minimum-players", ConfigMain.COORDINATES_MINPLAYERS);
		ConfigMain.COORDINATES_RANDOM_ENABLE = cfg.getBoolean("coordinates.random-hide.randomize", ConfigMain.COORDINATES_RANDOM_ENABLE);
		ConfigMain.COORDINATES_RANDOM_COUNT = cfg.getInt("coordinates.random-hide.count", ConfigMain.COORDINATES_RANDOM_COUNT);
		ConfigMain.COORDINATES_RANDOM_PREFIX = cfg.getString("coordinates.random-hide.prefix", ConfigMain.COORDINATES_RANDOM_PREFIX);
		ConfigMain.COORDINATES_RANDOM_OBFUSCATE = cfg.getBoolean("coordinates.random-hide.obfuscate", ConfigMain.COORDINATES_RANDOM_OBFUSCATE);
		
		ConfigMain.LOG_ENABLE = cfg.getBoolean("log.enable", ConfigMain.LOG_ENABLE);
		ConfigMain.LOG_FORMAT = cfg.getString("log.format", ConfigMain.LOG_FORMAT);
		ConfigMain.LOG_PRINTCONSOLE = cfg.getBoolean("log.print-console", ConfigMain.LOG_PRINTCONSOLE);
		ConfigMain.LOG_MODE = cfg.getInt("log.mode", ConfigMain.LOG_MODE);
		ConfigMain.LOG_FILE = cfg.getString("log.file", ConfigMain.LOG_FILE);
	}
	private void handleBlocks(ConfigurationSection cfg, String path) {
		Set<OreBlock> blocks = new HashSet<>();
		
		ConfigurationSection csBlocks = cfg.getConfigurationSection(path);
		if (csBlocks != null) {
			for (String key : csBlocks.getKeys(false)) {
				Material mat = Material.getMaterial(key);
				if (mat != null) {
					OreBlock block = new OreBlock(mat);
					block.setLightLevel(csBlocks.getInt(key + ".max-light-level"));
					block.setSingle(csBlocks.getString(key + ".single"));
					block.setMultiple(csBlocks.getString(key + ".multiple"));
					block.setCoordSingle(csBlocks.getString(key + ".coordinates.single"));
					block.setCoordMultiple(csBlocks.getString(key + ".coordinates.multiple"));
					blocks.add(block);
				} else {
					// Normal log, LoggerManager will give error
					plugin.log(ConsoleColor.RED.getCode() + Constants.DEBUG_CFG_WRONGBLOCK
							.replace("{block}", key));
				}
			}
			ConfigMain.BLOCKS_LIST = blocks;
		}
	}
	
	private void reloadMessages() {
		File msgFile = new File(plugin.getDataFolder(), "messages.yml");
		if (!msgFile.exists()) {
			plugin.saveResource("messages.yml", true);
		}
		FileConfiguration msg = YamlConfiguration.loadConfiguration(msgFile);
		
		if (msg.getInt("dont-edit-this.messages-version") < Constants.VERSION_MESSAGES)
			plugin.log(Constants.MESSAGES_OUTDATED);
		
		loadMessages(msg);
	}
	private void loadMessages(FileConfiguration msg) {
		Messages.OREANNOUNCER_UPDATEAVAILABLE = msg.getString("oreannouncer.update-available", Messages.OREANNOUNCER_UPDATEAVAILABLE);
		Messages.OREANNOUNCER_NOPERMISSION = msg.getString("oreannouncer.no-permission", Messages.OREANNOUNCER_NOPERMISSION);
		Messages.OREANNOUNCER_CONFIGURATIONRELOADED = msg.getString("oreannouncer.configuration-reloaded", Messages.OREANNOUNCER_CONFIGURATIONRELOADED);
		Messages.OREANNOUNCER_NOCONSOLE = msg.getString("oreannouncer.no-console", Messages.OREANNOUNCER_NOCONSOLE);
		Messages.OREANNOUNCER_NOTFOUND = msg.getString("oreannouncer.not-found", Messages.OREANNOUNCER_NOTFOUND);
		Messages.OREANNOUNCER_HELP = msg.getStringList("oreannouncer.help");
		
		Messages.BLOCKS_PREVENTBREAK = msg.getString("blocks.prevent-break", Messages.BLOCKS_PREVENTBREAK);
		
		Messages.HIDEALERT_ON = msg.getString("hide-alert.set-on", Messages.HIDEALERT_ON);
		Messages.HIDEALERT_OFF = msg.getString("hide-alert.set-off", Messages.HIDEALERT_OFF);
	}
}
