package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletTransaction {

    public String getTramsactionHash();

    public CryptoAddress getAddressFrom();

    public CryptoAddress getAddressTo();

    public UUID getActorTo();

    public UUID getActorFrom();

    public Actors getActorToType();

    public Actors getActorFromType();

    public long getRunningBookBalance();

    public long getRunningAvailableBalance();

    public BalanceType getBalanceType();

    public TransactionType getType();

    public long getTimestamp();

    public String getMemo();

}
