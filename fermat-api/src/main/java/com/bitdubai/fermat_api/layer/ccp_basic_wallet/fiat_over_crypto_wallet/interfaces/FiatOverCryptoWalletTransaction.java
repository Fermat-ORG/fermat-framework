package com.bitdubai.fermat_api.layer.ccp_basic_wallet.fiat_over_crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.ccp_basic_wallet.common.enums.TransactionType;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.ccp_basic_wallet.fiat_over_crypto_wallet.interfaces.FiatOverCryptoWalletTransaction</code>
 * provides the information of a transaction to the client of the fiat over crypto basic wallet.
 */
public interface FiatOverCryptoWalletTransaction {

    public UUID getTransactionId();

    public boolean comesFromARequest();

    public UUID getRequestId();

    public String getTransactionHash();

    public CryptoAddress getAddressFrom();

    public CryptoAddress getAddressTo();

    public String getActorToPublicKey();

    public String getActorFromPublicKey();

    public Actors getActorToType();

    public Actors getActorFromType();

    public BalanceType getBalanceType();

    public TransactionType getTransactionType();

    public long getTimestamp();

    public long getCryptoAmount();

    public FiatCurrency getFiatCurremcy();

    public long getFiatAmountAtTheTMomentOfTheTransaction();

    public long getRunningBookBalance();

    public long getRunningAvailableBalance();

    public String getMemo();

}
