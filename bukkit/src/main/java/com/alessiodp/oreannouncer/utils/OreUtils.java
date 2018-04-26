package com.alessiodp.oreannouncer.utils;

import com.alessiodp.oreannouncer.configuration.data.ConfigMain;
import org.bukkit.Material;

public class OreUtils {
	
	public static OreBlock getOreBlockByMaterial(Material material) {
		OreBlock ret = null;
		for (OreBlock ob : ConfigMain.BLOCKS_LIST) {
			if (material.equals(ob.getType())) {
				ret = ob;
				break;
			}
		}
		return ret;
	}
}
