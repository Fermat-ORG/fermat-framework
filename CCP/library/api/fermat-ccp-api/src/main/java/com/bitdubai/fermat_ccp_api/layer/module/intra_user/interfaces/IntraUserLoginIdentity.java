package com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces;

import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;

import java.io.Serializable;

/**
 * The interface <code>IntraUserLoginIdentity</code>
 * provides the methods to get the information of an identity a user can use to log in.
 */
public interface IntraUserLoginIdentity extends Serializable {



    /**
     * The method  <code>getAlias</code> returns the alias of the intra user identity
     *
     * @return the alias of the intra user
     */
    String getAlias();

    /**
     * The method  <code>getPublicKey</code> returns the public key of the intra user identity
     *
     * @return the public key of the intra user
     */
    String getPublicKey();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the intra user identity
     *
     * @return the profile image of the intra user
     */
    byte[] getProfileImage();

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

    /**
     * The method <code>getLocation</code> returns the intra user geolocation ubication
     * @return
     */
    Location getLocation();

}
