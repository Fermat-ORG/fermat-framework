package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantRegisterDebitDebitException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.TransactionManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InsufficientMoneyException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.23..
 */
public class OutgoingExtraUserTransactionManager implements DealsWithBitcoinWallet, DealsWithCryptoVault, TransactionManager {

    BitcoinWalletManager bitcoinWalletManager;
    @Override
    public void send(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount) {
        BitcoinWallet bitcoinWallet = null;

        try {
             bitcoinWallet = this.bitcoinWalletManager.loadWallet(UUID.randomUUID());
        } catch (CantLoadWalletException e) {
            System.out.println("THIS IS CURRENTLY IMPOSIBLE");
        }

        BitcoinTransaction bitcoinTransaction = new BitcoinTransaction();
        bitcoinTransaction.setAmount(cryptoAmount);

        try {
            this.cryptoVaultManager.sendBitcoins(walletID, UUID.randomUUID(), destinationAddress, cryptoAmount);
        } catch (InsufficientMoneyException e) {
            e.printStackTrace();
        }

        try {
            bitcoinWallet.debit(bitcoinTransaction);
        } catch (CantRegisterDebitDebitException e) {
            System.out.println("THIS IS CURRENTLY IMPOSIBLE");
        }
    }

    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }


    private CryptoVaultManager cryptoVaultManager;
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }
}
