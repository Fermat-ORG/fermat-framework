package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinLossProtectedWalletTransaction {

    UUID getTransactionId();

    String getTransactionHash();

    CryptoAddress getAddressFrom();

    CryptoAddress getAddressTo();

    String getActorToPublicKey();

    String getActorFromPublicKey();

    Actors getActorToType();

    Actors getActorFromType();

    BalanceType getBalanceType();

    com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType getTransactionType();

    long getTimestamp();

    long getAmount();

    long getRunningBookBalance();

    long getRunningAvailableBalance();

    String getMemo();

    TransactionState getTransactionState();

    BlockchainNetworkType getBlockchainNetworkType();

    double getExchangeRate();

}
