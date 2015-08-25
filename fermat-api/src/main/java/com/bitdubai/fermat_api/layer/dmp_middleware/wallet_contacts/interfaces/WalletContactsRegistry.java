package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.WalletContactsRegistry</code>
 * indicates the functionality of a WalletContactsRegistry
 * <p/>
 *
 * Created by loui on 18/02/15.
 * Reviewed by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactsRegistry {

    List<WalletContactRecord> listWalletContacts(String walletPublicKey) throws CantGetAllWalletContactsException;

    List<WalletContactRecord> listWalletContactsScrolling(String walletPublicKey, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    WalletContactRecord createWalletContact(UUID actorId, String actorName, Actors actorType, CryptoAddress receivedCryptoAddress, String walletPublicKey) throws CantCreateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    WalletContactRecord getWalletContactByNameAndWalletPublicKey(String actorName, String walletPublicKey) throws CantGetWalletContactException;

    List<WalletContactRecord>  getWalletContactByNameContainsAndWalletPublicKey(String actorName, String walletPublicKey) throws CantGetWalletContactException;

    List<WalletContactRecord> getWalletContactsByActorId(UUID actorId) throws CantGetAllWalletContactsException;

    WalletContactRecord getWalletContactByContactId(UUID contactId) throws CantGetWalletContactException, WalletContactNotFoundException;
}
