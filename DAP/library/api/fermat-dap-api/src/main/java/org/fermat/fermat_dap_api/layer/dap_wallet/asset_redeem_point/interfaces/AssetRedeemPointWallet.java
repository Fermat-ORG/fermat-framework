package org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWallet {

    //TODO:Documentar y manejo de excepciones

    AssetRedeemPointWalletBalance getBalance() throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> getTransactions(org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                            org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType,
                                                            int max,
                                                            int offset, String assetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> getTransactions(org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType, org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType, String assetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> getTransactionsForDisplay(String assetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> getTransactionsByActor(String actorPublicKey,
                                                                   org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                                   int max,
                                                                   int offset) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

    List<AssetRedeemPointWalletTransaction> gettLastActorTransactionsByTransactionType(org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType,
                                                                                       org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType transactionType,
                                                                                       int max,
                                                                                       int offset) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;

    void setTransactionDescription(UUID transactionID,
                                   String description) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException, org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;

    AssetRedeemPointWalletTransactionSummary getActorTransactionSummary(String actorPublicKey,
                                                                        org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType balanceType) throws org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;

    org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata getDigitalAssetMetadata(String assetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;

    void newAssetRedeemed(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata digitalAssetMetadata, String userPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantSaveRedeemPointStatisticException;

    List<RedeemPointStatistic> getAllStatistics() throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException, org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

    List<RedeemPointStatistic> getStatisticsByUser(String userPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException, org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

    List<RedeemPointStatistic> getStatisticsByAssetPublicKey(String assetPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException, org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;

    List<RedeemPointStatistic> getStatisticsByAssetAndUser(String assetPublicKey, String userPublicKey) throws org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException, org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
}
