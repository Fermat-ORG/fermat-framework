package org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantSingMessageException;

import java.io.Serializable;

import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;

/**
 * Created by Nerio on 07/09/15.
 * Modified by Franklin 03/11/2015
 */
public interface RedeemPointIdentity extends ActiveActorIdentityInformation, Serializable {

    /**
     * The method <code>setNewProfileImage</code> let the user set a new profile image
     *
     * @param newProfileImage the new profile image to set
     * @throws
     */
    void setNewProfileImage(byte[] newProfileImage);

    /**
     * This method let an intra user sign a message with his unique private key
     *
     * @param message the message to sign
     * @return the signature
     * @throws CantSingMessageException
     */
    String createMessageSignature(String message);

    String getContactInformation();

    String getCountryName();

    String getProvinceName();

    String getCityName();

    String getPostalCode();

    String getStreetName();

    String getHouseNumber();

    int getAccuracy();

    GeoFrequency getFrequency();
}
