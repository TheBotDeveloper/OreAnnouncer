package com.alessiodp.oreannouncer.configuration;

public class Constants {
	public static final int CURSE_PROJECT_ID = 255518;
	
	public static final String BLOCK_METADATA = "OreAnnouncer_counted";
	
	public static final int VERSION_CONFIG = 6;
	public static final int VERSION_MESSAGES = 1;
	
	public static final String CONFIGURATION_OUTDATED = "Configuration file outdated";
	public static final String MESSAGES_OUTDATED = "Messages file outdated";
	
	/*
	 * Updater
	 */
	public static final String UPDATER_FOUND = "OreAnnouncer v{currentVersion} found a new version: {newVersion}";
	public static final String UPDATER_FAILED_IO = "OreAnnouncer could not contact alessiodp.com for updating.";
	public static final String UPDATER_FAILED_GENERAL = "OreAnnouncer could not check for updates.";
	public static final String UPDATER_FALLBACK = "OreAnnouncer will use Gravity Updater to check for updates.";
	public static final String UPDATER_URL = "https://api.alessiodp.com/version.php?plugin=oreannouncer&version={version}";
	public static final String UPDATER_FIELD_VERSION = "version";
	public static final String UPDATER_DELIMITER_TYPE = "\\-";
	public static final String UPDATER_DELIMITER_VERSION = "\\.";
	
	/*
	 * Log
	 */
	public static final String DEBUG_OA_ENABLING = "Initializing OreAnnouncer {version}";
	public static final String DEBUG_OA_ENABLED = "OreAnnouncer v{version} enabled";
	public static final String DEBUG_OA_DISABLED = "OreAnnouncer disabled";
	public static final String DEBUG_OA_DISABLED_LOG = "========== OreAnnouncer disabled - End of Log ==========";
	public static final String DEBUG_OA_LOGERROR = "Error on sending log message: {error}";
	
	public static final String DEBUG_CFG_WRONGBLOCK = "Cannot find the block '{block}'";
	
	public static final String DEBUG_CMD_RELOAD = "Configuration reloaded";
	public static final String DEBUG_CMD_HIDE = "Player '{player}' hide alert set on {value}";
	
	public static final String DEBUG_BLOCKPLACED = "Set metadata to {ore} placed by {player}";
	public static final String DEBUG_BLOCKFOUND = "Found '{ore}' block by {player}";
	public static final String DEBUG_BLOCKMESSAGE_NOTFOUND = "Cannot find the message for the ore '{ore}'";
	
	public static final String DEBUG_STORAGE_CREATEFAIL = "Failed to create data file: {error}";
	public static final String DEBUG_STORAGE_ERROR_SAVEHIDEALERT = "Error on saving hide alerts list: {error}";
	public static final String DEBUG_STORAGE_ERROR_GETHIDEALERT = "Error on getting hide alerts list: {error}";
}
