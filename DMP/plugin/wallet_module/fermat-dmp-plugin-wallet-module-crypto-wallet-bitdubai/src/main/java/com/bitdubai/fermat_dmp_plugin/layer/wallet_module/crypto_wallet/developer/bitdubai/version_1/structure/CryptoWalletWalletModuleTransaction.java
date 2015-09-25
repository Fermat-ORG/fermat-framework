package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.ccp_actor.Actor;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletTransaction;
import com.bitdubai.fermat_api.layer.ccp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleTransaction</code>
 * TODO add detail
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 * @version 1.0
 */
public class CryptoWalletWalletModuleTransaction implements CryptoWalletTransaction {

    BitcoinWalletTransaction bitcoinWalletTransaction;

    Actor involvedActor;

    UUID contactId;

    public CryptoWalletWalletModuleTransaction(BitcoinWalletTransaction bitcoinWalletTransaction, Actor involvedActor, UUID contactId) {
        this.bitcoinWalletTransaction = bitcoinWalletTransaction;
        this.involvedActor = involvedActor;
        this.contactId = contactId;
    }

    @Override
    public BitcoinWalletTransaction getBitcoinWalletTransaction() {
        return bitcoinWalletTransaction;
    }

    @Override
    public Actor getInvolvedActor() {
        return involvedActor;
    }

    @Override
    public UUID getContactId() {
        return contactId;
    }
}
