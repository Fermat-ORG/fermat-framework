package com.bitdubai.fermat_bch_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import org.bitcoinj.core.AbstractWalletEventListener;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Wallet;

/**
 * Created by rodrigo on 9/30/15.
 */
public class NetworkMonitoringAgentEvents extends AbstractWalletEventListener{
    EventManager eventManager;

    /**
     * Constructor
     * @param eventManager
     */
    public NetworkMonitoringAgentEvents(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance) {
        super.onCoinsReceived(wallet, tx, prevBalance, newBalance);
        System.out.println("Bitcoins recieved. Prev Balance: "+ prevBalance + " new Balance:" + newBalance);

        //todo start IncomingCryptoOnBlockChain
        FermatEvent transactionEvent = eventManager.getNewEvent(EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN);
        transactionEvent.setSource(EventSource.CRYPTO_VAULT);
        eventManager.raiseEvent(transactionEvent);
    }

    @Override
    public void onTransactionConfidenceChanged(Wallet wallet, Transaction tx) {
        super.onTransactionConfidenceChanged(wallet, tx);
    }
}
