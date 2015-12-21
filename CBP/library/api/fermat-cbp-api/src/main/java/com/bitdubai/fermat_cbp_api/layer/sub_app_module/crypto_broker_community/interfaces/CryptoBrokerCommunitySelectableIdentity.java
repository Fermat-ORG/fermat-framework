package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;

/**
 * The interface <code>IntraUserLoginIdentity</code>
 * provides the methods to get the information of an identity a user can use to select.
 * 
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public interface CryptoBrokerCommunitySelectableIdentity {

    /**
     * The method  <code>getAlias</code> returns the alias of the crypto broker identity
     *
     * @return the alias of the crypto broker
     */
    String getAlias();

    /**
     * The method  <code>getActorType</code> returns the actor type of the crypto broker identity
     *
     * @return an element of the actors enum representing the actor type of the crypto broker selectable identity.
     */
    Actors getActorType();

    /**
     * The method  <code>getPublicKey</code> returns the public key of the crypto broker identity
     *
     * @return the public key of the crypto broker
     */
    String getPublicKey();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the crypto broker identity
     *
     * @return the profile image of the crypto broker
     */
    byte[] getProfileImage();

    /**
     * The method <code>select</code> you can select an identity to work with.
     *
     * @return the profile image of the crypto broker
     */
    void select() throws CantSelectIdentityException;

}
