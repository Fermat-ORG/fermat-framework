package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleTransaction</code>
 * TODO add detail
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 * @version 1.0
 */
public class CryptoWalletWalletModuleTransaction implements CryptoWalletTransaction {

    private final BitcoinWalletTransaction bitcoinWalletTransaction;
    private final UUID                     contactId;
    private final Actor                    involvedActor;

    public CryptoWalletWalletModuleTransaction(final BitcoinWalletTransaction bitcoinWalletTransaction,
                                               final UUID                     contactId,
                                               final Actor                    involvedActor) {

        this.bitcoinWalletTransaction = bitcoinWalletTransaction;
        this.contactId = contactId;
        this.involvedActor = involvedActor;
    }

    @Override
    public BitcoinWalletTransaction getBitcoinWalletTransaction() {
        return bitcoinWalletTransaction;
    }

    @Override
    public UUID getContactId() {
        return contactId;
    }

    @Override
    public Actor getInvolvedActor() {
        return involvedActor;
    }
}
