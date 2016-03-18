package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 *  Created by Joaquin Carrasquero on 17/03/16.
 */
public interface BitcoinLossProtectedWalletTransactionRecord {

    CryptoAddress getAddressFrom();

    UUID getTransactionId();

    UUID getRequestId();

    CryptoAddress getAddressTo();

    long getAmount();

    long getTimestamp();

    String getMemo();

    String getTransactionHash();

    String getActorToPublicKey();

    String getActorFromPublicKey();

    Actors getActorToType();

    Actors getActorFromType();

    BlockchainNetworkType getBlockchainNetworkType();



}
