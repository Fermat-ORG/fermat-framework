package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.wallet.Stock;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockTransaction;
import com.bitdubai.fermat_cbp_api.layer.cbp_identity.crypto_broker.interfaces.CryptoBrokerIdentity;

/**
 * Created by jorge on 29-09-2015.
 */
public interface CryptoBrokerWallet {

    CryptoBrokerIdentity getCryptoBroker();

    String getPublicKeyWallet();

    void replenishMerchandiseStock(StockTransaction transaction);
    void replenishCapitalStock(StockTransaction transaction);

    void purchaseMerchandise(StockTransaction transaction);
    void saleMerchandise(StockTransaction transaction);

    double getMerchandiseBalance();
    double getCapitalBalance();

}
