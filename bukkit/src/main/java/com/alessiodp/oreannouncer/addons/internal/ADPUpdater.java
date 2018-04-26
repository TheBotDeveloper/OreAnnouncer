package com.alessiodp.oreannouncer.addons.internal;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.addons.external.GravityUpdaterHandler;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import com.alessiodp.oreannouncer.configuration.data.Messages;
import com.alessiodp.oreannouncer.logging.LoggerManager;
import com.alessiodp.oreannouncer.utils.ConsoleColor;
import com.alessiodp.oreannouncer.logging.LogLevel;
import com.alessiodp.oreannouncer.players.OAPermission;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ADPUpdater {
	private static OreAnnouncer plugin;
	
	@Getter
	private static String foundVersion = "";
	
	public ADPUpdater(OreAnnouncer instance) {
		plugin = instance;
	}
	
	public static void alertPlayers() {
		if (ConfigMain.OREANNOUNCER_UPDATES_CHECK
				&& ConfigMain.OREANNOUNCER_UPDATES_WARN
				&& !foundVersion.isEmpty()) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				alertSinglePlayer(p);
			}
		}
	}
	public static void alertPlayer(Player player) {
		if (ConfigMain.OREANNOUNCER_UPDATES_CHECK
				&& ConfigMain.OREANNOUNCER_UPDATES_WARN
				&& !foundVersion.isEmpty()) {
			alertSinglePlayer(player);
		}
	}
	private static void alertSinglePlayer(Player player) {
		if (player.hasPermission(OAPermission.ALERTUPDATES.toString())) {
			plugin.getPlayerHandler().sendMessage(player, Messages.OREANNOUNCER_UPDATEAVAILABLE
					.replace("%version%", foundVersion)
					.replace("%thisversion%", plugin.getDescription().getVersion()));
		}
	}
	
	public static void asyncTaskCheckUpdates() {
		if (ConfigMain.OREANNOUNCER_UPDATES_CHECK) {
			plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
				checkUpdates();
			}, 20, 20*60*60*24); // 24 hours
		}
	}
	
	public static void asyncCheckUpdates() {
		if (ConfigMain.OREANNOUNCER_UPDATES_CHECK) {
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
				checkUpdates();
			}); // 24 hours
		}
	}
	
	
	private static void checkUpdates() {
		foundVersion = "";
		String version;
		JSONObject data = getVersionInfo();
		if (data != null) {
			version = (String) data.get(Constants.UPDATER_FIELD_VERSION);
		} else {
			// ADP Updater failed
			plugin.log(Constants.UPDATER_FALLBACK);
			version = getUpdatesFromFallback();
		}
		
		if (checkVersion(version, plugin.getDescription().getVersion())) {
			// New version found
			foundVersion = version;
			
			LoggerManager.log(LogLevel.BASE, Constants.UPDATER_FOUND
					.replace("{currentVersion}", plugin.getDescription().getVersion())
					.replace("{newVersion}", foundVersion), true, ConsoleColor.GREEN);
			alertPlayers();
		}
	}
	
	private static String getUpdatesFromFallback() {
		String ret = "";
		GravityUpdaterHandler updater = new GravityUpdaterHandler(plugin,
				Constants.CURSE_PROJECT_ID,
				null,
				GravityUpdaterHandler.UpdateType.NO_DOWNLOAD,
				false);
		if (updater.getResult() == GravityUpdaterHandler.UpdateResult.UPDATE_AVAILABLE) {
			String[] split = updater.getLatestName().split(GravityUpdaterHandler.DELIMETER);
			ret = split[split.length - 1];
		}
		return ret;
	}
	
	private static JSONObject getVersionInfo() {
		JSONObject ret = null;
		try {
			URLConnection conn = new URL(Constants.UPDATER_URL
					.replace("{version}", plugin.getDescription().getVersion())).openConnection();
			conn.setConnectTimeout(10000);
			conn.addRequestProperty("User-Agent", "ADP Updater");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			ret = (JSONObject) JSONValue.parse(br.readLine());
			br.close();
		} catch (IOException ex) {
			plugin.log(Constants.UPDATER_FAILED_IO);
		} catch (Exception ex) {
			plugin.log(Constants.UPDATER_FAILED_GENERAL);
		}
		return ret;
	}
	
	private static boolean checkVersion(String ver, String compareWith) {
		boolean ret = false;
		String[] splitVer = splitVersion(ver);
		String[] splitCompareWith = splitVersion(compareWith);
		
		try {
			for (int c=0; c < splitVer.length && !ret; c++) {
				int a = Integer.parseInt(splitVer[c]);
				int b = c < splitCompareWith.length ? Integer.parseInt(splitCompareWith[c]) : 0;
				if (a > b)
					ret = true;
				else if (a < b)
					break;
			}
		} catch (Exception ex) {
			ret = true;
		}
		return ret;
	}
	
	private static String[] splitVersion(String value) {
		value = value.split(Constants.UPDATER_DELIMITER_TYPE)[0];
		return value.split(Constants.UPDATER_DELIMITER_VERSION);
	}
}
