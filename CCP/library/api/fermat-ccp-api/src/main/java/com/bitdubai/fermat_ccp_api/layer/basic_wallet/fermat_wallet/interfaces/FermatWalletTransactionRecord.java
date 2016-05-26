package com.bitdubai.fermat_ccp_api.layer.basic_wallet.fermat_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * Joaquin Carrasquero on 17/03/16.
 */
public interface FermatWalletTransactionRecord {

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

    CryptoCurrency getCryptoCurrency();



}
