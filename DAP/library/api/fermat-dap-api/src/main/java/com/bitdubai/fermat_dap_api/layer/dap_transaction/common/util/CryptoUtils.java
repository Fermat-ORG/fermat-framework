package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;

import java.util.List;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 27/10/15.
 */
public class CryptoUtils {

    //VARIABLE DECLARATION

    //CONSTRUCTORS

    private CryptoUtils() {
        throw new AssertionError("NO INSTANCES!!!");
    }

    //PUBLIC METHODS


    public static CryptoTransaction getCryptoTransactionByCryptoStatus(BitcoinNetworkManager bitcoinNetworkManager, CryptoStatus cryptoStatus, String genesisTransaction) throws CantGetGenesisTransactionException {

        List<CryptoTransaction> transactionListFromCryptoNetwork = bitcoinNetworkManager.getCryptoTransaction(genesisTransaction);
        if (transactionListFromCryptoNetwork == null) {
            throw new CantGetGenesisTransactionException(CantGetGenesisTransactionException.DEFAULT_MESSAGE, null,
                    "Getting the cryptoStatus from CryptoNetwork",
                    "The crypto status from genesis transaction " + genesisTransaction + " return null");
        }

        if (transactionListFromCryptoNetwork.isEmpty()) {
            throw new CantGetGenesisTransactionException(CantGetGenesisTransactionException.DEFAULT_MESSAGE, null,
                    "Getting the cryptoStatus from CryptoNetwork",
                    "The genesis transaction " + genesisTransaction + " cannot be found in crypto network");
        }

        for (CryptoTransaction cryptoTransaction : transactionListFromCryptoNetwork) {
            if (cryptoTransaction.getCryptoStatus() == cryptoStatus) {
                return cryptoTransaction;
            }
        }
        throw new NullPointerException("Crypto Transaction not found in that status: " + cryptoStatus.getCode());
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
