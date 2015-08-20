package com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRecord</code>
 * indicates the functionality of a WalletCryptoAddressBookRecord.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 * @version 1.0
 */
public interface WalletAddressBookRecord {

    String getWalletPublicKey();

    ReferenceWallet getWalletType();

    CryptoAddress getCryptoAddress();
}
