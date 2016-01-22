package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionSender;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BlockchainConnectionStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantCancellBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainConnectionStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionCryptoStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantMonitorBitcoinNetworkException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantStoreBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.enums.CryptoVaults;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.UTXOProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

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
    List<CryptoTransaction> getCryptoTransactions(String txHash) throws CantGetCryptoTransactionException;

    /**
     * gets the current Crypto Status for the specified Transaction ID
     * @param txHash the Bitcoin transaction hash
     * @return the last crypto status
     * @throws CantGetTransactionCryptoStatusException
     */
    CryptoStatus getCryptoStatus(String txHash) throws CantGetTransactionCryptoStatusException;

    /**
     * Will get the CryptoTransaction directly from the blockchain by requesting it to a peer.
     * If the transaction is not part of any of our vaults, we will ask it to a connected peer to retrieve it.
     * @param txHash the Hash of the transaction we are going to look for.
     * @param blockHash the Hash of block where this transaction was stored..
     * @return a CryptoTransaction with the information of the transaction.
     * @throws CantGetCryptoTransactionException
     */
    CryptoTransaction getCryptoTransactionFromBlockChain(String txHash, String blockHash) throws CantGetCryptoTransactionException;

    /**********************************************Broadcasting functionality ********************************************/

    /**
     * Stores a Bitcoin Transaction in the CryptoNetwork to be broadcasted later
     * @param blockchainNetworkType
     * @param tx
     * @param transactionId
     * @throws CantStoreBitcoinTransactionException
     */
    void storeBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, Transaction tx, UUID transactionId) throws CantStoreBitcoinTransactionException;

    /**
     * Broadcast a well formed, commited and signed transaction into the network.
     * @param txHash
     * @throws CantBroadcastTransactionException
     */
    void broadcastTransaction (String txHash) throws CantBroadcastTransactionException;


    /**
     * Will mark the passed transaction as cancelled, and it won't be broadcasted again.
     * @param txHash
     * @throws CantCancellBroadcastTransactionException
     */
    void cancelBroadcast(String txHash) throws CantCancellBroadcastTransactionException;

    /**
     * Returns the broadcast Status for a specified transaction.
     * @param txHash
     * @return
     * @throws CantGetBroadcastStatusException
     */
    BroadcastStatus getBroadcastStatus (String txHash) throws CantGetBroadcastStatusException;

    /**
     * Get the bitcoin transaction stored by the CryptoNetwork
     * @param blockchainNetworkType the network type
     * @param transactionHash the transsaction hash
     * @return the bitcoin transaction
     */
    Transaction getBitcoinTransaction(BlockchainNetworkType blockchainNetworkType, String transactionHash);


    /**
     * Get the Unspent bitcoin transaction stored by the CryptoNetwork
     * @param blockchainNetworkType the network type
     * @return the bitcoin transaction
     */
    List<Transaction> getUnspentBitcoinTransactions(BlockchainNetworkType blockchainNetworkType);


    /**
     * Get the bitcoin transactions stored by the CryptoNetwork
     * @param blockchainNetworkType the network type
     * @return the bitcoin transaction
     */
    List<Transaction> getBitcoinTransactions(BlockchainNetworkType blockchainNetworkType);


    /**
     * Will get the BlockchainConnectionStatus for the specified network.
     * @param blockchainNetworkType the Network type we won't to get info from. If the passed network is not currently activated,
     * then we will receive null.
     * @return BlockchainConnectionStatus with information of amount of peers currently connected, etc.
     * @exception CantGetBlockchainConnectionStatusException
     */
    BlockchainConnectionStatus getBlockchainConnectionStatus(BlockchainNetworkType blockchainNetworkType)  throws CantGetBlockchainConnectionStatusException;

     /**
     * Gets a stored CryptoTransaction in wathever network.
     * @param txHash the transaction hash we want to get the CryptoTransaction
     * @return the last recorded CryptoTransaction.
     * @throws CantGetCryptoTransactionException
     */
    CryptoTransaction getCryptoTransaction(String txHash) throws CantGetCryptoTransactionException;

    /**
     * Based on the passed transaction chain of Transactions hashes and Blocks hashes, determines the entire path
     * of the chain until the Genesis Transaction is reached.
     * The genesis Transaction will be the first transaction in the map.
     * @param blockchainNetworkType the active network we are getting this info from. Defaults to BlockchainNetworkType.DEFAULT
     * @param transactionChain a Map with the form TransactionHash / BlockHash
     * @return all the CryptoTransactions originated at the genesis transaction
     * @throws CantGetCryptoTransactionException
     */
    CryptoTransaction getGenesisCryptoTransaction(@Nullable BlockchainNetworkType blockchainNetworkType, LinkedHashMap<String, String> transactionChain) throws CantGetCryptoTransactionException;


    /**
     * Based on the Parent trasaction passed, will return all the CryptoTransactions that are a direct descendant of this parent.
     * Only if it is a locally stored transaction
     * @param parentTransactionHash the hash of the parent trasaction
     * @return the list of CryptoTransactions that are a direct child of the parent.
     * @throws CantGetCryptoTransactionException
     */
    List<CryptoTransaction> getChildTransactionsFromParent(String parentTransactionHash) throws CantGetCryptoTransactionException;
}
