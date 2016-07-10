package com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantSetNewProfileImageException;

import java.io.Serializable;

/**
 * The interface <code>IntraWalletUser</code>
 * defines the methods related to the extraction of the information of an intra user
 */
public interface IntraWalletUserIdentity extends ActiveActorIdentityInformation,Serializable {

    /**
     * The method <code>getPhrase</code> returns the phrase created by the intra user
     * @return string phrase object
     */
    String getPhrase();

    /**
     * The method <code>getAccuracy</code> returns the phrase created by the intra user
     * @return string phrase object
     */
    long getAccuracy();

    /**
     * The method <code>getFrequency</code> returns the phrase created by the intra user
     * @return string phrase object
     */
    Frequency getFrequency();

    Location getLocation();
    
    /**
     * The method <code>setNewProfileImage</code> let the user set a new profile image
     *
     * @param newProfileImage the new profile image to set
     * @throws CantSetNewProfileImageException
     */
    void setNewProfileImage(byte[] newProfileImage);

    /**
     * This method let an intra user sign a message with his unique private key
     * @param message the message to sign
     * @return the signature
     * @throws com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantSignIntraWalletUserMessageException
     */
    String createMessageSignature(String message);


}
