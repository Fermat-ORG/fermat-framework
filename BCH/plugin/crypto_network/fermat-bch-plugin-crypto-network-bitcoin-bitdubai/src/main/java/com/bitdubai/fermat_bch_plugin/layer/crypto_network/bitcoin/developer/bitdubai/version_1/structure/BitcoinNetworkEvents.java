package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Block;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.core.WalletEventListener;
import org.bitcoinj.script.Script;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 10/4/15.
 */
public class BitcoinNetworkEvents implements WalletEventListener, PeerEventListener {
    /**
     * Class variables
     */
    BitcoinCryptoNetworkDatabaseDao dao;

    /**
     * Platform variables
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    /**
     * Constructor
     * @param pluginDatabaseSystem
     */
    public BitcoinNetworkEvents(PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    @Override
    public void onPeersDiscovered(Set<PeerAddress> peerAddresses) {

    }

    @Override
    public void onBlocksDownloaded(Peer peer, Block block, FilteredBlock filteredBlock, int blocksLeft) {

    }

    @Override
    public void onChainDownloadStarted(Peer peer, int blocksLeft) {

    }

    @Override
    public void onPeerConnected(Peer peer, int peerCount) {
        System.out.println("peer connected: " + peer.toString());
    }

    @Override
    public void onPeerDisconnected(Peer peer, int peerCount) {

    }

    @Override
    public Message onPreMessageReceived(Peer peer, Message m) {
        return null;
    }

    @Override
    public void onTransaction(Peer peer, Transaction t) {

    }

    @Nullable
    @Override
    public List<Message> getData(Peer peer, GetDataMessage m) {
        return null;
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        /**
         * Register the new incoming transaction into the database
         */
        try {
            getDao().saveNewIncomingTransaction(tx.getHashAsString(),
                    getTransactionCryptoStatus(tx),
                    tx.getConfidence().getDepthInBlocks(),
                    getIncomingTransactionAddressTo(wallet, tx),
                    getIncomingTransactionAddressFrom(tx),
                    tx.getValue(wallet).getValue(),
                    tx.getFee().getValue(),
                    ProtocolStatus.TO_BE_NOTIFIED);
        } catch (CantExecuteDatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCoinsSent(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        //todo record this transaction
    }

    @Override
    public void onReorganize(Wallet wallet) {

    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        // todo records this change in the transaction.

    }

    @Override
    public void onWalletChanged(Wallet wallet) {

    }

    @Override
    public void onScriptsChanged(Wallet wallet, List<Script> scripts, boolean isAddingScripts) {

    }

    @Override
    public void onKeysAdded(List<ECKey> keys) {
        // I may need to reset the wallet in this case?
    }

    /**
     * instantiates the database object
     * @return
     */
    private BitcoinCryptoNetworkDatabaseDao getDao(){
        if (dao == null)
            dao = new BitcoinCryptoNetworkDatabaseDao(this.pluginId, this.pluginDatabaseSystem);
        return dao;
    }

    /**
     * Gets the Crypto Status of the transaction by calculating the transaction depth
     * I need to check if the transaction had another Crypto Status to verify if this is a reversion.
     * Example, if it was under 1 block (ON_BLOCKCHAIN) and now is 0 (ON_CRYPTO_NETWORK), is a Reversion on Blockchain.
     * @param tx
     * @return
     */
    private CryptoStatus getTransactionCryptoStatus(Transaction tx){
        CryptoStatus currentCryptoStatus = getcurrentTransactionCryptoStatus(tx.getHashAsString());

        switch (tx.getConfidence().getDepthInBlocks()){
            case 0:
                if (currentCryptoStatus == CryptoStatus.ON_BLOCKCHAIN)
                    return CryptoStatus.REVERSED_ON_CRYPTO_NETWORK;
                else
                    return CryptoStatus.ON_CRYPTO_NETWORK;
            case 1:
                if (currentCryptoStatus != CryptoStatus.IRREVERSIBLE)
                    return CryptoStatus.REVERSED_ON_BLOCKCHAIN;
                else
                    return CryptoStatus.ON_BLOCKCHAIN;
            case 2:
                return CryptoStatus.IRREVERSIBLE;
            default:
                return CryptoStatus.PENDING_SUBMIT;
        }
    }

    private CryptoStatus getcurrentTransactionCryptoStatus(String txHashAsString) {
        //todo get CryptoStatus from database. Null if no records found.
        return null;
    }

    /**
     * Extracts the Address To from an Incoming Transaction
     * @param tx
     * @return
     */
    private CryptoAddress getIncomingTransactionAddressTo (Wallet wallet, Transaction tx){
        Address address = null;

        /**
         * I will loop from the outputs that include keys that are in my wallet
         */
        for (TransactionOutput output : tx.getWalletOutputs(wallet)){
            /**
             * get the address from the output
             */
            address = output.getAddressFromP2PKHScript(wallet.getNetworkParameters());
        }
        CryptoAddress cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.BITCOIN);
        return cryptoAddress;
    }

    /**
     * Extracts the Address From from an Incoming Transaction
     * @param tx
     * @return
     */
    private CryptoAddress getIncomingTransactionAddressFrom (Transaction tx){
        Address address = null;
        for (TransactionInput input : tx.getInputs()){
            if (input.getFromAddress() != null)
                address = input.getFromAddress();
        }

        CryptoAddress cryptoAddress = new CryptoAddress(address.toString(), CryptoCurrency.BITCOIN);
        return cryptoAddress;
    }
}
