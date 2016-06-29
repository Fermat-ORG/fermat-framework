package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces;

import java.io.Serializable;

/**
 * The interface <code>FermatWalletIntraUserActor</code>
 * haves all consumable methods of a crypto wallet intra user actor.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface FermatWalletIntraUserActor extends Serializable {

    /**
     * The method <code>getPublicKey</code> returns the public key of the actor.
     * @return a string containing the actor public key.
     */
    String getPublicKey();

    /**
     * The method <code>getAlias</code> returns the alias of the actor.
     * @return a string with the alias of the actor.
     */
    String getAlias();

    /**
     * The method <code>getProfileImage</code> returns the profile image of the actor.
     * @return the profile image in byte array format
     */
    byte[] getProfileImage();

    /**
     * The method <code>isContact</code> informs us if the intra user is already contact in the wallet.
     * @return a boolean value indicating if the intra user is already a contact.
     */
    boolean isContact();

    boolean isSelected();

    void setSelected(boolean isSelected);

}
