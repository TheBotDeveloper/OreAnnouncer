package com.alessiodp.oreannouncer.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.utils.ConsoleColor;
import com.alessiodp.oreannouncer.logging.LogLevel;

public class DatabaseManager {
	private OreAnnouncer plugin;
	private File dataFile;
	private FileConfiguration data;
	
	public DatabaseManager(OreAnnouncer instance) {
		plugin = instance;
	}
	
	public void reload() {
		dataFile = createDataFile();
		data = YamlConfiguration.loadConfiguration(dataFile);
	}
	
	private File createDataFile() {
		File ret = new File(plugin.getDataFolder(), ConfigMain.STORAGE_FILENAME);
		if (!ret.exists()) {
			try {
				YamlConfiguration file = new YamlConfiguration();
				file.createSection("players-hidealert");
				file.save(ret);
			} catch (Exception ex) {
				LoggerManager.log(LogLevel.BASE, Constants.DEBUG_STORAGE_CREATEFAIL
						.replace("{error}", ex.getMessage()), true, ConsoleColor.RED);
			}
		}
		return ret;
	}
	
	public void saveHideAlert(List<UUID> list) {
		List<String> stringList = new ArrayList<>();
		for (UUID u : list) {
			stringList.add(u.toString());
		}
		data.set("players-hidealert", stringList);
		
		try {
			data.save(dataFile);
		} catch (IOException ex) {
			LoggerManager.log(LogLevel.BASE, Constants.DEBUG_STORAGE_ERROR_SAVEHIDEALERT
					.replace("{error}", ex.getMessage()), true, ConsoleColor.RED);
		}
	}
	public List<UUID> getHideAlert() {
		List<UUID> ret = new ArrayList<>();
		List<String> list = data.getStringList("players-hidealert");
		
		for (String spy : list) {
			try {
				ret.add(UUID.fromString(spy));
			} catch (Exception ex) {
				LoggerManager.log(LogLevel.BASE, Constants.DEBUG_STORAGE_ERROR_GETHIDEALERT
						.replace("{error}", ex.getMessage()), true, ConsoleColor.RED);
			}
		}
		return ret;
	}
}
