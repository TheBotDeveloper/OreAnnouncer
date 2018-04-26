package com.alessiodp.oreannouncer.configuration.data;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;

import com.alessiodp.oreannouncer.utils.OreBlock;

public class ConfigMain {
	
	public static boolean OREANNOUNCER_UPDATES_CHECK;
	public static boolean OREANNOUNCER_UPDATES_WARN;
	
	public static boolean BLOCKS_PREVENTANNOUNCE_ONPLACED;
	public static boolean BLOCKS_LIGHTLEVEL_ENABLE;
	public static boolean BLOCKS_LIGHTLEVEL_ALERTONLY;
	public static boolean BLOCKS_LIGHTLEVEL_PREVENTBREAK;
	public static boolean BLOCKS_ALERTWRONGMSG;
	public static Set<OreBlock> BLOCKS_LIST;
	
	public static String STORAGE_FILENAME;
	
	public static boolean COORDINATES_ENABLE;
	public static int COORDINATES_MINPLAYERS;
	public static boolean COORDINATES_RANDOM_ENABLE;
	public static int COORDINATES_RANDOM_COUNT;
	public static String COORDINATES_RANDOM_PREFIX;
	public static boolean COORDINATES_RANDOM_OBFUSCATE;
	
	public static boolean LOG_ENABLE;
	public static String LOG_FORMAT;
	public static boolean LOG_PRINTCONSOLE;
	public static int LOG_MODE;
	public static String LOG_FILE;
	
	public ConfigMain() {
		loadDefaults();
	}
	public void loadDefaults() {
		OREANNOUNCER_UPDATES_CHECK = true;
		OREANNOUNCER_UPDATES_WARN = true;
		
		BLOCKS_PREVENTANNOUNCE_ONPLACED = true;
		BLOCKS_LIGHTLEVEL_ENABLE = false;
		BLOCKS_LIGHTLEVEL_ALERTONLY = true;
		BLOCKS_LIGHTLEVEL_PREVENTBREAK = false;
		BLOCKS_ALERTWRONGMSG = true;
		BLOCKS_LIST = new HashSet<>();
		BLOCKS_LIST.add(new OreBlock(Material.DIAMOND_ORE,
				100,
				"&6%player% &efound %number% diamond.",
				"&6%player% &efound %number% diamonds.",
				"&6%player% &efound %number% diamond. &7[x:%x%&7 z:%z%&7]",
				"&6%player% &efound %number% diamonds. &7[x:%x%&7 z:%z%&7]"));
		BLOCKS_LIST.add(new OreBlock(Material.EMERALD_ORE,
				0,
				"&6%player% &efound %number% emerald.",
				"&6%player &efound %number% emeralds.",
				null,
				null));
		
		STORAGE_FILENAME = "data.yml";
		
		COORDINATES_ENABLE = true;
		COORDINATES_MINPLAYERS = 0;
		COORDINATES_RANDOM_ENABLE = true;
		COORDINATES_RANDOM_COUNT = 1;
		COORDINATES_RANDOM_PREFIX = "&k";
		COORDINATES_RANDOM_OBFUSCATE = true;
		
		LOG_ENABLE = false;
		LOG_FORMAT = "%date% [%time%] %message%";
		LOG_PRINTCONSOLE = true;
		LOG_MODE = 2;
		LOG_FILE = "log.txt";
	}
}
