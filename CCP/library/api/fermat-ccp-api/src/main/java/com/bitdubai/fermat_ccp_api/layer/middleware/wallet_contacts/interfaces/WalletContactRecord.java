package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.WalletContactRecord</code>
 * indicates the functionality of a WalletContactRecord and its attributes
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactRecord extends Serializable {

    /**
     * Return the contact id
     *
     * @return UUID
     */
    UUID getContactId();

    /**
     * Return the walletPublicKey
     *
     * @return String
     */
    String getWalletPublicKey();

    /**
     * Return the actor public key
     *
     * @return String
     */
    String getActorPublicKey();

    /**
     * Return the actorType
     *
     * @return Actors
     */
    Actors getActorType();

    /**
     * Return the actor alias
     *
     * @return String
     */
    String getActorAlias();

    /**
     * Return the actor first name
     *
     * @return String
     */
    String getActorFirstName();

    /**
     * Return the actor last name
     *
     * @return String
     */
    String getActorLastName();

    /**
     * Return the crypto addresses
     *
     * @return HashMap of CryptoAddresses of the wallet contact
     */
    HashMap<BlockchainNetworkType,CryptoAddress> getCryptoAddresses();

    Compatibility getCompatibility();

}
