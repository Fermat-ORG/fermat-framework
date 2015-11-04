package com.bitdubai.fermat_cbp_api.all_definition.wallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantAddStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetStockCollectionCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantPerformTransactionException;

import java.util.Collection;

/**
 * Created by jorge on 30-09-2015.
 */
public interface Wallet {
    String getWalletPublicKey();
    String getOwnerPublicKey();
    void addStock(FermatEnum stockType) throws CantAddStockCryptoBrokerWalletException;
    Stock getStock(FermatEnum stockType) throws CantGetStockCryptoBrokerWalletException;
    Collection<Stock> getStocks() throws CantGetStockCollectionCryptoBrokerWalletException;
    void performTransaction(WalletTransaction transaction) throws CantPerformTransactionException;
}