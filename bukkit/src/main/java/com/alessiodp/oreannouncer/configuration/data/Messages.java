package com.alessiodp.oreannouncer.configuration.data;

import java.util.ArrayList;
import java.util.List;

public class Messages {
	
	public static String OREANNOUNCER_UPDATEAVAILABLE;
	public static String OREANNOUNCER_NOPERMISSION;
	public static String OREANNOUNCER_CONFIGURATIONRELOADED;
	public static String OREANNOUNCER_NOCONSOLE;
	public static String OREANNOUNCER_NOTFOUND;
	public static List<String> OREANNOUNCER_HELP;
	
	public static String BLOCKS_PREVENTBREAK;
	
	public static String HIDEALERT_ON;
	public static String HIDEALERT_OFF;
	
	public Messages() {
		loadDefaults();
	}
	
	public void loadDefaults() {
		OREANNOUNCER_UPDATEAVAILABLE = "&9New version of OreAnnouncer found: %version% (Current: %thisversion%)";
		OREANNOUNCER_NOPERMISSION = "&cYou do not have access to that command";
		OREANNOUNCER_CONFIGURATIONRELOADED = "&aConfiguration reloaded";
		OREANNOUNCER_NOCONSOLE = "You need to be a player to perform this command";
		OREANNOUNCER_NOTFOUND = "&cCommand not found";
		OREANNOUNCER_HELP = new ArrayList<>();
		OREANNOUNCER_HELP.add("&7===============[ &2OreAnnouncer &7]===============");
		OREANNOUNCER_HELP.add("&a/oa help &7- Print this help page");
		OREANNOUNCER_HELP.add("&a/oa hide &7- Hide announces");
		OREANNOUNCER_HELP.add("&a/oa reload &7- Reload the configuration");
		
		BLOCKS_PREVENTBREAK = "&cThere is too low light, you cannot break it!";
		
		HIDEALERT_ON = "&aHide alerts set to true";
		HIDEALERT_OFF = "&aHide alerts set to false";
	}
}
