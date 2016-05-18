package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;

import java.io.Serializable;
import java.util.UUID;

/**
 * The interface <code>CryptoWalletWalletModuleTransaction</code>
 * TODO add detail
 *
 * Created Natalia Cortez on 07/03/2016.
 * @version 1.0
 */
public class LossProtectedWalletModuleTransaction implements LossProtectedWalletTransaction,Serializable {

    private final UUID                     contactId;
    private final Actor                    involvedActor;

    // bitcoin wallet transaction fields
    private final UUID transactionId;
    private final String transactionHash;
    private final TransactionType transactionType;
    private final CryptoAddress addressFrom;
    private final CryptoAddress addressTo;
    private final String actorFromPublicKey;
    private final String actorToPublicKey;
    private final Actors actorFromType;
    private final Actors actorToType;
    private final BalanceType balanceType;
    private final long amount;
    private final long runningBookBalance;
    private final long runningAvailableBalance;
    private final long timeStamp;
    private final double rate;
    private final String memo;
    private final BlockchainNetworkType blockchainNetworkType;
    private final TransactionState transactionState;

    public LossProtectedWalletModuleTransaction(final BitcoinLossProtectedWalletTransaction bitcoinWalletTransaction,
                                               final UUID                     contactId,
                                               final Actor                    involvedActor) {

        this.contactId = contactId;
        this.involvedActor = involvedActor;

        // bitcoin wallet transaction fields
        this.transactionId = bitcoinWalletTransaction.getTransactionId();
        this.transactionHash = bitcoinWalletTransaction.getTransactionHash();
        this.transactionType = bitcoinWalletTransaction.getTransactionType();
        this.addressFrom = bitcoinWalletTransaction.getAddressFrom();
        this.addressTo = bitcoinWalletTransaction.getAddressTo();
        this.actorFromPublicKey = bitcoinWalletTransaction.getActorFromPublicKey();
        this.actorToPublicKey = bitcoinWalletTransaction.getActorToPublicKey();
        this.actorFromType = bitcoinWalletTransaction.getActorFromType();
        this.actorToType = bitcoinWalletTransaction.getActorToType();
        this.balanceType = bitcoinWalletTransaction.getBalanceType();
        this.amount = bitcoinWalletTransaction.getAmount();
        this.runningBookBalance = bitcoinWalletTransaction.getRunningBookBalance();
        this.runningAvailableBalance = bitcoinWalletTransaction.getRunningAvailableBalance();
        this.timeStamp = bitcoinWalletTransaction.getTimestamp();
        this.rate = bitcoinWalletTransaction.getExchangeRate();
        this.memo = bitcoinWalletTransaction.getMemo();
        this.blockchainNetworkType = bitcoinWalletTransaction.getBlockchainNetworkType();
        this.transactionState = bitcoinWalletTransaction.getTransactionState();
    }

    @Override
    public UUID getContactId() {
        return contactId;
    }

    @Override
    public Actor getInvolvedActor() {
        return involvedActor;
    }

    @Override
    public UUID getTransactionId() {
        return transactionId;
    }

    @Override
    public String getTransactionHash() {
        return transactionHash;
    }

    @Override
    public CryptoAddress getAddressFrom() {
        return addressFrom;
    }

    @Override
    public CryptoAddress getAddressTo() {
        return addressTo;
    }

    @Override
    public String getActorFromPublicKey() {
        return actorFromPublicKey;
    }

    @Override
    public String getActorToPublicKey() {
        return actorToPublicKey;
    }

    @Override
    public Actors getActorToType() {
        return actorToType;
    }

    @Override
    public Actors getActorFromType() {
        return actorFromType;
    }

    @Override
    public BalanceType getBalanceType() {
        return balanceType;
    }

    @Override
    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public TransactionState getTransactionState() {
        return transactionState;
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public long getAmount() { return amount; }

    @Override
    public long getRunningBookBalance() {
        return runningBookBalance;
    }

    @Override
    public long getRunningAvailableBalance() {
        return runningAvailableBalance;
    }

    @Override
    public String getMemo() {
        return memo;
    }

    @Override
    public double getExchangeRate() {
        return rate;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType;}
}
