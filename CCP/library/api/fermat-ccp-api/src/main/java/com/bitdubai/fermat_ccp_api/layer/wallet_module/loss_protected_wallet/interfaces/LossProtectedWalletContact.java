package com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>CryptoWalletWalletContact</code>
 * indicates the functionality of a CryptoWalletWalletContact and its attributes
 * <p/>
 *
 * Created by Natalia on 2016.03.04.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface LossProtectedWalletContact extends Serializable {

    /**
     * Return the contactId
     * @return UUID
     */
    UUID getContactId();

    /**
     * Return the walletPublicKey
     * @return String
     */
    String getWalletPublicKey();


    /**
     * Return the actorType
     * @return Actors
     */
    Actors getActorType();

    /**
     * Return if the contact is a intra user connection
     * @return true or false
     */
    boolean isConnection();

    /**
     * Return the Compatibility
     * @return Compatibility
     */
    Compatibility getCompatibility();

    /**
     * Return the deliveredCryptoAddress
     * @return a list of crypto addresses
     */
    HashMap<BlockchainNetworkType,CryptoAddress> getReceivedCryptoAddress();

    /**
     * Return the actor public key
     * @return UUID
     */
    String getActorPublicKey();

    /**
     * Return the actorName
     * @return String
     */
    String getActorName();

    /**
     * Return the profilePicture
     * @return byte[]
     */
    byte[] getProfilePicture();

    String toString();

}
