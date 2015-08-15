package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.*;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.CryptoWallet</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface CryptoWallet {

    /**
     * Contacts Fragment methods...
     */

    List<WalletContactRecord> listWalletContacts(String walletPublicKey) throws CantGetAllWalletContactsException;

    List<WalletContactRecord> listWalletContactsScrolling(String walletPublicKey, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, ReferenceWallet referenceWallet, String walletPublicKey) throws CantCreateWalletContactException;

    WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType,
                                            ReferenceWallet referenceWallet, String walletPublicKey, byte[] photo) throws CantCreateWalletContactException;

    void updateContactPhoto(UUID actorId, Actors actor, byte[] photo) throws CantUpdateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    List<WalletContactRecord> getWalletContactByNameContainsAndWalletId(String actorName, String walletPublicKey) throws CantGetWalletContactException;

    boolean isValidAddress(CryptoAddress cryptoAddress);

    /**
     * Balance Fragment methods
     */
    long getAvailableBalance(String walletPublicKey) throws CantGetBalanceException;

    long getBookBalance(String walletPublicKey)throws CantGetBalanceException; ;

    /**
     * Transactions Fragment methods
     */
    List<CryptoWalletTransaction> getTransactions(int max, int offset, String walletPublicKey) throws CantGetTransactionsException;

    /**
     * Receive methods
     */
    CryptoAddress requestAddress(UUID deliveredByActorId, Actors deliveredByActorType, String deliveredToActorName, Actors deliveredToActorType, ReferenceWallet referenceWallet, String walletPublicKey) throws CantRequestCryptoAddressException;

    CryptoAddress requestAddress(UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType, ReferenceWallet referenceWallet, String walletPublicKey) throws CantRequestCryptoAddressException;

    /**
     * Send money methods
     */
    void send(long cryptoAmount, CryptoAddress destinationAddress, String notes, String walletPublicKey, UUID deliveredByActorId, Actors deliveredByActorType, UUID deliveredToActorId, Actors deliveredToActorType) throws CantSendCryptoException, InsufficientFundsException;

}
