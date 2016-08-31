/*
 * @#PackageTypeNotSupportedException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception;


import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.exception.PackageTypeNotSupportedException</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 21/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PackageTypeNotSupportedException extends FermatException {

    /**
     * Constructor with parameters
     *
     * @param message
     */
    public PackageTypeNotSupportedException(final String message) {
        this(message, null, "", "");
    }

	/**
	 * Constructor with parameters
	 *
	 * @param message
	 * @param cause
	 * @param context
	 * @param possibleReason
	 */
	public PackageTypeNotSupportedException(final String message, final Exception cause, final String context, final String possibleReason) {
		super(message, cause, context, possibleReason);
	}

}
