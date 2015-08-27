package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleTransaction</code>
 * TODO add detail
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 * @version 1.0
 */
public class CryptoWalletWalletModuleTransaction implements CryptoWalletTransaction {

    BitcoinWalletTransaction bitcoinWalletTransaction;

    String involvedActorName;

    public CryptoWalletWalletModuleTransaction(BitcoinWalletTransaction bitcoinWalletTransaction, String involvedActorName) {
        this.bitcoinWalletTransaction = bitcoinWalletTransaction;
        this.involvedActorName = involvedActorName;
    }

    @Override
    public BitcoinWalletTransaction getBitcoinWalletTransaction() {
        return bitcoinWalletTransaction;
    }

    @Override
    public String getInvolvedActorName() {
        return involvedActorName;
    }
}
