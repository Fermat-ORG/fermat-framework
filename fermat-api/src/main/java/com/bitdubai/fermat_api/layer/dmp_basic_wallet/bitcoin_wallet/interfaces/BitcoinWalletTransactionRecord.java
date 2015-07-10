package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletTransactionRecord {


    public CryptoAddress getAddressFrom();

    public void setAddressFrom(CryptoAddress addressFrom);

    public UUID getIdTransaction();

    public void setIdTransaction(UUID id);

    public CryptoAddress getAddressTo();

    public void setAddressTo(CryptoAddress addressTo);

    public long getAmount();

    public void setAmount(long amount);

    public BalanceType getBalanceType();

    public void setBalanceType(BalanceType type);

    public TransactionType getType();

    public void setType(TransactionType type);
    
    public long getTimestamp();

    public void setTimestamp(long timestamp);

    public String getMemo();

    public void setMemo(String memo);

    public String getTramsactionHash();

    public void setTramsactionHash(String tramsactionHash);
}
