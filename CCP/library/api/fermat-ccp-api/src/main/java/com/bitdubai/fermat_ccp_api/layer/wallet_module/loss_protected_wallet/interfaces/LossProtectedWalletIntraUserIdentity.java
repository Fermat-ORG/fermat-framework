package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces;

/**
 * The interface <code>CryptoWalletIntraUserIdentity</code>
 * haves all consumable methods of a crypto wallet intra user identity.
 * <p/>
 *
 * Created by Natalia on 2016.03.04.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface LossProtectedWalletIntraUserIdentity  {

    /**
     * The method <code>getPublicKey</code> returns the public key of the identity.
     * @return a string containing the identity public key.
     */
    String getPublicKey();

    /**
     * The method <code>getAlias</code> returns the alias of the identity.
     * @return a string with the alias of the identity.
     */
    String getAlias();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the identity.
     * @return the profile image in byte array format
     */
    byte[] getProfileImage();

}
