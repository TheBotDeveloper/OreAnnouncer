package com.alessiodp.oreannouncer.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.alessiodp.oreannouncer.OreAnnouncer;
import com.alessiodp.oreannouncer.configuration.Constants;
import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import com.alessiodp.oreannouncer.utils.ConsoleColor;

public class LoggerManager {
	private static OreAnnouncer plugin;
	private static LogLevel logLevel;
	
	public LoggerManager(OreAnnouncer instance) {
		plugin = instance;
	}
	
	public static void reload() {
		logLevel = LogLevel.getEnum(ConfigMain.LOG_MODE);
	}
	
	public static void log(LogLevel level, String message, boolean printConsole) {
		log(level, message, printConsole, null);
	}
	
	public static void log(LogLevel level, String message, boolean printConsole, ConsoleColor color) {
		if (printConsole) {
			if (level.equals(LogLevel.BASE) || (ConfigMain.LOG_PRINTCONSOLE
					&& level.getLevel() <= logLevel.getLevel())) {
				// Preparing message to print
				String print = message;
				if (color != null)
					print = color.getCode() + print;
				if (level.equals(LogLevel.DEBUG))
					print = "[" + level.getLevel() + "] " + print;
				
				// Print it
				plugin.log(print);
			}
		}
		
		if (ConfigMain.LOG_ENABLE && level.getLevel() <= logLevel.getLevel()) {
			logToFile(level, message);
		}
	}
	
	private static void logToFile(LogLevel level, String message) {
		try {
			File file = new File(plugin.getDataFolder(), ConfigMain.LOG_FILE);
			if (!file.exists())
				file.createNewFile();
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(plugin.getDataFolder() + "/" + ConfigMain.LOG_FILE, true)));
			out.println(handleMessage(level, message));
			out.close();
		} catch (IOException ex) {
			log(LogLevel.BASE, Constants.DEBUG_OA_LOGERROR
					.replace("{error}", ex.getMessage()), true, ConsoleColor.RED);
		}
	}
	
	private static String handleMessage(LogLevel level, String message) {
		SimpleDateFormat sdf;
		String txt = ConfigMain.LOG_FORMAT;
		if (txt.contains("%date%")) {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			txt = txt.replace("%date%", sdf.format(Calendar.getInstance().getTime()));
		}
		if (txt.contains("%time%")) {
			sdf = new SimpleDateFormat("HH:mm:ss");
			txt = txt.replace("%time%", sdf.format(Calendar.getInstance().getTime()));
		}
		if (txt.contains("%level%"))
			txt = txt.replace("%level%", Integer.toString(level.getLevel()));
		if (txt.contains("%message%"))
			txt = txt.replace("%message%", message);
		return txt;
	}
}
