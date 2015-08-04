package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

public enum Actors {

	DEVICE_USER ("DUS"),
	INTRA_USER ("IUS"),
	EXTRA_USER ("EUS"),
	SHOP ("SHP");

	private String code;

	Actors(String code) {
		this.code = code;
	}

	public String getCode() { return this.code ; }

	public static Actors getByCode(String code)/*throws InvalidParameterException*/ {

		switch (code) {
			case "DUS": return Actors.DEVICE_USER;
			case "IUS": return Actors.INTRA_USER;
			case "EUS": return Actors.EXTRA_USER;
			case "SHP": return Actors.SHOP;
            //Modified by Manuel Perez on 03/08/2015
			//default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Actors enum");
		}

		/**
		 * Return by default.
		 */
		return Actors.DEVICE_USER;
	}
}
