package com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.WalletContact;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.*;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.NicheWalletTypeCryptoWalletManager</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface NicheWalletTypeCryptoWalletManager {

    /**
     * Contacts Activity methods...
     */

    List<WalletContact> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException;

    List<WalletContact> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    WalletContact createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, UUID walletId) throws CantCreateWalletContactException;

    void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException;

    void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    WalletContact getWalletContactByContainsLikeAndWalletId(String actorName, UUID walletId) throws CantGetWalletContactException;

}
