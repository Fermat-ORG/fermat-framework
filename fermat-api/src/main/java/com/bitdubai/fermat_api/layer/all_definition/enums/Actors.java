package com.bitdubai.fermat_api.layer.all_definition.enums;

public enum Actors {

	DEVICE_USER ("DUS"),
	INTRA_USER ("IUS"),
	EXTRA_USER ("EUS"),
	SHOP ("SHP");

	private final String code;

	Actors(String code) {
		this.code = code;
	}

	public String getCode() { return this.code ; }

	public static Actors getByCode(String code) {

		switch (code) {
			case "DUS": return Actors.DEVICE_USER;
			case "IUS": return Actors.INTRA_USER;
			case "EUS": return Actors.EXTRA_USER;
			case "SHP": return Actors.SHOP;
		}

		/**
		 * Return by default.
		 */
		return Actors.DEVICE_USER;
	}
}
