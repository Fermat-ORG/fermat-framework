package com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.identity.common.IdentityUserInformation;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;

/**
 * The interface <code>IntraUserInformation</code>
 * provides the method to extract information about an intra user.
 */
public interface IntraUserInformation extends IdentityUserInformation {

    /**
     * The method <code>getPublicKet</code> returns the public key of the represented Intra User
     * @return the public key of the intra user
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> returns the name of the represented intra user
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     *The method <code>getPhrase</code> returns the phrase of the intra user
     * @return the phrase of the intra user
     */
    String getPhrase();
    /**
     * The method <code>getCity</code> returns the City of the represented intra user
     *
     * @return the name of the intra user
     */
   // public String getCity();
    /**
     * The method <code>getCountry</code> returns the Country of the represented intra user
     *
     * @return the name of the intra user
     */
   // public String getCountry();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the represented intra user
     *
     * @return the profile image
     */
    byte[] getProfileImage();

    /**
     * The method <code>getConnectionState</code> returns the Connection State Status
     * @return ConnectionState object
     */
    ConnectionState getConnectionState();

    public ProfileStatus getState();

    void setProfileImageNull();

    /**
     * The method <code>getContactRegistrationDate</code> returns the Connection Registration Date
     * @return
     */
    long getContactRegistrationDate();
}
