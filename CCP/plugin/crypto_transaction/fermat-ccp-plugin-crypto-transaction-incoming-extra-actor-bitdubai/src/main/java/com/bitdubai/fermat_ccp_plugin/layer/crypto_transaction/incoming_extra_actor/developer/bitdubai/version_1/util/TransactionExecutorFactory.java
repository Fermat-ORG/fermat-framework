package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.interfaces.TransactionExecutor;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.executors.CryptoBasicWalletTransactionExecutor;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.executors.BitcoinLossProtectedWalletTransactionExecutor;

/**
 * Created by jorgegonzalez on 2015.07.08..
 */
public class TransactionExecutorFactory {

    private CryptoWalletManager cryptoWalletManager;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private BitcoinLossProtectedWalletManager lossProtectedWalletManager;

    public TransactionExecutorFactory(final CryptoWalletManager cryptoWalletManager, final CryptoAddressBookManager cryptoAddressBookManager, final BitcoinLossProtectedWalletManager lossProtectedWalletManager){
        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.lossProtectedWalletManager = lossProtectedWalletManager;
    }

    public TransactionExecutor newTransactionExecutor(final ReferenceWallet walletType, final String walletPublicKey) throws CantLoadWalletsException {
        try {
            switch (walletType) {
                case BASIC_WALLET_BITCOIN_WALLET:
                    return createBitcoinBasicWalletExecutor(walletPublicKey);
                case BASIC_WALLET_FERMAT_WALLET:
                    return createBitcoinBasicWalletExecutor(walletPublicKey);
                case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                    return createLossProtectedBasicWalletExecutor(walletPublicKey);
                default:
                    //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                    return null;
            }
        } catch (CantLoadWalletsException e) {
            throw e;
        } catch (Exception e) {
            throw new CantLoadWalletsException("An Unexpected Exception Happened", FermatException.wrapException(e),"","");
        }

    }

    private TransactionExecutor createBitcoinBasicWalletExecutor(final String walletPublicKey) throws CantLoadWalletsException {
        return new CryptoBasicWalletTransactionExecutor(cryptoWalletManager.loadWallet(walletPublicKey), this.cryptoAddressBookManager);
    }

    private TransactionExecutor createLossProtectedBasicWalletExecutor(final String walletPublicKey) throws CantLoadWalletException {
        return new BitcoinLossProtectedWalletTransactionExecutor(lossProtectedWalletManager.loadWallet(walletPublicKey), this.cryptoAddressBookManager);
    }

}
