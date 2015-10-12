package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.database.BitcoinCryptoNetworkDatabaseDao;
import com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.FilteredBlock;
import org.bitcoinj.core.GetDataMessage;
import org.bitcoinj.core.Message;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerEventListener;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionBag;
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
            getDao().saveNewIncomingTransaction(tx.getHashAsString(), CryptoStatus.ON_CRYPTO_NETWORK, tx.getConfidence().getDepthInBlocks());
        } catch (CantExecuteDatabaseOperationException e) {
            //todo handle I could try to save it to disk and each time is executed try to get it from disk
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
     * @param tx
     * @return
     */
    private CryptoStatus getTransactionCryptoStatus(Transaction tx){

        return null;
    }
}
