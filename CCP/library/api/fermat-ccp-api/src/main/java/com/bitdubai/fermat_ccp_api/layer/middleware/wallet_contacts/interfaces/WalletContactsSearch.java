package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;

import java.util.List;

/**
 * The Class <code>WalletContactsSearch</code>
 * contains the necessary functionality to search a wallet contact.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 04/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactsSearch {

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
     * @return a list of instances of wallet contact
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<WalletContactRecord> getResult() throws CantGetAllWalletContactsException;

    /**
     * get the result of the search with pagination params
     *
     * @param max    of results
     * @param offset pointer
     * @return a list of instances of wallet contact
     * @throws CantGetAllWalletContactsException if something goes wrong
     */
    List<WalletContactRecord> getResult(int max, int offset) throws CantGetAllWalletContactsException;

    /**
     * reset filters
     */
    void resetFilters();
}
