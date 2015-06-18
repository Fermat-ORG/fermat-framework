package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.WalletContact</code>
 * indicates the functionality of a WalletContact and its attributes
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContact {

    /**
     * Return the contactId
     *
     * @return UUID
     */
    public UUID getContactId();

    /**
     * Return the walletId
     *
     * @return UUID
     */
    public UUID getWalletId();


    /**
     * Return the actorType
     *
     * @return Actors
     */
    public Actors getActorType();

    /**
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    public CryptoAddress getReceivedCryptoAddress();

    /**
     * Return the actorId
     *
     * @return UUID
     */
    public UUID getActorId();

    /**
     * Return the actorName
     *
     * @return String
     */
    public String getActorName();

}
