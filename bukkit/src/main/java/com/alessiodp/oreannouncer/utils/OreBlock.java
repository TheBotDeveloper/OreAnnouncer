package com.alessiodp.oreannouncer.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

public class OreBlock {
	@Getter private Material type;
	@Getter @Setter private int lightLevel;
	@Getter @Setter private String single;
	@Getter @Setter private String multiple;
	@Getter @Setter private String coordSingle;
	@Getter @Setter private String coordMultiple;
	
	public OreBlock(Material mat) {
		type = mat;
	}
	public OreBlock(Material material, int light, String singleMessage, String multipleMessage, String coordinateSingle, String coordinateMultiple) {
		type = material;
		lightLevel = light;
		single = singleMessage;
		multiple = multipleMessage;
		coordSingle = coordinateSingle;
		coordMultiple = coordinateMultiple;
	}
}
