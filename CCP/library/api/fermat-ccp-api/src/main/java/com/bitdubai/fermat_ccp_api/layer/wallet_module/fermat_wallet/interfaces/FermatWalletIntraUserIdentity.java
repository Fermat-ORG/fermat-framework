package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

/**
 * The interface <code>FermatWalletIntraUserIdentity</code>
 * haves all consumable methods of a crypto wallet intra user identity.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatWalletIntraUserIdentity {

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
