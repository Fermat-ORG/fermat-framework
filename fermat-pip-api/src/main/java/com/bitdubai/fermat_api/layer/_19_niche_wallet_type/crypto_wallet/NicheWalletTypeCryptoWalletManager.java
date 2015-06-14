package com.bitdubai.fermat_api.layer._19_niche_wallet_type.crypto_wallet;

import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.WalletContact;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer._19_niche_wallet_type.crypto_wallet.NicheWalletTypeCryptoWalletManager</code>
 * haves all consumable methods from the plugin
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/06/15.
 * @version 1.0
 */
public interface NicheWalletTypeCryptoWalletManager {

    /**
     * Contacts Activity methods...
     */

    public List<WalletContact> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException;

    public List<WalletContact> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    public WalletContact createWalletContact(CryptoAddress receivedCryptoAddress, String userName, UUID walletId) throws CantCreateWalletContactException;

    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String userName) throws CantUpdateWalletContactException;

    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

}
