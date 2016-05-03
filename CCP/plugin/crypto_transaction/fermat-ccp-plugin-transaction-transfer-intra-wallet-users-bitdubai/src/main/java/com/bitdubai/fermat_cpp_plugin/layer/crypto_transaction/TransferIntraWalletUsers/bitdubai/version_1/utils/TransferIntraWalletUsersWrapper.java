package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.interfaces.TransferIntraWalletUsersRecord;
import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.enums.TransactionState;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public class TransferIntraWalletUsersWrapper implements TransferIntraWalletUsersRecord {



    private UUID TransactionId;
    private String TransactionHash;
    private long cryptoAmount;
    private TransactionState TransactionState;
    private String Memo;
    private long Timestamp;
    private Actors ActoType;
    private ReferenceWallet ReferenceWalletSending;
    private ReferenceWallet ReferenceWalletReceiving;
    private String WalletSendingPlublicKey;
    private String WalletReceivingPublicKey;
    private BlockchainNetworkType BlockchainNetworkType;


    public TransferIntraWalletUsersWrapper(UUID transactionId, String transactionHash, long cryptoAmount, com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.enums.TransactionState transactionState, String memo, long timestamp, Actors actoType, ReferenceWallet referenceWalletSending, ReferenceWallet referenceWalletReceiving, String walletSendingPlublicKey, String walletReceivingPublicKey, com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType blockchainNetworkType) {
        this.TransactionId = transactionId;
        this.TransactionHash = transactionHash;
        this.cryptoAmount = cryptoAmount;
        this.TransactionState = transactionState;
        this.Memo = memo;
        this.Timestamp = timestamp;
        this.ActoType = actoType;
        this.ReferenceWalletSending = referenceWalletSending;
        this.ReferenceWalletReceiving = referenceWalletReceiving;
        this.WalletSendingPlublicKey = walletSendingPlublicKey;
        this.WalletReceivingPublicKey = walletReceivingPublicKey;
        this.BlockchainNetworkType = blockchainNetworkType;
    }

    @Override
    public UUID getTransactionId() {
        return TransactionId;
    }

    @Override
    public String getTransactionHash() {
        return TransactionHash;
    }

    @Override
    public long cryptoAmount() {
        return cryptoAmount;
    }

    @Override
    public TransactionState getTransactionState() {
        return TransactionState;
    }

    @Override
    public String getMemo() {
        return Memo;
    }

    @Override
    public long getTimestamp() {
        return Timestamp;
    }

    @Override
    public Actors getActoType() {
        return ActoType;
    }

    @Override
    public ReferenceWallet getReferenceWalletSending() {
        return ReferenceWalletSending;
    }

    @Override
    public ReferenceWallet getReferenceWalletReceiving() {
        return ReferenceWalletReceiving;
    }

    @Override
    public String getWalletSendingPlublicKey() {
        return WalletSendingPlublicKey;
    }

    @Override
    public String getWalletReceivingPublicKey() {
        return WalletReceivingPublicKey;
    }

    @Override
    public BlockchainNetworkType getBlockchainNetworkType() {
        return BlockchainNetworkType;
    }
}
