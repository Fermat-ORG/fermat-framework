package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;

import com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.enums.TransactionState;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public interface TransferIntraWalletUsersRecord {


    UUID getTransactionId();

    String getTransactionHash();

    long cryptoAmount();

    TransactionState getTransactionState();

    String getMemo();

    long getTimestamp();

    Actors getActoType();

    ReferenceWallet getReferenceWalletSending();

    ReferenceWallet getReferenceWalletReceiving();

    String getWalletSendingPlublicKey();

    String getWalletReceivingPublicKey();

    BlockchainNetworkType getBlockchainNetworkType();
}
