package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.enums.TransactionState;

import java.util.UUID;

/**
 * Created by Joaquin Carrasquero on 18/03/16.
 */
public interface OutgoingDeviceUserTransactionRecord {


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
