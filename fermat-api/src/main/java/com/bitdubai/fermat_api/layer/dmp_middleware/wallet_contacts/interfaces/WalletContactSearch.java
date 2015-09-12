package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactSearch</code>
 * haves the necessary functionality for search a wallet contact.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactSearch {

    /**
     * set an actor alias to search
     */
    void setActorAlias(String actorAlias, boolean order);

    /**
     * set an actor first name to search
     */
    void setActorFirstName(String actorFirstName, boolean order);

    /**
     * set an actor last name to search
     */
    void setActorLastName(String actorLastName, boolean order);

    /**
     * get the result of the search
     *
     * @param walletPublicKey of the wallet in which we're working
     * @return a list of instances of wallet contact
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<WalletContactRecord> getResult(String walletPublicKey) throws CantGetAllWalletContactsException;

    /**
     * get the result of the search with pagination params
     *
     * @param walletPublicKey of the wallet in which we're working
     * @param max of results
     * @param offset pointer
     * @return a list of instances of wallet contact
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<WalletContactRecord> getResult(String walletPublicKey, int max, int offset) throws CantGetAllWalletContactsException;

}
