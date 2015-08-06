/*
 * @#ComponentPublishedInformation.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */

package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.enums.ComponentPublishedInformationTypes;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_publisher.interfaces.ComponentPublishedInformation</code> define
 * the static information about a published component.
 * <p/>
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 * @author Update by Roberto Requena - (rart3001@gmail.com) on 06/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface ComponentPublishedInformation {

    /**
     * This method gives us the version of the represented wallet
     *
     * @return the wallet version represented as a String
     */
    public String getVersion();

    /**
     * This method gives us the associated wallet factory project Id
     *
     * @return the associated wallet factory project id
     */
    public UUID getWalletFactoryProjectId();

    /**
     * This method gives us the associated wallet factory project name
     *
     * @return the associated wallet factory project name
     */
    public String getWalletFactoryProjectName();

    /**
     * This method gives us the id of the wallet version published in the catalogue
     *
     * @return the identifier of the version of the wallet used in the wallet catalogue of the wallet store
     */
    public UUID getWalletId();

    /**
     * This method gives us the timestamp of the publication of the first version of the wallet
     *
     * @return the timestamp represented as the result of System.currentTimeMillis()
     */
    public long getPublicationTimestamp();

    /**
     * This method gives us the timestamp of the publication of the version of wallet
     *
     * @return the timestamp represented as the result of System.currentTimeMillis()
     */
    public long getVersionTimestamp();



    public ComponentPublishedInformationTypes getType();


    public ComponentPublishedInformationStatus getStatus();
}
