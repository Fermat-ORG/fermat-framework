package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 07/09/15.
 */
public interface AssetIssuerWallet {

    //TODO:Documentar y manejo de excepciones

    AssetIssuerWalletBalance getBookBalance(BalanceType balanceType) throws CantGetTransactionsException;

    List<AssetWalletIssuerTransaction> getTransactions(BalanceType balanceType,
                                                        int max,
                                                        int offset) throws CantGetTransactionsException;

    List<AssetWalletIssuerTransaction> getTransactionsByActor(String actorPublicKey,
                                                              BalanceType balanceType,
                                                              int max,
                                                              int offset) throws CantGetTransactionsException;

    List<AssetWalletIssuerTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                  TransactionType transactionType,
                                                                                  int max,
                                                                                  int offset) throws CantGetTransactionsException;

    void setTransactionDescription(UUID transactionID,
                                   String description) throws CantFindTransactionException;

    AssetIssuerWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                   BalanceType balanceType) throws CantGetActorTransactionSummaryException;


}
