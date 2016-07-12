package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransaction;

import java.io.Serializable;
import java.util.UUID;

/**
 * The interface <code>CryptoWalletWalletModuleTransaction</code>
 * TODO add detail
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/07/15.
 * @version 1.0
 */
public class CryptoWalletWalletModuleTransaction implements com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction,Serializable {

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
    private final String memo;
    private final BlockchainNetworkType blockchainNetworkType;
    private final TransactionState transactionState;
    private final FeeOrigin feeOrigin;
    private final long fee;
    private final long total;

    public CryptoWalletWalletModuleTransaction(final CryptoWalletTransaction cryptoWalletTransaction,
                                               final UUID                     contactId,
                                               final Actor                    involvedActor) {

        this.contactId = contactId;
        this.involvedActor = involvedActor;

        // bitcoin wallet transaction fields
        this.transactionId = cryptoWalletTransaction.getTransactionId();
        this.transactionHash = cryptoWalletTransaction.getTransactionHash();
        this.transactionType = cryptoWalletTransaction.getTransactionType();
        this.addressFrom = cryptoWalletTransaction.getAddressFrom();
        this.addressTo = cryptoWalletTransaction.getAddressTo();
        this.actorFromPublicKey = cryptoWalletTransaction.getActorFromPublicKey();
        this.actorToPublicKey = cryptoWalletTransaction.getActorToPublicKey();
        this.actorFromType = cryptoWalletTransaction.getActorFromType();
        this.actorToType = cryptoWalletTransaction.getActorToType();
        this.balanceType = cryptoWalletTransaction.getBalanceType();
        this.amount = cryptoWalletTransaction.getAmount();
        this.runningBookBalance = cryptoWalletTransaction.getRunningBookBalance();
        this.runningAvailableBalance = cryptoWalletTransaction.getRunningAvailableBalance();
        this.timeStamp = cryptoWalletTransaction.getTimestamp();
        this.memo = cryptoWalletTransaction.getMemo();
        this.blockchainNetworkType = cryptoWalletTransaction.getBlockchainNetworkType();
        this.transactionState = cryptoWalletTransaction.getTransactionState();

       this.feeOrigin = cryptoWalletTransaction.getFeeOrigin();
        this.fee = cryptoWalletTransaction.getFee();
        this.total = cryptoWalletTransaction.getTotal();
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
    public long getTotal() {
        return this.total;
    }

    @Override
    public FeeOrigin getFeeOrigin() {
        return this.feeOrigin;
    }

    @Override
    public long getFee() {
        return this.fee;
    }

    public BlockchainNetworkType getBlockchainNetworkType() {return blockchainNetworkType;}
}
