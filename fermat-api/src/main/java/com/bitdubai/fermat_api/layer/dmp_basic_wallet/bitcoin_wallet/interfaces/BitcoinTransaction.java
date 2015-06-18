package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionState;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums.TransactionType;

/**
 * Created by eze on 2015.06.17..
 */
public class BitcoinTransaction {

    private CryptoAddress addressFrom;

    private CryptoAddress addressTo;

    private long amount;

    private TransactionType type;

    private TransactionState state;

    private long timestamp;

    private String memo;

}
