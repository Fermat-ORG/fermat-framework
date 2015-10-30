package com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;

import org.bitcoinj.core.Transaction;

/**
 * Created by rodrigo on 10/06/15.
 */
public interface BitcoinCryptoNetworkManager {
    public void setVault (CryptoVault cryptoVault);
    public void connectToBitcoinNetwork(CryptoVault cryptoVault) throws CantConnectToBitcoinNetwork;
    public void connectToBitcoinNetwork() throws CantConnectToBitcoinNetwork;
    public void disconnectFromBitcoinNetwork();
    public Object getBroadcasters();
    public void broadcastTransaction(Transaction transaction) throws CantBroadcastTransactionException;
}
