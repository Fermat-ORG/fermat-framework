package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 * updated by Andres Abreu aabreu1 2016.08.01..
 */
public interface BitcoinLossProtectedWalletTransactionRecord {

    CryptoAddress getAddressFrom();

    UUID getTransactionId();

    UUID getRequestId();

    CryptoAddress getAddressTo();

    long getAmount();

    long getTotal();

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

    FeeOrigin getFeeOrigin();

    long getFee() ;


}
