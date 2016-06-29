package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
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

    long getExchangRate();

    CryptoCurrency getCryptoCurrency();



}
