package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

/**
 * The interface <code>IntraUserLoginIdentity</code>
 * provides the methods to get the information of an identity a user can use to select.
 * 
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public interface CryptoBrokerLoginIdentity {

    /**
     * The method  <code>getAlias</code> returns the alias of the crypto broker identity
     *
     * @return the alias of the crypto broker
     */
    String getAlias();

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

}
