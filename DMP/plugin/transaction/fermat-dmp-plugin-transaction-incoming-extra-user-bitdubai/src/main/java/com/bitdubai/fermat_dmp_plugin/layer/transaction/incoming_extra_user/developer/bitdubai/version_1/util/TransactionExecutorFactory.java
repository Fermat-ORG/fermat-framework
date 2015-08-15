package com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.basic_wallet_common_exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.executors.BitcoinBasicWalletTransactionExecutor;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.08..
 */
public class TransactionExecutorFactory {

    private BitcoinWalletManager bitcoinWalletManager;
    private ActorAddressBookManager actorAddressBookManager;

    public TransactionExecutorFactory(final BitcoinWalletManager bitcoinWalletManager,final ActorAddressBookManager actorAddressBookManager){
        this.bitcoinWalletManager = bitcoinWalletManager;
        this.actorAddressBookManager = actorAddressBookManager;
    }

    public TransactionExecutor newTransactionExecutor(final ReferenceWallet walletType, final String walletPublicKey) throws CantLoadWalletException{
        switch (walletType){
            case BASIC_WALLET_BITCOIN_WALLET:
                return createBitcoinBasicWalletExecutor(walletPublicKey);
            default:
                return null;
        }
    }

    private TransactionExecutor createBitcoinBasicWalletExecutor(final String walletPublicKey) throws CantLoadWalletException {
        return new BitcoinBasicWalletTransactionExecutor(bitcoinWalletManager.loadWallet(walletPublicKey),this.actorAddressBookManager);
    }

}
