package com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>CantSetPhotoException</code>
 * is thrown when we cant set a photo.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantSetPhotoException extends FermatException {

	public static final String DEFAULT_MESSAGE = "CAN'T SET PHOTO EXCEPTION";

	public CantSetPhotoException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

	public CantSetPhotoException(final String message, final Exception cause) {
		this(message, cause, "", "");
	}

	public CantSetPhotoException(final String message) {
		this(message, null);
	}

	public CantSetPhotoException(final Exception exception) {
		this(exception.getMessage());
		setStackTrace(exception.getStackTrace());
	}

	public CantSetPhotoException() {
		this(DEFAULT_MESSAGE);
	}

}
