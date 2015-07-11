package com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletTransaction;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletNicheWalletTypeTransaction</code>
 * TODO add detail
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 * @version 1.0
 */
public class CryptoWalletNicheWalletTypeTransaction implements CryptoWalletTransaction {

    BitcoinWalletTransaction bitcoinWalletTransaction;

    String involvedActorName;

    public CryptoWalletNicheWalletTypeTransaction(BitcoinWalletTransaction bitcoinWalletTransaction, String involvedActorName) {
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
