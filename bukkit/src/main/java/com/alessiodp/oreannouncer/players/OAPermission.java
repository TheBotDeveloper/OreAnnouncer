package com.alessiodp.oreannouncer.players;

public enum OAPermission {
	ALERTUPDATES("oreannouncer.alertupdates"),
	BYPASS_ANNOUNCE("oreannouncer.bypass.announce"),
	BYPASS_PLACE("oreannouncer.bypass.place"),
	CMD_HELP("oreannouncer.cmd.help"),
	CMD_HIDE("oreannouncer.cmd.hide"),
	CMD_RELOAD("oreannouncer.cmd.reload"),
	SEE("oreannouncer.see");
	
	private final String perm;
	OAPermission(String t) {
		perm = t;
	}
	
	@Override
	public String toString() {
		return perm;
	}
}
