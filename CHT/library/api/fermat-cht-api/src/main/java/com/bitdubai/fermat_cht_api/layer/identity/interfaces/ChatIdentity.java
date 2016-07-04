package com.bitdubai.fermat_cht_api.layer.identity.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;

import java.io.Serializable;

/**
 * Created by franklin on 29/03/16.
 */
public interface ChatIdentity  extends ActiveActorIdentityInformation, Serializable {
    /**
     * The method <code>setNewProfileImage</code> let the user set a new profile image
     *
     * @param newProfileImage the new profile image to set
     * @throws
     */
    void setNewProfileImage(byte[] newProfileImage);

    /**
     * This method let an intra user sign a message with his unique private key
     * @param message the message to sign
     * @return the signature
     */
    String createMessageSignature(String message) ;

    /**
     * This method return boolean
     * @return the boolena
     */
    boolean getIsPaymetForChat();


    /**
     * This method return String with Country
     * @return the String
     */
    String getCountry();

    /**
     * This method return String with State
     * @return the String
     */
    String getState();

    /**
     * This method return String with City
     * @return the String
     */
    String getCity();

    /**
     * This method return String with ConnectionState
     * @return the String
     */
    String getConnectionState();

    /**
     * This method return long with Acurancy
     * @return the Long
     */
    long getAccuracy();

    /**
     * This method return enum with GeoFrequency
     * @return the Enum GeoFrequency
     */
    GeoFrequency getFrecuency();

}
