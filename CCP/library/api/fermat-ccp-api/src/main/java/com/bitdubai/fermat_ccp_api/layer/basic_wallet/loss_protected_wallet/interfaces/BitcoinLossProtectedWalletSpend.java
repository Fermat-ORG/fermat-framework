package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by natalia on 14/03/16.
 */
public interface BitcoinLossProtectedWalletSpend  extends Serializable{

    UUID getSpendId();

    UUID getTransactionId();


    long getTimestamp();

    long getAmount();

    double getExchangeRate();

    BlockchainNetworkType getBlockchainNetworkType();

}


