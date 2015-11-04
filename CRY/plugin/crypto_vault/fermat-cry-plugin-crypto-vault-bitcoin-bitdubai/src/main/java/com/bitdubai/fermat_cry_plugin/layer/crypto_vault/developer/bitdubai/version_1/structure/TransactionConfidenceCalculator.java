package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_cry_api.layer.definition.DepthInBlocksThreshold;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantCalculateTransactionConfidenceException;

import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;

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
    final int TARGET_DEPTH = DepthInBlocksThreshold.DEPTH;

    public TransactionConfidenceCalculator(Transaction transaction, Database database) {
        this.transaction = transaction;
        this.database = database;
    }

    private CryptoStatus getPreviousCryptoStatus(String txHash) throws CantLoadTableToMemoryException {
        DatabaseTable cryptoTransactionsTable = database.getTable(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_NAME);
        cryptoTransactionsTable.setStringFilter(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRX_HASH_COLUMN_NAME, txHash, DatabaseFilterType.EQUAL);
        cryptoTransactionsTable.loadToMemory();

        List<DatabaseTableRecord> databaseTableRecordList = cryptoTransactionsTable.getRecords();
        if (databaseTableRecordList.isEmpty()) {
            //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
            return null;
        } else {
            CryptoStatus previousCryptoStatus = null;
            for (DatabaseTableRecord record : databaseTableRecordList) {
                CryptoStatus cryptoStatus = null;
                try {
                    cryptoStatus = CryptoStatus.getByCode(record.getStringValue(CryptoVaultDatabaseConstants.CRYPTO_TRANSACTIONS_TABLE_TRANSACTION_STS_COLUMN_NAME));
                } catch (InvalidParameterException e) {
                    e.printStackTrace();
                }
                if (previousCryptoStatus == null)
                    previousCryptoStatus = cryptoStatus;
                else if (previousCryptoStatus.getOrder() < cryptoStatus.getOrder())
                    previousCryptoStatus = cryptoStatus;
            }
            return previousCryptoStatus;
        }
    }

    public CryptoStatus getCryptoStatus() throws CantCalculateTransactionConfidenceException {
        try {
            if (transaction == null)
                throw new CantCalculateTransactionConfidenceException("transaction null", "Transaction is null.");

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
                    if (height >= TARGET_DEPTH){
                        if (getPreviousCryptoStatus(transaction.getHashAsString()) == CryptoStatus.ON_CRYPTO_NETWORK)
                            return CryptoStatus.ON_BLOCKCHAIN;
                        else
                            return CryptoStatus.IRREVERSIBLE;
                    } else
                        return CryptoStatus.ON_BLOCKCHAIN;
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
            throw new CantCalculateTransactionConfidenceException();

        } catch (CantCalculateTransactionConfidenceException exception) {

            throw exception;
        } catch (Exception exception) {

            throw new CantCalculateTransactionConfidenceException(FermatException.wrapException(exception), null, "Unhandled error.");
        }
    }
}
