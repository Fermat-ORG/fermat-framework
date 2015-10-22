package com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletTransaction {

    public UUID getTransactionId();

    public String getTransactionHash();

    public CryptoAddress getAddressFrom();

    public CryptoAddress getAddressTo();

    public String getActorToPublicKey();

    public String getActorFromPublicKey();

    public Actors getActorToType();

    public Actors getActorFromType();

    public BalanceType getBalanceType();

    public com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType getTransactionType();

    public long getTimestamp();

    public long getAmount();

    public long getRunningBookBalance();

    public long getRunningAvailableBalance();

    public String getMemo();

}
