package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemory;
import com.bitdubai.fermat_cry_api.layer.definition.DepthInBlocksThreshold;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;

import java.util.UUID;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by rodrigo on 2015.06.24..
 */
public class TransactionConfidenceCalculator {
    /**
     * TransactionConfidenceCalculator variables
     */
    String txId;
    Transaction transaction=null;
    CryptoTransaction cryptoTransaction;
    CryptoStatus cryptoStatus;
    Database database;
    Wallet vault;
    final int TARGET_DEPTH = DepthInBlocksThreshold.DEPTH;


    /**
     * Constructor
     * @param txId
     */
    public TransactionConfidenceCalculator(String txId, Database database, Wallet vault){
        this.txId = txId;
        this.database = database;
        this.vault = vault;
    }

    public TransactionConfidenceCalculator(Transaction transaction, Wallet vault){
        this.transaction = transaction;
        this.vault = vault;
    }

    /**
     * Searchs in the database for the Transaction Hash
     * @return
     */
    private String getTransactionHash() throws CantLoadTableToMemory {
        DatabaseTable cryptoTransactionsTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTransactionsTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME,this.txId, DatabaseFilterType.EQUAL);
        cryptoTransactionsTable.loadToMemory();
        DatabaseTableRecord txHash = cryptoTransactionsTable.getRecords().get(0);
        return txHash.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME);
    }

    /**
     * Will get the transaction from the vault
     * @return
     */
    private Transaction getTransactionFromVault() throws CantCalculateTransactionConfidenceException {
        String dbHash = null;
        try {
            dbHash = getTransactionHash();
        } catch (CantLoadTableToMemory cantLoadTableToMemory) {
            throw new CantCalculateTransactionConfidenceException();
        }

        Sha256Hash txHash = new Sha256Hash(dbHash);
        this.transaction = vault.getTransaction(txHash);

        return transaction;
    }


    public CryptoStatus getCryptoStatus() throws CantCalculateTransactionConfidenceException {
        if (transaction ==null)
            transaction = getTransactionFromVault();

        TransactionConfidence.ConfidenceType confidence = transaction.getConfidence().getConfidenceType();

        switch (confidence){
            case BUILDING:
                /**
                 * If BUILDING, then the transaction is included in the best chain and your confidence in it is increasing.
                 * Depending of how deep is the transaction in blocks, the status will be RECEIVED or CONFIRMED.
                 */
                int height = transaction.getConfidence().getDepthInBlocks();

                /**
                 * if the depth is one block, then the status is RECEIVED
                 */
                if (height == 1)
                    return CryptoStatus.RECEIVED;

                /**
                 * if the height is equal or exceeds the threshold defined in DepthInBlocksThreshold.DEPTH, then changes to CONFIRMED
                 */
                if (height >= TARGET_DEPTH)
                    return CryptoStatus.CONFIRMED;
            case DEAD:
                /**
                 * If DEAD, then it means the transaction won’t confirm unless there is another re-org, because some other transaction is spending one of its inputs.
                 * When this happens, it change to REVERSED status.
                 */
                return CryptoStatus.REVERSED;
            case PENDING:
                /**
                 * If PENDING, then the transaction is unconfirmed and should be included shortly as long as it is
                 * being broadcast from time to time and is considered valid by the network.
                 * This is IDENTIFIED in the protocol.
                 */
                return CryptoStatus.IDENTIFIED;
            case UNKNOWN:
                /**
                 * UNKNOWN is the default state. I'm translating it to IDENTIFIED in the Transaction Protocol
                 */
                return CryptoStatus.IDENTIFIED;
            default:
                return CryptoStatus.IDENTIFIED;
        }

    }
}
