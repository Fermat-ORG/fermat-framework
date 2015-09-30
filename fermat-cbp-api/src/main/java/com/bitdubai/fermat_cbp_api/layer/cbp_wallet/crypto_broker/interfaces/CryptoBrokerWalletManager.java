package com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.BusinessTransactionType;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGetTransactionCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet.crypto_broker.exceptions.CantGenerateBalanceCryptoBrokerWalletException;

import java.util.List;

/**
 * Created by Yordin Alayn on 29.09.15.
 */
public interface CryptoBrokerWalletManager {

    List<CryptoBrokerWallet> getTransactions(BusinessTransactionType transactionType) throws CantGetTransactionCryptoBrokerWalletException;

    void cryptoBrokerWalletBalance() throws CantGenerateBalanceCryptoBrokerWalletException;

    Double TotalMerchandiseCurrency() throws CantGenerateBalanceCryptoBrokerWalletException;

    Double TotalInvestedMerchandiseCurrency() throws CantGenerateBalanceCryptoBrokerWalletException;

    Double PriceReferenceCurrencyCurrent() throws CantGenerateBalanceCryptoBrokerWalletException;

    Double PriceMarketMerchandiseCurrencyCurrent() throws CantGenerateBalanceCryptoBrokerWalletException;

}
