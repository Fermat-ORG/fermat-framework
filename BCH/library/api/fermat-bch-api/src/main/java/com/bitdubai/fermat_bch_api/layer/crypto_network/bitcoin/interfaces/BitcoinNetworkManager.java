package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.UTXOProvider;

import java.util.List;

/**
 * Created by rodrigo on 9/30/15.
 */
public interface BitcoinNetworkManager extends TransactionSender<CryptoTransaction> {

    /**
     * Starts monitoring the network active networks with the list of keys passed.
     * @param keyList
     * @throws CantMonitorBitcoinNetworkException
     */
    void monitorNetworkFromKeyList(CryptoVaults vault, List<BlockchainNetworkType> blockchainNetworkTypes,List<ECKey> keyList) throws CantMonitorBitcoinNetworkException;

    /**
     * Gests all the CryptoTransactions that matchs this transaction Hash
     * @param txHash
     * @return
     * @throws CantGetCryptoTransactionException
     */
    List<CryptoTransaction> getCryptoTransaction(String txHash) throws CantGetCryptoTransactionException;


    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     * @param txHash the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */
    CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException;

    /**
     * Broadcast a well formed, commited and signed transaction into the specified network
     * @param blockchainNetworkType
     * @param tx
     * @throws CantBroadcastTransactionException
     */
    void broadcastTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx) throws CantBroadcastTransactionException;

    /**
     * Gets the UTXO provider from the CryptoNetwork on the specified Network
     * @param blockchainNetworkType
     * @return
     */
    UTXOProvider getUTXOProvider(BlockchainNetworkType blockchainNetworkType);


    /**
     * Get the bitcoin transaction stored by the CryptoNetwork
     * @param blockchainNetworkType the network type
     * @param transactionHash the transsaction hash
     * @return the bitcoin transaction
     */
    Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash);

    /**
     * Gets the bitcoin transactions stored by the CryptoNetwork
     * @param blockchainNetworkType     the network type
     * @param ecKey the ECKey that is affected by the transaction
     * @return the bitcoin transaction
     */
    List<Transaction> getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, ECKey ecKey);

    /**
     * Gets the bitcoin transactions stored by the CryptoNetwork
     * @param blockchainNetworkType the network type.
     * @param ecKeys the list of ECKeys affected by the transactions returned.
     * @return the bitcoin transaction
     */
    List<Transaction> getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, List<ECKey> ecKeys);

    /**
     * Get the bitcoin transaction stored by the CryptoNetwork
     * @param blockchainNetworkType the network type
     * @param vaultType the crypto vault that generated the keys that affects the returned transactions
     * @return the bitcoin transaction
     */
    List<Transaction> getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, VaultType vaultType);

}
