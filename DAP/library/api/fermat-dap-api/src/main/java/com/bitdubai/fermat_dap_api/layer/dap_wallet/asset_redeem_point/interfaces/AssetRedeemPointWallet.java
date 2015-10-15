package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWallet {

    //TODO:Documentar y manejo de excepciones

    AssetRedeemPointWalletBalance getBookBalance(BalanceType balanceType) throws CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> getTransactions(BalanceType balanceType,
                                                     TransactionType transactionType,
                                                     int max,
                                                     int offset, String assetPublicKey) throws CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> getTransactionsByActor(String actorPublicKey,
                                                            BalanceType balanceType,
                                                            int max,
                                                            int offset) throws CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                TransactionType transactionType,
                                                                                int max,
                                                                                int offset) throws CantGetTransactionsException;

    void setTransactionDescription(UUID transactionID,
                                   String description) throws CantFindTransactionException, CantStoreMemoException;

    AssetRedeemPointWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                 BalanceType balanceType) throws CantGetActorTransactionSummaryException;


}
