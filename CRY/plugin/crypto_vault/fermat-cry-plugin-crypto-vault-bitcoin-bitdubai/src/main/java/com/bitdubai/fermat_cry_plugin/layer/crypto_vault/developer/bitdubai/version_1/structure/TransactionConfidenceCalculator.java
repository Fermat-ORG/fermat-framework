package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cry_api.layer.definition.DepthInBlocksThreshold;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;

import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.Wallet;

import java.util.List;

/**
 * Created by rodrigo on 2015.06.24..
 *
 */
public class TransactionConfidenceCalculator {
    /**
     * TransactionConfidenceCalculator variables
     */
    String txId;
    Transaction transaction = null;
    Database database;
    Wallet vault;
    final int TARGET_DEPTH = DepthInBlocksThreshold.DEPTH;


    /**
     * Constructor
     *
     * @param txId with
     */
    public TransactionConfidenceCalculator(String txId, Database database, Wallet vault) {
        this.txId = txId;
        this.database = database;
        this.vault = vault;
    }

    public TransactionConfidenceCalculator(Transaction transaction, Wallet vault) {
        this.transaction = transaction;
        this.vault = vault;
    }

    /**
     * Searchs in the database for the Transaction Hash
     *
     * @return hash of the transaction
     */
    private String getTransactionHash() throws CantLoadTableToMemoryException {
        DatabaseTable cryptoTransactionsTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTransactionsTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_ID_COLUMN_NAME, this.txId, DatabaseFilterType.EQUAL);
        cryptoTransactionsTable.loadToMemory();
        DatabaseTableRecord txHash = cryptoTransactionsTable.getRecords().get(0);
        return txHash.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME);
    }

    private CryptoStatus getPreviousCryptoStatus(String txHash) throws CantLoadTableToMemoryException {
        DatabaseTable cryptoTransactionsTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTransactionsTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        cryptoTransactionsTable.loadToMemory();

        List<DatabaseTableRecord> databaseTableRecordList = cryptoTransactionsTable.getRecords();
        if (databaseTableRecordList.isEmpty()) {
            return null;
        } else {
            CryptoStatus previousCryptoStatus = null;
            for (DatabaseTableRecord record : databaseTableRecordList) {
                CryptoStatus cryptoStatus = CryptoStatus.valueOf(record.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME));
                if (previousCryptoStatus == null)
                    previousCryptoStatus = cryptoStatus;
                else if (previousCryptoStatus.getOrder() < cryptoStatus.getOrder())
                    previousCryptoStatus = cryptoStatus;
            }
            return previousCryptoStatus;
        }
    }

    /**
     * Will get the transaction from the vault
     *
     * @return the transaction from vault
     */
    private Transaction getTransactionFromVault() throws CantCalculateTransactionConfidenceException {
        String dbHash;
        try {
            dbHash = getTransactionHash();
        } catch (CantLoadTableToMemoryException cantLoadTableToMemory) {
            throw new CantCalculateTransactionConfidenceException("Error calculating the confidence of transaction.", cantLoadTableToMemory, null, "error in database plugin");
        }
        Sha256Hash txHash = new Sha256Hash(dbHash);
        this.transaction = vault.getTransaction(txHash);

        return transaction;
    }


    public CryptoStatus getCryptoStatus() throws CantCalculateTransactionConfidenceException {
        try {
            if (transaction == null)
                transaction = getTransactionFromVault();

            TransactionConfidence.ConfidenceType confidence = transaction.getConfidence().getConfidenceType();

            switch (confidence) {
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
                        return CryptoStatus.ON_BLOCKCHAIN;

                    /**
                     * if the height is equal or exceeds the threshold defined in DepthInBlocksThreshold.DEPTH, then changes to CONFIRMED
                     *
                     * for a found bug we've in count two more blocks to corroborate if the transaction is in the correct state
                     */
                    if (height >= TARGET_DEPTH)
                        if (height < 3 && getPreviousCryptoStatus(transaction.getHashAsString()) == CryptoStatus.ON_CRYPTO_NETWORK)
                            return CryptoStatus.ON_BLOCKCHAIN;
                        else
                            return CryptoStatus.IRREVERSIBLE;
                    break;
                case DEAD:
                    /**
                     * If DEAD, then it means the transaction won’t confirm unless there is another re-org, because some other transaction is spending one of its inputs.
                     * When this happens, it change to REVERSED status.
                     * UPDATE: If the transaction was previously in ON_BLOCKCHAIN status, then I will set the new status to REVERSED_ON_BLOCKCHAIN.
                     * If it was in ON_CRYPTO_STATUS, then I will change it to REVERSED_ON_CRYPTO_STATUS
                     */
                    if (getPreviousCryptoStatus(transaction.getHashAsString()) == CryptoStatus.ON_CRYPTO_NETWORK)
                        return CryptoStatus.REVERSED_ON_CRYPTO_NETWORK;

                    if (getPreviousCryptoStatus(transaction.getHashAsString()) == CryptoStatus.ON_BLOCKCHAIN)
                        return CryptoStatus.REVERSED_ON_BLOCKCHAIN;
                    break;
                case PENDING:
                    /**
                     * If PENDING, then the transaction is unconfirmed and should be included shortly as long as it is
                     * being broadcast from time to time and is considered valid by the network.
                     * This is IDENTIFIED in the protocol.
                     */
                    return CryptoStatus.ON_CRYPTO_NETWORK;
                case UNKNOWN:
                    /**
                     * UNKNOWN is the default state. I'm translating it to IDENTIFIED in the Transaction Protocol
                     */
                    return CryptoStatus.PENDING_SUBMIT;
                default:
                    return CryptoStatus.PENDING_SUBMIT;
            }
            throw new CantCalculateTransactionConfidenceException(CantCalculateTransactionConfidenceException.DEFAULT_MESSAGE, null, null, null);
        } catch (CantCalculateTransactionConfidenceException exception) {
            throw new CantCalculateTransactionConfidenceException(CantCalculateTransactionConfidenceException.DEFAULT_MESSAGE, exception, null, null);
        } catch (Exception exception) {
            throw new CantCalculateTransactionConfidenceException(CantCalculateTransactionConfidenceException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }
}
