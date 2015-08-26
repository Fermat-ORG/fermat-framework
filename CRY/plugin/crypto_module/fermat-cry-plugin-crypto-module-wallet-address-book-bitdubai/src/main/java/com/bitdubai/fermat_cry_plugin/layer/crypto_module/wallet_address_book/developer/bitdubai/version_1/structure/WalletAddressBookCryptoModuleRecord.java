package com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord;

import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleRecord</code>
 * represents a wallet crypto address book record.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public class WalletAddressBookCryptoModuleRecord implements WalletAddressBookRecord {

    CryptoAddress cryptoAddress;

    ReferenceWallet referenceWallet;

    String walletPublicKey;

    public WalletAddressBookCryptoModuleRecord(CryptoAddress cryptoAddress, ReferenceWallet referenceWallet, String walletPublicKey) {
        this.cryptoAddress = cryptoAddress;
        this.referenceWallet = referenceWallet;
        this.walletPublicKey = walletPublicKey;
    }

    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    public ReferenceWallet getWalletType() {
        return referenceWallet;
    }

    public CryptoAddress getCryptoAddress() {
        return cryptoAddress;
    }
}