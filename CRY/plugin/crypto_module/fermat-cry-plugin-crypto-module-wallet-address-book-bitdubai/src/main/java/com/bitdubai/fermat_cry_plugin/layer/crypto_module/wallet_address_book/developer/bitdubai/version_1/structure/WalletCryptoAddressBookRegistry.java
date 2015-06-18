package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.WalletCryptoAddressBook;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletCryptoAddressBookRegistry</code>
 * represents a wallet crypto address book registry.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class WalletCryptoAddressBookRegistry implements WalletCryptoAddressBook {

    CryptoAddress cryptoAddress;

    PlatformWalletType platformWalletType;

    UUID walletId;

    public WalletCryptoAddressBookRegistry(CryptoAddress cryptoAddress, PlatformWalletType platformWalletType, UUID walletId) {
        this.cryptoAddress = cryptoAddress;
        this.platformWalletType = platformWalletType;
        this.walletId = walletId;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public PlatformWalletType getWalletType() {
        return platformWalletType;
    }

    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }
}